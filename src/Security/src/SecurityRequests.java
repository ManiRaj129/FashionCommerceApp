/**
 * @author Kenny
 */

package src.Security.src;

public class SecurityRequests {

    private String requestID, priorityLevel, department, location,
            description, duration, tasks, specialReqs, dateIssued,
            resolved, fileName;

    public SecurityRequests(){}

    public SecurityRequests (String requestID, String priorityLevel, String department,
                             String location, String description, String duration,
                             String tasks, String specialReqs, String dateIssued,
                             String resolved){
        this.requestID = requestID;
        this.priorityLevel = priorityLevel;
        this.department = department;
        this.location = location;
        this.description = description;
        this.duration = duration;
        this.tasks = tasks;
        this.specialReqs = specialReqs;
        this.dateIssued = dateIssued;
        this.resolved = resolved;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public String getDepartment() {
        return department;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getTasks() {
        return tasks;
    }

    public String getSpecialReqs() {
        return specialReqs;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public String getResolved() {
        return resolved;
    }

    public String getFileName(){
        return fileName;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public void setSpecialReqs(String specialReqs) {
        this.specialReqs = specialReqs;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }
}
