package src.Modeling.src.interfaces;

import java.util.Map;

public interface IEvent {
    int getId();
    String getType();
    String getCelebrity();

    void addCelebrity(String name);

    String getCollab();
    String getCompletion();
//    void endEvent();
    Map<String, String> toMap();
}
