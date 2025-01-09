package src.Treasury.src.interfaces;

import src.Treasury.src.Employee;

public interface EmployeeInterface {

	Employee getEmployeeDetails();
	
	double getSalary();
	
	void setSalary(double newSalary);
}
