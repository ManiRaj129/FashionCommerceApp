package src.Marketing.src.interfaces;

import src.Marketing.src.Team;
import src.Marketing.src.TeamMember;

import java.util.ArrayList;
import java.util.Map;

public interface IManager extends ITeamMember{
    Team getTeam();
    ArrayList<TeamMember> getTeamMembers();
    void addTeamMember(TeamMember member);

    Map<String, String> toMap();
}
