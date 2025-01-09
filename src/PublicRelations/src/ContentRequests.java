/**
 * @author Kenny
 */

package src.PublicRelations.src;

public class ContentRequests {

    String requestID, theme, contentCategory, platform, duration, deadline,
            description, tasks, department, priority, specialReqs, dateIssued,
            resolved, fileName;

    public ContentRequests(){}

    public ContentRequests(String requestID, String theme, String contentCategory,
                           String platform, String duration, String deadline,
                           String description, String tasks, String department,
                           String priority, String specialReqs, String dateIssued,
                           String resolved, String fileName){

        this.requestID = requestID;
        this.theme = theme;
        this.contentCategory = contentCategory;
        this.platform = platform;
        this.duration = duration;
        this.deadline = deadline;
        this.description = description;
        this.tasks = tasks;
        this.department = department;
        this.priority = priority;
        this.specialReqs = specialReqs;
        this.dateIssued = dateIssued;
        this.resolved = resolved;
        this.fileName = fileName;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getTheme() {
        return theme;
    }

    public String getContentCategory() {
        return contentCategory;
    }

    public String getPlatform() {
        return platform;
    }

    public String getDuration() {
        return duration;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public String getTasks() {
        return tasks;
    }

    public String getDepartment() {
        return department;
    }

    public String getPriority() {
        return priority;
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

    public String getFileName() {
        return fileName;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setContentCategory(String contentCategory) {
        this.contentCategory = contentCategory;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
