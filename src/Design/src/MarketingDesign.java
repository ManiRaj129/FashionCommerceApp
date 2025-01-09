package src.Design.src;

import src.Design.src.interfaces.MarketingDesignSpecifications;

import java.util.HashMap;
import java.util.Map;

public class MarketingDesign implements MarketingDesignSpecifications {

    private FinalDesign finalDesign;
    private String targetAudience;
    private String price;
    private String description;
    private String seasonType;
    private String marketingDesign;

    public MarketingDesign(FinalDesign finalDesign) {
        this.finalDesign = finalDesign;

    }

    @Override
    public String getDesignSketchName() {
        return finalDesign.getDesignName();
    }

    @Override
    public void setDesignSketchName(String designSketchName) {
        this.finalDesign.setDesignName(designSketchName);
    }


    @Override
    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;

    }

    @Override
    public String getTargetAudience() {
        return targetAudience;
    }

    @Override
    public void setPrice(String price) {
        this.price = price;

    }

    @Override
    public String getPrice() {
        return price;
    }

    @Override
    public void setSeasonType(String seasonType) {
        this.seasonType = seasonType;
    }

    @Override
    public String getSeasonType() {
        return seasonType;
    }

    @Override
    public void setProductDescription(String productDescription) {
        this.description = productDescription;
    }

    @Override
    public String getProductDescription() {
        return description;
    }

    @Override
    public String displayAllSpecifications() {
        return "Display all specifications \n" + "Design sketch: " + finalDesign.getDesignName() +
                "\n" + "Target Audience: " + targetAudience + "\n"
                + "Price: " + price + "\n" + "Season type: " + seasonType +
                "\n" + "Product description: " + description + "\n";
    }

    @Override
    public Map<String, Object> mapObjects() {
        Map<String, Object> map = new HashMap<>();
        map.put("sketch", finalDesign != null ? finalDesign.getDesignName() : "");
        map.put("targetAudience", targetAudience);
        map.put("price", price);
        map.put("seasonType", seasonType);

        return map;
    }

}
