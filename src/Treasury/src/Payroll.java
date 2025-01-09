/**
 * @author Kenny
 */

package src.Treasury.src;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import src.Treasury.src.interfaces.PayrollInterface;
import src.TextEditor.PoorTextEditor;

/*
 * Information Expert that knows Employee and Rates
 */
public class Payroll implements PayrollInterface {

	private ArrayList<Employee> employeeRepo = new ArrayList<Employee>();
	private Map<String, EmployeeDescription> employeeDescriptionRepo = new HashMap<>();
	private String payrollData = null;

	private Map<String, PayrollReport> payrollReportRepo = new HashMap<>();
	private Map<String, PayrollDescription> payrollDescriptionRepo = new HashMap<>();

	private double totalTaxDeductions, totalRetirementDeductions;
	
	PoorTextEditor editor = new PoorTextEditor();

	/**
	 * Add new department employee(s) to employee repository
	 * @param employeeData filepath to employee data
	 * @return true if successful, false otherwise
	 */
	@Override
	public boolean addEmployeeData(String employeeData) {

		editor.processTextFile(employeeData);

		if (editor.getRepository().isEmpty()) {
			return false;
		}

		String[] temp = editor.getArrayNames();
		for (String s : temp) {

			// storing employees to repo
			Employee employee = new Employee(s,
					editor.retrieveValue(s, "name"),
					editor.retrieveValue(s, "position"),
					editor.retrieveValue(s, "department"),
					editor.getDoubleValue(s, "work hours"),
					editor.getDoubleValue(s, "benefits"),
					editor.getDoubleValue(s, "overtime"),
					editor.getDoubleValue(s, "salary"));

			if (employee.getName() == null || employee.getPosition() == null || employee.getDepartment() == null){

				return false;
			}
			else {
				employeeRepo.add(employee);

				EmployeeDescription details = new EmployeeDescription(s, editor.retrieveValue(s, "name"),
												editor.retrieveValue(s, "position"), editor.retrieveValue(s, "department"));
				employeeDescriptionRepo.put(s, details);
			}
		}
		return true;
	}

	@Override
	public boolean printEmployeeData() {

		if (employeeRepo.isEmpty()){
			System.out.println("No employee data found");
			return false;
		}

		for (Employee e : employeeRepo){

			System.out.println("Employee ID: " + e.getEmployeeID());
			System.out.println("Name: " + e.getName());
			System.out.println("Position: " + e.getPosition());
			System.out.println("Department: " + e.getDepartment());
			System.out.println("Work hours: " + e.getWorkHours());
			System.out.println("Benefits: " + e.getBenefits());
			System.out.println("Overtime: " + e.getOvertime());
			System.out.println("Salary: " + e.getSalary());
			System.out.print("\n");
		}
		return true;
	}

	/**
	 * Remove entered employee data by ID
	 * @param employeeID
	 * @return
	 */
	@Override
	public boolean removeEmployeeByID(String employeeID) {

		if (employeeRepo.isEmpty()){
			System.out.println("No employee data found");
			return false;
		}

		if (!employeeDescriptionRepo.containsKey(employeeID)){
			System.out.println("Employee with ID: " + employeeID + " not found");
			return false;
		}

		for (int i = 0; i < employeeRepo.size(); i++){

			if (employeeRepo.get(i).getEmployeeID().equals(employeeID)){

				employeeRepo.remove(i);
				System.out.println("Employee with ID: " + employeeID + " removed");
				break;
			}
		}
		return true;
	}

	/**
	 * Add payroll rate data
	 * @param payrollData filepath to payroll rate data
	 * @return true if successful, false otherwise
	 */
	@Override
	public boolean addPayrollRates(String payrollData) {

		editor.processTextFile(payrollData);
		
		if (editor.getRepository().isEmpty()) {
			System.out.println("Incorrect path to Department Employee Data");
			return false;
		}
		
		this.payrollData = payrollData;
		return true;
	}

	@Override
	public boolean printPayrollRates() {

		if (payrollData == null){
			System.out.println("No payroll data entered");
			return false;
		}
		editor.processTextFile(payrollData);
		editor.prettyPrint();
		return true;
	}

	/**
	 * Begin process of creating payroll and payslips
	 * @param fileDirectory filepath to save payroll and payslips
	 * @return true if successful, false otherwise
	 */
	@Override
	public boolean processPayroll(String fileDirectory) {
		
		if (employeeRepo.isEmpty() || payrollData == null) {
			System.out.println("No Employee Data or Payroll Data found\n");
			return false;
		}

		File directory = new File(fileDirectory);
		if (!directory.exists()) {
			
			System.out.println("Directory does not exist");
			return false;
		}

		verifyDiscrepancy();
		if (!calculatePayroll()){
			return false;
		}
		
		createPayroll(fileDirectory);
		createPayslip(fileDirectory);

		// a copy of payroll report and payslips to HR
		createPayroll("src/HR/repository/payrollReports/");
		createPayroll("src/Treasury/repository/payrollReports/");
		createPayslip("src/HR/repository/payslips/");
		createPayslip("src/Treasury/repository/payslips/");
		return true;
	}

	@Override
	public PayrollApproval retrieveApproval(String reportID) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Payroll calculation
	 * @return true if successful, false otherwise
	 */
	private boolean calculatePayroll() {
		
		editor.processTextFile(payrollData);
		double salaryRate = editor.getDoubleValue("payrollRate", "salaryRate");
		double benefitsRate = editor.getDoubleValue("payrollRate", "benefitsRate");
		double overtimeRate = editor.getDoubleValue("payrollRate", "overtimeRate");
		double taxRate = editor.getDoubleValue("payrollRate", "taxRate");
		double retirementRate = editor.getDoubleValue("payrollRate", "retirementRate");

		double total;
		
		for (int i = 0; i < employeeRepo.size(); i++) {
			
			double basePay = Double.parseDouble(String.format("%.2f", salaryRate * getPositionBasePay(employeeRepo.get(i).getPosition())));
			double benefitsPay = Double.parseDouble(String.format("%.2f", benefitsRate * employeeRepo.get(i).getBenefits()));
			double overtimePay = Double.parseDouble(String.format("%.2f", overtimeRate * employeeRepo.get(i).getOvertime()));
			double grossPay = Double.parseDouble(String.format("%.2f", basePay + benefitsPay + overtimePay));
			double taxDeduction = Double.parseDouble(String.format("%.2f", grossPay * (taxRate / 100.0)));
			totalTaxDeductions += (grossPay - taxDeduction);
			double retirementDeduction = Double.parseDouble(String.format("%.2f", grossPay * (retirementRate / 100.0)));
			totalRetirementDeductions += (grossPay - retirementDeduction);
			double netPay = Double.parseDouble(String.format("%.2f", grossPay - taxDeduction - retirementDeduction));

			if (netPay < 0){
				System.out.println("Salary for " + employeeRepo.get(i).getName() + " is negative after deductions");
				return false;
			}
			
			PayrollReport subreport = new PayrollReport(basePay, benefitsPay, overtimePay, grossPay, netPay);
			
			if (payrollReportRepo.containsKey(employeeRepo.get(i).getDepartment())) {
				
				PayrollReport report = payrollReportRepo.get(employeeRepo.get(i).getDepartment());
				PayrollReport newReport = new PayrollReport(basePay + report.getTotalBasePay(),
															benefitsPay + report.getTotalBenefitsPay(),
															overtimePay + report.getTotalOvertimePay(),
															grossPay + report.getTotalGrossPay(),
															netPay + report.getTotalNetPay());
				payrollReportRepo.put(employeeRepo.get(i).getDepartment(), newReport);
			}
			else {
				payrollReportRepo.put(employeeRepo.get(i).getDepartment(), subreport);
			}
			employeeRepo.get(i).setSalary(netPay);
		}
		return verifyBudget();
	}

	/**
	 * Retrieve position based pay
	 * @param position position of current employee
	 * @return base pay of employee position
	 */
	private double getPositionBasePay(String position) {
		
		double baseSalary = -1;
		
		switch (position) {
		
		case "employee":
			baseSalary =  3000.00;
			break;
		case "manager":
			baseSalary =  6000.00;
			break;
		default:
			System.out.println("No such job position");
		}
		return baseSalary;
	}

	/**
	 * Retrieve position work hour maximum
	 * @param position position of current employee
	 * @return work hour maximum of current employee
	 */
	private double getPositionWorkHours(String position){

		double workHourMaximum = -1;

		switch (position) {

			case "employee":
				workHourMaximum = 200;
				break;
			case "manager":
				workHourMaximum = 210;
				break;
			default:
				System.out.println("No such Job position");
		}
		return workHourMaximum;
	}

	/**
	 * Retrieve position overtime maximum
	 * @param position position of current employee
	 * @return overtime maximum of current employee
	 */
	private double getPositionOvertime(String position){

		double overtimeMaximum = -1;

		switch (position) {

			case "employee":
				overtimeMaximum = 15;
				break;
			case "manager":
				overtimeMaximum = 15;
				break;
			default:
				System.out.println("No such Job position");
		}
		return overtimeMaximum;
	}

	/**
	 * Creation of payroll report
	 * @param fileDirectory filepath to store report
	 */
	@Override
	public void createPayroll(String fileDirectory) {
		
		BufferedWriter writer = null;
		
		try {
			// report ID creation
			LocalDateTime timeNow = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");
			String formattedDateTime = timeNow.format(formatter);

			writer = new BufferedWriter(new FileWriter(fileDirectory + "Payroll" + formattedDateTime + ".txt"));

			double benefits = 0;
			double salaries = 0;

			for (Map.Entry<String, PayrollReport> entry : payrollReportRepo.entrySet()) {
				
				String departmentName = entry.getKey();
				PayrollReport report = entry.getValue();
				
				writer.write(departmentName + "\n");
				writer.write("Total Base Pay: " + report.getTotalBasePay() + "$\n");
				writer.write("Total Benefits Pay: " + report.getTotalBenefitsPay() + "$\n");
				writer.write("Total Overtime Pay: " + report.getTotalOvertimePay() + "$\n");
				writer.write("Total Gross Pay: " + report.getTotalGrossPay() + "$\n");
				writer.write("Total Net Pay: " + report.getTotalNetPay() + "$\n");
				writer.write("\n");

				benefits += report.getTotalBenefitsPay();
				salaries += report.getTotalNetPay();
			}
			
			writer.write("========================================================================\n");
			writer.write("Total Salaries Paid: " + salaries + "$\n");

			String payrollID = "Payroll" + formattedDateTime;
			PayrollDescription description = new PayrollDescription(payrollID, salaries, benefits, totalTaxDeductions,
																	totalRetirementDeductions, employeeRepo.size());
			payrollDescriptionRepo.put(payrollID, description);

		} catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}

	/**
	 * Creation of payslips
	 * @param fileDirectory filepath to store payslips
	 */
	@Override
	public void createPayslip(String fileDirectory) {
		
		BufferedWriter writer = null;
		
		for (int i = 0; i < employeeRepo.size(); i++) {
			
			try {
				
				Employee employee = employeeRepo.get(i);
				writer = new BufferedWriter(new FileWriter(fileDirectory + "PaySlip_" + employee.getEmployeeID() + ".txt"));
				
				writer.write("Employee ID: " + employee.getEmployeeID() + "\n");
				writer.write("Name: " + employee.getName() + "\n");
				writer.write("\n");
				writer.write("Salary: " + employee.getSalary() + "$");
				
				
			} catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (writer != null) {
	                    writer.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		}
	}

	/**
	 * To retrieve and print existing payroll reports
	 * @return true if successful, false otherwise
	 */
	@Override
	public boolean printPayrollReports() {

		String payrollReportDirectory = "src/Treasury/repository/payrollReports/";

		File directory = new File(payrollReportDirectory);
		File[] textFiles = null;

		if (directory.exists() && directory.isDirectory()){

			// grab list of text files
			FilenameFilter textFileFilter = (dir, name) -> name.toLowerCase().endsWith(".txt");
			textFiles = directory.listFiles(textFileFilter);
		}
		else {
			return false;
		}

		if (textFiles != null){

			if (textFiles.length == 0){
				System.out.println("No Payroll Reports found");
				return false;
			}
			else {
				System.out.println("There are " + textFiles.length + " Payroll Reports found: ");
			}

			for (File file : textFiles){
				System.out.println(file.getName());
			}
			System.out.println("Name a Payroll Report to print (payroll11223344.txt): ");

			Scanner scanner = new Scanner(System.in);
			String payrollReport = scanner.nextLine();
			lineByLineFilePrinter(payrollReportDirectory + payrollReport, payrollReport);
		}
		else {
			System.out.println("No Payroll Reports found.");
			return false;
		}
		return true;
	}

	@Override
	public boolean printPayslips() {
		return false;
	}

	/**
	 * To verify employee work hours or overtime discrepancies
	 */
	private void verifyDiscrepancy(){

        for (Employee employee : employeeRepo) {

            if (employee.getWorkHours() > getPositionWorkHours(employee.getPosition())) {

                System.out.println("Employee " + employee.getName() + " from " + employee.getDepartment() + " has discrepancy:");
                System.out.println("Current work hours: " + employee.getWorkHours() + ", Maximum: " + getPositionWorkHours(employee.getPosition()) + "\n");
            } else if (employee.getOvertime() > getPositionOvertime(employee.getPosition())) {

                System.out.println("Employee " + employee.getName() + " from " + employee.getDepartment() + " has discrepancy:");
                System.out.println("Current overtime: " + employee.getOvertime() + ", Maximum: " + getPositionOvertime(employee.getPosition()) + "\n");
            }
        }
	}

	/**
	 * To verify if payroll total exceeds given budget
	 * @return true if does not exceed, false otherwise
	 */
	private boolean verifyBudget(){

		double salaries = 0;

		for (Map.Entry<String, PayrollReport> entry : payrollReportRepo.entrySet()) {

			String departmentName = entry.getKey();
			PayrollReport report = entry.getValue();
			salaries += report.getTotalNetPay();
		}

		editor.processTextFile("src/Treasury/repository/budgetData/budget.txt");
		double budget = Double.parseDouble(editor.retrieveValue("budget", "maxBudget"));

		if (salaries > budget){
			System.out.println("Total salaries paid: " + salaries + "$ has exceeded budget: " + budget + "$\n");
			return false;
		}
		return true;
	}

	private void lineByLineFilePrinter(String filePath, String fileName){

		System.out.println("Report for " + fileName + ":\n");
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

			String line;
			// Read each line until end of file
			while ((line = reader.readLine()) != null) {
				// Output each line to console
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}