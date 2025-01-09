package src.Modeling.src;

import src.HR.src.Department;
import src.HR.src.Employee;
import src.Modeling.ModelingDepartment;
import src.Modeling.src.interfaces.ITeamMember;

import java.util.HashMap;
import java.util.Map;

public class TeamMember implements ITeamMember {
    static int nextid = 0;
    int id;
    Employee employeeInfo;
    Team team;

    TeamMember(int i, Employee employee, Team team) {
        id = i;
        if (id >= nextid) nextid = id + 1;
        employeeInfo = employee;
        this.team = team;
    }

    TeamMember(Team team) {
        id = nextid;
        nextid++;
        employeeInfo = new Employee("teamMember", team.toString() + " Member", Department.MODELING, "teamMember", "Employeed", 80000);
        this.team = team;

        ModelingDepartment.fileManager.addTeamMember(this);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.team.toString() + " " + this.id + ": " + employeeInfo.toString();
    }

    @Override
    public Employee getEmployeeInfo() {
        return employeeInfo;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> memberDetails = new HashMap<>();
        memberDetails.put("id", Integer.toString(this.id));
        memberDetails.put("employeeInfo", this.employeeInfo.toString());
        memberDetails.put("team", this.team.toString());
        return memberDetails;
    }

    public static TeamMember parse(Map<String, String> member) {
        return new TeamMember(
                Integer.parseInt(member.get("id")),
                Employee.parseEmployee(member.get("employeeInfo")),
                Team.parseTeam(member.get("team"))
        );

    }
}
