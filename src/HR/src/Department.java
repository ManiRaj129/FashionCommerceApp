package src.HR.src;

/**
 * @author Sam Gumm
 */
public enum Department {
    ENGINEERING,
    MARKETING,
    HUMAN_RESOURCES,
    FINANCE,
    DESIGN,
    MODELING,
    MANUFACTURING;

    @Override
    public String toString() {
        return switch (this) {
            case ENGINEERING -> "Engineering";
            case MARKETING -> "Marketing";
            case HUMAN_RESOURCES -> "Human_Resources";
            case FINANCE -> "Finance";
            case DESIGN -> "Design";
            case MODELING -> "Modeling";
            case MANUFACTURING -> "Manufacturing";
        };
    }
}
