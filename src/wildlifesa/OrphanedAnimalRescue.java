package wildlifesa;

public class OrphanedAnimalRescue extends RescueCase {
    private int estimatedAgeMonths;
    private double feedingCost;
    private boolean fosterCareRequired;

    public OrphanedAnimalRescue(String rescueCaseId, String animalName, String species,
            String rescueLocation, String assignedRanger, int numberOfRescueDays,
            double dailyCareCost, RescueStatus currentRescueStatus, int estimatedAgeMonths,
            double feedingCost, boolean fosterCareRequired) {
        super(rescueCaseId, animalName, species, rescueLocation, assignedRanger,
                numberOfRescueDays, dailyCareCost, currentRescueStatus);
        setEstimatedAgeMonths(estimatedAgeMonths);
        setFeedingCost(feedingCost);
        this.fosterCareRequired = fosterCareRequired;
    }

    @Override
    public double calculateTotalRescueCost() {
        return calculateBasicCareCost() + feedingCost + (fosterCareRequired ? 2500 : 0);
    }

    @Override
    public String determineRescuePriority() {
        // Very young animals and animals needing foster care require urgent attention.
        return estimatedAgeMonths <= 6 || fosterCareRequired ? "HIGH" : "MEDIUM";
    }

    @Override
    public String getRescueType() { return "Orphaned Animal Rescue"; }

    @Override
    protected String getSpecificInformation() {
        return String.format("Estimated Age: %d months%nFeeding Cost: R%.2f%nFoster Care Required: %s",
                estimatedAgeMonths, feedingCost, fosterCareRequired ? "Yes" : "No");
    }

    public int getEstimatedAgeMonths() { return estimatedAgeMonths; }
    public double getFeedingCost() { return feedingCost; }
    public boolean isFosterCareRequired() { return fosterCareRequired; }

    public void setEstimatedAgeMonths(int estimatedAgeMonths) {
        if (estimatedAgeMonths <= 0) {
            throw new IllegalArgumentException("Estimated age must be greater than zero.");
        }
        this.estimatedAgeMonths = estimatedAgeMonths;
    }

    public void setFeedingCost(double feedingCost) {
        if (feedingCost <= 0) {
            throw new IllegalArgumentException("Feeding cost must be greater than zero.");
        }
        this.feedingCost = feedingCost;
    }

    public void setFosterCareRequired(boolean fosterCareRequired) { this.fosterCareRequired = fosterCareRequired; }
}
