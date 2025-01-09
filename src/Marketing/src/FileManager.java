package src.Marketing.src;

import src.Marketing.MarketingDepartment;
import src.Marketing.src.interfaces.ICollab;
import src.Marketing.src.interfaces.ICollabMember;
import src.TextEditor.PoorTextEditor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private Map<String, Map<String, String>> teamMembers = new HashMap<>();
    private Map<String, Map<String, String>> eventAdverts = new HashMap<>();
    private Map<String, Map<String, String>> designAdverts = new HashMap<>();
    private Map<String, Map<String, String>> approvedCollabs = new HashMap<>();
    private Map<String, Map<String, String>> collaborations = new HashMap<>();

    private final PoorTextEditor editor = new PoorTextEditor();

    private final String repo = "src/Marketing/repository/";

    public FileManager() {
        fillRepos();
    }

    public void fillRepos() {
        File f = new File(repo + "department.txt");
        if(f.exists()) {
            editor.processTextFile(repo + "department.txt");
            teamMembers = editor.getRepositoryStringMap();
        }

        f = new File(repo + "eventAdvert.txt");
        if(f.exists()) {
            editor.processTextFile(repo + "eventAdvert.txt");
            eventAdverts = editor.getRepositoryStringMap();
        }

        f = new File(repo + "designAdvert.txt");
        if(f.exists()) {
            editor.processTextFile(repo + "designAdvert.txt");
            designAdverts = editor.getRepositoryStringMap();
        }

        f = new File(repo + "collaborations/approved.txt");
        if(f.exists()) {
            editor.processTextFile(repo + "collaborations/approved.txt");
            approvedCollabs = editor.getRepositoryStringMap();
        }

        f = new File(repo + "collaborations/collaborations.txt");
        if(f.exists()) {
            editor.processTextFile(repo + "collaborations/collaborations.txt");
            collaborations = editor.getRepositoryStringMap();
        }
    }

//    ApprovedMembers
    public ArrayList<ICollabMember> getApprovedCollabMembers() {
        ArrayList<ICollabMember> tmp = new ArrayList<>();
        for(Map<String, String> members: approvedCollabs.values()) {
            if (members.containsKey("contractDetail")) {
                tmp.add(Celebrity.parse(members));
            } else {
                tmp.add(Brand.parse(members));
            }
        }
        return tmp;
    }

    public void printApprovedMembers() {
        for(ICollabMember member: getApprovedCollabMembers()) {
            System.out.println(member);
        }
    }

    public void addCollabMember(ICollabMember collabMember) {
        String memberType = collabMember instanceof Brand ? "Brand" : "Celebrity";
        approvedCollabs.put(memberType + " Member " + collabMember.getId(), collabMember.toMap());

        editor.setRepositoryStrings(approvedCollabs);
        editor.writeToTextFile(repo + "collaborations/approved.txt");
    }

    public ICollabMember getMemberById(boolean celebrity, int id) {
        String memberType = celebrity ? "Celebrity" : "Brand";
        for (Map.Entry<String, Map<String, String>> entry : approvedCollabs.entrySet()) {
            String key = entry.getKey();
            Map<String, String> memberData = entry.getValue();

            if (key.startsWith(memberType + " Member") && key.endsWith(Integer.toString(id))) {
                if(memberType.equals("Celebrity")) {
                    return Celebrity.parse(memberData);
                }
                return Brand.parse(memberData);
            }
        }
        return null;
    }

    public Boolean checkMemberExistence(String name) {
        for (Map.Entry<String, Map<String, String>> entry : approvedCollabs.entrySet()) {
            if (entry.getValue().get("name").equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void updateCollabMember(ICollabMember collabMember) {
        String memberType = collabMember instanceof Brand ? "Brand" : "Celebrity";
        String key = memberType + " Member " + collabMember.getId();

        if (approvedCollabs.containsKey(key)) {
            approvedCollabs.put(key, collabMember.toMap());

            editor.setRepositoryStrings(approvedCollabs);
            editor.writeToTextFile(repo + "collaborations/approved.txt");

            System.out.println("Collaboration member with ID " + collabMember.getId() + " has been updated.");
        } else {
            System.out.println("Collaboration member with ID " + collabMember.getId() + " not found.");
        }
    }

    public void removeCollabMember(ICollabMember collabMember) {
        String memberType = collabMember instanceof Brand ? "Brand" : "Celebrity";
        String key = memberType + " Member " + collabMember.getId();

        if (approvedCollabs.containsKey(key)) {
            approvedCollabs.remove(key);

            editor.setRepositoryStrings(approvedCollabs);
            editor.writeToTextFile(repo + "collaborations/approved.txt");

            System.out.println("Collaboration member with ID " + collabMember.getId() + " has been removed.");
        } else {
            System.out.println("Collaboration member with ID " + collabMember.getId() + " not found. No action taken.");
        }
    }

//    Collabs
    public ArrayList<ICollab> getCollabs() {
        ArrayList<ICollab> tmp = new ArrayList<>();
        for(Map<String, String> collab: collaborations.values()) {
            tmp.add(Collab.parse(collab));
        }
        return tmp;
    }

    public Collab checkCollabExistence(String name) {
        ICollabMember member = MarketingDepartment.hod.getMember(name);
        String memberId = Integer.toString(member.getId());

        for (Map.Entry<String, Map<String, String>> entry : collaborations.entrySet()) {
            Map<String, String> memberData = entry.getValue();
            if ((memberData.containsKey("celebrity") && memberData.get("celebrity").equals(memberId)) ||
                    (memberData.containsKey("brand") && memberData.get("brand").equals(memberId))) {
                return Collab.parse(memberData);
            }
        }
        return null;
    }

    public void addCollab(ICollab collab) {
        collaborations.put("Collab " + collab.getId(), collab.toMap());

        editor.setRepositoryStrings(collaborations);
        editor.writeToTextFile(repo + "collaborations/collaborations.txt");
    }

    public void updateCollab(ICollab collab) {
        String key = "Collab " + collab.getId();

        if (collaborations.containsKey(key)) {
            collaborations.put(key, collab.toMap());

            editor.setRepositoryStrings(collaborations);
            editor.writeToTextFile(repo + "collaborations/collaborations.txt");

            System.out.println("Collab with ID " + collab.getId() + " has been updated.");
        } else {
            System.out.println("Collab with ID " + collab.getId() + " not found.");
        }
    }

    public ICollab getCollabById(int id) {
        for (Map.Entry<String, Map<String, String>> entry : collaborations.entrySet()) {
            String key = entry.getKey();
            Map<String, String> collabData = entry.getValue();

            if (key.endsWith(Integer.toString(id))) {
                return Collab.parse(collabData);
            }
        }
        return null;
    }

    public void removeCollab(ICollab collab) {
        String key = "Collab " + collab.getId();

        if (collaborations.containsKey(key)) {
            collaborations.remove(key);

            editor.setRepositoryStrings(collaborations);
            editor.writeToTextFile(repo + "collaborations/collaborations.txt");

            System.out.println("Collab with ID " + collab.getId() + " has been removed.");
        } else {
            System.out.println("Collab with ID " + collab.getId() + " not found. No action taken.");
        }
    }

    //    EventsAdverts:
    public void addEventAdvert(EventAdvertisement advert) {
        eventAdverts.put("Advert " + advert.getId(), advert.toMap());

        editor.setRepositoryStrings(eventAdverts);
        editor.writeToTextFile(repo + "eventAdvert.txt");
    }

    public ArrayList<EventAdvertisement> getEventAdverts() {
        ArrayList<EventAdvertisement> tmp = new ArrayList<>();
        for(Map<String, String> advert: eventAdverts.values()) {
            tmp.add(EventAdvertisement.parse(advert));
        }
        return tmp;
    }

    public EventAdvertisement getEventAdvertById(int id) {
        String key = "Advert " + id;
        Map<String, String> advertDetails = eventAdverts.get(key);
        return (advertDetails != null) ? EventAdvertisement.parse(advertDetails) : null;
    }

    //    DesignAdverts:
    public void addDesignAdvert(DesignAdvertisement advert) {
        designAdverts.put("Advert " + advert.getId(), advert.toMap());

        editor.setRepositoryStrings(designAdverts);
        editor.writeToTextFile(repo + "designAdvert.txt");
    }

    public ArrayList<DesignAdvertisement> getDesignAdverts() {
        ArrayList<DesignAdvertisement> tmp = new ArrayList<>();
        for(Map<String, String> advert: designAdverts.values()) {
            tmp.add(DesignAdvertisement.parse(advert));
        }
        return tmp;
    }

    public DesignAdvertisement getDesignAdvertById(int id) {
        String key = "Advert " + id;
        Map<String, String> advertDetails = designAdverts.get(key);
        return (advertDetails != null) ? DesignAdvertisement.parse(advertDetails) : null;
    }


    //    TeamMembers:
    public void addTeamMember(TeamMember teamMember) {
        teamMembers.put("Team Member "+ teamMember.getId(), teamMember.toMap());

        editor.setRepositoryStrings(teamMembers);
        editor.writeToTextFile(repo + "department.txt");
    }

    public void addManager(Manager manager) {
        teamMembers.put(manager.getTeam().toString() + " Manager", manager.toMap());

        editor.setRepositoryStrings(teamMembers);
        editor.writeToTextFile(repo + "department.txt");
    }

    public void addHOD(HOD hod) {
        teamMembers.put("HOD", hod.toMap());

        editor.setRepositoryStrings(teamMembers);
        editor.writeToTextFile(repo + "department.txt");
    }

    public ArrayList<Manager> getManagers() {
        ArrayList<Manager> tmp = new ArrayList<>();
        for(Map<String, String> member: teamMembers.values()) {
            if(member.containsKey("teamMembers")) {
                tmp.add(Manager.parse(member));
            }
        }
        return tmp;
    }

    public ArrayList<TeamMember> getTeamMembers(Team team) {
        ArrayList<TeamMember> tmp = new ArrayList<>();
        for(Map<String, String> member: teamMembers.values()) {
            if (member.containsValue(team.toString()) && !member.containsKey("teamMembers") && !member.containsKey("managers")) {
                tmp.add(TeamMember.parse(member));
            }
        }
        return tmp;
    }

    public HOD getHOD() {
        return HOD.parse(teamMembers.get("HOD"));
    }
}
