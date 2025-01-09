package src.Design.src.interfaces;

import src.Design.DesignDepartment;
import src.Design.src.CustomDesign;
import src.Design.src.DesignSketch;
import src.Design.src.FinalDesign;
import src.Design.src.MarketingDesign;

import java.util.List;

public interface HeadOfDesignInterface {

    void viewSketches(List<DesignSketch> sketches);

    void selectSketch(int sketchNumber, List<DesignSketch> sketchList);

    CustomDesign confirmCustomDesign();

    FinalDesign confirmFinalDesign();

    MarketingDesign confirmMarketingDesign();

}
