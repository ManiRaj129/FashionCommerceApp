package src.Inventory.src;
import src.Inventory.src.interfaces.ProductDescription;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Mani Raj
 */
public class BasicProductDescription implements ProductDescription {

	//product details list
	private Map<String,String> pDesc=new HashMap<>();

	public BasicProductDescription(String description) {

		pDesc.put("Description",description);
	}
	public Map<String,String> productDetails()
	{
		return pDesc;
	}

	public void print() {
		System.out.println("-----------------------------------------------------");
		System.out.println("Product Information");
		for (Map.Entry<String, String> pd : pDesc.entrySet()) {


			System.out.printf("%-30s %s%n", pd.getKey(), pd.getValue());

		}
		System.out.println("-----------------------------------------------------");
	}
}
