package src.Marketing.src;

import src.Marketing.MarketingDepartment;
import src.Marketing.src.interfaces.ICollabMember;

import java.util.HashMap;
import java.util.Map;

public class Brand implements ICollabMember {
    static int nextid = 0;
    int id;

    String name;

    public Brand(int i, String name) {
        this.id = i;
        if (id >= nextid) nextid = id + 1;
        this.name = name;
    }

    public Brand(String name) {
        id = nextid;
        nextid++;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public boolean requestCollab() {
        return Math.random() < 0.5;
    }

    @Override
    public String toString() {
        return "\nCollab Member " + id + ":" + "\n Name: " + getName();
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> brandDetails = new HashMap<>();
        brandDetails.put("id", Integer.toString(this.id));
        brandDetails.put("name", this.name);
        return brandDetails;
    }

    public static Brand parse(Map<String, String> brand) {
        return new Brand(
                Integer.parseInt(brand.get("id")),
                brand.get("name")
        );
    }
}
