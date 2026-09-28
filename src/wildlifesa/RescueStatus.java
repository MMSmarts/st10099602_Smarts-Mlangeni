package wildlifesa;

public enum RescueStatus {
    RECORDED("RECORDED"),
    IN_PROGRESS("IN PROGRESS"),
    COMPLETED("COMPLETED");

    private final String displayName;

    RescueStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
