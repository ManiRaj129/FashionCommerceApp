package src.Design.src.interfaces;

import src.Design.src.DesignSketch;
import src.Design.src.FinalDesign;

import java.util.Map;
import java.util.Objects;

public interface MarketingDesignSpecifications {

    String getDesignSketchName();
    void setDesignSketchName(String designSketchName);

    void setTargetAudience(String targetAudience);
    String getTargetAudience();

    void setPrice(String price);
    String getPrice();

    void setSeasonType(String seasonType); //fall,spring...
    String getSeasonType();

    void setProductDescription(String productDescription);
    String getProductDescription();

    String displayAllSpecifications();

    Map<String, Object> mapObjects();

}
