/**
 * @author Kenny
 */

package src.Treasury.src;

import src.App;
import src.Security.src.SecurityRequestScheduler;
import src.Treasury.src.interfaces.PayrollControllerInterface;

import java.util.Scanner;

/**
 * Stores an instance of Payroll that is used to
 * call the Payroll object methods.
 */
public class PayrollController implements PayrollControllerInterface {

	private PayrollController controller;
	
	private Payroll payroll = null;
	
	private PayrollController (PayrollController controller) {
		this.controller = controller;
	}
	
	public PayrollController() {};
	
	/**
	 * Run the payroll program
	 */
	public void run() throws Exception {
		
		System.out.println("Welcome to the Payroll System");
		
		Scanner scan = new Scanner(System.in);
		boolean exit = false;
		
		while (!exit) {
			
			System.out.println("1. Load Department Employee Data List");
			System.out.println("2. View Entered Employee Data List");
			System.out.println("3. Remove Employee by ID");
			System.out.println("4. Load Payroll Rates");
			System.out.println("5. View Entered Payroll Rates");
			System.out.println("6. Process Payroll & Payslips");
			System.out.println("7. View Created Payroll Reports");
			System.out.println("8. View Created Payslips");
			SecurityRequestScheduler scheduler = new SecurityRequestScheduler();
			scheduler.optionsPrint();
			System.out.println("0. Exit program");
			
			int choice = scan.nextInt();
			scan.nextLine();
			
			switch (choice) {
				
				case 1:
					System.out.println("Enter path of Department Employee Data File:");
					String filePath = scan.nextLine();
					if (addEmployeeData(filePath)) {;
						System.out.println("Department Employee Data added successfully\n");
					}
					else {
						System.out.println("Invalid Employee Data file\n");
					}
					break;
				case 2:
					printEmployeeData();
					System.out.println();
					break;
				case 3:
					System.out.println("Enter employee to remove by ID: ");
					String employeeID = scan.nextLine();
					removeEmployeeByID(employeeID);
					System.out.println();
					break;
				case 4:
					System.out.println("Enter path of Payroll Rate File:");
					String filePath2 = scan.nextLine();
					if (addPayrollRates(filePath2)) {
						System.out.println("Payroll Rate added successfully\n");
					}
					else {
						System.out.println("Invalid Payroll Rate file\n");
					}
					break;
				case 5:
					printPayrollRates();
					System.out.println();
					break;
				case 6:
					System.out.println("Enter path to store Payroll Report & Payslips: (e.g. User\\Documents\\(files stored in here))");
					String filePath3 = scan.nextLine();
					if (processPayroll(filePath3)) {
						System.out.println("Payroll successfully processed and saved\n");
					}
					else {
						System.out.println("Payroll processing failed\n");
					}
					break;
				case 7:
					printPayrollReports();
					System.out.println();
					break;
				case 8:
					break;
				case 111:
					scheduler.addSecurityRequest();
					break;
				case 112:
					scheduler.showAllSecurityRequests();
					break;
				case 113:
					scheduler.deleteScheduleByID();
					break;
				case 0:
					System.out.println("Closing program...");
					exit = true;
					App.prompt();
				default:
					System.out.println("Incorrect choice. Try again");
			}
		}
		scan.close();
	}

	@Override
	public boolean addEmployeeData(String filePath) {
		
		return this.getPayrollInstance().addEmployeeData(filePath);
	}

	@Override
	public boolean printEmployeeData() {

		return this.getPayrollInstance().printEmployeeData();
	}

	@Override
	public boolean removeEmployeeByID(String employeeID) {

		return this.getPayrollInstance().removeEmployeeByID(employeeID);
	}

	@Override
	public boolean addPayrollRates(String filePath) {
		
		return this.getPayrollInstance().addPayrollRates(filePath);
	}

	@Override
	public boolean printPayrollRates() {

		return this.getPayrollInstance().printPayrollRates();
	}

	@Override
	public boolean processPayroll(String filePath) {
		
		return this.getPayrollInstance().processPayroll(filePath);
	}

	@Override
	public boolean printPayrollReports() {

		return this.getPayrollInstance().printPayrollReports();
	}

	@Override
	public boolean printPayslips() {

		return this.getPayrollInstance().printPayslips();
	}

	/**
	 * To define one instance of the class Payroll to 
	 * call Payroll methods
	 * @return Payroll instance
	 */
	private Payroll getPayrollInstance() {
		
		if (payroll == null) {
			payroll = new Payroll();
		}
		return payroll;
	}
}
