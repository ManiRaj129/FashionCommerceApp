package src.Marketing.src.interfaces;

import src.Marketing.src.*;
import src.Modeling.src.Event;

import java.util.ArrayList;
import java.util.Map;

public interface IHOD extends ITeamMember{
    ArrayList<EventAdvertisement> getEventAdverts();
    ArrayList<DesignAdvertisement> getDesignAdverts();

    ArrayList<ICollabMember> getApprovedCollabMembers();

    EventAdvertisement createEventAdvert(Event event, AdvertType type);
    DesignAdvertisement createDesignAdvert(AdvertType type, String notes);

    ICollabMember addApprovedCollab(ICollabMember member);

    void updateMember(ICollabMember member);

    void printCelebrities();

    void printBrand();

    void printApprovedMembers();

    ICollabMember getMember(String name);

    ICollab addCollab(ICollab collab);

    Map<String, String> toMap();
    void addManager(Manager manager);
    Manager getManager(Team team);
    Event requestPhotoshoot(int modelId);

    void updateCollab(ICollab collab);

    void removeCollab(ICollab collab);

    void printCollabs();
}
