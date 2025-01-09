/**
 * @author Kenny
 */

package src.PublicRelations.src;

import src.App;

import java.util.Scanner;

public class ContentSchedulerController implements src.PublicRelations.src.interfaces.ContentSchedulerController {

    private ContentSchedulerController controller;

    private ContentScheduler scheduler = null;

    public ContentSchedulerController (ContentSchedulerController controller){
        this.controller = controller;
    }

    public ContentSchedulerController(){}

    /**
     * Run the security program
     * @throws Exception
     */
    public void run() throws Exception {

        System.out.println("Welcome to the Social Content Scheduler System");

        boolean exit = false;

        while (!exit){

            Scanner scan = new Scanner(System.in);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("Select An Action: ");
            System.out.println("1. Add New Public Relations Employee");
            System.out.println("2. Delete Public Relations Employee by ID");
            System.out.println();
            System.out.println("3. Show Available Planning Employees");
            System.out.println("4. Show All Planning Employees");
            System.out.println("5. Show Available Review Employees");
            System.out.println("6. Show All Review Employees");
            System.out.println();
            System.out.println("7. Show Pending Content Requests");
            System.out.println("8. Show All Content Requests");
            System.out.println();
            System.out.println("9. Create Content Schedule");
            System.out.println("10. Delete Content Schedule");
            System.out.println("11. Edit Existing Content Schedule Team Assignment");
            System.out.println("12. Edit Existing Content Schedule Status");
            System.out.println();
            System.out.println("13. Show All Content Schedules");
            System.out.println("14. Show Content Schedules In Planning");
            System.out.println("15. Show Content Schedules In Reviewing");
            System.out.println("16. Show Content Schedules In Revising");
            System.out.println("17. Show Content Schedules In Ready");
            System.out.println("18. Show Content Schedule By ID");
            System.out.println();
            System.out.println("0. Exit program");
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

            String choice = scan.nextLine();

            switch (choice){

                case "1" -> {
                    addPREmployee();
                    System.out.println();
                }
                case "2" -> {
                    System.out.println("Enter Public Relations Employee ID to delete: ");
                    String deleteID = scan.nextLine();
                    deletePREmployee(deleteID);
                    System.out.println();
                }
                case "3" -> {
                    showFreePlanningEmployees();
                    System.out.println();
                }
                case "4" -> {
                    showAllPlanningEmployees();
                    System.out.println();
                }
                case "5" -> {
                    showFreeReviewEmployees();
                    System.out.println();
                }
                case "6" -> {
                    showAllReviewEmployees();
                    System.out.println();
                }
                case "7" -> {
                    showPendingRequests();
                    System.out.println();
                }
                case "8" -> {
                    showAllRequests();
                    System.out.println();
                }
                case "9" -> {
                    System.out.println("Enter content request ID to make schedule for: ");
                    String requestID = scan.nextLine();
                    createContentSchedule(requestID);
                    System.out.println();
                }
                case "10" -> {
                    System.out.println("Enter content schedule ID to delete: ");
                    String deleteScheduleID = scan.nextLine();
                    deleteContentSchedule(deleteScheduleID);
                    System.out.println();
                }
                case "11" -> {
                    System.out.println("Enter content schedule ID to modify assignments: ");
                    String assignScheduleID = scan.nextLine();
                    editContentScheduleAssignment(assignScheduleID);
                    System.out.println();
                }
                case "12" -> {
                    System.out.println("Enter content schedule ID to modify status: ");
                    String statusScheduleID = scan.nextLine();
                    editContentScheduleStatus(statusScheduleID);
                    System.out.println();
                }
                case "13" -> {
                    showAllContentSchedules();
                    System.out.println();
                }
                case "14" -> {
                    showPlanningContentSchedules();
                    System.out.println();
                }
                case "15" -> {
                    showReviewingContentSchedules();
                    System.out.println();
                }
                case "16" -> {
                    showRevisingContentSchedules();
                    System.out.println();
                }
                case "17" -> {
                    showReadyContentSchedules();
                    System.out.println();
                }
                case "18" -> {
                    System.out.println("Enter content schedule ID to view: ");
                    String showScheduleID = scan.nextLine();
                    showContentScheduleByID(showScheduleID);
                    System.out.println();
                }
                case "0" -> {
                    System.out.println("Closing Program");
                    App.prompt();
                }
                default -> System.out.println("Invalid choice. Try again");
            }
        }
    }

    @Override
    public boolean addPREmployee() {
        return this.getSchedulerInstance().addPREmployee();
    }

    @Override
    public boolean deletePREmployee(String employeeID) {
        return this.getSchedulerInstance().deletePREmployee(employeeID);
    }

    @Override
    public boolean showFreePlanningEmployees() {
        return this.getSchedulerInstance().showFreePlanningEmployees();
    }

    @Override
    public boolean showAllPlanningEmployees() {
        return this.getSchedulerInstance().showAllPlanningEmployees();
    }

    @Override
    public boolean showFreeReviewEmployees() {
        return this.getSchedulerInstance().showFreeReviewEmployees();
    }

    @Override
    public boolean showAllReviewEmployees() {
        return this.getSchedulerInstance().showAllReviewEmployees();
    }

    @Override
    public boolean showPendingRequests() {
        return this.getSchedulerInstance().showPendingRequests();
    }

    @Override
    public boolean showAllRequests() {
        return this.getSchedulerInstance().showAllRequests();
    }

    @Override
    public boolean createContentSchedule(String requestID) {
        return this.getSchedulerInstance().createContentSchedule(requestID);
    }

    @Override
    public boolean deleteContentSchedule(String contentID) {
        return this.getSchedulerInstance().deleteContentSchedule(contentID);
    }

    @Override
    public boolean editContentScheduleAssignment(String contentID) {
        return this.getSchedulerInstance().editContentScheduleAssignment(contentID);
    }

    @Override
    public boolean editContentScheduleStatus(String contentID) {
        return this.getSchedulerInstance().editContentScheduleStatus(contentID);
    }

    @Override
    public boolean showAllContentSchedules() {
        return this.getSchedulerInstance().showAllContentSchedules();
    }

    @Override
    public boolean showPlanningContentSchedules() {
        return this.getSchedulerInstance().showPlanningContentSchedules();
    }

    @Override
    public boolean showReviewingContentSchedules() {
        return this.getSchedulerInstance().showReviewingContentSchedules();
    }

    @Override
    public boolean showRevisingContentSchedules() {
        return this.getSchedulerInstance().showRevisingContentSchedules();
    }

    @Override
    public boolean showReadyContentSchedules() {
        return this.getSchedulerInstance().showReadyContentSchedules();
    }

    @Override
    public boolean showContentScheduleByID(String contentID) {
        return this.getSchedulerInstance().showContentScheduleByID(contentID);
    }

    /**
     * To define one instance of the class ContentScheduler to
     * call ContentScheduler methods
     * @return ContentScheduler instance
     */
    private ContentScheduler getSchedulerInstance(){

        if (scheduler == null){
            scheduler = new ContentScheduler();
        }
        return scheduler;
    }
}