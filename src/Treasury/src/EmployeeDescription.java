package src.Treasury.src;

public class EmployeeDescription {

    private String employeeID, name, position, department;

    public EmployeeDescription(String employeeID, String name, String position, String department){

        this.employeeID = employeeID;
        this.name = name;
        this.position = position;
        this.department = department;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
