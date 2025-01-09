package src.Design.src;

import src.Design.src.interfaces.DesignSpecifications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DesignSketch implements DesignSpecifications {

    private String designName;
    private String designImage;
    private List<String> colors;
    private List<String> rawMaterials;
    private List<String> sizes;
    private String quantity;

    public DesignSketch(String designName) {
        this.designName = designName;
    }

    @Override
    public void setColor(List<String> colors) {

        this.colors = colors;
    }

    @Override
    public void setRawMaterials(List<String> rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

    @Override
    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    @Override
    public void setQuantities(String quantities) {
        this.quantity = quantities;
    }

    @Override
    public void setDesignName(String designName) {
        this.designName = designName;
    }

    @Override
    public void setDesignImage(String image) {
        this.designImage = image;
    }

    @Override
    public List<String> getColors() {
        return colors;
    }

    @Override
    public List<String> getRawMaterials() {
        return rawMaterials;
    }

    @Override
    public List<String> getSizes() {
        return sizes;
    }

    @Override
    public String getQuantities() {
        return quantity;
    }

    @Override
    public String getDesignName() {
        return designName;
    }

    @Override
    public String getDesignImage() {
        return designImage;
    }

    @Override
    public String displayAllSpecifications() {
        return
                "Display all design specifications\n" +
                        "Design Name: " + designName + "\n" +
                        "Design Image: " + designImage + "\n" +
                        "Design Colors: " + colors + "\n" +
                        "Design Raw Materials: " + rawMaterials + "\n" +
                        "Design Sizes: " + sizes + "\n" +
                        "Design Quantities: " + quantity;
    }

    @Override
    public Map<String, String> mapObjects() {
        Map<String, String> map = new HashMap<>();

        map.put("Design Name", Objects.toString(this.designName, "null"));
        map.put("Design Raw Materials", this.rawMaterials != null ? String.join(",", this.rawMaterials) : "null");
        map.put("Design Size", this.sizes != null ? String.join(",", this.sizes) : "null");
        map.put("Design Quantities", Objects.toString(this.quantity, "null"));
        map.put("Design Colors", this.colors != null ? String.join(",", this.colors) : "null");
        map.put("Design Image", Objects.toString(this.designImage, "null"));

//        map.put("DesignName", designName);
//        map.put("DesignImage", designImage);
//        map.put("DesignColors", String.join(",", colors));
//        map.put("DesignRawMaterials", String.join(",", rawMaterials));
//        map.put("DesignSizes", String.join(",", sizes));
//        map.put("DesignQuantities", quantity);

        return map;
    }
}
