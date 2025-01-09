/**
 * @author Kenny
 */

package src.PublicRelations.src;

import java.util.List;

public class ContentSchedules {

    String scheduleID, requestID, theme, contentCategory, platform, duration, deadline,
            description, tasks, department, priority, status, dateScheduled, fileName;
    private List<String> planningTeamAssign, reviewTeamAssign;

    public ContentSchedules(){}

    public ContentSchedules(String scheduleID, String requestID, String theme,
                            String contentCategory, String platform, String duration,
                            String deadline, String description, String tasks,
                            String department, String priority, String status,
                            String dateIssued, String fileName){

        this.scheduleID = scheduleID;
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
        this.status = status;
        this.dateScheduled = dateIssued;
        this.fileName = fileName;
    }

    public String getScheduleID() {
        return scheduleID;
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

    public String getStatus() {
        return status;
    }

    public String getDateScheduled() {
        return dateScheduled;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getPlanningTeamAssign() {
        return planningTeamAssign;
    }

    public List<String> getReviewTeamAssign() {
        return reviewTeamAssign;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDateScheduled(String dateScheduled) {
        this.dateScheduled = dateScheduled;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPlanningTeamAssign(List<String> planningTeamAssign) {
        this.planningTeamAssign = planningTeamAssign;
    }

    public void setReviewTeamAssign(List<String> reviewTeamAssign) {
        this.reviewTeamAssign = reviewTeamAssign;
    }
}
