/**
 * @author Kenny
 */

package src.PublicRelations.src.interfaces;

public interface ContentScheduler {

    boolean addPREmployee();

    boolean deletePREmployee(String employeeID);

    boolean showFreePlanningEmployees();

    boolean showAllPlanningEmployees();

    boolean showFreeReviewEmployees();

    boolean showAllReviewEmployees();

    boolean showPendingRequests();

    boolean showAllRequests();

    boolean createContentSchedule(String requestID);

    boolean deleteContentSchedule(String contentID);

    boolean editContentScheduleAssignment(String contentID);

    boolean editContentScheduleStatus(String contentID);

    boolean showAllContentSchedules();

    boolean showPlanningContentSchedules();

    boolean showReviewingContentSchedules();

    boolean showRevisingContentSchedules();

    boolean showReadyContentSchedules();

    boolean showContentScheduleByID(String contentID);
}
