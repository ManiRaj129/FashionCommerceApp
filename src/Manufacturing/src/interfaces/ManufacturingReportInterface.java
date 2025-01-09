package src.Manufacturing.src.interfaces;

import java.text.ParseException;
import java.time.Duration;

public interface ManufacturingReportInterface {

    void setStartTime(String startTime) throws ParseException;
    String getStartTime();
    void setEndTime(String endTime) throws ParseException;
    String getEndTime();
    String getCollectingMaterialsTime();
    void setCollectingMaterialsTime(String collectingMaterialsTime) throws ParseException;
    String getVerifiedMaterialsTime();
    void setVerifiedMaterialsTime(String verifiedMaterialsTime) throws ParseException;
    Duration calculateTimeTakeToCollectMaterials();
    Duration calculateManufacturingTime();
    void displayManufacturingReport();
}
