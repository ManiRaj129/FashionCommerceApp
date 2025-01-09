package src.Design.src.interfaces;

import java.util.List;
import java.util.Map;

public interface DesignSpecifications {

    void setColor(List<String> colors);
    void setRawMaterials(List<String> rawMaterials);
    void setSizes(List<String> sizes);
    void setQuantities(String quantities);
    void setDesignName(String designName);
    void setDesignImage(String image);
    List<String> getColors();
    List<String> getRawMaterials();
    List<String> getSizes();
    String getQuantities();
    String getDesignName();
    String getDesignImage();
    String displayAllSpecifications();
    Map<String, String> mapObjects();


}
