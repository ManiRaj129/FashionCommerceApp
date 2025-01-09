package src.Modeling.src.interfaces;

import src.Modeling.src.TeamMember;

import java.util.Map;


public interface IFitting {
    int getId();
    TeamMember getModel();
    String getGarment();
    String getCompletion();
    Map<String, String> toMap();
//    void endFitting();
}
