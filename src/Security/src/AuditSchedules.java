/**
 * @author Kenny
 */

package src.Security.src;

import java.util.ArrayList;
import java.util.List;

public class AuditSchedules {

    private String scheduleID, priorityLevel, department, location,
            description, duration, tasks, status, dateScheduled;
    private List<String> assignedEmployeeIDs = new ArrayList<>();

    public AuditSchedules(){};

    public AuditSchedules (String scheduleID, String priorityLevel, String department,
                           String location, String description, String duration,
                           String tasks, String status, String dateScheduled) {
        this.scheduleID = scheduleID;
        this.priorityLevel = priorityLevel;
        this.department = department;
        this.location = location;
        this.description = description;
        this.duration = duration;
        this.tasks = tasks;
        this.status = status;
        this.dateScheduled = dateScheduled;
    }

    public String getScheduleID(){
        return scheduleID;
    }

    public String getPriorityLevel(){
        return priorityLevel;
    }

    public String getDepartment() {
        return department;
    }

    public String getLocation(){
        return location;
    }

    public String getDescription(){
        return description;
    }

    public String getDuration(){
        return duration;
    }

    public String getTasks(){
        return tasks;
    }

    public String getStatus(){
        return status;
    }

    public String getDateScheduled(){
        return dateScheduled;
    }

    public List<String> getAssignedEmployeeIDs(){
        return assignedEmployeeIDs;
    }

    public void setScheduleID(String scheduleID){
        this.scheduleID = scheduleID;
    }

    public void setPriorityLevel(String priorityLevel){
        this.priorityLevel = priorityLevel;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public void setTasks(String tasks){
        this.tasks = tasks;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setDateScheduled(String dateScheduled){
        this.dateScheduled = dateScheduled;
    }

    public void setAssignedEmployeeIDs(List<String> assignedEmployeeIDs){
        this.assignedEmployeeIDs = assignedEmployeeIDs;
    }
}
