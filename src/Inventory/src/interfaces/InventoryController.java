package src.Inventory.src.interfaces;

public interface InventoryController {

	void run();
	void registerProduct(String name, String desc);

	void addProductQuantity(String pname, int quantity);

	void removeProductQuantity(String pname, int quantity);
}
