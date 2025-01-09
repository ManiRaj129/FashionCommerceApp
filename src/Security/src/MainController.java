/**
 * @author Kenny
 */

package src.Security.src;

import src.App;

import java.util.Scanner;

public class MainController {

    private MainController controller;

    /**
     * Operation selection
     * @throws Exception
     */
    public void run() throws Exception {

        System.out.println("Welcome to Department of Security Management System.\nWhat would like to do?");

        Scanner scan = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {

            System.out.println("1. Enter On-site Security Management System");
            System.out.println("2. Enter Audit Security Management System");
            System.out.println("0. Exit program");

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {

                case 1:
                    SecuritySchedulerController securitySchedulerController = new SecuritySchedulerController();
                    securitySchedulerController.run();
                    break;
                case 2:
                    AuditSchedulerController auditSchedulerController = new AuditSchedulerController();
                    auditSchedulerController.run();
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
}
