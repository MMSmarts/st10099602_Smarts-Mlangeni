package wildlifesa;

import java.util.Scanner;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final RescueSystem SYSTEM = new RescueSystem();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readInt("Select an option: ", 1, 6);
            switch (choice) {
                case 1 -> createRescueCase();
                case 2 -> searchRescueCase();
                case 3 -> updateRescueStatus();
                case 4 -> System.out.println(SYSTEM.displayAllRescueCases());
                case 5 -> rescueOperationsMenu();
                case 6 -> running = false;
                default -> throw new IllegalStateException("Unexpected menu option.");
            }
        }
        System.out.println("Thank you for using the WildLife SA Rescue System.");
    }

    private static void displayMenu() {
        System.out.println("\n========= WILDLIFE SA RESCUE SYSTEM =========");
        System.out.println("1. Create a new rescue case");
        System.out.println("2. Search for a rescue case");
        System.out.println("3. Update rescue status");
        System.out.println("4. Display all rescue cases");
        System.out.println("5. Rescue operations and reports");
        System.out.println("6. Exit");
    }

    private static void createRescueCase() {
        System.out.println("\n1. Injured Animal  2. Orphaned Animal  3. Endangered Species");
        int type = readInt("Select rescue type: ", 1, 3);
        String id;
        do {
            id = readRequiredText("Rescue Case ID: ");
            if (SYSTEM.searchRescueCase(id) != null) {
                System.out.println("That Rescue Case ID already exists. Enter a unique ID.");
                id = null;
            }
        } while (id == null);

        String animalName = readRequiredText("Animal name: ");
        String species = readRequiredText("Species: ");
        String location = readRequiredText("Rescue location: ");
        String ranger = readRequiredText("Assigned ranger: ");
        int days = readPositiveInt("Number of rescue days: ");
        double dailyCost = readPositiveDouble("Daily care cost: R");
        RescueCase rescueCase;

        if (type == 1) {
            String injury = readRequiredText("Injury description: ");
            double treatmentCost = readPositiveDouble("Veterinary treatment cost: R");
            boolean surgery = readYesNo("Is surgery required? (Y/N): ");
            rescueCase = new InjuredAnimalRescue(id, animalName, species, location,
                    ranger, days, dailyCost, RescueStatus.RECORDED, injury, treatmentCost, surgery);
        } else if (type == 2) {
            int age = readPositiveInt("Estimated age in months: ");
            double feedingCost = readPositiveDouble("Feeding cost: R");
            boolean foster = readYesNo("Is foster care required? (Y/N): ");
            rescueCase = new OrphanedAnimalRescue(id, animalName, species, location,
                    ranger, days, dailyCost, RescueStatus.RECORDED, age, feedingCost, foster);
        } else {
            String classification = readRequiredText("Conservation classification: ");
            double securityCost = readPositiveDouble("Security cost: R");
            boolean specialist = readYesNo("Is a specialist team required? (Y/N): ");
            rescueCase = new EndangeredSpeciesRescue(id, animalName, species, location,
                    ranger, days, dailyCost, RescueStatus.RECORDED, classification, securityCost, specialist);
        }
        SYSTEM.addRescueCase(rescueCase);
        System.out.println("Rescue case created successfully.");
    }

    private static void searchRescueCase() {
        RescueCase rescueCase = findFromInput();
        if (rescueCase != null) System.out.println(rescueCase.displayDetails());
    }

    private static void updateRescueStatus() {
        String id = readRequiredText("Enter Rescue Case ID: ");
        if (SYSTEM.searchRescueCase(id) == null) {
            System.out.println("Rescue case not found.");
            return;
        }
        System.out.println("1. RECORDED  2. IN PROGRESS  3. COMPLETED");
        int statusChoice = readInt("Select the new status: ", 1, 3);
        RescueStatus status = switch (statusChoice) {
            case 1 -> RescueStatus.RECORDED;
            case 2 -> RescueStatus.IN_PROGRESS;
            case 3 -> RescueStatus.COMPLETED;
            default -> throw new IllegalStateException("Unexpected status selection.");
        };
        SYSTEM.updateRescueStatus(id, status);
        System.out.println("Rescue status updated successfully.");
    }

    private static void changeOperationStatus(boolean start) {
        String id = readRequiredText("Enter Rescue Case ID: ");
        boolean changed = start ? SYSTEM.startRescue(id) : SYSTEM.completeRescue(id);
        System.out.println(changed ? "Rescue operation status updated successfully." : "Rescue case not found.");
    }

    private static void displaySummary() {
        RescueCase rescueCase = findFromInput();
        if (rescueCase != null) System.out.println(rescueCase.generateRescueSummary());
    }

    private static void rescueOperationsMenu() {
        boolean returnToMainMenu = false;
        while (!returnToMainMenu) {
            System.out.println("\n========== RESCUE OPERATIONS ==========");
            System.out.println("1. Start a rescue operation");
            System.out.println("2. Complete a rescue operation");
            System.out.println("3. Generate a rescue summary");
            System.out.println("4. Generate rescue report");
            System.out.println("5. Return to main menu");
            int choice = readInt("Select an option: ", 1, 5);
            switch (choice) {
                case 1 -> changeOperationStatus(true);
                case 2 -> changeOperationStatus(false);
                case 3 -> displaySummary();
                case 4 -> System.out.println(SYSTEM.generateRescueReport());
                case 5 -> returnToMainMenu = true;
                default -> throw new IllegalStateException("Unexpected operations option.");
            }
        }
    }

    private static RescueCase findFromInput() {
        String id = readRequiredText("Enter Rescue Case ID: ");
        RescueCase rescueCase = SYSTEM.searchRescueCase(id);
        if (rescueCase == null) System.out.println("Rescue case not found.");
        return rescueCase;
    }

    private static String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = INPUT.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This value may not be blank.");
        }
    }

    private static int readInt(String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(INPUT.nextLine().trim());
                if (value >= minimum && value <= maximum) return value;
            } catch (NumberFormatException ignored) { }
            System.out.printf("Enter a whole number from %d to %d.%n", minimum, maximum);
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(INPUT.nextLine().trim());
                if (value > 0) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a whole number greater than zero.");
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(INPUT.nextLine().trim());
                if (value > 0) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a numeric value greater than zero.");
        }
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = INPUT.nextLine().trim();
            if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("YES")) return true;
            if (value.equalsIgnoreCase("N") || value.equalsIgnoreCase("NO")) return false;
            System.out.println("Enter Y for Yes or N for No.");
        }
    }
}
