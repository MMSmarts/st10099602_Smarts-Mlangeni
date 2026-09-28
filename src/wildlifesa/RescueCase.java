package wildlifesa;

public abstract class RescueCase implements RescueOperations {
    private final String rescueCaseId;
    private String animalName;
    private String species;
    private String rescueLocation;
    private String assignedRanger;
    private int numberOfRescueDays;
    private double dailyCareCost;
    private RescueStatus currentRescueStatus;

    protected RescueCase(String rescueCaseId, String animalName, String species,
            String rescueLocation, String assignedRanger, int numberOfRescueDays,
            double dailyCareCost, RescueStatus currentRescueStatus) {
        this.rescueCaseId = requireText(rescueCaseId, "Rescue Case ID");
        this.animalName = requireText(animalName, "Animal name");
        this.species = requireText(species, "Species");
        this.rescueLocation = requireText(rescueLocation, "Rescue location");
        this.assignedRanger = requireText(assignedRanger, "Assigned ranger");
        setNumberOfRescueDays(numberOfRescueDays);
        setDailyCareCost(dailyCareCost);
        setCurrentRescueStatus(currentRescueStatus);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " may not be blank.");
        }
        return value.trim();
    }

    protected double calculateBasicCareCost() {
        return numberOfRescueDays * dailyCareCost;
    }

    public abstract double calculateTotalRescueCost();
    public abstract String determineRescuePriority();
    public abstract String getRescueType();
    protected abstract String getSpecificInformation();

    @Override
    public void startRescueOperation() {
        currentRescueStatus = RescueStatus.IN_PROGRESS;
    }

    @Override
    public void completeRescueOperation() {
        currentRescueStatus = RescueStatus.COMPLETED;
    }

    @Override
    public String generateRescueSummary() {
        return String.format(
                "Rescue Case ID: %s%nRescue Type: %s%nSpecies: %s%nAssigned Ranger: %s%n"
                + "Rescue Priority: %s%nCurrent Status: %s%nTotal Rescue Cost: R%.2f",
                rescueCaseId, getRescueType(), species, assignedRanger,
                determineRescuePriority(), currentRescueStatus, calculateTotalRescueCost());
    }

    public String displayDetails() {
        return String.format(
                "%s%nAnimal Name: %s%nRescue Location: %s%nRescue Days: %d%n"
                + "Daily Care Cost: R%.2f%n%s",
                generateRescueSummary(), animalName, rescueLocation,
                numberOfRescueDays, dailyCareCost, getSpecificInformation());
    }

    public String getRescueCaseId() { return rescueCaseId; }
    public String getAnimalName() { return animalName; }
    public String getSpecies() { return species; }
    public String getRescueLocation() { return rescueLocation; }
    public String getAssignedRanger() { return assignedRanger; }
    public int getNumberOfRescueDays() { return numberOfRescueDays; }
    public double getDailyCareCost() { return dailyCareCost; }
    public RescueStatus getCurrentRescueStatus() { return currentRescueStatus; }

    public void setAnimalName(String animalName) {
        this.animalName = requireText(animalName, "Animal name");
    }

    public void setSpecies(String species) {
        this.species = requireText(species, "Species");
    }

    public void setRescueLocation(String rescueLocation) {
        this.rescueLocation = requireText(rescueLocation, "Rescue location");
    }

    public void setAssignedRanger(String assignedRanger) {
        this.assignedRanger = requireText(assignedRanger, "Assigned ranger");
    }

    public void setNumberOfRescueDays(int numberOfRescueDays) {
        if (numberOfRescueDays <= 0) {
            throw new IllegalArgumentException("Number of rescue days must be greater than zero.");
        }
        this.numberOfRescueDays = numberOfRescueDays;
    }

    public void setDailyCareCost(double dailyCareCost) {
        if (dailyCareCost <= 0) {
            throw new IllegalArgumentException("Daily care cost must be greater than zero.");
        }
        this.dailyCareCost = dailyCareCost;
    }

    public void setCurrentRescueStatus(RescueStatus currentRescueStatus) {
        if (currentRescueStatus == null) {
            throw new IllegalArgumentException("Rescue status may not be null.");
        }
        this.currentRescueStatus = currentRescueStatus;
    }
}
