package src.Treasury.src.interfaces;

import src.Treasury.src.PayrollApproval;

public interface PayrollInterface {

	boolean addEmployeeData(String filePath);

	boolean printEmployeeData();

	boolean removeEmployeeByID(String employeeID);
	
	boolean addPayrollRates(String filePath);

	boolean printPayrollRates();

	boolean processPayroll(String fileDirectory);
	
	void createPayroll(String fileDirectory);
	
	void createPayslip(String fileDirectory);

	boolean printPayrollReports();

	boolean printPayslips();
	
	PayrollApproval retrieveApproval(String reportID);
}
