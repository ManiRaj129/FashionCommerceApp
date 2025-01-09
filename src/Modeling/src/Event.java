package src.Modeling.src;

import src.Modeling.ModelingDepartment;
import src.Modeling.src.interfaces.IEvent;

import java.util.*;

public class Event implements IEvent {
    static int nextid = 0;
    int id;
    ArrayList<TeamMember> teamMembers;
    Boolean type; //true for photoshoot, false for fashion show
    String celebrity;
    String collab;
    Boolean completionStatus;

    Event(ArrayList<TeamMember> teamMembers, Boolean type, String celebrity, String collab) {
        id = nextid;
        nextid++;
        this.teamMembers = teamMembers;
        this.type = type;
        this.celebrity = celebrity;
        this.collab = collab;
        this.completionStatus = false;
    }

    Event(int id, ArrayList<TeamMember> teamMembers, Boolean type, String celebrity, String collab, Boolean completionStatus) {
        this.id = id;
        if (id >= nextid) nextid = id+1;
        this.teamMembers = teamMembers;
        this.type = type;
        this.celebrity = celebrity;
        this.collab = collab;
        this.completionStatus = completionStatus;
    }

    Event(TeamMember teamMember, Boolean type, String collab) {
        id = nextid;
        nextid++;
        this.teamMembers = new ArrayList<>();
        this.teamMembers.add(teamMember);
        this.type = type;
        this.celebrity = "";
        this.collab = collab;
        this.completionStatus = false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getType() {
        if(this.type) return "Photoshoot";
        return "Fashion Show";
    }

    @Override
    public String getCelebrity() {
        return this.celebrity;
    }

    @Override
    public void addCelebrity(String name) {
        this.celebrity = name;
    }

    @Override
    public String getCollab() {
        return this.collab;
    }

    @Override
    public String getCompletion() {
        if(completionStatus) return "true";
        return "false";
    }

//    @Override
//    public void endEvent() {
//        this.completionStatus = true;
//        System.out.println(this.toString());
//    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\nEvent: " + id);
        if(this.type) {
            str.append("\n Photoshoot");
        } else {
            str.append("\n Fashion show");
        }
        str.append("\n Team Members: ");

        for (TeamMember teamMember : teamMembers) {
            str.append("\n ").append(teamMember.toString());
        }
        str.append("\n Celebrity: ").append(this.celebrity).append("\n Collab: ").append(this.collab).append("\n Completion Status: ").append(this.completionStatus);
        return str.toString();
    }

    @Override
    public Map<String, String> toMap() {
            Map<String, String> eventDetails = new HashMap<>();
            eventDetails.put("id", Integer.toString(this.id));
            eventDetails.put("type", this.getType());
            Integer[] tmp = new Integer[teamMembers.size()];
            for(int i = 0; i < teamMembers.size(); i++) {
                tmp[i] = teamMembers.get(i).getId();
            }
            eventDetails.put("teamMembers", Arrays.toString(tmp));
            eventDetails.put("celebrity", this.getCelebrity());
            eventDetails.put("collab", this.getCollab());
            eventDetails.put("completionStatus", this.getCompletion());
            return eventDetails;
    }

    public static Event parse(Map<String, String> event) {
        return new Event(
            Integer.parseInt(event.get("id")),
            ModelingDepartment.fileManager.getTeamMembers(),
            event.get("type").equals("Photoshoot"),
            event.get("celebrity"),
            event.get("collab"),
            Boolean.parseBoolean(event.get("completionStatus"))
        );
    }
}
