/**
 * @author Kenny
 */

package src.Security.src.interfaces;

import java.util.Scanner;

public interface SecuritySchedulerController {

    boolean addSecurityPersonnel();

    boolean deleteSecurityPersonnel(String employeeID);
    /**
     * Show all pending security requests
     * @return true if successful retrieval, otherwise false
     */
    boolean showPendingEvents();

    /**
     * Show all security requests
     * @return true if successful retrieval, otherwise false
     */
    boolean showAllEvents();

    boolean showFreeSecurityEmployees();

    boolean showAllSecurityEmployees();

    /**
     * Create a new security schedule based on an available security request ID
     * @param requestID specified requestID
     * @return true if successfully created, false otherwise
     */
    boolean createSchedule(String requestID);

    boolean deleteSchedule(String scheduleID);

    boolean editScheduleAssignments(String scheduleID);

    boolean showAllSchedules();

    boolean showScheduleByID(String ScheduleID);
}
