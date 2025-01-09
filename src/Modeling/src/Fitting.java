package src.Modeling.src;

import src.Modeling.ModelingDepartment;
import src.Modeling.src.interfaces.IFitting;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Fitting implements IFitting {
    static int nextid = 0;
    int id;
    TeamMember model;
    String garment;
    LocalDateTime date;
    Boolean completionStatus;

    Fitting (int id, TeamMember model, String garment, LocalDateTime date, Boolean completionStatus) {
        this.id = id;
        if (id >= nextid) nextid = id+1;
        this.model = model;
        this.garment = garment;
        this.date = date;
        this.completionStatus = completionStatus;
    }

    Fitting (TeamMember model, String garment, LocalDateTime date) {
        id = nextid;
        nextid++;
        this.model = model;
        this.garment = garment;
        this.date = date;
        this.completionStatus = false;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public TeamMember getModel() {
        return model;
    }

    @Override
    public String getGarment() {
        return this.garment;
    }

    @Override
    public String getCompletion() {
        if(completionStatus) return "true";
        return "false";
    }

//    @Override
//    public void endFitting() {
//        this.completionStatus = true;
//        System.out.println(this.toString());
//    }

    @Override
    public String toString() {
        String str = "\nFitting " + id + ":";
        str += "\nModel: " + this.model.toString();
        str += "\nGarment: " + this.garment +
                "\nDate: " + this.date +
                "\nCompletion Status: " + this.completionStatus;
        return str;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> fittingDetails = new HashMap<>();
        fittingDetails.put("id", Integer.toString(this.id));
        fittingDetails.put("model", Integer.toString(model.getId()));
        fittingDetails.put("garment", garment);
        fittingDetails.put("date", date.toString());
        fittingDetails.put("completionStatus", this.getCompletion());
        return fittingDetails;
    }

    public static Fitting parse(Map<String, String> fitting) {
        return new Fitting(
                Integer.parseInt(fitting.get("id")),
                ModelingDepartment.fileManager.getModel(Integer.parseInt(fitting.get("model"))),
                fitting.get("garment"),
                LocalDateTime.parse(fitting.get("date")),
                Boolean.parseBoolean(fitting.get("completionStatus"))
        );
    }
}
