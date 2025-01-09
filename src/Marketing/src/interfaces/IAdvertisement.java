package src.Marketing.src.interfaces;

import src.Marketing.src.AdvertType;
import src.Marketing.src.Team;
import src.Modeling.src.Event;

import java.util.Map;

public interface IAdvertisement {
    int getId();
    AdvertType getAdvertType();
    Team[] getAssociatedTeams();
    void changeAdvertType(AdvertType type);
    void addPhotoshoot(int modelId);
    Map<String, String> toMap();
}