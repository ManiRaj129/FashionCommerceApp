package src.HR.src;

import src.HR.src.interfaces.IEmployee;

/**
 * @author Sam Gumm
 */
public class Employee implements IEmployee {
    String employeeId;
    String name;
    Department department;
    String position;
    String employmentStatus;
    int salary;

    /**
     *
     * @param employeeId String, the Employee's identifying number
     * @param name String the Employee's name
     * @param department Department, the Employee's assigned department
     * @param position String, the Employee's assigned position
     * @param employmentStatus String, the Employee's current employment status:
     *                                      Onboarding --> Just hired from Candidate
     *                                      Active --> Finished Onboarding tasks, full employee
     *                                      Offboarding --> Pending being deleted from system (Employee has been fired)
     * @param salary int, the Employee's recorded salary
     */
    public Employee(String employeeId, String name, Department department, String position, String employmentStatus, int salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.position = position;
        this.employmentStatus = employmentStatus;
        this.salary = salary;
    }

    /**
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void changeName(String name) {
        this.name = name;
    }

    /**
     *
     * @return employeeId
     */
    @Override
    public String getEmployeeID() {
        return employeeId;
    }

    /**
     * @return department
     */
    @Override
    public String getDepartment() {
        return String.valueOf(department);
    }

    /**
     *
     * @return position
     */
    @Override
    public String getPosition() {
        return position;
    }

    /**
     *
     * @return employmentStatus
     */
    @Override
    public String getEmployementStatus() {
        return employmentStatus;
    }

    /**
     * @return salary
     */
    @Override
    public String getSalary() {
        return String.valueOf(salary);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Department: %s, Position: %s, Status: %s, Salary: %d", 
                employeeId, name, department, position, employmentStatus, salary);
    }

    public static Employee parseEmployee(String employeeString) {
        // Remove unnecessary spaces and split the string by commas
        String[] parts = employeeString.split(", ");

        // Extract the individual fields using the known format
        String employeeId = parts[0].split(": ")[1];
        String name = parts[1].split(": ")[1];
        Department department = Department.valueOf(parts[2].split(": ")[1].toUpperCase()); // Assuming Department is an enum
        String position = parts[3].split(": ")[1];
        String employmentStatus = parts[4].split(": ")[1];
        int salary = Integer.parseInt(parts[5].split(": ")[1]);

        // Create and return the Employee object
        return new Employee(employeeId, name, department, position, employmentStatus, salary);
    }
}
