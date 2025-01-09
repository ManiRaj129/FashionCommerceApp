package src.Modeling.src;

import src.Marketing.src.Brand;
import src.TextEditor.PoorTextEditor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileManager {
    private Map<String, Map<String, String>> events = new HashMap<>();
    private Map<String, Map<String, String>> teamMembers = new HashMap<>();
    private Map<String, Map<String, String>> fittings = new HashMap<>();
    private Map<String, Map<String, String>> clothingItems = new HashMap<>();
    private Map<String, Map<String, String>> makeupItems = new HashMap<>();
    private Map<String, Map<String, String>> modelingItems = new HashMap<>();

    private final PoorTextEditor editor = new PoorTextEditor();

    private final String repo = "src/Modeling/repository/";

    public FileManager() {
        fillRepos();
    }

    public void fillRepos() {
        File f = new File(repo + "events.txt");
        if(f.exists()) {
            editor.processTextFile(repo + "events.txt");
            events = editor.getRepositoryStringMap();
        }

        f = new File(repo + "fittings.txt");
        if(f.exists()) {
            editor.processTextFile(repo + "fittings.txt");
            fittings = editor.getRepositoryStringMap();
        }

        f = new File(repo + "department.txt");
        if(f.exists()) {
            editor.processTextFile(repo + "department.txt");
            teamMembers = editor.getRepositoryStringMap();
        }

        setItems(Team.MODELING);
        setItems(Team.CLOTHING);
        setItems(Team.MAKEUP);
    }

//    Storage:
    private Map<String, Map<String, String>> getTargetMap(Team team) {
        switch (team) {
            case CLOTHING:
                return clothingItems;
            case MAKEUP:
                return makeupItems;
            case MODELING:
                return modelingItems;
            default:
                throw new IllegalArgumentException("Unknown team: " + team);
        }
    }

    private Map<String, Map<String, String>> getTargetMap(Team team, String category) {
        Map<String, Map<String, String>> target = getTargetMap(team);
        Map<String, Map<String, String>> tmp = new HashMap<>();

        for(Map.Entry<String, Map<String, String>> entry: target.entrySet()) {
            if(entry.getKey().equals(category + ".txt")) {
                tmp.put(entry.getKey(), entry.getValue());
            }
        }
        return tmp;
    }

    public Map<Team, Map<String, Item[]>> convertItemsToTeamCategoryMap() {
        Map<Team, Map<String, Item[]>> convertedMap = new HashMap<>();

        for (Team team : Team.values()) {
            if(team.equals(Team.STORAGE)) continue;
            Map<String, Map<String, String>> itemTypeMap = getItemMapForTeam(team);

            Map<String, List<Item>> categoryItemMap = new HashMap<>();

            List<Item> itemList = new ArrayList<>();
            for (Map.Entry<String, Map<String, String>> itemTypeEntry : itemTypeMap.entrySet()) {
                String category = itemTypeEntry.getKey();
                Map<String, String> itemDetailsMap = itemTypeEntry.getValue();

                Item item = Item.parse(itemDetailsMap);
                itemList.add(item);

                categoryItemMap.put(category, itemList);
            }

            Map<String, Item[]> categoryItemArrayMap = new HashMap<>();
            for (Map.Entry<String, List<Item>> entry : categoryItemMap.entrySet()) {
                categoryItemArrayMap.put(entry.getKey(), entry.getValue().toArray(new Item[0]));
            }

            convertedMap.put(team, categoryItemArrayMap);
        }

        return convertedMap;
    }

    private Map<String, Map<String, String>> getItemMapForTeam(Team team) {
        switch (team) {
            case CLOTHING:
                return clothingItems;
            case MAKEUP:
                return makeupItems;
            case MODELING:
                return modelingItems;
            default:
                return new HashMap<>();
        }
    }

    public StorageManager getStorageManager() {
        return StorageManager.parse(teamMembers.get("Storage Manager"));
    }

    public void setItems(Team team) {
        String category = team.name().toLowerCase();
        File folder = new File(repo + "warehouse/" + category);
        String[] files = folder.list();

        if (files != null) {
            for (String fileName : files) {
                editor.processTextFile(folder.getPath() + "/" + fileName);
                Map<String, Map<String, String>> itemMap = editor.getRepositoryStringMap();

                getTargetMap(team, category).putAll(itemMap);
            }
        }
    }

    public void addItem(Item item) throws IOException {
        Team team = item.getAssociatedTeam();
        String category = team.name().toLowerCase();
        File file = new File(repo + "warehouse/" + category + "/" + item.getItemType() + ".txt");

        if (!file.exists()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type " + item.getItemType() + " is not found in the system. Would you like to create it? (y/n)");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) {
                if (file.createNewFile()) {
                    System.out.println("File created successfully: " + file.getAbsolutePath());
                } else {
                    System.out.println("Failed to create the file.");
                    return;
                }
            } else {
                return;
            }
        }

        Map<String, Map<String, String>> targetMap = getTargetMap(team, category);

        targetMap.put("Item " + item.getId(), item.toMap());

        editor.setRepositoryStrings(targetMap);
        editor.writeToTextFile(file.getPath());
    }

    public List<Integer> getAllItemIdsForTeam(Team team) {
        List<Integer> itemIds = new ArrayList<>();

        // Retrieve the map for the team
        Map<String, Map<String, String>> itemMap = getTargetMap(team);

        // Iterate through all items and extract their IDs
        for (Map<String, String> itemDetails : itemMap.values()) {
            if (itemDetails.containsKey("id")) {
                int itemId = Integer.parseInt(itemDetails.get("id"));
                itemIds.add(itemId);
            }
        }

        return itemIds; // Return the list of item IDs
    }

    public Item getItemById(Team team, int itemId) {
        Map<String, Map<String, String>> itemMap = getTargetMap(team);

        for (Map<String, String> itemDetails : itemMap.values()) {
            if (itemDetails.containsKey("id") && Integer.parseInt(itemDetails.get("id")) == itemId) {
                return Item.parse(itemDetails);
            }
        }

        return null; // Return null if not found
    }

    public List<Item> findDamagedItems() {
        List<Item> damagedItems = new ArrayList<>();

        for (Team team : Team.values()) {
            if(team.equals(Team.STORAGE)) continue;
            Map<String, Map<String, String>> itemMap = getTargetMap(team);

            for (Map<String, String> itemDetails : itemMap.values()) {
                if ("true".equalsIgnoreCase(itemDetails.get("damaged"))) {
                    damagedItems.add(Item.parse(itemDetails)); // Add the damaged item
                }
            }
        }

        return damagedItems;
    }

    public List<Integer> findCheckedOutIdsItems(Team team) {
        List<Integer> checkedOutItems = new ArrayList<>();
        Map<String, Map<String, String>> itemMap = getTargetMap(team);

        for (Map<String, String> itemDetails : itemMap.values()) {
            if ("true".equalsIgnoreCase(itemDetails.get("checkedOut"))) {
                checkedOutItems.add(Item.parse(itemDetails).getId()); // Add the damaged item
            }
        }

        return checkedOutItems;
    }

    public void updateItem(Item item, Team oldTeam) throws IOException {
        Team newTeam = item.getAssociatedTeam();

        // Delete the item from the old team
        boolean deleted = deleteById(oldTeam, item.getId());
        if (!deleted) {
            System.out.println("Failed to delete item from old team.");
            return;
        }

        // Define the file paths
        String newFilePath = repo + "warehouse/" + newTeam.name().toLowerCase() + "/" + item.getItemType() + ".txt";

        // Create new file if it doesn't exist
        File newFile = new File(newFilePath);
        if (!newFile.exists()) {
            new File(newFile.getParent()).mkdirs(); // Create directories if necessary
            newFile.createNewFile();
        }

        // Add the updated item to the new team
        Map<String, Map<String, String>> targetMap = getTargetMap(newTeam);
        targetMap.put("Item " + item.getId(), item.toMap());

        // Update the file for the new team
        editor.setRepositoryStrings(targetMap);
        editor.writeToTextFile(newFile.getPath());

        System.out.println("Item updated successfully.");
    }

    public String[] getCategories(Team team) {
        File f = new File(repo + "warehouse/" + team.toString());
        String[] files = f.list();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                int dotIndex = files[i].lastIndexOf('.');
                if (dotIndex > 0) {
                    files[i] = files[i].substring(0, dotIndex);
                }
            }
        }
        return files;
    }

    public boolean deleteById(Team team, int itemId) {
        Map<String, Map<String, String>> targetMap = getTargetMap(team);

        Iterator<Map.Entry<String, Map<String, String>>> iterator = targetMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map<String, String>> entry = iterator.next();
            Map<String, String> itemDetails = entry.getValue();

            if (itemDetails.containsKey("id") && Integer.parseInt(itemDetails.get("id")) == itemId) {
                iterator.remove();

                // Update the corresponding text file
                String category = team.name().toLowerCase();
                File file = new File(repo + "warehouse/" + category + "/" + itemDetails.get("itemType") + ".txt");

                if (targetMap.isEmpty() && file.exists()) {
                    if (file.delete()) {
                        System.out.println("File deleted successfully: " + file.getAbsolutePath());
                    } else {
                        System.out.println("Failed to delete the file.");
                    }
                } else {
                    editor.setRepositoryStrings(targetMap);
                    editor.writeToTextFile(file.getPath());
                }
                return true;
            }
        }

        System.out.println("Item with ID " + itemId + " not found in " + team.name() + ".");
        return false;
    }

    public void printItems() {
        System.out.println("Clothing Items:");
        printCategoryItems(clothingItems);

        System.out.println("Makeup Items:");
        printCategoryItems(makeupItems);

        System.out.println("Modeling Items:");
        printCategoryItems(modelingItems);
    }

    private void printCategoryItems(Map<String, Map<String, String>> categoryItems) {
        for (Map.Entry<String, Map<String, String>> entry : categoryItems.entrySet()) {
            System.out.println("  Item: " + entry.getKey());
            for (Map.Entry<String, String> detail : entry.getValue().entrySet()) {
                System.out.println("    " + detail.getKey() + ": " + detail.getValue());
            }
        }
    }


//    Events:
    public void addEvent(Event event) {
        events.put("Event " + event.getId(), event.toMap());

        editor.setRepositoryStrings(events);
        editor.writeToTextFile(repo + "events.txt");
    }

    public ArrayList<Event> getEvents() {
        ArrayList<Event> tmp = new ArrayList<>();
        for(Map<String, String> event: events.values()) {
            tmp.add(Event.parse(event));
        }
        return tmp;
    }

    public Event getEvent(int id) {
        Event tmp = null;
        for(Map<String, String> event: events.values()) {
            if(event.get("id").equals(Integer.toString(id))){
                tmp = Event.parse(event);
                break;
            }
        }
        return tmp;
    }

    public void updateEvent(Event event) {
        String key = "Event " + event.getId();

        if (events.containsKey(key)) {
            events.put(key, event.toMap());

            editor.setRepositoryStrings(events);
            editor.writeToTextFile(repo + "events.txt");

            System.out.println("Event with ID " + event.getId() + " has been updated.");
        } else {
            System.out.println("Event with ID " + event.getId() + " not found.");
        }
    }

//    Fittings:
    public ArrayList<Fitting> getFittings() {
        ArrayList<Fitting> tmp = new ArrayList<>();
        for(Map<String, String> event: fittings.values()) {
            tmp.add(Fitting.parse(event));
        }
        return tmp;
    }

//    TeamMembers:
    public void addTeamMember(TeamMember teamMember) {
        teamMembers.put("Team Member "+ teamMember.getId(), teamMember.toMap());

        editor.setRepositoryStrings(teamMembers);
        editor.writeToTextFile(repo + "department.txt");
    }

    public void addManager(Manager manager) {
        teamMembers.put(manager.getTeam().toString() + " Manager", manager.toMap());

        editor.setRepositoryStrings(teamMembers);
        editor.writeToTextFile(repo + "department.txt");
    }

    public void addHOD(HOD hod) {
        teamMembers.put("HOD", hod.toMap());

        editor.setRepositoryStrings(teamMembers);
        editor.writeToTextFile(repo + "department.txt");
    }

    public ArrayList<Manager> getManagers() {
        ArrayList<Manager> tmp = new ArrayList<>();
        for(Map<String, String> member: teamMembers.values()) {
            if(member.containsKey("teamMembers")) {
                tmp.add(Manager.parse(member));
            }
        }
        return tmp;
    }

    public ArrayList<TeamMember> getTeamMembers(Team team) {
        ArrayList<TeamMember> tmp = new ArrayList<>();
        for(Map<String, String> member: teamMembers.values()) {
            if (member.containsValue(team.toString()) && !member.containsKey("teamMembers") && !member.containsKey("managers")) {
                tmp.add(TeamMember.parse(member));
            }
        }
        return tmp;
    }

    public ArrayList<TeamMember> getTeamMembers() {
        ArrayList<TeamMember> tmp = new ArrayList<>();
        for(Map<String, String> member: teamMembers.values()) {
            if (!member.containsKey("teamMembers") && !member.containsKey("managers")) {
                tmp.add(TeamMember.parse(member));
            }
        }
        return tmp;
    }

    public HOD getHOD() {
        return HOD.parse(teamMembers.get("HOD"));
    }

    public void addFitting(Fitting fitting) {
        fittings.put("Fitting " + fitting.getId(), fitting.toMap());

        editor.setRepositoryStrings(fittings);
        editor.writeToTextFile(repo + "fittings.txt");
    }

    public TeamMember getModel(int model) {
        return TeamMember.parse(teamMembers.get("Team Member "+ model));
    }

}
