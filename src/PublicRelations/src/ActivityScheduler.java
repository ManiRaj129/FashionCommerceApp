/**
 * @author Kenny
 */

package src.PublicRelations.src;

import src.TextEditor.PoorTextEditor;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ActivityScheduler implements src.PublicRelations.src.interfaces.ActivityScheduler {

    private final String PRPlanningEmployeesDir = "src/PublicRelations/repository/planningEmployees/";
    private final String PRReviewEmployeesDir = "src/PublicRelations/repository/reviewEmployees/";
    private final String activityScheduleDir = "src/PublicRelations/repository/activitySchedules/";
    private final String printedActivitySchedulesDir = "src/PublicRelations/repository/printedActivitySchedules/";

    PoorTextEditor editor = new PoorTextEditor();

    // sorts content schedules by deadline first, then by status
    PriorityQueue<ActivitySchedules> damageControlActivitySchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ActivitySchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparing(ActivitySchedules::getStatus)
    );

    PriorityQueue<ActivitySchedules> planningActivitySchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ActivitySchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparing(ActivitySchedules::getStatus)
    );

    PriorityQueue<ActivitySchedules> reviewingActivitySchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ActivitySchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparing(ActivitySchedules::getStatus)
    );

    PriorityQueue<ActivitySchedules> revisingActivitySchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ActivitySchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparing(ActivitySchedules::getStatus)
    );

    PriorityQueue<ActivitySchedules> readyActivitySchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ActivitySchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparing(ActivitySchedules::getStatus)
    );

    PriorityQueue<ActivitySchedules> allActivitySchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ActivitySchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparing(ActivitySchedules::getStatus)
    );
    /**
     * Repository to store all content schedules
     */
    Map<String, ActivitySchedules> activitySchedulesRepository = new LinkedHashMap<>();

    // sorts employees by division, then position, then rating in descending order
    PriorityQueue<PRPlanningEmployee> availablePRPlanningEmployeePriorityQueue = new PriorityQueue<>(
            Comparator.comparing(PRPlanningEmployee::getDivision)
                    .thenComparing(PRPlanningEmployee::getPosition)
                    .thenComparing((e1, e2) -> e2.getRating().compareTo(e1.getRating()))
    );
    PriorityQueue<PRPlanningEmployee> allPRPlanningEmployeePriorityQueue = new PriorityQueue<>(
            Comparator.comparing(PRPlanningEmployee::getDivision)
                    .thenComparing(PRPlanningEmployee::getPosition)
                    .thenComparing((e1, e2) -> e2.getRating().compareTo(e1.getRating()))
    );
    PriorityQueue<PRReviewEmployee> availablePRReviewEmployeePriorityQueue = new PriorityQueue<>(
            Comparator.comparing(PRReviewEmployee::getDivision)
                    .thenComparing(PRReviewEmployee::getPosition)
                    .thenComparing((e1, e2) -> e2.getRating().compareTo(e1.getRating()))
    );
    PriorityQueue<PRReviewEmployee> allPRReviewEmployeePriorityQueue = new PriorityQueue<>(
            Comparator.comparing(PRReviewEmployee::getDivision)
                    .thenComparing(PRReviewEmployee::getPosition)
                    .thenComparing((e1, e2) -> e2.getRating().compareTo(e1.getRating()))
    );
    /**
     * Repository to store all PR employees
     */
    Map<String, PRPlanningEmployee> PRPlanningEmployeeRepository = new LinkedHashMap<>();
    Map<String, PRReviewEmployee> PRReviewEmployeeRepository = new LinkedHashMap<>();
    
    @Override
    public boolean addPREmployee() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Choose to add (Planning) or (Review) employee");
        String choice = scan.nextLine();
        String directoryInUse;
        String employeeIdPrefix;
        String fileNameInUse;

        switch (choice){

            case "Planning" -> {
                directoryInUse = PRPlanningEmployeesDir;
                employeeIdPrefix = "DoPR_Employee_Planning_";
                fileNameInUse = "PRPlanningEmployeeList.txt";
            }

            case "Review" -> {
                directoryInUse = PRReviewEmployeesDir;
                employeeIdPrefix = "DoPR_Employee_Review_";
                fileNameInUse = "PRReviewEmployeeList.txt";
            }
            default -> {
                System.out.println("No such employee");
                return false;
            }
        }

        Map<String, Object> employeeHashMap = retrieveEmployeeHashmap(directoryInUse);

        if (employeeHashMap == null){
            return false;
        }

        String latestEmployeeID = null;
        for (String ID : employeeHashMap.keySet()){
            latestEmployeeID = ID;
        }

        String newEmployeeID = "-1";
        if (latestEmployeeID != null){
            newEmployeeID = latestEmployeeID.substring(latestEmployeeID.length() - 4);
        }

        String ID = String.format("%04d", Integer.parseInt(newEmployeeID) + 1);
        newEmployeeID = employeeIdPrefix + ID;

        Map<String, Object> newEmployeeData = new LinkedHashMap<>();

        System.out.println("Adding employee under ID: " + newEmployeeID);
        System.out.println("Enter employee name: ");
        newEmployeeData.put("name", scan.nextLine());

        System.out.println("Enter employee division: ");
        newEmployeeData.put("division", scan.nextLine());

        System.out.println("Enter employee position: ");
        newEmployeeData.put("position", scan.nextLine());

        newEmployeeData.put("department", "Department of Public Relations");

        System.out.println("Enter employee rating: (0.0 - 5.0)");
        newEmployeeData.put("rating", scan.nextLine());

        System.out.println("Enter employee years of experience: ");
        newEmployeeData.put("yearsOfExperience", scan.nextLine());

        newEmployeeData.put("previousAssignment", "free");
        newEmployeeData.put("currentAssignment", "free");

        employeeHashMap.put(newEmployeeID, newEmployeeData);
        editor.setRepository(employeeHashMap);
        editor.writeToTextFile(directoryInUse + fileNameInUse);
        return true;
    }

    @Override
    public boolean deletePREmployee(String employeeID) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Choose to delete (Planning) or (Review) employee");
        String choice = scan.nextLine();
        String directoryInUse;
        String fileNameInUse;

        switch (choice){

            case "Planning" -> {
                directoryInUse = PRPlanningEmployeesDir;
                fileNameInUse = "PRPlanningEmployeeList.txt";
            }

            case "Review" -> {
                directoryInUse = PRReviewEmployeesDir;
                fileNameInUse = "PRReviewEmployeeList.txt";
            }
            default -> {
                System.out.println("No such employee");
                return false;
            }
        }

        Map<String, Object> employeeHashMap = retrieveEmployeeHashmap(directoryInUse);

        if (employeeHashMap == null){
            return false;
        }

        if (!employeeHashMap.containsKey(employeeID)){

            System.out.println("Employee with ID: " + employeeID + " not found");
            return false;
        }

        editor.setRepository(employeeHashMap);
        if (!editor.retrieveValue(employeeID, "currentAssignment").equals("free")){

            System.out.println("Employee is already assigned to a task. Unassign first.");
            return false;
        }

        editor.removeArrayItem(employeeID);
        editor.writeToTextFile(directoryInUse + fileNameInUse);
        System.out.println("Employee with ID: " + employeeID + " successfully deleted");
        return true;
    }

    @Override
    public boolean showFreePlanningEmployees() {

        if (!retrieveAllPREmployees()){
            return false;
        }

        PriorityQueue<PRPlanningEmployee> tempQueue = new PriorityQueue<>(availablePRPlanningEmployeePriorityQueue);

        while (!tempQueue.isEmpty()){
            planningEmployeeToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showAllPlanningEmployees() {

        if (!retrieveAllPREmployees()){
            return false;
        }

        PriorityQueue<PRPlanningEmployee> tempQueue = new PriorityQueue<>(allPRPlanningEmployeePriorityQueue);

        while (!tempQueue.isEmpty()){
            planningEmployeeToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showFreeReviewEmployees() {

        if (!retrieveAllPREmployees()){
            return false;
        }

        PriorityQueue<PRReviewEmployee> tempQueue = new PriorityQueue<>(availablePRReviewEmployeePriorityQueue);

        while (!tempQueue.isEmpty()){
            reviewEmployeeToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showAllReviewEmployees() {

        if (!retrieveAllPREmployees()){
            return false;
        }

        PriorityQueue<PRReviewEmployee> tempQueue = new PriorityQueue<>(allPRReviewEmployeePriorityQueue);

        while (!tempQueue.isEmpty()){
            reviewEmployeeToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean createActivity() {

        Scanner scan = new Scanner(System.in);

        String scheduleID = "socialActivity-" + IDGenerator();

        System.out.println("Enter social activity status: (Recommended: No status | Damage Control |---| Others: Planning | Reviewing | Revising | Ready)");
        String status = scan.nextLine();

        String department = "Department of Public Relations";

        System.out.println("Enter social activity name: ");
        String activityName = scan.nextLine();

        System.out.println("Enter social activity type: " );
        String activityType = scan.nextLine();

        System.out.println("Enter social activity objective: ");
        String objective = scan.nextLine();

        System.out.println("Enter social activity target audience: ");
        String targetAudience = scan.nextLine();

        System.out.println("Enter social activity description: ");
        String description = scan.nextLine();

        System.out.println("Enter social activity tasks: ");
        String tasks = scan.nextLine();

        System.out.println("Enter social activity location: (Country - City - Specific Location)");
        String location = scan.nextLine();

        System.out.println("Enter social activity duration: (MM-dd-yyyy - MM-dd-yyyy)");
        String duration = scan.nextLine();

        System.out.println("Enter social activity deadline: (MM-dd-yyyy)");
        String deadline = scan.nextLine();

        String dateScheduled = dateIssuer();

        ActivitySchedules schedule = new ActivitySchedules(
                scheduleID,
                activityName,
                activityType,
                objective,
                targetAudience,
                description,
                tasks,
                department,
                location,
                duration,
                deadline,
                status,
                dateScheduled,
                null);

        // assigning planning employee to schedule
        if (!addPlanningEmployeeToSchedule(schedule)){

            System.out.println("Planning employee assignment for activity schedule ID: " + schedule.getScheduleID() + " was cancelled");
            return false;
        }

        // assigning review employee to schedule
        if (!addReviewEmployeeToSchedule(schedule)){

            System.out.println("Review employee assignment for activity schedule ID: " + schedule.getScheduleID() + " was cancelled");
            return false;
        }

        // writing schedule to repository
        editor.setRepository(activityScheduleToHashmap(schedule));
        editor.writeToTextFile(activityScheduleDir + schedule.getScheduleID() + ".txt");

        // writing printed schedule to repository
        printActivitySchedule(schedule);

        return true;
    }

    @Override
    public boolean deleteActivity(String activityID) {

        if (!retrieveAllActivities()) return false;

        if (!retrieveAllPREmployees()) return false;

        if (!activitySchedulesRepository.containsKey(activityID)){

            System.out.println("Activity schedule ID: " + activityID + " not found");
            return false;
        }

        ActivitySchedules schedule = activitySchedulesRepository.get(activityID);

        if (schedule.getPlanningTeamAssign().isEmpty()|| schedule.getReviewTeamAssign().isEmpty()){

            System.out.println("Incomplete employee assignment for activity schedule ID: " + activityID);
            return false;
        }

        /**
         * Freeing planning employees current assignment
         */
        List<String> planningEmployees = schedule.getPlanningTeamAssign();
        editor.processTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");
        for (String s : planningEmployees){
            editor.setValue(s, "currentAssignment", "free");
        }
        editor.writeToTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");

        /**
         * Freeing review employees current assignment
         */
        List<String> reviewEmployees = schedule.getReviewTeamAssign();
        editor.processTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");
        for (String s : reviewEmployees){
            editor.setValue(s, "currentAssignment", "free");
        }
        editor.writeToTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");

        /**
         * Deleting content schedule
         */
        File fileToDelete = new File(activityScheduleDir, schedule.getFileName());

        if (fileToDelete.exists()){

            if (fileToDelete.delete()){

                System.out.println("Activity schedule of ID: " + activityID + " successfully deleted");
            }
            else {

                System.out.println("Failed to delete activity schedule ID: " + activityID);
                return false;
            }
        }
        else {

            System.out.println("Activity schedule with ID: " + activityID + " not found");
            return false;
        }
        return true;
    }

    @Override
    public boolean editActivity(String activityID) {

        if (!retrieveAllActivities()){
            return false;
        }

        if (!activitySchedulesRepository.containsKey(activityID)){

            System.out.println("Activity schedule with ID: " + activityID + " does not exists");
            return false;
        }

        ActivitySchedules schedule = activitySchedulesRepository.get(activityID);

        Scanner scan = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Editing activity schedule ID: " + schedule.getScheduleID());
        while (!exit){

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("Select a category to edit: ");
            System.out.println("1. Activity Name | Current: " + schedule.getActivityName());
            System.out.println("2. Activity Type | Current: " + schedule.getActivityType());
            System.out.println("3. Objective | Current: " + schedule.getObjective());
            System.out.println("4. Target Audience | Current: " + schedule.getTargetAudience());
            System.out.println("5. Description | Current: " + schedule.getDescription());
            System.out.println("6. Tasks | Current: " + schedule.getTasks());
            System.out.println();
            System.out.println("7. Location | Current: " + schedule.getLocation());
            System.out.println("8. Duration | Current: " + schedule.getDuration());
            System.out.println("9. Deadline | Current: " + schedule.getDeadline());
            System.out.println("10. Status | Current: " + schedule.getStatus());
            System.out.println("11. Planning Team Assignment");
            System.out.println("12. Review Team Assignment");
            System.out.println("0. Save & Exit");
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");

            String choice = scan.nextLine();

            switch (choice) {

                case "1" -> {
                    System.out.println("Enter social activity name: ");
                    schedule.setActivityName(scan.nextLine());
                }
                case "2" -> {
                    System.out.println("Enter social activity type: " );
                    schedule.setActivityType(scan.nextLine());
                }
                case "3" -> {
                    System.out.println("Enter social activity objective: ");
                    schedule.setObjective(scan.nextLine());
                }
                case "4" -> {
                    System.out.println("Enter social activity target audience: ");
                    schedule.setTargetAudience(scan.nextLine());
                }
                case "5" -> {
                    System.out.println("Enter social activity description: ");
                    schedule.setDescription(scan.nextLine());
                }
                case "6" -> {
                    System.out.println("Enter social activity tasks: ");
                    schedule.setTasks(scan.nextLine());
                }
                case "7" -> {
                    System.out.println("Enter social activity location: (Country - City - Specific Location)");
                    schedule.setLocation(scan.nextLine());
                }
                case "8" -> {
                    System.out.println("Enter social activity duration: (MM-dd-yyyy - MM-dd-yyyy)");
                    schedule.setDuration(scan.nextLine());
                }
                case "9" -> {
                    System.out.println("Enter social activity deadline: (MM-dd-yyyy)");
                    schedule.setDeadline(scan.nextLine());
                }
                case "10" -> {
                    System.out.println("Enter social activity status: (No status | Damage Control | Planning | Reviewing | Revising | Ready)");
                    schedule.setStatus(scan.nextLine());
                }
                case "11" -> {
                    addPlanningEmployeeToSchedule(schedule);
                }
                case "12" -> {
                    addReviewEmployeeToSchedule(schedule);
                }
                case "0" -> {
                    exit = true;
                }
                default -> System.out.println("Invalid choice. Try again");
            }
        }

        // writing schedule to repository
        editor.setRepository(activityScheduleToHashmap(schedule));
        editor.writeToTextFile(activityScheduleDir + schedule.getFileName());

        // writing printed schedule to repository
        printActivitySchedule(schedule);

        System.out.println("Update successful for activity schedule ID: " + activityID);
        return true;
    }

    @Override
    public boolean showAllActivities() {

        if (!retrieveAllActivities()){
            return false;
        }

        if (allActivitySchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ActivitySchedules> tempQueue = new PriorityQueue<>(allActivitySchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            activityScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showDamageControlActivities() {

        if (!retrieveAllActivities()){
            return false;
        }

        if (damageControlActivitySchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ActivitySchedules> tempQueue = new PriorityQueue<>(damageControlActivitySchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            activityScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showPlanningActivities() {

        if (!retrieveAllActivities()){
            return false;
        }

        if (planningActivitySchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ActivitySchedules> tempQueue = new PriorityQueue<>(planningActivitySchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            activityScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showReviewingActivities() {

        if (!retrieveAllActivities()){
            return false;
        }

        if (reviewingActivitySchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ActivitySchedules> tempQueue = new PriorityQueue<>(reviewingActivitySchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            activityScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showRevisingContents() {

        if (!retrieveAllActivities()){
            return false;
        }

        if (revisingActivitySchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ActivitySchedules> tempQueue = new PriorityQueue<>(revisingActivitySchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            activityScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showReadyContents() {

        if (!retrieveAllActivities()){
            return false;
        }

        if (readyActivitySchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ActivitySchedules> tempQueue = new PriorityQueue<>(readyActivitySchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            activityScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showActivityByID(String activityID) {

        if (!retrieveAllActivities()){
            return false;
        }

        if (!activitySchedulesRepository.containsKey(activityID)){

            System.out.println("Activity schedule with ID: " + activityID + " not found");
            return false;
        }

        activityScheduleToText(activitySchedulesRepository.get(activityID));
        return true;
    }

    /**
     * To add planning employee to a schedule
     * @param schedule given schedule
     * @return true if successful, false otherwise
     */
    private boolean addPlanningEmployeeToSchedule(ActivitySchedules schedule){

        if (!retrieveAllPREmployees()){
            return false;
        }

        List<String> selectedPlanningEmployeeIDs = new ArrayList<>();
        if (schedule.getPlanningTeamAssign() != null && !schedule.getPlanningTeamAssign().isEmpty()){
            selectedPlanningEmployeeIDs = schedule.getPlanningTeamAssign();
        }

        Scanner scan = new Scanner(System.in);

        boolean endProgram = false;
        boolean returnValue = false;

        while (!endProgram){

            System.out.println("Planning Employee Assignment Manager: ");
            System.out.println("1. Add Planning Employee by ID");
            System.out.println("2. Remove Planning Employee by ID");
            System.out.println("3. Show Scheduled Planning Employees");
            System.out.println("4. Complete Schedule");
            System.out.println("5. Cancel Schedule");
            System.out.println("0. Exit");

            String choice = scan.nextLine();

            switch (choice){

                case "1" -> {

                    System.out.println("Enter planning employee ID: ");
                    String employeeID = scan.nextLine();

                    if (!PRPlanningEmployeeRepository.containsKey(employeeID)){

                        System.out.println("Planning employee with ID: " + employeeID + " does not exists");
                        break;
                    }

                    PRPlanningEmployee employee = PRPlanningEmployeeRepository.get(employeeID);

                    if (!employee.getCurrentAssignment().equals("free")){

                        System.out.println("This employee has already been assigned to :" + employee.getCurrentAssignment() + "\n" +
                                "Overwrite? (y/n)\n" +
                                "!!!WARNING!!! This process is irreversible!");

                        String yesNo = scan.nextLine();

                        if (yesNo.equals("y")){

                            employee.setPreviousAssignment(employee.getCurrentAssignment());
                            employee.setCurrentAssignment(schedule.getScheduleID());

                            if (!selectedPlanningEmployeeIDs.contains(employee.getEmployeeID())){

                                selectedPlanningEmployeeIDs.add(employee.getEmployeeID());
                            }

                            // updating employee assignments to file
                            editor.processTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");
                            editor.setValue(employee.getEmployeeID(), "previousAssignment", employee.getPreviousAssignment());
                            editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                            editor.writeToTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");
                        }
                    }
                    else {

                        availablePRPlanningEmployeePriorityQueue.remove(employee);
                        employee.setCurrentAssignment(schedule.getScheduleID());
                        selectedPlanningEmployeeIDs.add(employee.getEmployeeID());

                        // updating employee assignments to file
                        editor.processTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");
                        editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                        editor.writeToTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");
                    }
                }
                case "2" -> {

                    if (selectedPlanningEmployeeIDs.isEmpty()){

                        System.out.println("No planning employees to remove from this schedule");
                        break;
                    }

                    System.out.println("Enter planning employee ID to remove: ");
                    String removeID = scan.nextLine();

                    if (!selectedPlanningEmployeeIDs.contains(removeID)){

                        System.out.println("Planning employee ID: " + removeID + " not assigned to this schedule");
                        break;
                    }

                    selectedPlanningEmployeeIDs.remove(removeID);
                    PRPlanningEmployee employee = PRPlanningEmployeeRepository.get(removeID);
                    employee.setCurrentAssignment("free");
                    availablePRPlanningEmployeePriorityQueue.add(employee);

                    // updating employee assignments to file
                    editor.processTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");
                    editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                    editor.writeToTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");
                }
                case "3" -> {

                    if (selectedPlanningEmployeeIDs.isEmpty()){

                        System.out.println("No planning employees to show from this schedule");
                        break;
                    }

                    System.out.println("Selected planning employees by ID: ");
                    for (String s : selectedPlanningEmployeeIDs){
                        System.out.println(s);
                    }
                    System.out.println();
                }
                case "4" -> {
                    if (selectedPlanningEmployeeIDs.isEmpty()){

                        System.out.println("Have at least one assigned employee");
                        break;
                    }

                    schedule.setPlanningTeamAssign(selectedPlanningEmployeeIDs);
                    System.out.println("Planning assignment created");
                    endProgram = true;
                    returnValue = true;
                }
                case "5" -> {

                    for (String s : selectedPlanningEmployeeIDs){

                        PRPlanningEmployee resetEmployee = PRPlanningEmployeeRepository.get(s);
                        resetEmployee.setCurrentAssignment("free");
                        availablePRPlanningEmployeePriorityQueue.add(resetEmployee);

                        // updating employee assignment to files
                        editor.processTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");
                        editor.setValue(resetEmployee.getEmployeeID(), "currentAssignment", resetEmployee.getCurrentAssignment());
                        editor.writeToTextFile(PRPlanningEmployeesDir + "PRPlanningEmployeeList.txt");
                    }
                    return returnValue;
                }
                case "0" -> {

                    if (selectedPlanningEmployeeIDs.isEmpty()){

                        endProgram = true;
                    }
                    else {

                        System.out.println("Remove all assigned planning employees or cancel before exiting");
                    }
                }
                default -> System.out.println("Invalid choice. Try again");
            }
        }
        return returnValue;
    }

    /**
     * To add review employee to a schedule
     * @param schedule given schedule
     * @return true if successful, false otherwise
     */
    private boolean addReviewEmployeeToSchedule(ActivitySchedules schedule){

        if (!retrieveAllPREmployees()){
            return false;
        }

        List<String> selectedReviewEmployeeIDs = new ArrayList<>();
        if (schedule.getReviewTeamAssign() != null && !schedule.getReviewTeamAssign().isEmpty()){
            selectedReviewEmployeeIDs = schedule.getReviewTeamAssign();
        }

        Scanner scan = new Scanner(System.in);

        boolean endProgram = false;
        boolean returnValue = false;

        while (!endProgram){

            System.out.println("Review Employee Assignment Manager: ");
            System.out.println("1. Add Review Employee by ID");
            System.out.println("2. Remove Review Employee by ID");
            System.out.println("3. Show Scheduled Review Employees");
            System.out.println("4. Complete Schedule");
            System.out.println("5. Cancel Schedule");
            System.out.println("0. Exit");

            String choice = scan.nextLine();

            switch (choice){

                case "1" -> {

                    System.out.println("Enter review employee ID: ");
                    String employeeID = scan.nextLine();

                    if (!PRReviewEmployeeRepository.containsKey(employeeID)){

                        System.out.println("Review employee with ID: " + employeeID + " does not exists");
                        break;
                    }

                    PRReviewEmployee employee = PRReviewEmployeeRepository.get(employeeID);

                    if (!employee.getCurrentAssignment().equals("free")){

                        System.out.println("This employee has already been assigned to :" + employee.getCurrentAssignment() + "\n" +
                                "Overwrite? (y/n)\n" +
                                "!!!WARNING!!! This process is irreversible!");

                        String yesNo = scan.nextLine();

                        if (yesNo.equals("y")){

                            employee.setPreviousAssignment(employee.getCurrentAssignment());
                            employee.setCurrentAssignment(schedule.getScheduleID());

                            if (!selectedReviewEmployeeIDs.contains(employee.getEmployeeID())){

                                selectedReviewEmployeeIDs.add(employee.getEmployeeID());
                            }

                            // updating employee assignments to file
                            editor.processTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");
                            editor.setValue(employee.getEmployeeID(), "previousAssignment", employee.getPreviousAssignment());
                            editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                            editor.writeToTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");
                        }
                    }
                    else {

                        availablePRReviewEmployeePriorityQueue.remove(employee);
                        employee.setCurrentAssignment(schedule.getScheduleID());
                        selectedReviewEmployeeIDs.add(employee.getEmployeeID());

                        // updating employee assignments to file
                        editor.processTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");
                        editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                        editor.writeToTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");
                    }
                }
                case "2" -> {

                    if (selectedReviewEmployeeIDs.isEmpty()){

                        System.out.println("No review employees to remove from this schedule");
                        break;
                    }

                    System.out.println("Enter review employee ID to remove: ");
                    String removeID = scan.nextLine();

                    if (!selectedReviewEmployeeIDs.contains(removeID)){

                        System.out.println("Review employee ID: " + removeID + " not assigned to this schedule");
                        break;
                    }

                    selectedReviewEmployeeIDs.remove(removeID);
                    PRReviewEmployee employee = PRReviewEmployeeRepository.get(removeID);
                    employee.setCurrentAssignment("free");
                    availablePRReviewEmployeePriorityQueue.add(employee);

                    // updating employee assignments to file
                    editor.processTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");
                    editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                    editor.writeToTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");
                }
                case "3" -> {

                    if (selectedReviewEmployeeIDs.isEmpty()){

                        System.out.println("No review employees to show from this schedule");
                        break;
                    }

                    System.out.println("Selected review employees by ID: ");
                    for (String s : selectedReviewEmployeeIDs){
                        System.out.println(s);
                    }
                    System.out.println();
                }
                case "4" -> {
                    if (selectedReviewEmployeeIDs.isEmpty()){

                        System.out.println("Have at least one assigned employee");
                        break;
                    }

                    schedule.setReviewTeamAssign(selectedReviewEmployeeIDs);
                    System.out.println("Review assignment created");
                    endProgram = true;
                    returnValue = true;
                }
                case "5" -> {

                    for (String s : selectedReviewEmployeeIDs){

                        PRReviewEmployee resetEmployee = PRReviewEmployeeRepository.get(s);
                        resetEmployee.setCurrentAssignment("free");
                        availablePRReviewEmployeePriorityQueue.add(resetEmployee);

                        // updating employee assignment to files
                        editor.processTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");
                        editor.setValue(resetEmployee.getEmployeeID(), "currentAssignment", resetEmployee.getCurrentAssignment());
                        editor.writeToTextFile(PRReviewEmployeesDir + "PRReviewEmployeeList.txt");
                    }
                    return returnValue;
                }
                case "0" -> {

                    if (selectedReviewEmployeeIDs.isEmpty()){

                        endProgram = true;
                    }
                    else {

                        System.out.println("Remove all assigned review employees or cancel before exiting");
                    }
                }
                default -> System.out.println("Invalid choice. Try again");
            }
        }
        return returnValue;
    }

    /**
     * Retrieve all activity schedules
     * @return true if successful, false otherwise
     */
    private boolean retrieveAllActivities() {

        damageControlActivitySchedulesPriorityQueue.clear();
        planningActivitySchedulesPriorityQueue.clear();
        reviewingActivitySchedulesPriorityQueue.clear();
        revisingActivitySchedulesPriorityQueue.clear();
        readyActivitySchedulesPriorityQueue.clear();
        allActivitySchedulesPriorityQueue.clear();
        activitySchedulesRepository.clear();

        File[] scheduleTextFiles = retrieveActivityFiles(activityScheduleDir);

        if (scheduleTextFiles == null){
            return false;
        }

        for (File f : scheduleTextFiles){

            editor.processTextFile(activityScheduleDir + f.getName());
            String[] schedules = editor.getArrayNames();

            for (String s : schedules){

                ActivitySchedules schedule = new ActivitySchedules(s,
                        editor.retrieveValue(s, "activityName"),
                        editor.retrieveValue(s, "activityType"),
                        editor.retrieveValue(s, "objective"),
                        editor.retrieveValue(s, "targetAudience"),
                        editor.retrieveValue(s, "description"),
                        editor.retrieveValue(s, "tasks"),
                        editor.retrieveValue(s, "department"),
                        editor.retrieveValue(s, "location"),
                        editor.retrieveValue(s, "duration"),
                        editor.retrieveValue(s, "deadline"),
                        editor.retrieveValue(s, "status"),
                        editor.retrieveValue(s, "dateScheduled"),
                        f.getName());

                List<String> planningEmployees = new ArrayList<>();
                int num = 0;
                String planningEmployeeID = "employeePlanning" + num;
                while ((editor.retrieveValue(s, planningEmployeeID)) != null){

                    planningEmployees.add(editor.retrieveValue(s, planningEmployeeID));
                    num++;
                    planningEmployeeID = "employeePlanning" + num;
                }
                schedule.setPlanningTeamAssign(planningEmployees);

                List<String> reviewEmployees = new ArrayList<>();
                int num1 = 0;
                String reviewEmployeeID = "employeeReview" + num1;
                while ((editor.retrieveValue(s, reviewEmployeeID)) != null){

                    reviewEmployees.add(editor.retrieveValue(s, reviewEmployeeID));
                    num1++;
                    reviewEmployeeID = "employeeReview" + num1;
                }
                schedule.setReviewTeamAssign(reviewEmployees);

                if (schedule.getScheduleID() == null || schedule.getActivityName() == null ||
                    schedule.getActivityType() == null || schedule.getObjective() == null ||
                    schedule.getTargetAudience() == null || schedule.getDescription() == null ||
                    schedule.getTasks() == null || schedule.getDepartment() == null ||
                    schedule.getLocation() == null || schedule.getDuration() == null ||
                    schedule.getDeadline() == null || schedule.getStatus() == null ||
                    schedule.getDateScheduled() == null){

                    System.out.println("Invalid schedule data detected");
                    return false;
                }

                String status = schedule.getStatus();
                switch (status){
                    case "Damage Control" -> damageControlActivitySchedulesPriorityQueue.add(schedule);
                    case "Planning" -> planningActivitySchedulesPriorityQueue.add(schedule);
                    case "Reviewing" -> reviewingActivitySchedulesPriorityQueue.add(schedule);
                    case "Revising" -> revisingActivitySchedulesPriorityQueue.add(schedule);
                    case "Ready" -> readyActivitySchedulesPriorityQueue.add(schedule);
                }

                allActivitySchedulesPriorityQueue.add(schedule);
                activitySchedulesRepository.put(s, schedule);
            }
        }
        return true;
    }

    /**
     * Retrieve schedule file list
     * @param fileDirectory given directory
     * @return schedule file list
     */
    private File[] retrieveActivityFiles(String fileDirectory){

        File directory = new File(fileDirectory);
        File[] textFiles = null;

        if (directory.exists() && directory.isDirectory()){

            // grab list of text files
            FilenameFilter textFileFilter = ((dir, name) -> name.toLowerCase().endsWith(".txt"));
            textFiles = directory.listFiles(textFileFilter);
        }
        else {

            System.out.println("Repository not found for directory: " + fileDirectory);
            return null;
        }

        if (textFiles != null) {

            if (textFiles.length == 0){
                System.out.println("No activity schedules found");
                return null;
            }
        }
        else {
            System.out.println("No activity schedules found");
            return null;
        }
        return textFiles;
    }

    /**
     * Retrieve all PR Planning and Review Employees
     * @return true if successful, false otherwise
     */
    private boolean retrieveAllPREmployees(){

        // clearing repositories and queues
        availablePRPlanningEmployeePriorityQueue.clear();
        availablePRReviewEmployeePriorityQueue.clear();
        allPRPlanningEmployeePriorityQueue.clear();
        allPRReviewEmployeePriorityQueue.clear();
        PRPlanningEmployeeRepository.clear();
        PRReviewEmployeeRepository.clear();

        // saving Planning employees
        File[] planningTextFiles = retrieveEmployeeFiles(PRPlanningEmployeesDir);

        if (planningTextFiles == null){
            return false;
        }

        for (File f : planningTextFiles){

            editor.processTextFile(PRPlanningEmployeesDir + f.getName());
            String[] employees = editor.getArrayNames();

            for (String s : employees){

                PRPlanningEmployee employee = new PRPlanningEmployee(s,
                        editor.retrieveValue(s,"name"),
                        editor.retrieveValue(s, "division"),
                        editor.retrieveValue(s, "position"),
                        editor.retrieveValue(s, "department"),
                        editor.retrieveValue(s, "rating"),
                        editor.retrieveValue(s, "yearsOfExperience"),
                        editor.retrieveValue(s, "previousAssignment"),
                        editor.retrieveValue(s, "currentAssignment"));

                if (employee.getEmployeeID() == null || employee.getName() == null ||
                        employee.getDivision() == null || employee.getPosition() == null ||
                        employee.getDepartment() == null || employee.getRating() == null ||
                        employee.getYearsOfExperience() == null || employee.getPreviousAssignment() == null ||
                        employee.getCurrentAssignment() == null) {

                    System.out.println("Invalid employee data detected");
                    return false;
                }

                if (employee.getCurrentAssignment().equals("free")){
                    availablePRPlanningEmployeePriorityQueue.add(employee);
                }
                allPRPlanningEmployeePriorityQueue.add(employee);
                PRPlanningEmployeeRepository.put(s, employee);
            }
        }

        // saving Review employees
        File[] reviewTextFiles = retrieveEmployeeFiles(PRReviewEmployeesDir);

        if (reviewTextFiles == null){
            return false;
        }

        for (File f : reviewTextFiles){

            editor.processTextFile(PRReviewEmployeesDir + f.getName());
            String[] employees = editor.getArrayNames();

            for (String s : employees){

                PRReviewEmployee employee = new PRReviewEmployee(s,
                        editor.retrieveValue(s,"name"),
                        editor.retrieveValue(s, "division"),
                        editor.retrieveValue(s, "position"),
                        editor.retrieveValue(s, "department"),
                        editor.retrieveValue(s, "rating"),
                        editor.retrieveValue(s, "yearsOfExperience"),
                        editor.retrieveValue(s, "previousAssignment"),
                        editor.retrieveValue(s, "currentAssignment"));

                if (employee.getEmployeeID() == null || employee.getName() == null ||
                        employee.getDivision() == null || employee.getPosition() == null ||
                        employee.getDepartment() == null || employee.getRating() == null ||
                        employee.getYearsOfExperience() == null || employee.getPreviousAssignment() == null ||
                        employee.getCurrentAssignment() == null) {

                    System.out.println("Invalid employee data detected");
                    return false;
                }

                if (employee.getCurrentAssignment().equals("free")){
                    availablePRReviewEmployeePriorityQueue.add(employee);
                }
                allPRReviewEmployeePriorityQueue.add(employee);
                PRReviewEmployeeRepository.put(s, employee);
            }
        }
        return true;
    }

    /**
     * Retrieve employee file list
     * @param fileDirectory given directory
     * @return employee file list
     */
    private File[] retrieveEmployeeFiles(String fileDirectory){

        File directory = new File(fileDirectory);
        File[] textFiles = null;

        if (directory.exists() && directory.isDirectory()){

            // grab list of text files
            FilenameFilter textFileFilter = ((dir, name) -> name.toLowerCase().endsWith(".txt"));
            textFiles = directory.listFiles(textFileFilter);
        }
        else {

            System.out.println("Repository not found for directory: " + fileDirectory);
            return null;
        }

        if (textFiles != null) {

            if (textFiles.length == 0){
                System.out.println("No employees found");
                return null;
            }
        }
        else {
            System.out.println("No employees found");
            return null;
        }
        return textFiles;
    }

    /**
     * Converts ActivitySchedules object into nested HashMap
     * @param schedule specified ActivitySchedule
     * @return HashMap of ActivitySchedule
     */
    private Map<String, Object> activityScheduleToHashmap(ActivitySchedules schedule){

        Map<String, Object> innerMap = new LinkedHashMap<>();
        innerMap.put("activityName", schedule.getActivityName());
        innerMap.put("activityType", schedule.getActivityType());
        innerMap.put("objective", schedule.getObjective());
        innerMap.put("targetAudience", schedule.getTargetAudience());
        innerMap.put("description", schedule.getDescription());
        innerMap.put("tasks", schedule.getTasks());
        innerMap.put("department", schedule.getDepartment());
        innerMap.put("location", schedule.getLocation());
        innerMap.put("duration", schedule.getDuration());
        innerMap.put("deadline", schedule.getDeadline());
        innerMap.put("status", schedule.getStatus());
        innerMap.put("dateScheduled", schedule.getDateScheduled());

        int num = 0;
        for (String s : schedule.getPlanningTeamAssign()){
            String employee = "employeePlanning" + num;
            innerMap.put(employee, s);
            num++;
        }

        int num1 = 0;
        for (String s : schedule.getReviewTeamAssign()){
            String employee = "employeeReview" + num1;
            innerMap.put(employee, s);
            num1++;
        }

        Map<String, Object> outerMap = new LinkedHashMap<>();
        outerMap.put(schedule.getScheduleID(), innerMap);

        return outerMap;
    }

    /**
     * Returns all public relations employees hashmap repo
     * @return all public relations employees hashmap repo
     */
    private Map<String, Object> retrieveEmployeeHashmap(String fileDirectory){

        File directory = new File(fileDirectory);
        File[] textFiles = null;

        if (directory.exists() && directory.isDirectory()){

            //grab list of text files
            FilenameFilter textFileFilter = ((dir, name) -> name.toLowerCase().endsWith(".txt"));
            textFiles = directory.listFiles(textFileFilter);
        }
        else {
            System.out.println("Repository not found");
            return null;
        }

        if (textFiles != null){

            if (textFiles.length == 0){
                System.out.println("No public relations employees found");
                return null;
            }
        }
        else {
            System.out.println("No public relations employees found");
            return null;
        }

        editor.processTextFile(fileDirectory + textFiles[0].getName());
        return editor.getRepository();
    }

    /**
     * Creates printable content schedule
     * @param schedule specific content schedule
     */
    private void printActivitySchedule(ActivitySchedules schedule){

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(printedActivitySchedulesDir + schedule.getScheduleID() + ".txt"));
            writer.write("======================================================================\n");
            writer.write("ACTIVITY SCHEDULE\n");
            writer.write("Schedule ID: " + schedule.getScheduleID() + "\n");
            writer.write("Status: " + schedule.getStatus() + " (No status | Planning | Reviewing | Revising | Ready | Damage Control)\n");
            writer.write("Department: " + schedule.getDepartment() + "\n");
            writer.write("----------------------------------------------------------------------\n");
            writer.write("Activity Name: " + schedule.getActivityName() + "\n");
            writer.write("Activity Type: " + schedule.getActivityType() + "\n");
            writer.write("Objective: " + schedule.getObjective() + "\n");
            writer.write("Target Audience: " + schedule.getTargetAudience() + "\n");
            writer.write("Description: " + schedule.getDescription() + "\n");
            writer.write("Tasks: " + schedule.getTasks() + "\n");
            writer.write("----------------------------------------------------------------------\n");
            writer.write("Location: " + schedule.getLocation() + "\n");
            writer.write("Duration: " + schedule.getDuration() + "\n");
            writer.write("Deadline: " + schedule.getDeadline() + "\n");
            writer.write("Date Scheduled: " + schedule.getDateScheduled() + "\n");
            writer.write("----------------------------------------------------------------------\n");
            writer.write("Assigned Planning Employees:\n");
            for (String s : schedule.getPlanningTeamAssign()){
                writer.write(s + "\n");
            }
            writer.write("Assigned Review Employees:\n");
            for (String s : schedule.getReviewTeamAssign()){
                writer.write(s + "\n");
            }
            writer.write("======================================================================\n");
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
     * Pretty print planning employee
     * @param employee specific planning employee
     */
    private void planningEmployeeToText(PRPlanningEmployee employee){

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Employee ID: " + employee.getEmployeeID());
        System.out.println("Name: " + employee.getName());
        System.out.println("Division: " + employee.getDivision());
        System.out.println("Position: " + employee.getPosition());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("------------------------------------------------------");
        System.out.println("Rating: " + employee.getRating());
        System.out.println("Years of Experience: " + employee.getYearsOfExperience());
        System.out.println("Previous Assignment: " + employee.getPreviousAssignment());
        System.out.println("Current Assignment: " + employee.getCurrentAssignment());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }

    /**
     * Pretty print review employee
     * @param employee specific review employee
     */
    private void reviewEmployeeToText(PRReviewEmployee employee){

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Employee ID: " + employee.getEmployeeID());
        System.out.println("Name: " + employee.getName());
        System.out.println("Division: " + employee.getDivision());
        System.out.println("Position: " + employee.getPosition());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("------------------------------------------------------");
        System.out.println("Rating: " + employee.getRating());
        System.out.println("Years of Experience: " + employee.getYearsOfExperience());
        System.out.println("Previous Assignment: " + employee.getPreviousAssignment());
        System.out.println("Current Assignment: " + employee.getCurrentAssignment());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }

    /**
     * Pretty print activity schedules
     * @param schedule specific activity schedule
     */
    private void activityScheduleToText(ActivitySchedules schedule){

        System.out.println("=======================================================");
        System.out.println("Schedule ID: " + schedule.getScheduleID());
        System.out.println("Status: " + schedule.getStatus() + " (No status | Planning | Reviewing | Revising | Ready | Damage Control)");
        System.out.println("Department: " + schedule.getDepartment());
        System.out.println("------------------------------------------------------");
        System.out.println("Activity Name: " + schedule.getActivityType());
        System.out.println("Activity Type: " + schedule.getActivityType());
        System.out.println("Objective: " + schedule.getObjective());
        System.out.println("Target Audience: " + schedule.getTargetAudience());
        System.out.println("Description: " + schedule.getDescription());
        System.out.println("Tasks: " + schedule.getTasks());
        System.out.println("------------------------------------------------------");
        System.out.println("Location: " + schedule.getLocation());
        System.out.println("Duration: " + schedule.getDuration());
        System.out.println("Deadline: " + schedule.getDeadline());
        System.out.println("Date Scheduled: " + schedule.getDateScheduled());
        System.out.println("------------------------------------------------------");
        System.out.println("Assigned Planning Employees:");
        for (String s : schedule.getPlanningTeamAssign()){
            System.out.println(s);
        }
        System.out.println("------------------------------------------------------");
        System.out.println("Assigned Review Employees:");
        for (String s : schedule.getReviewTeamAssign()){
            System.out.println(s);
        }
        System.out.println("=======================================================\n");
    }

    /**
     * Generates IDs for activity schedules
     * @return generated activity schedule ID
     */
    private String IDGenerator(){

        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");

        return timeNow.format(formatter);
    }

    /**
     * Returns current time in MM-dd-yyyy-HH-mm-ss format when called
     * @return date in MM-dd-yyyy-HH-mm-ss format
     */
    private String dateIssuer(){

        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm-ss");

        return timeNow.format(formatter);
    }
}
