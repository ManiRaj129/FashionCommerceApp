package src.Marketing.src;

public enum AdvertType {
    BILLBOARD,
    MAGAZINE,
    SOCIALMEDIA,
    COMMERCIAL;

    public String toString() {
        return switch (this) {
            case BILLBOARD -> "Billboard";
            case MAGAZINE -> "Magazine";
            case SOCIALMEDIA -> "Social-Media";
            case COMMERCIAL -> "Commercial";
        };
    }

    public static AdvertType parseType(String team) {
        return switch (team) {
            case "Billboard" -> BILLBOARD;
            case "Magazine" -> MAGAZINE;
            case "Social-Media" -> SOCIALMEDIA;
            case "Commercial" -> COMMERCIAL;
            default -> throw new IllegalArgumentException();
        };
    }
}
