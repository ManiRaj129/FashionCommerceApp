/**
 * @author Kenny
 */

package src.Security.src;

import src.TextEditor.PoorTextEditor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SecurityRequestScheduler {

    private PoorTextEditor editor = new PoorTextEditor();
    private final String securitySchedulesDir = "src/Security/repository/departmentRequests/";
    private Map<String, Object> repositoryToCheck = new LinkedHashMap<>();

    /**
     * Allows the scheduling of security requests
     * @return true if successful, false otherwise
     */
    public boolean addSecurityRequest(){

        Scanner scan = new Scanner(System.in);
        showAllDepartmentTags();
        String choice = scan.nextLine();

        String fileName = getFileNameByTag(choice);
        if (fileName == null){
            return false;
        }

        editor.processTextFile(securitySchedulesDir + fileName);
        repositoryToCheck = editor.getRepository();
        String latestRequest = null;

        for (String requestID : repositoryToCheck.keySet()){
            latestRequest = requestID;
        }

        String requestIDNum = "-1";

        if (latestRequest != null){
            requestIDNum = latestRequest.substring(latestRequest.length() - 4);
        }

        String ID = String.format("%04d", Integer.parseInt(requestIDNum) + 1);
        String newID = choice + "_Sec_Req_" + ID;

        System.out.println("Current security request ID: " + newID);
        Map<String, Object> newRequest = new LinkedHashMap<>();

        System.out.println("Specify priority level: (3 = Emergency, 2 = High, 1 = Medium, 0 = Low)");
        String priorityLevel = scan.nextLine();

        if (Integer.parseInt(priorityLevel) < 0 || Integer.parseInt(priorityLevel) > 3){

            System.out.println("No such level");
            return false;
        }
        else {
            newRequest.put("priorityLevel", priorityLevel);
        }

        String department = getDepartmentByTag(choice);
        newRequest.put("department", department);

        System.out.println("Specify location: (Country - City - Specific location name)");
        String location = scan.nextLine();
        newRequest.put("location", location);

        System.out.println("Specify security request description:");
        String description = scan.nextLine();
        newRequest.put("description", description);

        System.out.println("Specify security request duration: (MM-DD-YYYY HH:MM a.m. - HH:MM p.m.)");
        String duration = scan.nextLine();
        newRequest.put("duration", duration);

        System.out.println("Specify security request tasks:");
        String tasks = scan.nextLine();
        newRequest.put("tasks", tasks);

        System.out.println("Specify special requests:");
        String specialReqs = scan.nextLine();
        newRequest.put("specialReqs", specialReqs);

        String dateIssued = dateIssuer();
        newRequest.put("dateIssued", dateIssued);

        newRequest.put("resolved", "false");

        repositoryToCheck.put(newID, newRequest);
        editor.setRepository(repositoryToCheck);
        editor.writeToTextFile(securitySchedulesDir + fileName);

        return true;
    }

    /**
     * Show all security requests
     * @return alls ecurity requests
     */
    public boolean showAllSecurityRequests(){

        Scanner scan = new Scanner(System.in);
        showAllDepartmentTags();
        String choice = scan.nextLine();

        if (getFileNameByTag(choice) == null){
            return false;
        }

        editor.processTextFile(securitySchedulesDir + getFileNameByTag(choice));

        if (editor.getRepository().isEmpty()){

            System.out.println("No security requests found");
            return false;
        }
        editor.prettyPrint();
        return true;
    }

    /**
     * Delete specific security schedule by ID
     * @return true if successful deletion, false otherwise
     */
    public boolean deleteScheduleByID(){

        Scanner scan = new Scanner(System.in);
        showAllDepartmentTags();
        String choice = scan.nextLine();

        String fileName = getFileNameByTag(choice);
        if (fileName == null){
            return false;
        }

        editor.processTextFile(securitySchedulesDir + fileName);
        repositoryToCheck = editor.getRepository();

        System.out.println("Enter security request ID to delete:");
        String deleteID = scan.nextLine();

        if (!repositoryToCheck.containsKey(deleteID)){

            System.out.println("Security request ID: " + deleteID + " does not exists");
            return false;
        }

        repositoryToCheck.remove(deleteID);
        editor.setRepository(repositoryToCheck);
        editor.writeToTextFile(securitySchedulesDir + fileName);
        System.out.println("Security schedule with ID: " + deleteID + " has been successfully deleted");
        return true;
    }

    /**
     * Retrieve department name by tag
     * @param tag department tag
     * @return department name
     */
    private String getDepartmentByTag(String tag){

        return switch (tag){

            case "DoD" -> "Department of Design";
            case "DoHR" -> "Department of Human Resources";
            case "DoI" -> "Department of Inventory";
            case "DoMfg" -> "Department of Manufacturing";
            case "DoMkt" -> "Department of Marketing";
            case "DoMod" -> "Department of Modelling";
            case "DoSal" -> "Department of Sales";
            case "DoT" -> "Department of Treasury";
            default -> null;
        };
    }

    /**
     * Retrieve department security request file by tag
     * @param tag department tag
     * @return department security request file
     */
    private String getFileNameByTag(String tag){

        String fileName;
        switch (tag){

            case "DoD" -> fileName = "DoD_Security_Requests.txt";
            case "DoHR" -> fileName = "DoHR_Security_Requests.txt";
            case "DoI" -> fileName = "DoI_Security_Requests.txt";
            case "DoMfg" -> fileName = "DoMfg_Security_Requests.txt";
            case "DoMkt" -> fileName = "DoMkt_Security_Requests.txt";
            case "DoMod" -> fileName = "DoMod_Security_Requests.txt";
            case "DoSal" -> fileName = "DoSal_Security_Requests.txt";
            case "DoT" -> fileName = "DoT_Security_Requests.txt";
            default -> {
                System.out.println("Department does not exists.");
                fileName = null;
            }
        }
        return fileName;
    }

    /**
     * Show all department names with tags
     */
    private void showAllDepartmentTags(){

        System.out.println("Specify your department by signature:");
        System.out.println("DoD (Department of Design)");
        System.out.println("DoHR (Department of Human Resources)");
        System.out.println("DoI (Department of Inventory)");
        System.out.println("DoMfg (Department of Manufacturing)");
        System.out.println("DoMkt (Department of Marketing)");
        System.out.println("DoMod (Department of Modelling)");
        System.out.println("DoSal (Department of Sales)");
        System.out.println("DoT (Department of Treasury)");
    }

    /**
     * Issues date in MM-dd-yyyy format
     * @return date in MM-dd-yyyy format
     */
    private String dateIssuer(){

        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        return timeNow.format(formatter);
    }

    /**
     * Print available security request options
     */
    public void optionsPrint(){

        System.out.println("111. Create Security Request");
        System.out.println("112. Show All Security Requests");
        System.out.println("113. Delete Security Request By ID");
    }
}
