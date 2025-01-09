/**
 * @author Kenny
 */

package src.PublicRelations.src;

import java.util.List;

public class ActivitySchedules {

    private String scheduleID, activityName, activityType, objective, targetAudience, description,
                    tasks, department, location, duration, deadline, status, dateScheduled, fileName;
    private List<String> planningTeamAssign, reviewTeamAssign;

    public ActivitySchedules(){}

    public ActivitySchedules(String scheduleID, String activityName, String activityType,
                             String objective, String targetAudience, String description,
                             String tasks, String department, String location,
                             String duration, String deadline, String status,
                             String dateIssued, String fileName){

        this.scheduleID = scheduleID;
        this.activityName = activityName;
        this.activityType = activityType;
        this.objective = objective;
        this.targetAudience = targetAudience;
        this.description = description;
        this.tasks = tasks;
        this.department = department;
        this.location = location;
        this.duration = duration;
        this.deadline = deadline;
        this.status = status;
        this.dateScheduled = dateIssued;
        this.fileName = fileName;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getObjective() {
        return objective;
    }

    public String getTargetAudience() {
        return targetAudience;
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

    public String getLocation() {
        return location;
    }

    public String getDuration() {
        return duration;
    }

    public String getDeadline() {
        return deadline;
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

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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
