package wildlifesa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RescueSystem {
    private final ArrayList<RescueCase> rescueCases = new ArrayList<>();

    public boolean addRescueCase(RescueCase rescueCase) {
        if (rescueCase == null || searchRescueCase(rescueCase.getRescueCaseId()) != null) {
            return false;
        }
        rescueCases.add(rescueCase);
        return true;
    }

    public RescueCase searchRescueCase(String rescueCaseId) {
        if (rescueCaseId == null) return null;
        for (RescueCase rescueCase : rescueCases) {
            if (rescueCase.getRescueCaseId().equalsIgnoreCase(rescueCaseId.trim())) {
                return rescueCase;
            }
        }
        return null;
    }

    public boolean updateRescueStatus(String rescueCaseId, RescueStatus newStatus) {
        RescueCase rescueCase = searchRescueCase(rescueCaseId);
        if (rescueCase == null || newStatus == null) return false;
        rescueCase.setCurrentRescueStatus(newStatus);
        return true;
    }

    public boolean startRescue(String rescueCaseId) {
        RescueCase rescueCase = searchRescueCase(rescueCaseId);
        if (rescueCase == null) return false;
        rescueCase.startRescueOperation();
        return true;
    }

    public boolean completeRescue(String rescueCaseId) {
        RescueCase rescueCase = searchRescueCase(rescueCaseId);
        if (rescueCase == null) return false;
        rescueCase.completeRescueOperation();
        return true;
    }

    public List<RescueCase> getAllRescueCases() {
        return Collections.unmodifiableList(rescueCases);
    }

    public String displayAllRescueCases() {
        if (rescueCases.isEmpty()) return "No rescue cases have been recorded.";
        StringBuilder output = new StringBuilder();
        for (RescueCase rescueCase : rescueCases) {
            output.append("----------------------------------------\n")
                    .append(rescueCase.displayDetails()).append('\n');
        }
        return output.toString();
    }

    public String generateRescueReport() {
        StringBuilder report = new StringBuilder("========== WILDLIFE RESCUE REPORT ==========\n");
        String format = "%-10s %-27s %-15s %-20s %-18s %-10s %-12s %14s";
        String header = String.format(format, "CASE ID", "RESCUE TYPE", "SPECIES",
                "LOCATION", "RANGER", "PRIORITY", "STATUS", "TOTAL COST");
        String separator = "-".repeat(header.length());
        report.append(header).append(System.lineSeparator());
        report.append(separator).append(System.lineSeparator());
        double totalCost = 0;
        for (RescueCase rescueCase : rescueCases) {
            report.append(String.format(format,
                    rescueCase.getRescueCaseId(), rescueCase.getRescueType(),
                    rescueCase.getSpecies(), rescueCase.getRescueLocation(),
                    rescueCase.getAssignedRanger(), rescueCase.determineRescuePriority(),
                    rescueCase.getCurrentRescueStatus(),
                    String.format("R%.2f", rescueCase.calculateTotalRescueCost())))
                    .append(System.lineSeparator());
            totalCost += rescueCase.calculateTotalRescueCost();
        }
        report.append(separator).append(System.lineSeparator())
                .append("Total number of rescue cases: ").append(rescueCases.size()).append('\n')
                .append(String.format("Total estimated rescue cost: R%.2f%n", totalCost));
        return report.toString();
    }
}
