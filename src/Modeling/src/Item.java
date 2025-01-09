package src.Modeling.src;

import src.Modeling.src.interfaces.IItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Item implements IItem {
    static int nextid = 0;
    int id;

    Team associatedTeam;
    String itemType;
    String[] itemLocation;
    String itemName;
    RecurrenceType recurrenceType;

    boolean checkedOut;
    boolean damaged;


    Item(int i, Team associatedTeam, String itemType, String[] itemLocation, String itemName, RecurrenceType recurrenceType, boolean checkedOut, boolean damaged) {
        id = i;
        if (id >= nextid) nextid = id + 1;
        this.associatedTeam = associatedTeam;
        this.itemType = itemType;
        this.itemLocation = itemLocation;
        this.itemName = itemName;
        this.recurrenceType = recurrenceType;
        this.checkedOut = checkedOut;
        this.damaged = damaged;
    }

    public Item(Team associatedTeam, String itemType, String itemName, RecurrenceType recurrenceType) {
        id = nextid;
        nextid++;
        this.associatedTeam = associatedTeam;
        this.itemType = itemType;
        this.itemLocation = new String[]{Integer.toString(id), associatedTeam.toChar()};
        this.itemName = itemName;
        this.recurrenceType = recurrenceType;

        checkedOut = false;
        damaged = false;
    }

    @Override
    public int getId() {return id;}

    @Override
    public Team getAssociatedTeam() {return associatedTeam;}

    @Override
    public String getItemType() {return itemType;}

    @Override
    public String getItemName() {return itemName;}

    @Override
    public void setAssociatedTeam(Team team) {
        associatedTeam = team;
        this.itemLocation = new String[]{Integer.toString(id), associatedTeam.toChar()};
    }

    @Override
    public void setType(String itemType) {this.itemType = itemType;}

    @Override
    public void setItemName(String name) {this.itemName = name;}

    @Override
    public String getItemLocation() {return itemLocation[0] + itemLocation[1];}

    @Override
    public void flagDamaged() {this.damaged = true;}

    @Override
    public void flagCheckedOut() {this.checkedOut = true;}

    @Override
    public void flagReturned() {this.checkedOut = false;}

    @Override
    public String toString() {
        return "\n" + this.associatedTeam.toString() + " Item: " + id +
                "\n Item Type: " + this.itemType +
                "\n Item Location: " + this.getItemLocation() +
                "\n Item Name: " + this.getItemName() +
                "\n Recurrence: " + this.recurrenceType.getRecurrence() +
                "\n Checked Out: " + this.checkedOut +
                "\n Damaged: " + this.damaged;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> itemDetails = new HashMap<>();
        itemDetails.put("id", Integer.toString(this.id));
        itemDetails.put("associatedTeam", this.associatedTeam.toString());
        itemDetails.put("itemType", this.itemType);
        itemDetails.put("itemLocation", Arrays.toString(this.itemLocation));
        itemDetails.put("itemName", this.itemName);
        itemDetails.put("recurrenceType", this.recurrenceType.toString());
        itemDetails.put("checkedOut", Boolean.toString(this.checkedOut));
        itemDetails.put("damaged", Boolean.toString(this.damaged));
        return itemDetails;
    }

    public static Item parse(Map<String, String> item) {
        return new Item(
                Integer.parseInt(item.get("id")),
                Team.parseTeam(item.get("associatedTeam")),
                item.get("itemType"),
                item.get("itemLocation").replaceAll("[\\[\\] ]", "").split(","),
                item.get("itemName"),
                RecurrenceType.parseType(item.get("recurrenceType")),
                Boolean.parseBoolean(item.get("checkedOut")),
                Boolean.parseBoolean(item.get("damaged"))
        );
    }
}
