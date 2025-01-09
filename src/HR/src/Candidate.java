package src.HR.src;

import src.HR.src.interfaces.ICandidate;

/**
 * @author Sam Gumm
 */

public class Candidate implements ICandidate {
    String candidateId;
    String name;
    String positionApplied;
    candidateStatus status;

    /**
     * @param candidateId the Candidate's identifying number
     * @param name the Candidates name
     * @param positionApplied the position the Candidate has applied to
     * @param status  the Status of the Candidate in the Hiring process
     */
    public Candidate(String candidateId, String name, String positionApplied, candidateStatus status) {
        this.candidateId = candidateId;
        this.name = name;
        this.positionApplied = positionApplied;
        this.status = status;
    }

    /**
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @return candidateId
     */
    @Override
    public String getCandidateId() {
        return candidateId;
    }

    /**
     *
     * @return positionApplied
     */
    @Override
    public String getPositionApplied() {
        return positionApplied;
    }

    /**
     * @return status
     */
    @Override
    public candidateStatus getStatus() {
        return status;
    }

    /**
     *
     * @param status the Status can be 5 things:
     *               Applied --> they have submitted an application, but not had an interview
     *               Pending --> they are waiting on an interview
     *               Approved --> they have had an interview and are approved for next stage
     *               Rejected --> they have been ultimately rejected at any stage
     *               Hired --> they are set to be turned into employees
     */
    @Override
    public void setStatus(String status) {

        if(status == null) {
            throw new NullPointerException("Status cannot be null");
        }

        //TODO: use enumeration here (maybe?)
        else if (status.equalsIgnoreCase("APPLIED") || status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("Hired")) {
            this.status = candidateStatus.valueOf(status);
        }
        else {
            throw new IllegalArgumentException("Status must be: Applied, Pending, Approved, Rejected, Hired");
        }
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Position Applied: %s, Status: %s", 
                candidateId, name, positionApplied, status);
    }
}