package src.Manufacturing.src;

import src.Manufacturing.src.interfaces.ManagerInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
@author
 */
public class ManufacturingManager implements ManagerInterface {

    private Map<String, String> collectedMaterials = new HashMap<>();
    private Product product;
    private String selectedDesignName;
    private Map<String, Object> selectedDesignDetails = new HashMap<>();
    private CustomProduct customProduct;


    public void setSelectedDesign(String designName, Map<String, Object> designDetails) {
        this.selectedDesignName = designName;
        this.selectedDesignDetails = designDetails;
    }

    public Map<String, Object> getSelectedDesignDetails() {
        return selectedDesignDetails;
    }

    @Override
    public boolean createProduct(Map<String, Object> verifiedMaterials) {

        System.out.println("Creating product with the following materials: ");
        verifiedMaterials.forEach((material, value) -> System.out.println(value + "items of " + material));
//        this.product = new Product(verifiedMaterials.toString());
        return true;
    }

    @Override
    public void deliverProduct(Product product) {

        if (product == null) {
            System.out.println("Product has not been created");
            return;
        }
        System.out.println("Delivered to the head of manufacturing complete!");


    }

    @Override
    public void setProducts(Product product) {
        this.product = product;
    }

    @Override
    public void setCustomProducts(CustomProduct customProduct) {
        this.customProduct = customProduct;
    }


    @Override
    public void collectRawMaterials(Map<String, Object> materials) {
        if (selectedDesignDetails == null || selectedDesignDetails.isEmpty()) {
            System.out.println("No materials were collected");
            return;
        }
        System.out.println("Collecting raw materials for: " + selectedDesignName);
        Object rawMaterials = selectedDesignDetails.get(selectedDesignName); //or RawMaterials
        if (rawMaterials == null) {
            System.out.println("No materials were collected");
            return;
        }
        List<String> requiredMaterials = new ArrayList<>();
        if (rawMaterials instanceof String) {
            requiredMaterials = List.of(((String) rawMaterials).split(","));
        } else if (rawMaterials instanceof List) {
            requiredMaterials = (List<String>) rawMaterials;
        } else {
            System.out.println("Invalid raw materials were collected");
            return;
        }
        for (String material : requiredMaterials) {
            if (materials.containsKey(material)) {
                collectedMaterials.put(material, materials.get(material).toString());
            } else {
                System.out.println("Material " + material + " does not exist");
            }
        }
        System.out.println("Collected materials: " + collectedMaterials);
    }

    @Override
    public Map<String, String> getCollectedMaterials() {
        return collectedMaterials;
    }

    public String getProductName() {
        return product.getName();
    }

}
