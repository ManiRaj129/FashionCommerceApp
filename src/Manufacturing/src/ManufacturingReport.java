package src.Manufacturing.src;

import src.Design.src.FinalDesign;
import src.Manufacturing.src.interfaces.ManufacturingReportInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ManufacturingReport implements ManufacturingReportInterface {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime startCollecting;
    private LocalDateTime verificationTime;
    private String designName;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ManufacturingReport() {
    }


    @Override
    public void setStartTime(String startTime) throws ParseException {
        this.startTime = parseDateTime(startTime);

    }

    @Override
    public String getStartTime() {
        return startTime.format(formatter);
    }

    @Override
    public void setEndTime(String endTime) throws ParseException {
        this.endTime = parseDateTime(endTime);
    }

    @Override
    public String getEndTime() {
        return endTime.format(formatter);
    }

    @Override
    public String getCollectingMaterialsTime() {
        return startCollecting.format(formatter);
    }

    @Override
    public void setCollectingMaterialsTime(String collectingMaterialsTime) throws ParseException {
        this.startCollecting = parseDateTime(collectingMaterialsTime);
    }

    @Override
    public String getVerifiedMaterialsTime() {
        return verificationTime.format(formatter);
    }

    @Override
    public void setVerifiedMaterialsTime(String verifiedMaterialsTime) throws ParseException {
        this.verificationTime = parseDateTime(verifiedMaterialsTime);
    }

    @Override
    public Duration calculateTimeTakeToCollectMaterials() {
        return Duration.between(startCollecting, verificationTime);
    }

    @Override
    public Duration calculateManufacturingTime() {
        return Duration.between(startTime, endTime);
    }

    private LocalDateTime parseDateTime(String input) throws ParseException {
        try {
            return LocalDateTime.parse(input, formatter);
        } catch (Exception e) {
            System.out.println("Invalid time format");
            throw new IllegalArgumentException("Failed to parse date-time: " + input, e);
        }
    }

    @Override
    public void displayManufacturingReport() {
        System.out.println("Manufacturing Report");
        System.out.println("Time when finished collecting Raw Materials: " + (startCollecting != null ? getCollectingMaterialsTime() : "Not Set"));
        System.out.println("Time when finished verified Materials: " + (verificationTime != null ? getVerifiedMaterialsTime() : "Not Set"));
        System.out.println("Time when starting Production: " + (startTime != null ? getStartTime() : "Not Set"));
        System.out.println("Time when finishing Production: " + (endTime != null ? getEndTime() : "Not Set"));
        //call to show the time difference between actions
        if (startCollecting != null && verificationTime != null) {
            Duration collectionToVerification = calculateTimeTakeToCollectMaterials();
            System.out.println("Time Difference from collecting Raw Materials to verifying Raw Materials: " +
                    collectionToVerification.toHours() + " hours " +
                    collectionToVerification.toMinutesPart() + " minutes");
        } else {
            System.out.println("Time Difference from verified Materials to verifying Raw Materials");
        }

        if (startTime != null && endTime != null) {
            Duration productionDuration = calculateManufacturingTime();
            System.out.println("Product Production Duration: " + productionDuration.toHours() + " hours " +
                    productionDuration.toMinutesPart() + " minutes");
        } else {
            System.out.println("Time Difference from start and end time of production.");
        }


    }

    public Map<String, Object> toMap(String designName) {
        Map<String, Object> result = new HashMap<>();

        result.put("DesignName", designName);//return name from final Design
        result.put("StartTime", getStartTime());
        result.put("EndTime", getEndTime());
        result.put("CollectingMaterialsTime", getCollectingMaterialsTime());
        result.put("VerifiedMaterialsTime", getVerifiedMaterialsTime());
        result.put("Verification Process", calculateTimeTakeToCollectMaterials());
        result.put("Manufacturing Process Time", calculateManufacturingTime());
        return result;
    }

    public void saveReportToFile(String filePath, String designName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Append mode
            writer.write("Manufacturing Report\n");
            writer.write("----------------------------\n");
            writer.write("Design Name: " + designName + "\n");
            writer.write("Start Time: " + (startTime != null ? getStartTime() : "Not Set") + "\n");
            writer.write("End Time: " + (endTime != null ? getEndTime() : "Not Set") + "\n");
            writer.write("Collecting Materials Time: " + (startCollecting != null ? getCollectingMaterialsTime() : "Not Set") + "\n");
            writer.write("Verification Time: " + (verificationTime != null ? getVerifiedMaterialsTime() : "Not Set") + "\n");

//            writer.write("\nCollected Materials:\n");
//            for (Map.Entry<String, String> entry : collectedMaterials.entrySet()) {
//                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
//            }

            writer.write("\nProcess Durations:\n");
            if (startCollecting != null && verificationTime != null) {
                writer.write("Time Taken to Collect Materials: " + calculateTimeTakeToCollectMaterials().toHours() + " hours " +
                        calculateTimeTakeToCollectMaterials().toMinutesPart() + " minutes\n");
            } else {
                writer.write("Time taken to collect Materials Missing Data");
            }

            if (startTime != null && endTime != null) {
                writer.write("Manufacturing Process Time: " + calculateManufacturingTime().toHours() + " hours " +
                        calculateManufacturingTime().toMinutesPart() + " minutes\n");
            } else {
                writer.write("Time taken for Manufacturing Process Missing Data");
            }
            writer.write("----------------------------\n");
            System.out.println("Manufacturing report successfully saved to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving manufacturing report to file: " + e.getMessage());
        }
    }

}

