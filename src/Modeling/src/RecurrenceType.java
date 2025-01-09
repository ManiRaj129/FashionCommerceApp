package src.Modeling.src;

public enum RecurrenceType {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
    NONE;

    public String toString() {
        return switch (this) {
            case DAILY -> "DAILY";
            case WEEKLY -> "WEEKLY";
            case MONTHLY -> "MONTHLY";
            case YEARLY -> "YEARLY";
            case NONE -> "NONE";
        };
    }

    public String getRecurrence() {
        return switch (this) {
            case DAILY -> "Daily at 9am";
            case WEEKLY -> "Weekly on Mondays at 9am";
            case MONTHLY -> "First of the Month at 9am";
            case YEARLY -> "January 1st at 9am";
            case NONE -> "Never";
        };
    }

    public static RecurrenceType parseType(String type) {
        return switch (type) {
            case "DAILY", "daily", "Daily", "d", "D" -> DAILY;
            case "WEEKLY", "weekly", "Weekly", "w", "W" -> WEEKLY;
            case "MONTHLY", "monthly", "Monthly", "m", "M" -> MONTHLY;
            case "YEARLY", "yearly", "Yearly", "y", "Y" -> YEARLY;
            case "NONE", "none", "None", "n", "N" -> NONE;
            default -> throw new IllegalArgumentException();
        };
    }
}
