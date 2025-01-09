package src.Sales.src;

import src.Sales.src.interfaces.Retailer;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Mani Raj
 */
public class BasicRetailer implements Retailer {
	private String name;
	private String location;


	private Map<String,String> rDetails=new HashMap<>();

	public BasicRetailer(String name, String location) {
		this.name = name;
		this.location = location;
		rDetails.put("name",name);
		rDetails.put("retailerLocation", location);

		// initially zero reward points
		rDetails.put("rewardPoints","0");
	}

	public Map<String,String> getRDetails()
	{
		return  rDetails;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		rDetails.put("name",name);
	}
	public String getLocation() {
		return location;
	}

	public void setLocation() {
		rDetails.put("retailerLocation", location);
	}

	public void print() {
		System.out.println("-----------------------------------------------------");
		System.out.println("Retailer Information");
		for (Map.Entry<String, String> rd : rDetails.entrySet()) {

			System.out.printf("%-30s %s%n", rd.getKey(), rd.getValue());

		}
		System.out.println("-----------------------------------------------------");
	}
}
