/**
 * @author Kenny
 */

package src.PublicRelations.src;

import src.App;

import java.util.Scanner;

public class ActivitySchedulerController implements src.PublicRelations.src.interfaces.ActivitySchedulerController {

    private ActivitySchedulerController controller;

    private ActivityScheduler scheduler = null;

    public ActivitySchedulerController (ActivitySchedulerController controller){
        this.controller = controller;
    }

    public ActivitySchedulerController(){};

    /**
     * Run the security program
     * @throws Exception
     */
    public void run() throws Exception {

        System.out.println("Welcome to the Social Activity Scheduler System");

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
            System.out.println("7. Create Activity Schedule");
            System.out.println("8. Delete Activity Schedule");
            System.out.println("9. Edit Existing Activity Schedule");
            System.out.println();
            System.out.println("10. Show All Activity Schedules");
            System.out.println("11. Show Activity Schedules In Damage Control");
            System.out.println("12. Show Activity Schedules In Planning");
            System.out.println("13. Show Activity Schedules In Reviewing");
            System.out.println("14. Show Activity Schedules In Revising");
            System.out.println("15. Show Activity Schedules In Ready");
            System.out.println("16. Show Activity Schedule By ID");
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
                    createActivity();
                    System.out.println();
                }
                case "8" -> {
                    System.out.println("Enter activity schedule ID to delete: ");
                    String deleteScheduleID = scan.nextLine();
                    deleteActivity(deleteScheduleID);
                    System.out.println();
                }
                case "9" -> {
                    System.out.println("Enter activity schedule ID to modify: ");
                    String activityID = scan.nextLine();
                    editActivity(activityID);
                    System.out.println();
                }
                case "10" -> {
                    showAllActivities();
                    System.out.println();
                }
                case "11" -> {
                    showDamageControlActivities();
                    System.out.println();
                }
                case "12" -> {
                    showPlanningActivities();
                    System.out.println();
                }
                case "13" -> {
                    showReviewingActivities();
                    System.out.println();
                }
                case "14" -> {
                    showRevisingContents();
                    System.out.println();
                }
                case "15" -> {
                    showReadyContents();
                    System.out.println();
                }
                case "16" -> {
                    System.out.println("Enter activity schedule ID to view: ");
                    String activityViewID = scan.nextLine();
                    showActivityByID(activityViewID);
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
    public boolean createActivity() {
        return this.getSchedulerInstance().createActivity();
    }

    @Override
    public boolean deleteActivity(String activityID) {
        return this.getSchedulerInstance().deleteActivity(activityID);
    }

    @Override
    public boolean editActivity(String activityID) {
        return this.getSchedulerInstance().editActivity(activityID);
    }

    @Override
    public boolean showAllActivities() {
        return this.getSchedulerInstance().showAllActivities();
    }

    @Override
    public boolean showDamageControlActivities() {
        return this.getSchedulerInstance().showDamageControlActivities();
    }

    @Override
    public boolean showPlanningActivities() {
        return this.getSchedulerInstance().showPlanningActivities();
    }

    @Override
    public boolean showReviewingActivities() {
        return this.getSchedulerInstance().showReviewingActivities();
    }

    @Override
    public boolean showRevisingContents() {
        return this.getSchedulerInstance().showRevisingContents();
    }

    @Override
    public boolean showReadyContents() {
        return this.getSchedulerInstance().showReadyContents();
    }

    @Override
    public boolean showActivityByID(String activityID) {
        return this.getSchedulerInstance().showActivityByID(activityID);
    }

    /**
     * To define one instance of the class ActivityScheduler to
     * call ActivityScheduler methods
     * @return ActivityScheduler instance
     */
    private ActivityScheduler getSchedulerInstance(){

        if (scheduler == null){
            scheduler = new ActivityScheduler();
        }
        return scheduler;
    }
}
