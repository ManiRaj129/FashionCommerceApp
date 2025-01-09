package src.HR.src.interfaces;

import src.HR.src.candidateStatus;

public interface ICandidate {
    String getName();
    String getCandidateId();
    String getPositionApplied();
    candidateStatus getStatus();
    void setStatus(String status);

    @Override
    String toString();
}
