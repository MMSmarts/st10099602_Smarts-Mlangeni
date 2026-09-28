package wildlifesa;

public class InjuredAnimalRescue extends RescueCase {
    private String injuryDescription;
    private double veterinaryTreatmentCost;
    private boolean surgeryRequired;

    public InjuredAnimalRescue(String rescueCaseId, String animalName, String species,
            String rescueLocation, String assignedRanger, int numberOfRescueDays,
            double dailyCareCost, RescueStatus currentRescueStatus, String injuryDescription,
            double veterinaryTreatmentCost, boolean surgeryRequired) {
        super(rescueCaseId, animalName, species, rescueLocation, assignedRanger,
                numberOfRescueDays, dailyCareCost, currentRescueStatus);
        setInjuryDescription(injuryDescription);
        setVeterinaryTreatmentCost(veterinaryTreatmentCost);
        this.surgeryRequired = surgeryRequired;
    }

    @Override
    public double calculateTotalRescueCost() {
        return calculateBasicCareCost() + veterinaryTreatmentCost + (surgeryRequired ? 5000 : 0);
    }

    @Override
    public String determineRescuePriority() {
        // Surgery makes an injury time-critical; other injuries remain high priority.
        return surgeryRequired ? "CRITICAL" : "HIGH";
    }

    @Override
    public String getRescueType() { return "Injured Animal Rescue"; }

    @Override
    protected String getSpecificInformation() {
        return String.format("Injury Description: %s%nVeterinary Treatment Cost: R%.2f%nSurgery Required: %s",
                injuryDescription, veterinaryTreatmentCost, surgeryRequired ? "Yes" : "No");
    }

    public String getInjuryDescription() { return injuryDescription; }
    public double getVeterinaryTreatmentCost() { return veterinaryTreatmentCost; }
    public boolean isSurgeryRequired() { return surgeryRequired; }

    public void setInjuryDescription(String injuryDescription) {
        if (injuryDescription == null || injuryDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Injury description may not be blank.");
        }
        this.injuryDescription = injuryDescription.trim();
    }

    public void setVeterinaryTreatmentCost(double veterinaryTreatmentCost) {
        if (veterinaryTreatmentCost <= 0) {
            throw new IllegalArgumentException("Veterinary treatment cost must be greater than zero.");
        }
        this.veterinaryTreatmentCost = veterinaryTreatmentCost;
    }

    public void setSurgeryRequired(boolean surgeryRequired) { this.surgeryRequired = surgeryRequired; }
}
