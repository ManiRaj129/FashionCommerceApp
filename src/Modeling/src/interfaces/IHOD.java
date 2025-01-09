package src.Modeling.src.interfaces;

import src.Modeling.src.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IHOD extends ITeamMember {
    Event createEvent(Boolean type, String celebrity, String collab);
    Event createEvent(Boolean type, int modelID, String collab);
    ArrayList<Event> getEvents();

    Event updateEvent(Event event);

    ArrayList<Fitting> getFittings();
    void addManager(Manager manager);
    Manager getManager(Team team);
    void requestFitting(Team team, TeamMember model, String garment, LocalDateTime date);
}
