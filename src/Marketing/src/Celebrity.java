package src.Marketing.src;

import src.HR.src.Department;
import src.HR.src.Employee;
import src.Marketing.src.interfaces.ICollabMember;

import java.util.HashMap;
import java.util.Map;

public class Celebrity implements ICollabMember {
    static int nextid = 0;
    int id;

    String name;
    Employee tempEmployeeInfo;

    public Celebrity(int i, String name, Employee employee) {
        this.id = i;
        if (id >= nextid) nextid = id + 1;
        this.name = name;
        this.tempEmployeeInfo = employee;
    }

    public Celebrity(String name) {
        id = nextid;
        nextid++;
        this.name = name;
        this.tempEmployeeInfo = new Employee("Celebrity", name, Department.MODELING, "Temp Worker", "Contract", 1000000);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getEmployeeInfo() {
        return this.tempEmployeeInfo.toString();
    }

    @Override
    public boolean requestCollab() {
        return Math.random() < 0.5;
    }

    @Override
    public void changeName(String name) {
        this.tempEmployeeInfo.changeName(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nCollab Member " + id + ":" + "\n Name: " + getName() + "\n Contract Details: " + getEmployeeInfo();
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> brandDetails = new HashMap<>();
        brandDetails.put("id", Integer.toString(this.id));
        brandDetails.put("name", this.name);
        brandDetails.put("contractDetail", this.getEmployeeInfo());
        return brandDetails;
    }

    public static Celebrity parse(Map<String, String> celeb) {
        return new Celebrity(
                Integer.parseInt(celeb.get("id")),
                celeb.get("name"),
                Employee.parseEmployee(celeb.get("contractDetail"))
        );
    }

}
