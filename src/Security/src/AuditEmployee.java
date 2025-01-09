/**
 * @author Kenny
 */

package src.Security.src;

public class AuditEmployee {

    private String employeeID, name, division, position,
            department, expertise, rating, yearsOfExperience,
            previousAssignment, currentAssignment;

    private AuditEmployee(){}

    public AuditEmployee(String employeeID, String name, String division,
                         String position, String department, String expertise,
                         String rating, String yearsOfExperience, String previousAssignment,
                         String currentAssignment){
        this.employeeID = employeeID;
        this.name = name;
        this.division = division;
        this.position = position;
        this.department = department;
        this.expertise = expertise;
        this.rating = rating;
        this.yearsOfExperience = yearsOfExperience;
        this.previousAssignment = previousAssignment;
        this.currentAssignment = currentAssignment;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public String getDivision() {
        return division;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public String getExpertise() {
        return expertise;
    }

    public String getRating() {
        return rating;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getPreviousAssignment() {
        return previousAssignment;
    }

    public String getCurrentAssignment() {
        return currentAssignment;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setPreviousAssignment(String previousAssignment) {
        this.previousAssignment = previousAssignment;
    }

    public void setCurrentAssignment(String currentAssignment) {
        this.currentAssignment = currentAssignment;
    }
}
