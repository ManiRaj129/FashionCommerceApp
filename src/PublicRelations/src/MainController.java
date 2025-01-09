/**
 * @author Kenny
 */

package src.PublicRelations.src;

import src.App;
import src.Security.src.AuditSchedulerController;
import src.Security.src.SecuritySchedulerController;

import java.util.Scanner;

public class MainController {

    private MainController controller;

    /**
     * Operation selection
     * @throws Exception
     */
    public void run() throws Exception {

        System.out.println("Welcome to the Department of Public Relations System.\nWhat would you like to do?");

        Scanner scan = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {

            System.out.println("1. Enter Social Content Management System");
            System.out.println("2. Enter Social Activity Management System");
            System.out.println("0. Exit program");

            String choice = scan.nextLine();

            switch (choice) {

                case "1":
                    ContentSchedulerController contentSchedulerController = new ContentSchedulerController();
                    contentSchedulerController.run();
                    break;
                case "2":
                    ActivitySchedulerController activitySchedulerController = new ActivitySchedulerController();
                    activitySchedulerController.run();
                    break;
                case "0":
                    System.out.println("Closing program...");
                    exit = true;
                    App.prompt();
                default:
                    System.out.println("Invalid choice. Try again");
            }
        }
    }
}
