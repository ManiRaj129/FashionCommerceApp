package src.Modeling.src.interfaces;

import src.Modeling.src.Team;

import java.util.Map;

public interface IItem {
    int getId();

    Team getAssociatedTeam();

    String getItemType();

    void setAssociatedTeam(Team team);

    String getItemName();

    void setType(String itemType);

    void setItemName(String name);

    String getItemLocation();

    void flagDamaged();

    void flagCheckedOut();

    void flagReturned();

    Map<String, String> toMap();
}
