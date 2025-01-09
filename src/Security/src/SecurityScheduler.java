/**
 * @author Kenny
 */

package src.Security.src;

import src.TextEditor.PoorTextEditor;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SecurityScheduler implements src.Security.src.interfaces.SecurityScheduler {

    PriorityQueue<SecurityRequests> securityRequestsPriorityQueue = new PriorityQueue<>(
            Comparator.comparingInt(request -> -Integer.parseInt(request.getPriorityLevel()))
    );
    PriorityQueue<SecurityRequests> allSecurityRequestPriorityQueue = new PriorityQueue<>(
            Comparator.comparingInt(request -> -Integer.parseInt(request.getPriorityLevel()))
    );
    /**
     * Repository to store all security request from departments
     */
    Map<String, SecurityRequests> securityRequestsRepository = new LinkedHashMap<>();

    // sorts security employees first by division, then position, then by rating in descending order
    PriorityQueue<SecurityEmployee> availableSecurityEmployeePriorityQueue = new PriorityQueue<>(
            Comparator.comparing(SecurityEmployee::getDivision)
                    .thenComparing(SecurityEmployee::getPosition)
                    .thenComparing((e1, e2) -> e2.getRating().compareTo(e1.getRating()))
    );
    PriorityQueue<SecurityEmployee> allSecurityEmployeePriorityQueue = new PriorityQueue<>(
            Comparator.comparing(SecurityEmployee::getDivision)
                    .thenComparing(SecurityEmployee::getPosition)
                    .thenComparing((e1, e2) -> e2.getRating().compareTo(e1.getRating()))
    );
    /**
     * Repository to store all security employees obtained
     */
    Map<String, SecurityEmployee> securityEmployeeRepository = new LinkedHashMap<>();

    // sorts security schedules first by issue date, then by priority level
    PriorityQueue<SecuritySchedules> securitySchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.comparing((SecuritySchedules schedule) -> {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm-ss");
                return LocalDateTime.parse(schedule.getDateScheduled(), formatter);
            }).reversed().thenComparing(schedule -> -Integer.parseInt(schedule.getPriorityLevel()))
    );
    /**
     * Repository to store all security schedules obtained
     */
    Map<String, SecuritySchedules> securitySchedulesRepository = new LinkedHashMap<>();

    private final String departmentRequestsDir = "src/Security/repository/departmentRequests/";
    private final String securitySchedulesDir = "src/Security/repository/securitySchedules/";
    private final String printedSecuritySchedulesDir = "src/Security/repository/printedSecuritySchedules/";
    private File[] securityRequestFiles = null;

    private final String securityEmployeeDir = "src/Security/repository/securityEmployees/";

    private PoorTextEditor editor = new PoorTextEditor();

    @Override
    public boolean addSecurityPersonnel() {

        Map<String, Object> employeeHashMap = retrieveEmployeeHashmap();

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
        newEmployeeID = "DoS_Employee_" + ID;

        Scanner scan = new Scanner(System.in);
        Map<String, Object> newEmployeeData = new LinkedHashMap<>();

        System.out.println("Adding employee under ID: " + newEmployeeID);
        System.out.println("Enter employee name: ");
        newEmployeeData.put("name", scan.nextLine());

        System.out.println("Enter employee division: ");
        newEmployeeData.put("division", scan.nextLine());

        System.out.println("Enter employee position: ");
        newEmployeeData.put("position", scan.nextLine());

        newEmployeeData.put("department", "Security");

        System.out.println("Enter employee expertise: (Example_Expertise Another_Example_Expertise)");
        newEmployeeData.put("expertise", scan.nextLine());

        System.out.println("Enter employee rating: (0.0 - 5.0)");
        newEmployeeData.put("rating", scan.nextLine());

        System.out.println("Enter employee years of experience: ");
        newEmployeeData.put("yearsOfExperience", scan.nextLine());

        newEmployeeData.put("previousAssignment", "free");
        newEmployeeData.put("currentAssignment", "free");

        employeeHashMap.put(newEmployeeID, newEmployeeData);
        editor.setRepository(employeeHashMap);
        editor.writeToTextFile(securityEmployeeDir + "securityEmployeeList.txt");
        return true;
    }

    @Override
    public boolean deleteSecurityPersonnel(String employeeID) {

        Map<String, Object> employeeHashMap = retrieveEmployeeHashmap();

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
        editor.writeToTextFile(securityEmployeeDir + "securityEmployeeList.txt");
        System.out.println("Employee with ID: " + employeeID + " successfully deleted");
        return true;
    }

    @Override
    public boolean showPendingEvents() {

        if (!retrievePendingRequests()) {
            return false;
        }

        for (SecurityRequests request : securityRequestsPriorityQueue){

            securityRequestToText(request);
        }
        return true;
    }

    @Override
    public boolean showAllEvents() {

        if (!retrieveAllRequests()){
            return false;
        }

        for (SecurityRequests request : allSecurityRequestPriorityQueue){

            securityRequestToText(request);
        }
        return true;
    }

    @Override
    public boolean showFreeSecurityEmployees() {

        if (!retrieveEmployees()){
            return false;
        }

        for (SecurityEmployee employee : availableSecurityEmployeePriorityQueue){

            securityEmployeeToText(employee);
        }
        return true;
    }

    @Override
    public boolean showAllSecurityEmployees() {

        if (!retrieveEmployees()){
            return false;
        }

        for (SecurityEmployee employee : allSecurityEmployeePriorityQueue){

            securityEmployeeToText(employee);
        }
        return true;
    }

    @Override
    public boolean createSchedule(String requestID) {

        if (securityRequestsRepository.isEmpty()){

            if (!retrieveAllRequests()){
                return false;
            }
        }

        // check if security request ID exists
        if (!securityRequestsRepository.containsKey(requestID)){

            System.out.println("Request ID: " + requestID + " not found");
            return false;
        }

        // check if security request already solved
        if (checkIfRequestResolved(requestID)){

            System.out.println("This security request has already been resolved. Edit it instead.");
            return false;
        }

        SecurityRequests request = securityRequestsRepository.get(requestID);
        securityRequestToText(request);

        // creating new security schedule filled with security request details
        SecuritySchedules schedule = new SecuritySchedules(
                IDGenerator() + "-" + request.getRequestID(),
                request.getRequestID(),
                request.getPriorityLevel(),
                request.getDepartment(),
                request.getLocation(),
                request.getDescription(),
                request.getDuration(),
                request.getTasks(),
                dateIssuer());

        // assigning security personnel to schedule
        if (!addPersonnelToSchedule(schedule)){
            System.out.println("Schedule creation for Request ID: " + requestID + " was cancelled");
            return false;
        }

        // writing schedule to repository
        editor.setRepository(securityScheduleToHashmap(schedule));
        editor.writeToTextFile(securitySchedulesDir + schedule.getScheduleID() + ".txt");

        // writing printed schedules to repository
        printSecuritySchedule(schedule);

        // removing specific request from pending request queue
        securityRequestsPriorityQueue.remove(request);
        // removing specific request in all queue
        allSecurityRequestPriorityQueue.remove(request);
        // setting resolve status of security request to true
        request.setResolved("true");
        // adding back changed specific request
        allSecurityRequestPriorityQueue.add(request);

        // editing security resolved status in text file repository
        editor.processTextFile(departmentRequestsDir + request.getFileName());
        editor.setValue(request.getRequestID(), "resolved", "true");
        editor.writeToTextFile(departmentRequestsDir + request.getFileName());

        return true;
    }

    @Override
    public boolean deleteSchedule(String scheduleID) {

        if (securityEmployeeRepository.isEmpty()){

            if (!retrieveEmployees()){
                return false;
            }
        }

        if (!retrieveSchedules()){
            return false;
        }

        if (!retrieveAllRequests()){
            return false;
        }

        if (!securitySchedulesRepository.containsKey(scheduleID)){

            System.out.println("Schedule ID: " + scheduleID + " not found");
            return false;
        }

        SecuritySchedules schedule = securitySchedulesRepository.get(scheduleID);

        if (!schedule.getAssignedEmployeeIDs().isEmpty()){

            List<String> scheduledEmployeeIDs = schedule.getAssignedEmployeeIDs();
            editor.processTextFile(securityEmployeeDir + "securityEmployeeList.txt");

            for (String s : scheduledEmployeeIDs){

                editor.setValue(s, "currentAssignment", "free");
            }

            editor.writeToTextFile(securityEmployeeDir + "securityEmployeeList.txt");
        }

        // reverting resolved status
        SecurityRequests request = securityRequestsRepository.get(schedule.getRequestID());
        editor.processTextFile(departmentRequestsDir + request.getFileName());
        editor.setValue(schedule.getRequestID(), "resolved", "false");
        editor.writeToTextFile(departmentRequestsDir + request.getFileName());

        File fileToDelete = new File(securitySchedulesDir, scheduleID + ".txt");

        if (fileToDelete.exists()) {

            if (fileToDelete.delete()) {

                System.out.println("Security schedule deleted successfully");
            } else {

                System.out.println("Failed to security schedule");
                return false;
            }
        } else {
            System.out.println("Security schedule not found");
            return false;
        }
        return true;
    }

    @Override
    public boolean editScheduleAssignments(String scheduleID) {

        if (!retrieveSchedules()){
            return false;
        }

        if (!securitySchedulesRepository.containsKey(scheduleID)){

            System.out.println("Security schedule with ID: " + scheduleID + " does not exists");
            return false;
        }

        SecuritySchedules schedule = securitySchedulesRepository.get(scheduleID);
        if (!addPersonnelToSchedule(schedule)){
            System.out.println("Updating cancelled");
            return false;
        }

        // writing schedule to repository
        editor.setRepository(securityScheduleToHashmap(schedule));
        editor.writeToTextFile(securitySchedulesDir + schedule.getScheduleID() + ".txt");

        // writing printed schedules to repository
        printSecuritySchedule(schedule);

        System.out.println("Successfully updated");
        return true;
    }

    @Override
    public boolean showAllSchedules() {

        if (!retrieveSchedules()){
            return false;
        }

        for (SecuritySchedules schedule : securitySchedulesPriorityQueue){

            securityScheduleToText(schedule);
        }
        return true;
    }

    @Override
    public boolean showScheduleByID(String ScheduleID) {

        if (!retrieveSchedules()){
            return false;
        }

        if (!securitySchedulesRepository.containsKey(ScheduleID)){
            System.out.println("Security schedule with ID: " + ScheduleID + "not found");
            return false;
        }

        securityScheduleToText(securitySchedulesRepository.get(ScheduleID));
        return true;
    }

    /**
     * To add security personnel to a schedule
     * @param schedule given schedule
     */
    private boolean addPersonnelToSchedule(SecuritySchedules schedule){

        if (securityEmployeeRepository.isEmpty()){

            if (!retrieveEmployees()){
                return false;
            }
        }

        List<String> selectedEmployeeIDs = new ArrayList<>();
        if (!schedule.getAssignedEmployeeIDs().isEmpty()){
            selectedEmployeeIDs = schedule.getAssignedEmployeeIDs();
        }
        int employeeCount = schedule.getAssignedEmployeeIDs().size();
        Scanner scan = new Scanner(System.in);

        boolean endProgram = false;
        boolean returnValue = false;

        while (!endProgram){

            if (Integer.parseInt(schedule.getPriorityLevel()) < 3){
                System.out.println(minimumEmployeeRequirement(schedule.getPriorityLevel()) - employeeCount + " employees left to add\n");
            }
            else {
                System.out.println("<<Overridden>> employees left to add");
            }
            System.out.println("1. Add Security Employee by ID");
            System.out.println("2. Remove Security Employee by ID");
            System.out.println("3. Show Scheduled Employees");
            System.out.println("4. Complete Schedule");
            System.out.println("5. Cancel Schedule");
            System.out.println("0. Exit");

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice){

                case 1:
                    if (employeeCount >= minimumEmployeeRequirement(schedule.getPriorityLevel())){
                        System.out.println("Exceeded limit");
                        break;
                    }

                    System.out.println("Enter Employee ID: ");
                    String employeeID = scan.nextLine();

                    if (!securityEmployeeRepository.containsKey(employeeID)){

                        System.out.println("Employee with ID: " + employeeID + " does not exists");
                        break;
                    }

                    SecurityEmployee employee = securityEmployeeRepository.get(employeeID);

                    if (!employee.getCurrentAssignment().equals("free")){

                        System.out.println("This employee has already been assigned to :" + employee.getCurrentAssignment() + "\n" +
                                "Overwrite? (y/n)\n" +
                                "!!!WARNING!!! This process is irreversible!");

                        String yesNo = scan.nextLine();

                        if (yesNo.equals("y")){
                            employee.setPreviousAssignment(employee.getCurrentAssignment());
                            employee.setCurrentAssignment(schedule.getScheduleID());

                            if (!selectedEmployeeIDs.contains(employee.getEmployeeID())){
                                selectedEmployeeIDs.add(employee.getEmployeeID());
                            }

                            // updating employee assignments
                            editor.processTextFile(securityEmployeeDir + "securityEmployeeList.txt");
                            editor.setValue(employee.getEmployeeID(), "previousAssignment", employee.getPreviousAssignment());
                            editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                            editor.writeToTextFile(securityEmployeeDir + "securityEmployeeList.txt");

                            employeeCount++;
                        }
                    }
                    else {

                        availableSecurityEmployeePriorityQueue.remove(employee);
                        employee.setCurrentAssignment(schedule.getScheduleID());
                        selectedEmployeeIDs.add(employee.getEmployeeID());

                        // updating employee assignments
                        editor.processTextFile(securityEmployeeDir + "securityEmployeeList.txt");
                        editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                        editor.writeToTextFile(securityEmployeeDir + "securityEmployeeList.txt");

                        employeeCount++;
                    }
                    break;
                case 2:
                    if (selectedEmployeeIDs.isEmpty()){

                        System.out.println("No employees to remove from this schedule");
                        break;
                    }

                    System.out.println("Enter Employee ID to remove: ");
                    String removingID = scan.nextLine();

                    if (!selectedEmployeeIDs.contains(removingID)){
                        System.out.println("Employee ID: " + removingID + " not assigned to this schedule");
                        break;
                    }

                    selectedEmployeeIDs.remove(removingID);
                    SecurityEmployee employee2 = securityEmployeeRepository.get(removingID);
                    employee2.setCurrentAssignment("free");
                    availableSecurityEmployeePriorityQueue.add(employee2);

                    // updating employee assignments
                    editor.processTextFile(securityEmployeeDir + "securityEmployeeList.txt");
                    editor.setValue(employee2.getEmployeeID(), "currentAssignment", employee2.getCurrentAssignment());
                    editor.writeToTextFile(securityEmployeeDir + "securityEmployeeList.txt");

                    employeeCount--;
                    break;
                case 3:
                    if (selectedEmployeeIDs.isEmpty()){
                        System.out.println("No employees to show from this schedule");
                        break;
                    }

                    System.out.println("Selected Employees by ID: ");
                    for (String s : selectedEmployeeIDs){
                        System.out.println(s);
                    }
                    System.out.println();
                    break;
                case 4:
                    if (employeeCount != 0){
                        schedule.setAssignedEmployeeIDs(selectedEmployeeIDs);
                        System.out.println("Assignment created");
                        endProgram = true;
                        returnValue = true;
                    }
                    else {
                        System.out.println("Have at least one assigned employee");
                    }
                    break;
                case 5:
                    for (String s : selectedEmployeeIDs){

                        SecurityEmployee resetEmployee = securityEmployeeRepository.get(s);
                        resetEmployee.setCurrentAssignment("free");
                        availableSecurityEmployeePriorityQueue.add(resetEmployee);

                        // updating employee assignments
                        editor.processTextFile(securityEmployeeDir + "securityEmployeeList.txt");
                        editor.setValue(resetEmployee.getEmployeeID(), "currentAssignment", resetEmployee.getCurrentAssignment());
                        editor.writeToTextFile(securityEmployeeDir + "securityEmployeeList.txt");
                    }
                    return returnValue;
                case 0:
                    if (selectedEmployeeIDs.isEmpty()){
                        endProgram = true;
                    }
                    else {
                        System.out.println("Remove all assigned employees or cancel before exiting");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again");
            }
        }
        return returnValue;
    }

    /**
     * Retrieve files containing security requests
     * @return files containing security requests
     */
    private boolean retrieveRequestFiles(){

        File directory = new File(departmentRequestsDir);
        File[] textFiles = null;

        if (directory.exists() && directory.isDirectory()){

            //grab list of text files
            FilenameFilter textFileFilter = ((dir, name) -> name.toLowerCase().endsWith(".txt"));
            textFiles = directory.listFiles(textFileFilter);
        }
        else {
            System.out.println("Repository not found");
            return false;
        }

        if (textFiles != null){

            if (textFiles.length == 0){
                System.out.println("No security requests found");
                return false;
            }
            securityRequestFiles = textFiles;
        }
        else {
            System.out.println("No security requests found");
            return false;
        }
        return true;
    }

    /**
     * Retrieve all pending security requests
     * @return true if successful retrieval, otherwise false
     */
    public boolean retrievePendingRequests() {

        securityRequestsPriorityQueue.clear();

        if (!retrieveRequestFiles()){
            return false;
        }

        // saving file requests to repository
        for (File f : securityRequestFiles) {

            editor.processTextFile(departmentRequestsDir + f.getName());
            String[] requests = editor.getArrayNames();

            for (String s : requests) {

                if (!Boolean.parseBoolean(editor.retrieveValue(s, "resolved"))) {

                    SecurityRequests request = new SecurityRequests(s,
                            editor.retrieveValue(s, "priorityLevel"),
                            editor.retrieveValue(s, "department"),
                            editor.retrieveValue(s, "location"),
                            editor.retrieveValue(s, "description"),
                            editor.retrieveValue(s, "duration"),
                            editor.retrieveValue(s, "tasks"),
                            editor.retrieveValue(s, "specialReqs"),
                            editor.retrieveValue(s, "dateIssued"),
                            editor.retrieveValue(s, "resolved"));

                    request.setFileName(f.getName());

                    if (request.getPriorityLevel() == null || request.getDepartment() == null ||
                            request.getLocation() == null || request.getDescription() == null ||
                            request.getDuration() == null || request.getTasks() == null ||
                            request.getSpecialReqs() == null || request.getDateIssued() == null) {

                        System.out.println("Invalid security requests detected");
                        return false;
                    }

                    // adding security request to priority queue
                    securityRequestsPriorityQueue.add(request);
                }
            }
        }
        return true;
    }

    /**
     * Retrieve all security requests
     * @return true if successful retrieval, otherwise false
     */
    private boolean retrieveAllRequests() {

        securityRequestsRepository.clear();
        allSecurityEmployeePriorityQueue.clear();

        if (!retrieveRequestFiles()) {
            return false;
        }

        // saving file requests to repository
        for (File f : securityRequestFiles) {

            editor.processTextFile(departmentRequestsDir + f.getName());
            String[] requests = editor.getArrayNames();

            for (String s : requests) {

                SecurityRequests request = new SecurityRequests(s,
                        editor.retrieveValue(s, "priorityLevel"),
                        editor.retrieveValue(s, "department"),
                        editor.retrieveValue(s, "location"),
                        editor.retrieveValue(s, "description"),
                        editor.retrieveValue(s, "duration"),
                        editor.retrieveValue(s, "tasks"),
                        editor.retrieveValue(s, "specialReqs"),
                        editor.retrieveValue(s, "dateIssued"),
                        editor.retrieveValue(s, "resolved"));

                request.setFileName(f.getName());

                if (request.getPriorityLevel() == null || request.getDepartment() == null ||
                        request.getLocation() == null || request.getDescription() == null ||
                        request.getDuration() == null || request.getTasks() == null ||
                        request.getSpecialReqs() == null || request.getDateIssued() == null) {

                    System.out.println("Invalid security requests detected");
                    return false;
                }

                // adding security request to repository and priority queue
                securityRequestsRepository.put(s, request);
                allSecurityRequestPriorityQueue.add(request);
            }
        }
        return true;
    }

    /**
     * Retrieve all security employees
     * @return all security employees
     */
    private boolean retrieveEmployees(){

        availableSecurityEmployeePriorityQueue.clear();
        allSecurityEmployeePriorityQueue.clear();
        securityEmployeeRepository.clear();

        File directory = new File(securityEmployeeDir);
        File[] textFiles = null;

        if (directory.exists() && directory.isDirectory()){

            //grab list of text files
            FilenameFilter textFileFilter = ((dir, name) -> name.toLowerCase().endsWith(".txt"));
            textFiles = directory.listFiles(textFileFilter);
        }
        else {
            System.out.println("Repository not found");
            return false;
        }

        if (textFiles != null){

            if (textFiles.length == 0){
                System.out.println("No security employees found");
                return false;
            }
        }
        else {
            System.out.println("No security employees found");
            return false;
        }

        // saving employees to repository
        for (File f : textFiles) {

            editor.processTextFile(securityEmployeeDir + f.getName());
            String[] employees = editor.getArrayNames();

            for (String s : employees) {

                SecurityEmployee employee = new SecurityEmployee(s,
                        editor.retrieveValue(s, "name"),
                        editor.retrieveValue(s, "division"),
                        editor.retrieveValue(s, "position"),
                        editor.retrieveValue(s, "department"),
                        editor.retrieveValue(s, "expertise"),
                        editor.retrieveValue(s, "rating"),
                        editor.retrieveValue(s, "yearsOfExperience"),
                        editor.retrieveValue(s, "previousAssignment"),
                        editor.retrieveValue(s, "currentAssignment"));

                if (employee.getEmployeeID() == null || employee.getName() == null ||
                        employee.getDivision() == null || employee.getPosition() == null ||
                        employee.getDepartment() == null || employee.getExpertise() == null ||
                        employee.getRating() == null || employee.getYearsOfExperience() == null ||
                        employee.getPreviousAssignment() == null || employee.getCurrentAssignment() == null) {

                    System.out.println("Invalid employee data detected");
                    return false;
                }

                // adding available employees to priority queue
                if (employee.getCurrentAssignment().equals("free")){
                    availableSecurityEmployeePriorityQueue.add(employee);
                }
                // adding security request to priority queue
                allSecurityEmployeePriorityQueue.add(employee);
                securityEmployeeRepository.put(s, employee);
            }
        }
        return true;
    }

    /**
     * Return all security employees hashmap repo
     * @return all security employees hashmap repo
     */
    private Map<String, Object> retrieveEmployeeHashmap(){

        File directory = new File(securityEmployeeDir);
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
                System.out.println("No security employees found");
                return null;
            }
        }
        else {
            System.out.println("No security employees found");
            return null;
        }

        editor.processTextFile(securityEmployeeDir + textFiles[0].getName());
        return editor.getRepository();
    }

    /**
     * Retrieves all security schedules
     * @return all security schedules
     */
    private boolean retrieveSchedules(){

        securitySchedulesPriorityQueue.clear();
        securitySchedulesRepository.clear();

        File directory = new File(securitySchedulesDir);
        File[] textFiles = null;

        if (directory.exists() && directory.isDirectory()){

            // grab list of text files
            FilenameFilter textFileFilter = (dir, name) -> name.toLowerCase().endsWith(".txt");
            textFiles = directory.listFiles(textFileFilter);
        }
        else {
            System.out.println("Repository not found");
            return false;
        }

        if (textFiles != null){

            if (textFiles.length == 0){
                System.out.println("No security schedules found");
                return false;
            }
        }
        else {
            System.out.println("No security schedules found");
            return false;
        }

        for (File f : textFiles){

            editor.processTextFile(securitySchedulesDir + f.getName());
            String[] scheduleIDs = editor.getArrayNames();
            String id = scheduleIDs[0];

            SecuritySchedules schedule = new SecuritySchedules(id,
                    editor.retrieveValue(id, "requestID"),
                    editor.retrieveValue(id, "priorityLevel"),
                    editor.retrieveValue(id, "department"),
                    editor.retrieveValue(id, "location"),
                    editor.retrieveValue(id, "description"),
                    editor.retrieveValue(id, "duration"),
                    editor.retrieveValue(id, "tasks"),
                    editor.retrieveValue(id, "dateScheduled"));

            List<String> employeeIDs = new ArrayList<>();
            int num = 0;
            String employeeNum = "employee" + num;
            while ((editor.retrieveValue(id, employeeNum) != null)){

                employeeIDs.add(editor.retrieveValue(id, "employee" + num));
                num++;
                employeeNum = "employee" + num;
            }
            schedule.setAssignedEmployeeIDs(employeeIDs);

            if (schedule.getScheduleID() == null || schedule.getRequestID() == null ||
                schedule.getPriorityLevel() == null || schedule.getDepartment() == null ||
                schedule.getLocation() == null || schedule.getDescription() == null ||
                schedule.getDuration() == null || schedule.getTasks() == null ||
                schedule.getDateScheduled() == null || employeeIDs.isEmpty()){

                System.out.println("Invalid schedule data detected");
                return false;
            }

            securitySchedulesPriorityQueue.add(schedule);
            securitySchedulesRepository.put(id, schedule);
        }
        return true;
    }

    /**
     * Pretty print security requests
     * @param request specific security request
     */
    private void securityRequestToText(SecurityRequests request){

        System.out.println("======================================================");
        System.out.println("Security Request ID: " + request.getRequestID());
        System.out.println("Priority Level: " + request.getPriorityLevel());
        System.out.println("Department: " + request.getDepartment());
        System.out.println();
        System.out.println("Location: " + request.getLocation());
        System.out.println("Description: " + request.getDescription());
        System.out.println("Duration: " + request.getDuration());
        System.out.println("Tasks: " + request.getTasks());
        System.out.println("Special Requests: " + request.getSpecialReqs());
        System.out.println();
        System.out.println("Date Issued: " + request.getDateIssued());
        System.out.println("Resolve Status: " + request.getResolved());
        System.out.println("======================================================\n");
    }

    /**
     * Pretty print security employee
     * @param employee specific security employee
     */
    private void securityEmployeeToText(SecurityEmployee employee){

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Employee ID: " + employee.getEmployeeID());
        System.out.println("Name: " + employee.getName());
        System.out.println("Division: " + employee.getDivision());
        System.out.println("Position: " + employee.getPosition());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("------------------------------------------------------");
        System.out.println("Expertise: " + employee.getExpertise());
        System.out.println("Rating: " + employee.getRating());
        System.out.println("Years of Experience: " + employee.getYearsOfExperience());
        System.out.println("Previous Assignment: " + employee.getPreviousAssignment());
        System.out.println("Current Assignment: " + employee.getCurrentAssignment());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }

    /**
     * Pretty print security schedule
     * @param schedule specific security schedule
     */
    private void securityScheduleToText(SecuritySchedules schedule){

        System.out.println("=======================================================");
        System.out.println("Schedule ID: " + schedule.getScheduleID());
        System.out.println("is associated with");
        System.out.println("Request ID: " + schedule.getRequestID());
        System.out.println("------------------------------------------------------");
        System.out.println("Priority Level: " + schedule.getPriorityLevel());
        System.out.println("Department: " + schedule.getDepartment());
        System.out.println("Location: " + schedule.getLocation());
        System.out.println("Description: " + schedule.getDescription());
        System.out.println("Duration: " + schedule.getDuration());
        System.out.println("Tasks: " + schedule.getTasks());
        System.out.println("Date Scheduled: " + schedule.getDateScheduled());
        System.out.println("------------------------------------------------------");
        System.out.println("Assigned Security Members:");
        for (String s : schedule.getAssignedEmployeeIDs()){
            System.out.println(s);
        }
        System.out.println("=======================================================\n");
    }

    /**
     * Check if a security request has already been resolved
     * @param requestID ID of security request
     * @return true if already resolved, otherwise false
     */
    private boolean checkIfRequestResolved(String requestID){

        return Boolean.parseBoolean(securityRequestsRepository.get(requestID).getResolved());
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

    /**
     * Generates IDs for security schedules
     * @return generated security schedule ID
     */
    private String IDGenerator(){

        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");

        return timeNow.format(formatter);
    }

    /**
     * Converts SecuritySchedules object into nested HashMap
     * @param schedule specified SecuritySchedule
     * @return HashMap of SecuritySchedule
     */
    private Map<String, Object> securityScheduleToHashmap(SecuritySchedules schedule){

        Map<String, Object> innerMap = new LinkedHashMap<>();
        innerMap.put("requestID", schedule.getRequestID());
        innerMap.put("priorityLevel", schedule.getPriorityLevel());
        innerMap.put("department", schedule.getDepartment());
        innerMap.put("location", schedule.getLocation());
        innerMap.put("description", schedule.getDescription());
        innerMap.put("duration", schedule.getDuration());
        innerMap.put("tasks", schedule.getTasks());
        innerMap.put("dateScheduled", schedule.getDateScheduled());

        // debug
        //System.out.println("Assigned Employee IDs hashing part: " + schedule.getAssignedEmployeeIDs());

        int num = 0;
        for (String s : schedule.getAssignedEmployeeIDs()){
            String employee = "employee" + num;
            innerMap.put(employee, s);
            num++;
        }

        Map<String, Object> outerMap = new LinkedHashMap<>();
        outerMap.put(schedule.getScheduleID(), innerMap);

        return outerMap;
    }

    /**
     * To created printable security schedules
     * @param schedule specific security schedule
     */
    private void printSecuritySchedule(SecuritySchedules schedule){

        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(printedSecuritySchedulesDir + schedule.getScheduleID() + ".txt"));
            writer.write("======================================================================\n");
            writer.write("SECURITY SCHEDULE\n");
            writer.write("Schedule ID: " + schedule.getScheduleID() + " is associated with Request ID: " + schedule.getRequestID() + "\n");
            writer.write("Priority Level: " + schedule.getPriorityLevel() + " (3 = Emergency, 2 = High, 1 = Medium, 0 = Low)\n");
            writer.write("Department: " + schedule.getDepartment() + "\n");
            writer.write("----------------------------------------------------------------------\n");
            writer.write("Location: " + schedule.getLocation() + "\n");
            writer.write("Description: " + schedule.getDescription() + "\n");
            writer.write("Duration :" + schedule.getDuration() + "\n");
            writer.write("Tasks: " + schedule.getTasks() + "\n");
            writer.write("Date Scheduled: " + schedule.getDateScheduled() + "\n");
            writer.write("----------------------------------------------------------------------\n");
            writer.write("Assigned Personnel by IDs:\n");
            for (String s : schedule.getAssignedEmployeeIDs()){
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
     * Returns minimum amount of security personnel required for a given
     * priority level
     * @param priorityLevel given priority level
     * @return minimum number of employees required
     */
    private int minimumEmployeeRequirement(String priorityLevel){

        return switch (priorityLevel) {
            case "3" -> Integer.MAX_VALUE;
            case "2" -> 10;
            case "1" -> 7;
            case "0" -> 3;
            default -> -1;
        };
    }

    /**
     * To announce the amount of high priority requests pending resolution
     */
    public void announceHighPriorityRequests(){

        if (!retrievePendingRequests()) {
            return;
        }

        int highPriorityCount = 0;
        for (SecurityRequests request : securityRequestsPriorityQueue){

            if (request.getPriorityLevel().equals("3")){
                highPriorityCount++;
            }
        }

        if (highPriorityCount > 0) {
            System.out.println("There are currently " + highPriorityCount + " high priority security request(s) waiting to be resolved\n");
        }
    }
}
