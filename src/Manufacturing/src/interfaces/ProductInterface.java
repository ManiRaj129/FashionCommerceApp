package src.Manufacturing.src.interfaces;

import src.Manufacturing.src.Product;

import java.util.Map;

public interface ProductInterface {

    void setName(String name);
    String getName();

    void setDescription(String description);
    String getDescription();

    void setQuantity(String quantity);
    String getQuantity();

    void setCategory(String category);
    String getCategory();

    String displayProducts();
    void setPrice(String price);
    String getPrice();



}
