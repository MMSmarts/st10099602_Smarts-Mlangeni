package wildlifesa;

public class EndangeredSpeciesRescue extends RescueCase {
    private String conservationClassification;
    private double securityCost;
    private boolean specialistTeamRequired;

    public EndangeredSpeciesRescue(String rescueCaseId, String animalName, String species,
            String rescueLocation, String assignedRanger, int numberOfRescueDays,
            double dailyCareCost, RescueStatus currentRescueStatus,
            String conservationClassification, double securityCost,
            boolean specialistTeamRequired) {
        super(rescueCaseId, animalName, species, rescueLocation, assignedRanger,
                numberOfRescueDays, dailyCareCost, currentRescueStatus);
        setConservationClassification(conservationClassification);
        setSecurityCost(securityCost);
        this.specialistTeamRequired = specialistTeamRequired;
    }

    @Override
    public double calculateTotalRescueCost() {
        return calculateBasicCareCost() + securityCost + (specialistTeamRequired ? 8000 : 0);
    }

    @Override
    public String determineRescuePriority() {
        // Critically endangered animals or specialist-team cases receive critical priority.
        return specialistTeamRequired || conservationClassification.equalsIgnoreCase("Critically Endangered")
                ? "CRITICAL" : "HIGH";
    }

    @Override
    public String getRescueType() { return "Endangered Species Rescue"; }

    @Override
    protected String getSpecificInformation() {
        return String.format("Conservation Classification: %s%nSecurity Cost: R%.2f%nSpecialist Team Required: %s",
                conservationClassification, securityCost, specialistTeamRequired ? "Yes" : "No");
    }

    public String getConservationClassification() { return conservationClassification; }
    public double getSecurityCost() { return securityCost; }
    public boolean isSpecialistTeamRequired() { return specialistTeamRequired; }

    public void setConservationClassification(String conservationClassification) {
        if (conservationClassification == null || conservationClassification.trim().isEmpty()) {
            throw new IllegalArgumentException("Conservation classification may not be blank.");
        }
        this.conservationClassification = conservationClassification.trim();
    }

    public void setSecurityCost(double securityCost) {
        if (securityCost <= 0) {
            throw new IllegalArgumentException("Security cost must be greater than zero.");
        }
        this.securityCost = securityCost;
    }

    public void setSpecialistTeamRequired(boolean specialistTeamRequired) {
        this.specialistTeamRequired = specialistTeamRequired;
    }
}
