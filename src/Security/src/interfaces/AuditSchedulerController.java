/**
 * @author Kenny
 */

package src.Security.src.interfaces;

public interface AuditSchedulerController {

    boolean addAuditPersonnel();

    boolean deleteAuditPersonnel(String employeeID);

    boolean showOngoingAudits();

    boolean showPassedAudits();

    boolean showFailedAudits();

    boolean showAllAudits();

    boolean showAuditByID(String auditID);

    boolean showFreeAuditEmployees();

    boolean showAllAuditEmployees();

    boolean createAudit();

    boolean deleteAudit(String auditID);

    boolean editAudit(String auditID);

    boolean sendFailedAudit(String auditID);
}
