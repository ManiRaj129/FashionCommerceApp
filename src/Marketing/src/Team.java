package src.Marketing.src;

public enum Team {
    EDITING,
    SOCIALMEDIA,
    VIDEO,
    DISTRIBUTION;

    public String toString() {
        return switch (this) {
            case EDITING -> "Editing";
            case SOCIALMEDIA -> "Social-Media";
            case VIDEO -> "Video";
            case DISTRIBUTION -> "Distribution";
        };
    }

    public static Team parseTeam(String team) {
        return switch (team) {
            case "Editing" -> EDITING;
            case "Social-Media" -> SOCIALMEDIA;
            case "Video" -> VIDEO;
            case "Distribution" -> DISTRIBUTION;
            default -> throw new IllegalArgumentException();
        };
    }
}
