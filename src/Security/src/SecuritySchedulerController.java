/**
 * @author Kenny
 */

package src.Security.src;

import src.App;

import java.util.Scanner;

public class SecuritySchedulerController implements src.Security.src.interfaces.SecuritySchedulerController {

    private SecuritySchedulerController controller;

    private SecurityScheduler scheduler = null;

    public SecuritySchedulerController (SecuritySchedulerController controller){
        this.controller = controller;
    }

    public SecuritySchedulerController(){}

    /**
     * Run the security program
     */
    public void run() throws Exception {

        System.out.println("Welcome to the On-site Security Scheduler System");

        boolean exit = false;

        while (!exit) {

            Scanner scan = new Scanner(System.in);

            this.getSchedulerInstance().announceHighPriorityRequests();

            System.out.println("1. Add New Security Personnel");
            System.out.println("2. Delete Security Personnel by ID");
            System.out.println("3. Show Pending Security Events");
            System.out.println("4. Show All Security Events");
            System.out.println("5. Show Available Security Employees");
            System.out.println("6. Show All Security Employees");
            System.out.println("7. Create Security Schedule");
            System.out.println("8. Delete Security Schedule");
            System.out.println("9. Edit Existing Security Schedule Team Assignment");
            System.out.println("10. Show All Security Schedules");
            System.out.println("11. Show Security Schedule by ID");
            System.out.println("0. Exit program");

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {

                case 1:
                    addSecurityPersonnel();
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Enter Security Employee ID to delete: ");
                    String deleteID = scan.nextLine();
                    deleteSecurityPersonnel(deleteID);
                    System.out.println();
                    break;
                case 3:
                    showPendingEvents();
                    System.out.println();
                    break;
                case 4:
                    showAllEvents();
                    System.out.println();
                    break;
                case 5:
                    showFreeSecurityEmployees();
                    System.out.println();
                    break;
                case 6:
                    showAllSecurityEmployees();
                    System.out.println();
                    break;
                case 7:
                    System.out.println("Enter Security Request/Event ID to create a schedule for : ");
                    String requestID = scan.next();
                    createSchedule(requestID);
                    System.out.println();
                    break;
                case 8:
                    System.out.println("Enter Security Schedule ID to delete : ");
                    String deleteScheduleID = scan.next();
                    deleteSchedule(deleteScheduleID);
                    System.out.println();
                    break;
                case 9:
                    System.out.println("Enter Security Schedule ID to edit : ");
                    String editScheduleID = scan.next();
                    editScheduleAssignments(editScheduleID);
                    System.out.println();
                    break;
                case 10:
                    showAllSchedules();
                    System.out.println();
                    break;
                case 11:
                    System.out.println("Enter Security Schedule ID to view : ");
                    String viewScheduleID = scan.next();
                    showScheduleByID(viewScheduleID);
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

    @Override
    public boolean addSecurityPersonnel() {
        return this.getSchedulerInstance().addSecurityPersonnel();
    }

    @Override
    public boolean deleteSecurityPersonnel(String employeeID) {
        return this.getSchedulerInstance().deleteSecurityPersonnel(employeeID);
    }

    @Override
    public boolean showPendingEvents() {
        return this.getSchedulerInstance().showPendingEvents();
    }

    @Override
    public boolean showAllEvents() {
        return this.getSchedulerInstance().showAllEvents();
    }

    @Override
    public boolean showFreeSecurityEmployees() {
        return this.getSchedulerInstance().showFreeSecurityEmployees();
    }

    @Override
    public boolean showAllSecurityEmployees() {
        return this.getSchedulerInstance().showAllSecurityEmployees();
    }

    @Override
    public boolean createSchedule(String requestID) {
        return this.getSchedulerInstance().createSchedule(requestID);
    }

    @Override
    public boolean deleteSchedule(String scheduleID) {
        return this.getSchedulerInstance().deleteSchedule(scheduleID);
    }

    @Override
    public boolean editScheduleAssignments(String scheduleID) {
        return this.getSchedulerInstance().editScheduleAssignments(scheduleID);
    }

    @Override
    public boolean showAllSchedules() {
        return this.getSchedulerInstance().showAllSchedules();
    }

    @Override
    public boolean showScheduleByID(String ScheduleID) {
        return this.getSchedulerInstance().showScheduleByID(ScheduleID);
    }

    /**
     * To define one instance of the class SecurityScheduler to
     * call SecurityScheduler methods
     * @return SecurityScheduler instance
     */
    private SecurityScheduler getSchedulerInstance(){

        if (scheduler == null){
            scheduler = new SecurityScheduler();
        }
        return scheduler;
    }
}
