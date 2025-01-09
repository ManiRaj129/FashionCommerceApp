package src.Treasury.src.interfaces;

/**
 * Stores an instance of Payroll that is used to
 * call the Payroll object methods.
 */
public interface PayrollControllerInterface {

	boolean addEmployeeData(String filePath);

	boolean printEmployeeData();

	boolean removeEmployeeByID(String employeeID);
	
	boolean addPayrollRates(String filePath);

	boolean printPayrollRates();
	
	boolean processPayroll(String filepath);

	boolean printPayrollReports();

	boolean printPayslips();
}
