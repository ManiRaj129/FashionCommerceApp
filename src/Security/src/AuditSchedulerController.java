/**
 * @author Kenny
 */

package src.Security.src;

import src.App;

import java.util.Scanner;

public class AuditSchedulerController implements src.Security.src.interfaces.AuditSchedulerController {

    private AuditSchedulerController controller;

    private AuditScheduler scheduler = null;

    public AuditSchedulerController (AuditSchedulerController controller){
        this.controller = controller;
    }

    public AuditSchedulerController(){}

    /**
     * Run the audit program
     */
    public void run() throws Exception {

        System.out.println("Welcome to the Audit Security Scheduler System");

        boolean exit = false;

        while (!exit) {

            Scanner scan = new Scanner(System.in);

            System.out.println("1. Add New Audit Personnel");
            System.out.println("2. Delete Audit Personnel by ID");
            System.out.println("3. Show Ongoing Audits");
            System.out.println("4. Show Passed Audits");
            System.out.println("5. Show Failed Audits");
            System.out.println("6. Show All Audits");
            System.out.println("7. Show Audit by ID");
            System.out.println("8. Show Available Audit Employees");
            System.out.println("9. Show All Audit Employees");
            System.out.println("10. Create New Audit");
            System.out.println("11. Delete Audit by ID");
            System.out.println("12. Edit Audit by ID");
            System.out.println("13. Send Failed Audit by ID");
            System.out.println("0. Exit program");

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {

                case 1:
                    addAuditPersonnel();
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Enter Audit Employee ID to delete: ");
                    String deleteID = scan.nextLine();
                    deleteAuditPersonnel(deleteID);
                    System.out.println();
                    break;
                case 3:
                    showOngoingAudits();
                    System.out.println();
                    break;
                case 4:
                    showPassedAudits();
                    System.out.println();
                    break;
                case 5:
                    showFailedAudits();
                    System.out.println();
                    break;
                case 6:
                    showAllAudits();
                    System.out.println();
                    break;
                case 7:
                    System.out.println("Enter Audit ID: ");
                    String findAuditID = scan.next();
                    showAuditByID(findAuditID);
                    System.out.println();
                    break;
                case 8:
                    showFreeAuditEmployees();
                    System.out.println();
                    break;
                case 9:
                    showAllAuditEmployees();
                    System.out.println();
                    break;
                case 10:
                    createAudit();
                    System.out.println();
                    break;
                case 11:
                    System.out.println("Enter Audit Schedule ID to delete : ");
                    String deleteAuditID = scan.next();
                    deleteAudit(deleteAuditID);
                    System.out.println();
                    break;
                case 12:
                    System.out.println("Enter Audit Schedule ID to edit : ");
                    String editAuditID = scan.next();
                    editAudit(editAuditID);
                    System.out.println();
                    break;
                case 13:
                    System.out.println("Enter Failed Audit Schedule ID to send : ");
                    String sendFailedAuditID = scan.next();
                    sendFailedAudit(sendFailedAuditID);
                    System.out.println();
                    break;
                case 0:
                    System.out.println("Closing program...");
                    exit = true;
                    App.prompt();
                default:
                    System.out.println("Incorrect choice. Try again");
            }
        }
    }

    /**
     * To define one instance of the class SecurityScheduler to
     * call SecurityScheduler methods
     * @return SecurityScheduler instance
     */
    private AuditScheduler getSchedulerInstance(){

        if (scheduler == null){
            scheduler = new AuditScheduler();
        }
        return scheduler;
    }

    @Override
    public boolean addAuditPersonnel() {
        return this.getSchedulerInstance().addAuditPersonnel();
    }

    @Override
    public boolean deleteAuditPersonnel(String employeeID) {
        return this.getSchedulerInstance().deleteAuditPersonnel(employeeID);
    }

    @Override
    public boolean showOngoingAudits() {
        return this.getSchedulerInstance().showOngoingAudits();
    }

    @Override
    public boolean showPassedAudits() {
        return this.getSchedulerInstance().showPassedAudits();
    }

    @Override
    public boolean showFailedAudits() {
        return this.getSchedulerInstance().showFailedAudits();
    }

    @Override
    public boolean showAllAudits() {
        return this.getSchedulerInstance().showAllAudits();
    }

    @Override
    public boolean showAuditByID(String auditID) {
        return this.getSchedulerInstance().showAuditByID(auditID);
    }

    @Override
    public boolean showFreeAuditEmployees() {
        return this.getSchedulerInstance().showFreeAuditEmployees();
    }

    @Override
    public boolean showAllAuditEmployees() {
        return this.getSchedulerInstance().showAllAuditEmployees();
    }

    @Override
    public boolean createAudit() {
        return this.getSchedulerInstance().createAudit();
    }

    @Override
    public boolean deleteAudit(String auditID) {
        return this.getSchedulerInstance().deleteAudit(auditID);
    }

    @Override
    public boolean editAudit(String auditID) {
        return this.getSchedulerInstance().editAudit(auditID);
    }

    @Override
    public boolean sendFailedAudit(String auditID) {
        return this.getSchedulerInstance().sendFailedAudit(auditID);
    }
}
