package src.Manufacturing.src.interfaces;

import src.Manufacturing.src.CustomProduct;
import src.Manufacturing.src.Product;

import java.util.Map;

public interface ManagerInterface {

    void collectRawMaterials(Map<String, Object> materials);
    Map<String, String> getCollectedMaterials();
    boolean createProduct(Map<String, Object> materials);
    void deliverProduct(Product product);
    void setProducts(Product product);
    void setCustomProducts(CustomProduct customProduct);

}
