package src.Modeling.src.interfaces;

import src.HR.src.Employee;

import java.util.Map;

public interface ITeamMember {
    int getId();
    Employee getEmployeeInfo();
    Map<String, String> toMap();
}
