package src.Marketing.src;

import src.App;
import src.Marketing.MarketingDepartment;
import src.Marketing.src.interfaces.*;
import src.Modeling.src.Event;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EventAdvertisement implements IAdvertisement {
    static int nextid = 0;
    int id;
    Event event;
    AdvertType advertType;
    Team[] associatedTeams;

    Event modelEvent;

    EventAdvertisement(Event event, AdvertType advertType) {
        id = nextid;
        nextid++;
        assignTeams(advertType);
        this.advertType = advertType;
        this.event = event;
    }

    EventAdvertisement(int i, Event event, AdvertType advertType) {
        id = i;
        if (id >= nextid) nextid = id + 1;
        assignTeams(advertType);
        this.advertType = advertType;
        this.event = event;
    }

    EventAdvertisement(int i, Event event, AdvertType advertType, Event modelEvent) {
        id = i;
        if (id >= nextid) nextid = id + 1;
        assignTeams(advertType);
        this.advertType = advertType;
        this.event = event;
        this.modelEvent = modelEvent;
    }

    private void assignTeams(AdvertType advertType) {
        switch (advertType) {
            case BILLBOARD, MAGAZINE -> associatedTeams = new Team[]{Team.EDITING, Team.DISTRIBUTION};
            case SOCIALMEDIA -> associatedTeams = new Team[]{Team.SOCIALMEDIA, Team.EDITING};
            case COMMERCIAL -> associatedTeams = new Team[]{Team.EDITING, Team.VIDEO};
        }
    }

    @Override
    public int getId() {return id;}
    @Override
    public AdvertType getAdvertType() {return advertType;}
    @Override
    public Team[] getAssociatedTeams() {return associatedTeams;}
    @Override
    public void changeAdvertType(AdvertType type) {
        this.advertType = type;
        assignTeams(type);
    }

    @Override
    public void addPhotoshoot(int modelId) {
        modelEvent = MarketingDepartment.hod.requestPhotoshoot(modelId);
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public String toString() {
        String str =  "\nEvent Advertisement " + id + ":" +
                "\n Event: " + event.getId() +
                "\n AdvertType: " + advertType.toString() +
                "\n Associated Teams: " + associatedTeams[0].toString() + ", " + associatedTeams[1].toString();
        if(modelEvent != null) {
            str += "\n Modeling Event: " + modelEvent.getId();
        }

        return str;
    }
    
    @Override
    public Map<String, String> toMap() {
        Map<String, String> advertDetails = new HashMap<>();
        advertDetails.put("id", Integer.toString(this.id));
        advertDetails.put("event", Integer.toString(event.getId()));
        advertDetails.put("advertType", this.getAdvertType().toString());
        if(modelEvent != null) {
            advertDetails.put("modelEvent", Integer.toString(modelEvent.getId()));
        }
        String[] tmp = new String[associatedTeams.length];
        for(int i = 0; i < associatedTeams.length; i++) {
            tmp[i] = associatedTeams[i].toString();
        }
        advertDetails.put("associatedTeams", Arrays.toString(tmp));
        return advertDetails;
    }

    public static EventAdvertisement parse(Map<String, String> advert) {
        if(advert.containsKey("modelEvent")) {
            return new EventAdvertisement(
                    Integer.parseInt(advert.get("id")),
                    App.modelingDepartment.getEvent(Integer.parseInt(advert.get("event"))),
                    AdvertType.parseType(advert.get("advertType")),
                    App.modelingDepartment.getEvent(Integer.parseInt(advert.get("modelEvent")))
            );
        }
        return new EventAdvertisement(
                Integer.parseInt(advert.get("id")),
                App.modelingDepartment.getEvent(Integer.parseInt(advert.get("event"))),
                AdvertType.parseType(advert.get("advertType"))
        );
    }
}
