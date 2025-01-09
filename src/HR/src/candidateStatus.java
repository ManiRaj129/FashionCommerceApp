package src.HR.src;

public enum candidateStatus {
    APPLIED,
    APPROVED,
    HIRING,
    PENDING,
    REJECTED;

    @Override
    public String toString() {
        return switch (this) {
            case APPLIED -> "Applied";
            case APPROVED -> "Approved";
            case HIRING -> "Hiring";
            case PENDING -> "Pending";
            case REJECTED -> "Rejected";
        };
    }
}
