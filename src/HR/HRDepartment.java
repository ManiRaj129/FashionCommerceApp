package src.HR;

import src.App;
import src.HR.src.*;
import src.Security.src.SecurityRequestScheduler;

import java.nio.file.Path;

public class HRDepartment {
    fileStorageHR storage = new fileStorageHR();
    employeeRecordManager empHandler = new employeeRecordManager();
    candidateRecordManager canHandler = new candidateRecordManager();
    hiringProcess hireHandler = new hiringProcess();
    valueHandling valHandler = new valueHandling();

    /**
     *
     * @throws Exception from internal mechanisms
     */
    public void start() throws Exception {
        boolean loop = true;
        boolean firstTime = true;

        //start of user interaction
        while(loop) {
            if(firstTime) {
                System.out.println("\n\n\n");
                firstTime = false;
            }
            /*
            TODO:
                - branch major decisions together (i.e. first decision tree is employee actions...)
                - Replace with container system so i dont have to deal with the switch cases
                - Add in method to transfer Candidate to Employee
                - interview process
                - resume process
                - extract creation process from candidate to information expert
                - unify employee data processing
             */
            System.out.println("Welcome to the HR Department!");
            System.out.println("Please choose from these options:");

            //employee
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. Retrieve Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Display All Employees");
            System.out.println("6. Display All Employees By Department");

            //candidate
            System.out.println("7. Add Candidate");
            System.out.println("8. Remove Candidate");
            System.out.println("9. Retrieve Candidate");
            System.out.println("10. Update Candidate or Add Resume Key Notes");
            System.out.println("11. Display All Candidates");
            System.out.println("12. Display All Candidates By Status");

            //interview
            System.out.println("13. Create Interview Time Slot");
            System.out.println("14. Display Individual Interview Time Slot by ID");
            System.out.println("15. Edit Existing Interview Time Slot");
            System.out.println("16. Delete Existing Interview Time Slot");
            System.out.println("17. Display Interview Time Slots");

            SecurityRequestScheduler scheduler = new SecurityRequestScheduler();
            scheduler.optionsPrint();



            System.out.println("0. Exit");



            int choice = Integer.parseInt(valHandler.inputValidator(false));
            switch (choice) {
                case 1: //add employee
                    empHandler.addEmployee();
                    System.out.println("\n\n\n\n");
                    break;

                case 2: //remove employee
                    System.out.println("Enter employee ID: ");
                    String markedEmployee = valHandler.inputValidator(true);
                    empHandler.removeEmployee(markedEmployee);
                    System.out.println("Employee removed successfully!\n\n\n\n");
                    break;

                case 3: //retrieve employee
                    System.out.println("Enter Employee ID: ");
                    String markedEmpID = valHandler.inputValidator(true);
                    Path currentEmpFile = empHandler.findEmployeeFile(markedEmpID);
                    storage.loadFileAndPrint(currentEmpFile.toString());
                    System.out.println("Employee retrieved successfully!\n\n\n\n");
                    break;

                case 4: //update employee
                    System.out.println("Enter employee ID: ");
                    String updateEmployeeID = valHandler.inputValidator(true);
                    empHandler.updateEmployee(updateEmployeeID);
                    System.out.println("Employee updated successfully!\n\n\n\n");
                    break;

                case 5: //display all employees
                    System.out.println("List of All Employees: ");
                    for (Department department1 : Department.values()) {
                        empHandler.displayEmployeesByDepartment(department1);
                    }
                    System.out.println("\nEND OF LIST, Returning to menu...\n\n\n\n");
                    break;

                case 6: //display employees in a department
                    System.out.print("Please enter Department folder to list: ");
                    Department departmentFolder = Department.valueOf(valHandler.inputValidator(true).toUpperCase());
                    System.out.println();
                    empHandler.displayEmployeesByDepartment(departmentFolder);
                    System.out.println("\nEND OF LIST, Returning to menu...\n\n\n\n");
                    break;

                case 7: //add candidate
                    System.out.println("Enter candidate name: ");
                    String candidateName = valHandler.inputValidator(true);
                    String candidateId = String.valueOf(canHandler.generateID());
                    System.out.println("Enter position candidate applied for: ");
                    String positionApplied = valHandler.inputValidator(true);
                    System.out.print("Enter candidate status: \nApplied\nApproved\nHiring\nPending\nRejected\nEnter Here: ");
                    candidateStatus candidateStatus = src.HR.src.candidateStatus.valueOf(valHandler.inputValidator(true).toUpperCase());
                    Candidate newCandidate = new Candidate(candidateId, candidateName, positionApplied, candidateStatus);
                    canHandler.addCandidate(newCandidate);
                    System.out.println("\n\n\n\n");
                    break;

                case 8: //remove candidate
                    System.out.println("Enter candidate ID to be removed: ");
                    String candidateID = valHandler.inputValidator(true);
                    canHandler.removeCandidate(candidateID);
                    System.out.println("Candidate removed successfully!\n\n\n\n");
                    break;

                case 9: //retrieve candidate by id
                    System.out.println("Enter Candidate ID: ");
                    String candidateID3 = valHandler.inputValidator(true);
                    Path currentCandidateFile = canHandler.findCandidateFile(candidateID3);
                    storage.loadFileAndPrint(currentCandidateFile.toString());
                    break;

                case 10: //update candidate
                    System.out.println("Edit Candidate Information [1]");
                    System.out.println("Add Resume Key Notes       [2]");
                    String response = valHandler.inputValidator(true);
                    if(response.strip().equals("1")) {
                        System.out.println("Enter Candidate ID: ");
                        String candidateID2 = valHandler.inputValidator(true);
                        canHandler.updateCandidate(candidateID2);
                    }
                    else if (response.strip().equals("2")) {
                        System.out.println("Please enter CandidateID: ");
                        String candID4 = valHandler.inputValidator(true);
                        hireHandler.enterResumeData(candID4);
                    }
                    System.out.println("\nReturning to menu\n\n\n\n");
                    break;

                case 11: //list all candidates
                    System.out.println("List of All Candidates: ");
                    for (src.HR.src.candidateStatus candidate : src.HR.src.candidateStatus.values()) {
                        canHandler.displayCandidatesByStatus(String.valueOf(candidate));
                    }
                    System.out.println("\nEND OF LIST, Returning to menu...\n\n\n\n");
                    break;

                case 12: //list candidates by status
                    System.out.print("Please enter Status folder to list: ");
                    //TODO: list Status folders
                    String statusFolder = valHandler.inputValidator(true).toUpperCase();
                    System.out.println();
                    canHandler.displayCandidatesByStatus(statusFolder);
                    System.out.println("\nEND OF LIST, Returning to menu...\n\n\n\n");
                    break;

                case 13: //create interview
                    hireHandler.createInterview();
                    System.out.println("\n\n\n\n");
                    break;

                case 14: //display interview
                    System.out.println("Enter Interview ID: ");
                    String interviewID = valHandler.inputValidator(true);
                    hireHandler.printInterview(interviewID);
                    System.out.println("\nEND OF LIST, Returning to menu...\n\n\n\n");
                    break;

                case 15: //edit interview TODO
                    hireHandler.editInterview();
                    break;

                case 16: //delete interview TODO
                    System.out.println("Enter Interview ID: ");
                    String interviewID2 = valHandler.inputValidator(true);
                    hireHandler.deleteInterview(interviewID2);
                    break;

                case 17: //display interviews
                    System.out.println("Display All Interviews [1]");
                    System.out.println("Display Interviews by Interviewer [2]");
                    String answer = valHandler.inputValidator(true);
                    if (answer.strip().equals("1")) {
                        hireHandler.printAllInterviews();
                    }
                    else if(answer.strip().equals("2")) {
                        System.out.println("Please enter Interviewer: ");
                        String interviewerID = valHandler.inputValidator(true);
                        hireHandler.printAllInterviewsByInterviewer(interviewerID);
                    }
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
                    loop = false;
                    System.out.println("EXITING...");
                    App.prompt(); //<---- Kicks you back to the main homepage
                    break;
            }
        }
    }

    /**
     *
     * @param employeeID String ID of the employee to be returned.
     * @return returns a complete Employee object
     * @throws Exception if getEmployee fails to return a valid object
     */
    public Employee getEmployee(String employeeID) throws Exception {
        return empHandler.getEmployee(employeeID);
    }
}
