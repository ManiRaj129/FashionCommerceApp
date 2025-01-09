package src.Sales.src;

import java.util.HashMap;
import java.util.Map;


public class Order {
	private int id;
	private Map<String, String> productList; // productId mapped to quantity

	private String retailerName;

	private Map<String, Object> orderDetails=new HashMap<>();

	public Order(int id,String retailerName) {
		this.id = id;
		this.retailerName=retailerName;
		this.productList = new HashMap<>();
	}

	public void setOrderDetails()
	{
		orderDetails.put(retailerName,productList);
	}

	public void addProduct(String pName, int quantity) {
		productList.put(pName, String.valueOf(quantity));
	}

	public void deleteProduct(String pName) {
		productList.remove(pName);
	}

	public  Map<String, Object> getOrderDetails()
	{
		return orderDetails;
	}

	public Map<String, String> getProductList() {
		return productList;
	}

	public int getId() {

		return id;
	}

}
