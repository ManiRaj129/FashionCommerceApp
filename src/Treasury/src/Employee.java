/**
 * @author Kenny
 */

package src.Treasury.src;

import src.Treasury.src.interfaces.EmployeeInterface;

public class Employee implements EmployeeInterface {

	private String employeeID, name, position, department;
	private double workHours, benefits, overtime, salary;
	
	public Employee() {};
	
	public Employee(String employeeID, String name, String position, String department,
					double workHours, double benefits, double overtime, double salary) {
		
		this.employeeID = employeeID;
		this.name = name;
		this.position = position;
		this.department = department;
		this.workHours = workHours;
		this.benefits = benefits;
		this.overtime = overtime;
		this.salary = salary;
	}
	
	@Override
	public Employee getEmployeeDetails() {

		return this;
	}

	public String getEmployeeID() {
		
		return employeeID;
	}
	
	public String getName() {
		
		return name;
	}
	
	public String getPosition() {
		
		return position;
	}
	
	public String getDepartment() {
		
		return department;
	}
	
	public double getWorkHours() {
		
		return workHours;
	}
	
	public double getBenefits() {
		
		return benefits;
	}
	
	public double getOvertime() {
		
		return overtime;
	}
	
	@Override
	public double getSalary() {
		
		return salary;
	}

	@Override
	public void setSalary(double newSalary) {
		this.salary = newSalary;
	}

}
