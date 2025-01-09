package src.Inventory.src;

import src.Inventory.src.interfaces.ProductDescription;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Mani Raj
 */
public class Product {

	private int id;
	private double price;
	private int count;

	private Map<String,String> pdetails =new HashMap<>();

	public Product(double price, int count) {


		this.price = price;
		this.count=count;

		setPrice(price);
		setCount(count);
	}

	public void setPrice(double price)
	{
		pdetails.put("price",String.valueOf(price));
	}
	public void setCount(int count)
	{
		pdetails.put("count",String.valueOf(count));
	}

	public Map<String,String> getPdetails()
	{
		return pdetails;
	}

	public int getId() {
		return id;
	}
}
