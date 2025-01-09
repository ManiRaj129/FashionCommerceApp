/**
 * @author Kenny
 */

package src.Security.src;

import src.TextEditor.PoorTextEditor;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AuditScheduler implements src.Security.src.interfaces.AuditScheduler {

    PriorityQueue<AuditSchedules> ongoingAuditSchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.comparingInt(request -> -Integer.parseInt(request.getPriorityLevel()))
    );
    PriorityQueue<AuditSchedules> passedAuditSchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.comparingInt(request -> -Integer.parseInt(request.getPriorityLevel()))
    );
    PriorityQueue<AuditSchedules> failedAuditSchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.comparingInt(request -> -Integer.parseInt(request.getPriorityLevel()))
    );
    PriorityQueue<AuditSchedules> allAuditSchedulesPriorityQueue = new PriorityQueue<>(
            Comparator.comparingInt(request -> -Integer.parseInt(request.getPriorityLevel()))
    );
    /**
     * Repository to store all security request from departments
     */
    Map<String, AuditSchedules> auditSchedulesRepository = new LinkedHashMap<>();

    // sorts audit employees first by division, then position, then by rating in descending order
    PriorityQueue<AuditEmployee> availableAuditEmployeePriorityQueue = new PriorityQueue<>(
            Comparator.comparing(AuditEmployee::getDivision)
                    .thenComparing(AuditEmployee::getPosition)
                    .thenComparing((e1, e2) -> e2.getRating().compareTo(e1.getRating()))
    );
    PriorityQueue<AuditEmployee> allAuditEmployeePriorityQueue = new PriorityQueue<>(
            Comparator.comparing(AuditEmployee::getDivision)
                    .thenComparing(AuditEmployee::getPosition)
                    .thenComparing((e1, e2) -> e2.getRating().compareTo(e1.getRating()))
    );
    /**
     * Repository to store all security employees obtained
     */
    Map<String, AuditEmployee> auditEmployeeRepository = new LinkedHashMap<>();

    private final String auditSchedulesDir = "src/Security/repository/auditSchedules/";
    private final String auditEmployeeDir = "src/Security/repository/auditEmployees/";

    private final String printedAuditSchedulesDir = "src/Security/repository/printedAuditSchedules/";

    private final String securitySchedulesDir = "src/Security/repository/departmentRequests/";

    PoorTextEditor editor = new PoorTextEditor();
    private File[] auditSchedulesFiles = null;

    @Override
    public boolean addAuditPersonnel() {

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
        newEmployeeID = "DoS_Employee_Audit_" + ID;

        Scanner scan = new Scanner(System.in);
        Map<String, Object> newEmployeeData = new LinkedHashMap<>();

        System.out.println("Adding employee under ID: " + newEmployeeID);
        System.out.println("Enter employee name: ");
        newEmployeeData.put("name", scan.nextLine());

        System.out.println("Enter employee division: ");
        newEmployeeData.put("division", scan.nextLine());

        System.out.println("Enter employee position: ");
        newEmployeeData.put("position", scan.nextLine());

        newEmployeeData.put("department", "Audit");

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
        editor.writeToTextFile(auditEmployeeDir + "auditEmployeeList.txt");
        return true;
    }

    @Override
    public boolean deleteAuditPersonnel(String employeeID) {

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
        editor.writeToTextFile(auditEmployeeDir + "auditEmployeeList.txt");
        System.out.println("Employee with ID: " + employeeID + " successfully deleted");
        return true;
    }

    @Override
    public boolean showOngoingAudits() {

        if (!retrieveAllAudits()){
            return false;
        }

        if (ongoingAuditSchedulesPriorityQueue.isEmpty()){

            System.out.println("No ongoing audits found");
            return false;
        }

        for (AuditSchedules schedule : ongoingAuditSchedulesPriorityQueue){

            auditScheduleToText(schedule);
        }
        return true;
    }

    @Override
    public boolean showPassedAudits() {

        if (!retrieveAllAudits()){
            return false;
        }

        if (passedAuditSchedulesPriorityQueue.isEmpty()){

            System.out.println("No passed audits found");
            return false;
        }

        for (AuditSchedules schedules : passedAuditSchedulesPriorityQueue){

            auditScheduleToText(schedules);
        }
        return true;
    }

    @Override
    public boolean showFailedAudits() {

        if (!retrieveAllAudits()){
            return false;
        }

        if (failedAuditSchedulesPriorityQueue.isEmpty()){

            System.out.println("No failed audits found");
            return false;
        }

        for (AuditSchedules schedule : failedAuditSchedulesPriorityQueue){

            auditScheduleToText(schedule);
        }
        return true;
    }

    @Override
    public boolean showAllAudits() {

        if (!retrieveAllAudits()){
            return false;
        }

        if (allAuditSchedulesPriorityQueue.isEmpty()){

            System.out.println("No audits found");
            return false;
        }

        for (AuditSchedules schedule : allAuditSchedulesPriorityQueue){

            auditScheduleToText(schedule);
        }
        return true;
    }

    @Override
    public boolean showAuditByID(String auditID) {

        if (!retrieveAllAudits()){
            return false;
        }

        if (!auditSchedulesRepository.containsKey(auditID)){
            System.out.println("Audit schedule with ID: " + auditID + "not found");
            return false;
        }

        auditScheduleToText(auditSchedulesRepository.get(auditID));
        return true;
    }

    @Override
    public boolean showFreeAuditEmployees() {

        if (!retrieveEmployees()){
            return false;
        }

        for (AuditEmployee employee : availableAuditEmployeePriorityQueue){

            auditEmployeeToText(employee);
        }
        return true;
    }

    @Override
    public boolean showAllAuditEmployees() {

        if (!retrieveEmployees()){
            return false;
        }

        for (AuditEmployee employee : allAuditEmployeePriorityQueue){

            auditEmployeeToText(employee);
        }
        return true;
    }

    @Override
    public boolean createAudit() {

        Scanner scan = new Scanner(System.in);

        String scheduleID = "audit-" + IDGenerator();
        System.out.println("Enter priority level: (0 = Low,  1 = Medium, 2 = High, 3 = Emergency)");
        String priorityLevel = scan.nextLine();

        System.out.println("Enter department to audit: ");
        String department = scan.nextLine();

        System.out.println("Enter audit location: (Country - Specific Location)");
        String location = scan.nextLine();

        System.out.println("Enter description of audit: ");
        String description = scan.nextLine();

        System.out.println("Enter duration of audit: (MM-DD-YYYY TT:MM a.m. - TT:MM p.m.)");
        String duration = scan.nextLine();

        System.out.println("Enter tasks of audit: ");
        String tasks = scan.nextLine();

        String status = "ongoing";
        String dateScheduled = dateIssuer();

        AuditSchedules schedule = new AuditSchedules(
                scheduleID,
                priorityLevel,
                department,
                location,
                description,
                duration,
                tasks,
                status,
                dateScheduled);

        // assigning security personnel to schedule
        if (!addPersonnelToSchedule(schedule)){
            System.out.println("Schedule creation for Request ID: " + scheduleID + " was cancelled");
            return false;
        }

        // writing schedule to repository
        editor.setRepository(auditScheduleToHashMap(schedule));
        editor.writeToTextFile(auditSchedulesDir + schedule.getScheduleID() + ".txt");

        // writing printed schedules to repository
        printSecuritySchedule(schedule);

        return true;
    }

    @Override
    public boolean deleteAudit(String auditID) {

        if (auditEmployeeRepository.isEmpty()){

            if (!retrieveEmployees()){
                return false;
            }
        }

        if (!retrieveAllAudits()){
            return false;
        }

        if (!auditSchedulesRepository.containsKey(auditID)){

            System.out.println("Audit Schedule ID: " + auditID + " not found");
            return false;
        }

        AuditSchedules schedule = auditSchedulesRepository.get(auditID);

        if (!schedule.getAssignedEmployeeIDs().isEmpty()){

            List<String> scheduledEmployeeIDs = schedule.getAssignedEmployeeIDs();
            editor.processTextFile(auditEmployeeDir + "auditEmployeeList.txt");

            for (String s : scheduledEmployeeIDs){

                editor.setValue(s,"currentAssignment", "free");
            }

            editor.writeToTextFile(auditEmployeeDir + "auditEmployeeList.txt");
        }

        File fileToDelete = new File(auditSchedulesDir, auditID + ".txt");

        if (fileToDelete.exists()) {

            if (fileToDelete.delete()) {

                System.out.println("Audit schedule deleted successfully");
            } else {

                System.out.println("Failed to audit schedule");
                return false;
            }
        } else {
            System.out.println("Audit schedule not found");
            return false;
        }
        return true;
    }

    @Override
    public boolean editAudit(String auditID) {

        if (!retrieveAllAudits()){
            return false;
        }

        if (!auditSchedulesRepository.containsKey(auditID)){

            System.out.println("Audit schedule with ID: " + auditID + " does not exists");
            return false;
        }

        AuditSchedules schedule = auditSchedulesRepository.get(auditID);
        Scanner scan = new Scanner(System.in);

        boolean finish = false;

        while (!finish){

            System.out.println("Select field to edit (audit ID: " + auditID + "):");
            System.out.println("1. Audit Priority Level. Current: " + schedule.getPriorityLevel());
            System.out.println("2. Audited Department. Current: " + schedule.getDepartment());
            System.out.println("3. Audit Location. Current: " + schedule.getLocation());
            System.out.println("4. Audit Description. Current: " + schedule.getDescription());
            System.out.println("5. Audit Duration. Current: " + schedule.getDuration());
            System.out.println("6. Audit Tasks. Current: " + schedule.getTasks());
            System.out.println("7. Audit Status. Current: " + schedule.getStatus());
            System.out.println("0. Complete Editing");

            String choice = scan.nextLine();

            switch (choice){

                case "1" -> {
                    System.out.println("Enter priority level: (0 = Low,  1 = Medium, 2 = High, 3 = Emergency)");
                    schedule.setPriorityLevel(scan.nextLine());
                }
                case "2" -> {
                    System.out.println("Enter department to audit: ");
                    schedule.setDepartment(scan.nextLine());
                }
                case "3" -> {
                    System.out.println("Enter audit location: (Country - City - Specific Location)");
                    schedule.setLocation(scan.nextLine());
                }
                case "4" -> {
                    System.out.println("Enter description of audit: ");
                    schedule.setDescription(scan.nextLine());
                }
                case "5" -> {
                    System.out.println("Enter duration of audit: (MM-DD-YYYY TT:MM a.m. - TT:MM p.m.)");
                    schedule.setDuration(scan.nextLine());
                }
                case "6" -> {
                    System.out.println("Enter tasks of audit: ");
                    schedule.setTasks(scan.nextLine());
                }
                case "7" -> {
                    System.out.println("Enter audit status: (ongoing, pass, fail)");
                    schedule.setStatus(scan.nextLine());
                }
                case "0" -> finish =  true;

                default -> System.out.println("No such field to edit. Try again.");
            }
        }

        if (!addPersonnelToSchedule(schedule)){
            System.out.println("Updating cancelled");
            return false;
        }

        // writing schedule to repository
        editor.setRepository(auditScheduleToHashMap(schedule));
        editor.writeToTextFile(auditSchedulesDir + schedule.getScheduleID() + ".txt");

        // writing printed schedules to repository
        printSecuritySchedule(schedule);

        System.out.println("Successfully updated");
        return true;
    }

    @Override
    public boolean sendFailedAudit(String auditID) {

        if (!retrieveAllAudits()){
            return false;
        }

        if (failedAuditSchedulesPriorityQueue.isEmpty()){

            System.out.println("No failed audits found");
            return false;
        }

        if (!auditSchedulesRepository.containsKey(auditID)){

            System.out.println("Security audit with ID: " + auditID + " not found");
            return false;
        }

        AuditSchedules audit = auditSchedulesRepository.get(auditID);

        if (!audit.getStatus().equals("fail")){

            System.out.println("Security audit with ID: " + auditID + " is not a failed audit");
            return false;
        }

        List<String> employeeList = audit.getAssignedEmployeeIDs();
        String authors = "Audit authors: ";
        for (String s : employeeList){
            authors = authors + s + "|";
        }

        Map<String, Object> requestData = new LinkedHashMap<>();
        requestData.put("priorityLevel", audit.getPriorityLevel());
        requestData.put("department", audit.getDepartment());
        requestData.put("location", audit.getLocation());
        requestData.put("description", audit.getDescription());

        Scanner scan = new Scanner(System.in);
        System.out.println("Specify security request duration: (MM-DD-YYYY HH:MM a.m. - HH:MM p.m.)");
        requestData.put("duration", scan.nextLine());

        requestData.put("tasks", "Audit tasks (" + audit.getTasks() + ")");
        requestData.put("specialReqs", authors);
        requestData.put("dateIssued", dateIssuer());
        requestData.put("resolved", "false");

        editor.processTextFile(securitySchedulesDir + "Audit_Security_Requests.txt");
        Map<String, Object> repository = editor.getRepository();
        repository.put("DoS_Failed_Audit_" + audit.getScheduleID(), requestData);
        editor.setRepository(repository);
        editor.writeToTextFile(securitySchedulesDir + "Audit_Security_Requests.txt");

        System.out.println("Failed audit with ID: " + auditID + " successfully sent to Security");
        return true;
    }

    /**
     * To add audit personnel to a schedule
     * @param schedule given schedule
     */
    private boolean addPersonnelToSchedule(AuditSchedules schedule){

        if (auditEmployeeRepository.isEmpty()){

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
            System.out.println("1. Add Audit Employee by ID");
            System.out.println("2. Remove Audit Employee by ID");
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

                    if (!auditEmployeeRepository.containsKey(employeeID)){

                        System.out.println("Employee with ID: " + employeeID + " does not exists");
                        break;
                    }

                    AuditEmployee employee = auditEmployeeRepository.get(employeeID);

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
                            editor.processTextFile(auditEmployeeDir + "auditEmployeeList.txt");
                            editor.setValue(employee.getEmployeeID(), "previousAssignment", employee.getPreviousAssignment());
                            editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                            editor.writeToTextFile(auditEmployeeDir + "auditEmployeeList.txt");

                            employeeCount++;
                        }
                    }
                    else {

                        availableAuditEmployeePriorityQueue.remove(employee);
                        employee.setCurrentAssignment(schedule.getScheduleID());
                        selectedEmployeeIDs.add(employee.getEmployeeID());

                        // updating employee assignments
                        editor.processTextFile(auditEmployeeDir + "auditEmployeeList.txt");
                        editor.setValue(employee.getEmployeeID(), "currentAssignment", employee.getCurrentAssignment());
                        editor.writeToTextFile(auditEmployeeDir + "auditEmployeeList.txt");

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
                    AuditEmployee employee2 = auditEmployeeRepository.get(removingID);
                    employee2.setCurrentAssignment("free");
                    availableAuditEmployeePriorityQueue.add(employee2);

                    // updating employee assignments
                    editor.processTextFile(auditEmployeeDir + "auditEmployeeList.txt");
                    editor.setValue(employee2.getEmployeeID(), "currentAssignment", employee2.getCurrentAssignment());
                    editor.writeToTextFile(auditEmployeeDir + "auditEmployeeList.txt");

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

                        AuditEmployee resetEmployee = auditEmployeeRepository.get(s);
                        resetEmployee.setCurrentAssignment("free");
                        availableAuditEmployeePriorityQueue.add(resetEmployee);

                        // updating employee assignments
                        editor.processTextFile(auditEmployeeDir + "auditEmployeeList.txt");
                        editor.setValue(resetEmployee.getEmployeeID(), "currentAssignment", resetEmployee.getCurrentAssignment());
                        editor.writeToTextFile(auditEmployeeDir + "auditEmployeeList.txt");
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
     * Retrieve all audit schedules
     * @return true if successful retrieval, otherwise false
     */
    private boolean retrieveAllAudits(){

        if (!retrieveAuditFiles()){
            return false;
        }

        // clearing everything for new retrieval
        auditSchedulesRepository.clear();
        allAuditSchedulesPriorityQueue.clear();
        ongoingAuditSchedulesPriorityQueue.clear();
        failedAuditSchedulesPriorityQueue.clear();
        passedAuditSchedulesPriorityQueue.clear();

        // saving file requests to repository
        for (File f : auditSchedulesFiles) {

            editor.processTextFile(auditSchedulesDir + f.getName());
            String[] audits = editor.getArrayNames();
            String id = audits[0];

            for (String s : audits) {

                AuditSchedules audit = new AuditSchedules(s,
                        editor.retrieveValue(s, "priorityLevel"),
                        editor.retrieveValue(s, "department"),
                        editor.retrieveValue(s, "location"),
                        editor.retrieveValue(s, "description"),
                        editor.retrieveValue(s, "duration"),
                        editor.retrieveValue(s, "tasks"),
                        editor.retrieveValue(s, "status"),
                        editor.retrieveValue(s, "dateScheduled"));

                List<String> employeeIDs = new ArrayList<>();
                int num = 0;
                String employeeNum = "employee" + num;
                while ((editor.retrieveValue(id, employeeNum) != null)){

                    employeeIDs.add(editor.retrieveValue(id, "employee" + num));
                    num++;
                    employeeNum = "employee" + num;
                }
                audit.setAssignedEmployeeIDs(employeeIDs);

                if (audit.getPriorityLevel() == null || audit.getDepartment() == null ||
                        audit.getLocation() == null || audit.getDescription() == null ||
                        audit.getDuration() == null || audit.getTasks() == null ||
                        audit.getStatus() == null || audit.getDateScheduled() == null) {

                    System.out.println("Invalid security requests detected");
                    return false;
                }

                // adding audit schedules to repository and priority queue
                auditSchedulesRepository.put(s, audit);
                allAuditSchedulesPriorityQueue.add(audit);

                if (audit.getStatus().equals("ongoing")){
                    ongoingAuditSchedulesPriorityQueue.add(audit);
                }
                if (audit.getStatus().equals("pass")){
                    passedAuditSchedulesPriorityQueue.add(audit);
                }
                if (audit.getStatus().equals("fail")){
                    failedAuditSchedulesPriorityQueue.add(audit);
                }
            }
        }
        return true;
    }

    /**
     * Retrieve files containing audits
     * @return files containing audits
     */
    private boolean retrieveAuditFiles(){

        File directory = new File(auditSchedulesDir);
        File[] textFiles = null;

        if (directory.exists() && directory.isDirectory()){

            //grab list of text files
            FilenameFilter textFileFilter = (((dir, name) -> name.toLowerCase().endsWith(".txt")));
            textFiles = directory.listFiles(textFileFilter);
        }
        else {
            System.out.println("Repository not found");
            return false;
        }

        if (textFiles != null){

            if (textFiles.length == 0){
                System.out.println("No audit schedules found");
                return false;
            }
            auditSchedulesFiles = textFiles;
        }
        else {
            System.out.println("No audit schedules found");
            return false;
        }
        return true;
    }

    /**
     * Retrieve all audit employees
     * @return all audit employees
     */
    private boolean retrieveEmployees(){

        availableAuditEmployeePriorityQueue.clear();
        allAuditEmployeePriorityQueue.clear();
        auditEmployeeRepository.clear();

        File directory = new File(auditEmployeeDir);
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
                System.out.println("No audit employees found");
                return false;
            }
        }
        else {
            System.out.println("No audit employees found");
            return false;
        }

        // saving employees to repository
        for (File f : textFiles) {

            editor.processTextFile(auditEmployeeDir + f.getName());
            String[] employees = editor.getArrayNames();

            for (String s : employees) {

                AuditEmployee employee = new AuditEmployee(s,
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
                    availableAuditEmployeePriorityQueue.add(employee);
                }
                // adding security request to priority queue
                allAuditEmployeePriorityQueue.add(employee);
                auditEmployeeRepository.put(s, employee);
            }
        }
        return true;
    }

    /**
     * Return all audit employees hashmap repo
     * @return all audit employees hashmap repo
     */
    private Map<String, Object> retrieveEmployeeHashmap(){

        File directory = new File(auditEmployeeDir);
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

        editor.processTextFile(auditEmployeeDir + textFiles[0].getName());
        return editor.getRepository();
    }

    /**
     * Converts SecuritySchedules object into nested HashMap
     * @param schedule specified SecuritySchedule
     * @return HashMap of SecuritySchedule
     */
    private Map<String, Object> auditScheduleToHashMap(AuditSchedules schedule){

        Map<String, Object> innerMap = new LinkedHashMap<>();
        innerMap.put("priorityLevel", schedule.getPriorityLevel());
        innerMap.put("department", schedule.getDepartment());
        innerMap.put("location", schedule.getLocation());
        innerMap.put("description", schedule.getDescription());
        innerMap.put("duration", schedule.getDuration());
        innerMap.put("tasks", schedule.getTasks());
        innerMap.put("status",schedule.getStatus());
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
     * Pretty print audit schedules
     * @param schedule specific audit schedule
     */
    private void auditScheduleToText(AuditSchedules schedule){

        System.out.println("=======================================================");
        System.out.println("Audit Schedule ID: " + schedule.getScheduleID());
        System.out.println("Priority Level: " + schedule.getPriorityLevel());
        System.out.println("Department: " + schedule.getDepartment());
        System.out.println("------------------------------------------------------");
        System.out.println("Location: " + schedule.getLocation());
        System.out.println("Description: " + schedule.getDescription());
        System.out.println("Duration: " + schedule.getDuration());
        System.out.println("Tasks: " + schedule.getTasks());
        System.out.println("------------------------------------------------------");
        System.out.println("Date Issued: " + schedule.getDateScheduled());
        System.out.println("Audit Status: " + schedule.getStatus());
        System.out.println("------------------------------------------------------");
        System.out.println("Assigned Security Members:");
        for (String s : schedule.getAssignedEmployeeIDs()){
            System.out.println(s);
        }
        System.out.println("=======================================================\n");
    }

    /**
     * Pretty print security employee
     * @param employee specific audit employee
     */
    private void auditEmployeeToText(AuditEmployee employee){

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
     * To created printable audit schedules
     * @param schedule specific audit schedule
     */
    private void printSecuritySchedule(AuditSchedules schedule){

        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(printedAuditSchedulesDir + schedule.getScheduleID() + ".txt"));
            writer.write("======================================================================\n");
            writer.write("AUDIT SCHEDULE\n");
            writer.write("Schedule ID: " + schedule.getScheduleID() + "\n");
            writer.write("Priority Level: " + schedule.getPriorityLevel() + " (3 = Emergency, 2 = High, 1 = Medium, 0 = Low)\n");
            writer.write("Department: " + schedule.getDepartment() + "\n");
            writer.write("----------------------------------------------------------------------\n");
            writer.write("Location: " + schedule.getLocation() + "\n");
            writer.write("Description: " + schedule.getDescription() + "\n");
            writer.write("Duration :" + schedule.getDuration() + "\n");
            writer.write("Tasks: " + schedule.getTasks() + "\n");
            writer.write("----------------------------------------------------------------------\n");
            writer.write("Date Issued: " + schedule.getDateScheduled() + "\n");
            writer.write("Audit status: " + schedule.getStatus() + "\n");
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
}
