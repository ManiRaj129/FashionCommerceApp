package src.Sales.src.interfaces;

import java.util.Map;

public interface Retailer {

	Map<String,String> getRDetails();
	String getName();
	String getLocation();
	void print();
}
