package src.Marketing.src.interfaces;

import java.util.ArrayList;
import java.util.Map;

public interface ICollab {

    void addAdvertisement(Boolean design, IAdvertisement advert);

    void removeAdvertisement(Boolean design, IAdvertisement advert);

    ArrayList<Integer> getAdvertisementIds(Boolean design);

    int getId();

    ICollabMember getMember();

    Map<String, String> toMap();
}
