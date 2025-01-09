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

public class ContentScheduler implements src.PublicRelations.src.interfaces.ContentScheduler {

    private final String departmentRequestDir = "src/PublicRelations/repository/departmentRequests/";
    private final String PRPlanningEmployeesDir = "src/PublicRelations/repository/planningEmployees/";
    private final String PRReviewEmployeesDir = "src/PublicRelations/repository/reviewEmployees/";
    private final String contentSchedulesDir = "src/PublicRelations/repository/contentSchedules/";
    private final String printedContentSchedulesDir = "src/PublicRelations/repository/printedContentSchedules/";

    PoorTextEditor editor = new PoorTextEditor();

    // sorts content requests by deadline first, then by priority
    PriorityQueue<ContentRequests> pendingContentRequestsPriorityQueue = new PriorityQueue<>(
            Comparator.<ContentRequests, LocalDate>comparing(request -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(request.getDeadline(), formatter);
            }).thenComparingInt(request -> -Integer.parseInt(request.getPriority()))
    );
    PriorityQueue<ContentRequests> allContentRequestsPriorityQueue = new PriorityQueue<>(
            Comparator.<ContentRequests, LocalDate>comparing(request -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(request.getDeadline(), formatter);
            }).thenComparingInt(request -> -Integer.parseInt(request.getPriority()))
    );
    /**
     * Repository to store all content requests
     */
    Map<String, ContentRequests> contentRequestsRepository = new LinkedHashMap<>();

    // sorts content schedules by deadline first, then by priority
    PriorityQueue<ContentSchedules> planningContentSchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ContentSchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparingInt(schedule -> -Integer.parseInt(schedule.getPriority()))
    );

    PriorityQueue<ContentSchedules> reviewingContentSchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ContentSchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparingInt(schedule -> -Integer.parseInt(schedule.getPriority()))
    );

    PriorityQueue<ContentSchedules> revisingContentSchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ContentSchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparingInt(schedule -> -Integer.parseInt(schedule.getPriority()))
    );

    PriorityQueue<ContentSchedules> readyContentSchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ContentSchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparingInt(schedule -> -Integer.parseInt(schedule.getPriority()))
    );

    PriorityQueue<ContentSchedules> allContentSchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.<ContentSchedules, LocalDate>comparing(schedule -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                return LocalDate.parse(schedule.getDeadline(), formatter);
            }).thenComparingInt(schedule -> -Integer.parseInt(schedule.getPriority()))
    );
    /**
     * Repository to store all content schedules
     */
    Map<String, ContentSchedules> contentSchedulesRepository = new LinkedHashMap<>();

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
    public boolean showPendingRequests() {

        if (!retrieveAllRequests()){
            return false;
        }

        PriorityQueue<ContentRequests> tempQueue = new PriorityQueue<>(pendingContentRequestsPriorityQueue);

        while (!tempQueue.isEmpty()){
            contentRequestToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showAllRequests() {

        if (!retrieveAllRequests()){
            return false;
        }

        PriorityQueue<ContentRequests> tempQueue = new PriorityQueue<>(allContentRequestsPriorityQueue);

        while (!tempQueue.isEmpty()){
            contentRequestToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean createContentSchedule(String requestID) {

        if (!retrieveAllRequests()){
            return false;
        }

        if (!contentRequestsRepository.containsKey(requestID)){

            System.out.println("Content request ID: " + requestID + " not found");
            return false;
        }

        if (Boolean.parseBoolean(contentRequestsRepository.get(requestID).getResolved())){

            System.out.println("Security request ID: " + requestID + " has already been resolved. Edit it instead");
            return false;
        }

        ContentRequests request = contentRequestsRepository.get(requestID);
        contentRequestToText(request);

        ContentSchedules schedule = new ContentSchedules(
                IDGenerator() + "-" + request.getRequestID(),
                request.getRequestID(),
                request.getTheme(),
                request.getContentCategory(),
                request.getPlatform(),
                request.getDuration(),
                request.getDeadline(),
                request.getDescription(),
                request.getTasks(),
                request.getDepartment(),
                request.getPriority(),
                "no status",
                dateIssuer(),
                null);

        // assigning planning employee to schedule
        if (!addPlanningEmployeeToSchedule(schedule)){

            System.out.println("Planning employee schedule for request ID: " + requestID + " was cancelled");
            return false;
        }

        // assigning review employee to schedule
        if (!addReviewEmployeeToSchedule(schedule)){

            System.out.println("Review employee schedule for request ID: " + requestID + " was cancelled");
            return false;
        }

        // writing schedule to repository
        editor.setRepository(contentScheduleToHashmap(schedule));
        editor.writeToTextFile(contentSchedulesDir + schedule.getScheduleID() + ".txt");

        // writing printed schedule to repository
        printContentSchedule(schedule);

        // removing specific request from pending request queue
        pendingContentRequestsPriorityQueue.remove(request);
        // removing specific request in all queue
        allContentRequestsPriorityQueue.remove(request);
        // setting resolve status of content request to true
        request.setResolved("true");
        // adding back changed specific request
        allContentRequestsPriorityQueue.add(request);

        // editing content request resolved status in files
        editor.processTextFile(departmentRequestDir + request.getFileName());
        editor.setValue(request.getRequestID(), "resolved", "true");
        editor.writeToTextFile(departmentRequestDir + request.getFileName());

        return true;
    }

    @Override
    public boolean deleteContentSchedule(String contentID) {

        if (!retrieveAllSchedules()) return false;

        if (!retrieveAllPREmployees()) return false;

        if (!retrieveAllRequests()) return false;

        if (!contentSchedulesRepository.containsKey(contentID)){

            System.out.println("Content schedule ID: " + contentID + " not found");
            return false;
        }

        ContentSchedules schedule = contentSchedulesRepository.get(contentID);

        if (schedule.getPlanningTeamAssign().isEmpty()|| schedule.getReviewTeamAssign().isEmpty()){

            System.out.println("Incomplete employee assignment for content schedule ID: " + contentID);
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
         * Setting content request resolved status back to false
         */
        ContentRequests request = contentRequestsRepository.get(schedule.getRequestID());
        editor.processTextFile(departmentRequestDir + request.getFileName());
        editor.setValue(schedule.getRequestID(), "resolved", "false");
        editor.writeToTextFile(departmentRequestDir + request.getFileName());

        /**
         * Deleting content schedule
         */
        File fileToDelete = new File(contentSchedulesDir, schedule.getFileName());

        if (fileToDelete.exists()){

            if (fileToDelete.delete()){

                System.out.println("Content schedule of ID: " + contentID + " successfully deleted");
            }
            else {

                System.out.println("Failed to delete content schedule ID: " + contentID);
                return false;
            }
        }
        else {

            System.out.println("Content schedule with ID: " + contentID + " not found");
            return false;
        }
        return true;
    }

    @Override
    public boolean editContentScheduleAssignment(String contentID) {

        if (!retrieveAllSchedules()){
            return false;
        }

        if (!contentSchedulesRepository.containsKey(contentID)){

            System.out.println("Content schedule with ID: " + contentID + " does not exists");
            return false;
        }

        ContentSchedules schedule = contentSchedulesRepository.get(contentID);
        if (!addPlanningEmployeeToSchedule(schedule)){
            System.out.println("Planning employee update cancelled");
            return false;
        }
        if (!addReviewEmployeeToSchedule(schedule)){
            System.out.println("Review employee update cancelled");
            return false;
        }

        // writing schedule to repository
        editor.setRepository(contentScheduleToHashmap(schedule));
        editor.writeToTextFile(contentSchedulesDir + schedule.getFileName());

        // writing printed schedule to repository
        printContentSchedule(schedule);

        System.out.println("Update successful for content schedule ID: " + contentID);
        return true;
    }

    @Override
    public boolean editContentScheduleStatus(String contentID) {

        if (!retrieveAllSchedules()){
            return false;
        }

        if (!contentSchedulesRepository.containsKey(contentID)){

            System.out.println("Content schedule with ID: " + contentID + " does not exists");
            return false;
        }

        ContentSchedules schedule = contentSchedulesRepository.get(contentID);
        System.out.println("Edit status for content schedule ID: " + contentID + "\n(Planning | Reviewing | Revising | Ready)");
        Scanner scan = new Scanner(System.in);
        schedule.setStatus(scan.nextLine());

        // writing schedule to repository
        editor.setRepository(contentScheduleToHashmap(schedule));
        editor.writeToTextFile(contentSchedulesDir + schedule.getFileName());

        // writing printed schedule to repository
        printContentSchedule(schedule);

        System.out.println("Update successful for content schedule ID: " + contentID);
        return true;
    }

    @Override
    public boolean showAllContentSchedules() {

        if (!retrieveAllSchedules()){
            return false;
        }
        
        if (allContentSchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ContentSchedules> tempQueue = new PriorityQueue<>(allContentSchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            contentScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showPlanningContentSchedules() {

        if (!retrieveAllSchedules()){
            return false;
        }

        if (planningContentSchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ContentSchedules> tempQueue = new PriorityQueue<>(planningContentSchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            contentScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showReviewingContentSchedules() {

        if (!retrieveAllSchedules()){
            return false;
        }

        if (reviewingContentSchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ContentSchedules> tempQueue = new PriorityQueue<>(reviewingContentSchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            contentScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showRevisingContentSchedules() {

        if (!retrieveAllSchedules()){
            return false;
        }

        if (revisingContentSchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ContentSchedules> tempQueue = new PriorityQueue<>(revisingContentSchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            contentScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showReadyContentSchedules() {

        if (!retrieveAllSchedules()){
            return false;
        }

        if (readyContentSchedulesPriorityQueue.isEmpty()){
            System.out.println("Nothing was found");
            return false;
        }

        PriorityQueue<ContentSchedules> tempQueue = new PriorityQueue<>(readyContentSchedulesPriorityQueue);

        while (!tempQueue.isEmpty()){
            contentScheduleToText(tempQueue.poll());
        }
        return true;
    }

    @Override
    public boolean showContentScheduleByID(String contentID) {

        if (!retrieveAllSchedules()){
            return false;
        }

        if (!contentSchedulesRepository.containsKey(contentID)){

            System.out.println("Content schedule with ID: " + contentID + " not found");
            return false;
        }

        contentScheduleToText(contentSchedulesRepository.get(contentID));
        return true;
    }

    /**
     * To add planning employee to a schedule
     * @param schedule given schedule
     * @return true if successful, false otherwise
     */
    private boolean addPlanningEmployeeToSchedule(ContentSchedules schedule){

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
    private boolean addReviewEmployeeToSchedule(ContentSchedules schedule){

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
     * Retrieve all content schedules
     * @return true if successful, false otherwise
     */
    private boolean retrieveAllSchedules() {

        planningContentSchedulesPriorityQueue.clear();
        reviewingContentSchedulesPriorityQueue.clear();
        revisingContentSchedulesPriorityQueue.clear();
        readyContentSchedulesPriorityQueue.clear();
        allContentSchedulesPriorityQueue.clear();
        contentSchedulesRepository.clear();

        File[] scheduleTextFiles = retrieveScheduleFiles(contentSchedulesDir);

        if (scheduleTextFiles == null){
            return false;
        }

        for (File f : scheduleTextFiles){

            editor.processTextFile(contentSchedulesDir + f.getName());
            String[] schedules = editor.getArrayNames();

            for (String s : schedules){

                ContentSchedules schedule = new ContentSchedules(s,
                        editor.retrieveValue(s, "requestID"),
                        editor.retrieveValue(s, "theme"),
                        editor.retrieveValue(s, "contentCategory"),
                        editor.retrieveValue(s, "platform"),
                        editor.retrieveValue(s, "duration"),
                        editor.retrieveValue(s, "deadline"),
                        editor.retrieveValue(s, "description"),
                        editor.retrieveValue(s, "tasks"),
                        editor.retrieveValue(s, "department"),
                        editor.retrieveValue(s, "priority"),
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

                if (schedule.getScheduleID() == null || schedule.getTheme() == null ||
                    schedule.getContentCategory() == null || schedule.getPlatform() == null ||
                    schedule.getDuration() == null || schedule.getDeadline() == null ||
                    schedule.getDescription() == null || schedule.getTasks() == null ||
                    schedule.getDepartment() == null || schedule.getPriority() == null ||
                    schedule.getStatus() == null || schedule.getDateScheduled() == null ||
                    schedule.getRequestID() == null || schedule.getPlanningTeamAssign().isEmpty() ||
                    schedule.getReviewTeamAssign().isEmpty()){

                    System.out.println("Invalid schedule data detected");
                    return false;
                }

                String status = schedule.getStatus();
                switch (status){
                    case "Planning" -> planningContentSchedulesPriorityQueue.add(schedule);
                    case "Reviewing" -> reviewingContentSchedulesPriorityQueue.add(schedule);
                    case "Revising" -> revisingContentSchedulesPriorityQueue.add(schedule);
                    case "Ready" -> readyContentSchedulesPriorityQueue.add(schedule);
                }

                allContentSchedulesPriorityQueue.add(schedule);
                contentSchedulesRepository.put(s, schedule);
            }
        }
        return true;
    }

    /**
     * Retrieve schedule file list
     * @param fileDirectory given directory
     * @return schedule file list
     */
    private File[] retrieveScheduleFiles(String fileDirectory){

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
                System.out.println("No content schedules found");
                return null;
            }
        }
        else {
            System.out.println("No content schedules found");
            return null;
        }
        return textFiles;
    }

    /**
     * Retrieve all content requests
     * @return true if successful, false otherwise
     */
    private boolean retrieveAllRequests() {

        // clearing repositories and queues
        pendingContentRequestsPriorityQueue.clear();
        allContentRequestsPriorityQueue.clear();
        contentRequestsRepository.clear();

        File[] requestTextFiles = retrieveRequestFiles(departmentRequestDir);

        if (requestTextFiles == null){
            return false;
        }

        for (File f : requestTextFiles){

            editor.processTextFile(departmentRequestDir + f.getName());
            String[] requests = editor.getArrayNames();

            for (String s : requests){

                ContentRequests request = new ContentRequests(s,
                        editor.retrieveValue(s, "theme"),
                        editor.retrieveValue(s, "contentCategory"),
                        editor.retrieveValue(s, "platform"),
                        editor.retrieveValue(s, "duration"),
                        editor.retrieveValue(s, "deadline"),
                        editor.retrieveValue(s, "description"),
                        editor.retrieveValue(s, "tasks"),
                        editor.retrieveValue(s, "department"),
                        editor.retrieveValue(s, "priority"),
                        editor.retrieveValue(s, "specialReqs"),
                        editor.retrieveValue(s, "dateIssued"),
                        editor.retrieveValue(s, "resolved"),
                        (f.getName()));

                if (request.getTheme() == null || request.getContentCategory() == null ||
                    request.getPlatform() == null || request.getDuration() == null ||
                    request.getDeadline() == null || request.getDescription() == null ||
                    request.getTasks() == null || request.getDepartment() == null ||
                    request.getPriority() == null || request.getSpecialReqs() == null ||
                    request.getDateIssued() == null || request.getResolved() == null){

                    System.out.println("Invalid content request detected");
                    return false;
                }

                if (!Boolean.parseBoolean(request.getResolved())){
                    pendingContentRequestsPriorityQueue.add(request);
                }
                allContentRequestsPriorityQueue.add(request);
                contentRequestsRepository.put(s,request);
            }
        }
        return true;
    }

    /**
     * Retrieve request file list
     * @param fileDirectory given directory
     * @return request file list
     */
    private File[] retrieveRequestFiles(String fileDirectory) {

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
                System.out.println("No content requests found");
                return null;
            }
        }
        else {
            System.out.println("No content requests found");
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
     * Converts ContentSchedules object into nested HashMap
     * @param schedule specified ContentSchedule
     * @return HashMap of ContentSchedule
     */
    private Map<String, Object> contentScheduleToHashmap(ContentSchedules schedule){

        Map<String, Object> innerMap = new LinkedHashMap<>();
        innerMap.put("requestID", schedule.getRequestID());
        innerMap.put("theme", schedule.getTheme());
        innerMap.put("contentCategory", schedule.getContentCategory());
        innerMap.put("platform", schedule.getPlatform());
        innerMap.put("duration", schedule.getDuration());
        innerMap.put("deadline", schedule.getDeadline());
        innerMap.put("description", schedule.getDescription());
        innerMap.put("tasks", schedule.getTasks());
        innerMap.put("department", schedule.getDepartment());
        innerMap.put("priority", schedule.getPriority());
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
    private void printContentSchedule(ContentSchedules schedule){

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(printedContentSchedulesDir + schedule.getScheduleID() + ".txt"));
            writer.write("======================================================================\n");
            writer.write("CONTENT SCHEDULE\n");
            writer.write("Schedule ID: " + schedule.getScheduleID() + " is associated with Request ID: " + schedule.getRequestID() + "\n");
            writer.write("----------------------------------------------------------------------\n");
            writer.write("Status: " + schedule.getStatus() + "\n");
            writer.write("Priority: " + schedule.getPriority() + " (1 = Urgent, 0 = Normal)\n");
            writer.write("Department: " + schedule.getDepartment() + "\n");
            writer.write("----------------------------------------------------------------------\n");
            writer.write("Content Category: " + schedule.getContentCategory() + "\n");
            writer.write("Theme: " + schedule.getTheme() + "\n");
            writer.write("Platform: " + schedule.getPlatform() + "\n");
            writer.write("Description: " + schedule.getDescription() + "\n");
            writer.write("Tasks: " + schedule.getTasks() + "\n");
            writer.write("----------------------------------------------------------------------\n");
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
     * Pretty print content requests
     * @param request specific content request
     */
    private void contentRequestToText(ContentRequests request) {

        System.out.println("======================================================");
        System.out.println("Security Request ID: " + request.getRequestID());
        System.out.println("Priority: " + request.getPriority() + " (0 = Normal | 1 = Urgent)");
        System.out.println("Department: " + request.getDepartment());
        System.out.println();
        System.out.println("Content Category: " + request.getContentCategory());
        System.out.println("Theme: " + request.getTheme());
        System.out.println("Platform: " + request.getPlatform());
        System.out.println("Description: " + request.getDescription());
        System.out.println("Tasks: " + request.getTasks());
        System.out.println();
        System.out.println("Duration: " + request.getDuration());
        System.out.println("Deadline: " + request.getDeadline());
        System.out.println("Special Requests: " + request.getSpecialReqs());
        System.out.println();
        System.out.println("Date Issued: " + request.getDateIssued());
        System.out.println("Resolve Status: " + request.getResolved());
        System.out.println("======================================================\n");
    }

    /**
     * Pretty print content schedules
     * @param schedule specific content schedule
     */
    private void contentScheduleToText(ContentSchedules schedule){

        System.out.println("=======================================================");
        System.out.println("Schedule ID: " + schedule.getScheduleID());
        System.out.println("is associated with");
        System.out.println("Request ID: " + schedule.getRequestID());
        System.out.println("------------------------------------------------------");
        System.out.println("Status: " + schedule.getStatus() + " (No status | Planning | Reviewing | Revising | Ready)");
        System.out.println("Priority: " + schedule.getPriority() + " (0 = Normal | 1 = Urgent)");
        System.out.println("Department: " + schedule.getDepartment());
        System.out.println("------------------------------------------------------");
        System.out.println("Content Category: " + schedule.getContentCategory());
        System.out.println("Theme: " + schedule.getTheme());
        System.out.println("Platform: " + schedule.getPlatform());
        System.out.println("Description: " + schedule.getDescription());
        System.out.println("Tasks: " + schedule.getTasks());
        System.out.println("------------------------------------------------------");
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
     * Generates IDs for content schedules
     * @return generated content schedule ID
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
