package src.Marketing.src;

import src.App;
import src.Design.src.FinalDesign;
import src.Marketing.MarketingDepartment;
import src.Marketing.src.interfaces.IAdvertisement;
import src.Modeling.src.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DesignAdvertisement implements IAdvertisement {
    static int nextid = 0;
    int id;
    FinalDesign design;
    AdvertType advertType;
    Team[] associatedTeams;
    String notes;

    Event photoshoot;

    DesignAdvertisement(AdvertType advertType, String notes) {
        id = nextid;
        nextid++;
        assignTeams(advertType);
        this.advertType = advertType;
        this.design = generateDesign();
        this.notes = notes;
    }

    DesignAdvertisement(int i, AdvertType advertType, String notes) {
        id = i;
        if (id >= nextid) nextid = id + 1;
        assignTeams(advertType);
        this.advertType = advertType;
        this.design = generateDesign();
        this.notes = notes;
    }

    DesignAdvertisement(int i, AdvertType advertType, Event photoshoot, String notes) {
        id = i;
        if (id >= nextid) nextid = id + 1;
        assignTeams(advertType);
        this.advertType = advertType;
        this.design = generateDesign();
        this.photoshoot = photoshoot;
        this.notes = notes;
    }

    private void assignTeams(AdvertType advertType) {
        switch (advertType) {
            case BILLBOARD, MAGAZINE -> associatedTeams = new Team[]{Team.EDITING, Team.DISTRIBUTION};
            case SOCIALMEDIA -> associatedTeams = new Team[]{Team.SOCIALMEDIA, Team.EDITING};
            case COMMERCIAL -> associatedTeams = new Team[]{Team.EDITING, Team.VIDEO};
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public AdvertType getAdvertType() {
        return advertType;
    }

    @Override
    public Team[] getAssociatedTeams() {
        return associatedTeams;
    }

    public String getNotes() {
        return notes;
    }

    public FinalDesign getDesign() {
        return design;
    }

    @Override
    public void changeAdvertType(AdvertType type) {
        advertType = type;
        assignTeams(type);
    }

    @Override
    public void addPhotoshoot(int modelId) {
        photoshoot = MarketingDepartment.hod.requestPhotoshoot(modelId);
    }

    @Override
    public String toString() {
        String str =  "\nDesign Advertisement " + id + ":" +
                "\n Design: " + design.getDesignName() +
                "\n AdvertType: " + advertType.toString() +
                "\n Associated Teams: " + associatedTeams[0].toString() + ", " + associatedTeams[1].toString() +
                "\n Notes: " + notes;
        if(photoshoot != null) {
            str += "\n Modeling Event: " + photoshoot.getId();
        }

        return str;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> advertDetails = new HashMap<>();
        advertDetails.put("id", Integer.toString(this.id));
        advertDetails.put("design", design.getDesignName());
        advertDetails.put("advertType", this.getAdvertType().toString());
        if(photoshoot != null) {
            advertDetails.put("photoshoot", Integer.toString(photoshoot.getId()));
        }
        String[] tmp = new String[associatedTeams.length];
        for(int i = 0; i < associatedTeams.length; i++) {
            tmp[i] = associatedTeams[i].toString();
        }
        advertDetails.put("associatedTeams", Arrays.toString(tmp));
        advertDetails.put("notes", notes);
        return advertDetails;
    }

    private FinalDesign generateDesign() {
        FinalDesign d = new FinalDesign("Ball Gown");
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("Red");
        tmp.add("Green");
        d.setColor(tmp);
        tmp.clear();
        tmp.add("Small");
        tmp.add("Medium");
        d.setSizes(tmp);
        d.setQuantities("5");
        return d;
    }

    public static DesignAdvertisement parse(Map<String, String> advert) {
        if(advert.containsKey("photoshoot")) {
            return new DesignAdvertisement(
                    Integer.parseInt(advert.get("id")),
                    AdvertType.parseType(advert.get("advertType")),
                    App.modelingDepartment.getEvent(Integer.parseInt(advert.get("photoshoot"))),
                    advert.get("notes")
            );
        }
        return new DesignAdvertisement(
                Integer.parseInt(advert.get("id")),
                AdvertType.parseType(advert.get("advertType")),
                advert.get("notes")
        );
    }
}
