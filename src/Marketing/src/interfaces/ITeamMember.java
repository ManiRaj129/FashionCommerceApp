package src.Marketing.src.interfaces;

import src.HR.src.Employee;
import src.Marketing.src.Team;

import java.util.Map;

public interface ITeamMember {
    int getId();
    Employee getEmployeeInfo();
    Map<String, String> toMap();
}
