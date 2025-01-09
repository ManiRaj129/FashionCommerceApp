/**
 * @author Kenny
 */

package src.PublicRelations.src.interfaces;

public interface ActivitySchedulerController {
    
    boolean addPREmployee();
    
    boolean deletePREmployee(String employeeID);

    boolean showFreePlanningEmployees();

    boolean showAllPlanningEmployees();

    boolean showFreeReviewEmployees();

    boolean showAllReviewEmployees();

    boolean createActivity();
    
    boolean deleteActivity(String activityID);
    
    boolean editActivity(String activityID);
    
    boolean showAllActivities();

    boolean showDamageControlActivities();

    boolean showPlanningActivities();

    boolean showReviewingActivities();

    boolean showRevisingContents();

    boolean showReadyContents();
    
    boolean showActivityByID(String activityID);
}
