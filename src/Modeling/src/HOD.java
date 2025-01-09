package src.Modeling.src;

import src.HR.src.Department;
import src.HR.src.Employee;
import src.Modeling.ModelingDepartment;
import src.Modeling.src.interfaces.IHOD;

import java.time.LocalDateTime;
import java.util.*;

public class HOD implements IHOD {

    private final Employee employeeInfo;
    private final ArrayList<Manager> managers;

    public ArrayList<Event> events;
    public ArrayList<Fitting> fittings;

    public HOD(Employee employeeInfo, ArrayList<Manager> managers) {
        this.employeeInfo = employeeInfo;
        this.managers = managers;

        events = ModelingDepartment.fileManager.getEvents();
        fittings = ModelingDepartment.fileManager.getFittings();
    }

    public HOD() {
        employeeInfo = new Employee("hod", "Head of Modeling", Department.MODELING, "HOD", "Employeed", 100000);
        managers = new ArrayList<>();
        managers.add(new Manager(Team.MODELING));
        managers.add(new Manager(Team.MAKEUP));
        managers.add(new Manager(Team.CLOTHING));
        managers.add(new Manager(Team.STORAGE));

        ModelingDepartment.fileManager.addHOD(this);
    }

    @Override
    public Event createEvent(Boolean type, String celebrity, String collab) {
        ArrayList<TeamMember> teamMembers = new ArrayList<>();

        for(Manager manager: managers) {
            ArrayList<TeamMember> team = manager.getTeamMembers();
            team.addAll(manager.getTeamMembers());
        }

        Event event = new Event(teamMembers, type, celebrity, collab);

        events.add(event);
        System.out.println(event);
        ModelingDepartment.fileManager.addEvent(event);

        return event;
    }

    @Override
    public Event createEvent(Boolean type, int modelID, String collab) {
        TeamMember model = ModelingDepartment.fileManager.getModel(modelID);
        Event event = new Event(model, type, collab);

        events.add(event);
        System.out.println(event);
        ModelingDepartment.fileManager.addEvent(event);

        return event;
    }

    @Override
    public int getId() {return 0;}

    @Override
    public Employee getEmployeeInfo() {
        return employeeInfo;
    }

    @Override
    public ArrayList<Event> getEvents() {return events;}

    @Override
    public Event updateEvent(Event event) {
        for(Event e: events) {
            if(e.getId() == event.getId()) {
                e = event;
                ModelingDepartment.fileManager.updateEvent(event);
                return e;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Fitting> getFittings() {return fittings;}

    @Override
    public Map<String, String> toMap() {
        Map<String, String> memberDetails = new HashMap<>();
        memberDetails.put("employeeInfo", this.employeeInfo.toString());
        Integer[] tmp = new Integer[managers.size()];
        for(int i = 0; i < managers.size(); i++) {
            tmp[i] = managers.get(i).getId();
        }
        memberDetails.put("managers", Arrays.toString(tmp));
        return memberDetails;
    }

    @Override
    public void addManager(Manager manager) {
        managers.add(manager);
        ModelingDepartment.fileManager.addManager(manager);
    }

    @Override
    public Manager getManager(Team team) {
        for (Manager manager: managers) {
            if (manager.getTeam() == team) {
                return manager;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\nHOD: ");
        str.append("\nEmployeeInfo: ").append(this.employeeInfo.toString());
        str.append("\nManagers: ");
        for (Manager manager : managers) {
            str.append("\n  ").append(manager.toString());
        }
        return str.toString();
    }

    @Override
    public void requestFitting(Team team, TeamMember model, String garment, LocalDateTime date) {
        Fitting fitting = getManager(team).scheduleFitting(model, garment, date);
        fittings.add(fitting);
        System.out.println(fitting.toString());
        ModelingDepartment.fileManager.addFitting(fitting);
    }

    public static HOD parse(Map<String, String> hod) {
        return new HOD(
                Employee.parseEmployee(hod.get("employeeInfo")),
                ModelingDepartment.fileManager.getManagers()
        );
    }
}
