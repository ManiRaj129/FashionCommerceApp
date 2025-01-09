package src.Inventory.src.interfaces;

public interface StorageManagement {
    String registerProduct(String name, String Desc);

    void addProductCount(String pname, int quantityChange);

    void removeProductCount(String pname, int quantityChange);

}