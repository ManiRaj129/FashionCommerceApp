package src.Inventory.src;

import java.io.File;
import java.util.*;

import src.Sales.src.BasicRetailer;
import src.Sales.src.Order;
import src.TextEditor.PoorTextEditor;
import src.Inventory.src.interfaces.ProductDescription;
import src.Sales.src.interfaces.Retailer;
import src.Inventory.src.interfaces.StorageManagement;


/**
 * @ Mani Raj
 */
public class BasicStorageManage implements StorageManagement {

    Map<String, Map<String, String>> products;


    private String location;

    // name to details
    private Map<String, Map<String, String>> availableProducts;


    PoorTextEditor textEditor;

    private String repoPath = "src/inventory/repository/";


    public BasicStorageManage() {

        repoPath = repoPath + "Main" + "/";

        this.products = new HashMap<>();

        this.location = "Main";
        this.availableProducts = new HashMap<>();
        set();

    }

    public BasicStorageManage(String storageLoc) {

        repoPath = repoPath + storageLoc + "/";


        this.products = new HashMap<>();

        this.location = storageLoc;
        this.availableProducts = new HashMap<>();

        set();
    }

    private void set() {
        String pPath = repoPath + "Products.txt";
        String aPPath = repoPath + "AvailableProducts.txt";
        try {
            File pFile = new File(pPath);
            if (pFile.exists()) {

                textEditor = new PoorTextEditor();
                textEditor.processTextFile(pPath);
                products = textEditor.getRepositoryString();


            }
        } catch (Exception e) {
            System.out.println("Error processing Products.txt ");

        }
        try {
            File aPPFile = new File(aPPath);
            if (aPPFile.exists()) {
                textEditor = new PoorTextEditor();
                textEditor.processTextFile(aPPath);
                availableProducts = textEditor.getRepositoryString();

            }
        } catch (Exception e) {
            System.out.println("Error processing AvailableProducts.txt ");
        }
    }


    //update product count in storage
    @Override
    public void addProductCount(String pname, int quantityChange) {

        textEditor = new PoorTextEditor();

        System.out.println(pname);
        if (products.containsKey(pname)) {
            if (availableProducts.containsKey(pname)) {
                int ncount = Integer.parseInt(availableProducts.get(pname).get("count"));
                ncount = ncount + quantityChange;
                availableProducts.get(pname).put("count", String.valueOf(ncount));
            } else {
                System.out.println("the requested product not in the available products." +
                        "To add first restore the product details as below");
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter price of the product:");
                double price = sc.nextDouble();
                Product p = new Product(price, quantityChange);
                availableProducts.put(pname, p.getPdetails());
            }

            textEditor.setRepositoryStrings(availableProducts);
            textEditor.writeToTextFile(repoPath + "AvailableProducts.txt");
            set();
        } else {
            System.out.println("Product does not exists. Register Product to add!");
        }

    }


    public void changePrice(int price, String pname) {
        if (availableProducts.containsKey(pname)) {
            textEditor = new PoorTextEditor();
            availableProducts.get(pname).put("price", String.valueOf(price));
            textEditor.setRepositoryStrings(availableProducts);
            textEditor.writeToTextFile(repoPath + "AvailableProducts.txt");
        } else {
            System.out.println(pname + " is not registered in the inventory");
        }
    }


    public void removeProductCount(String pname, int quantityChange) {

        textEditor = new PoorTextEditor();

        if (products.containsKey(pname)) {
            if (availableProducts.containsKey(pname)) {
                int ncount = Integer.parseInt(availableProducts.get(pname).get("count"));
                ncount = ncount - quantityChange;
                if (ncount < 0) {
                    System.out.println("quantity removal exceeds the available quantity");
                } else {
                    if (ncount == 0) {
                        availableProducts.remove(pname);
                        System.out.println("inventory updated the product count and " + pname + " no longer available in the stock");
                    } else {
                        availableProducts.get(pname).put("count", String.valueOf(ncount));
                    }

                    set();
                    textEditor.setRepositoryStrings(availableProducts);
                    textEditor.writeToTextFile(repoPath + "AvailableProducts.txt");
                }

            } else {
                System.out.println("requested product is not available");
            }
        } else {
            System.out.println("Product does not exists. Register Product!");
        }
    }


    @Override
    public String registerProduct(String name, String Desc) {

        textEditor = new PoorTextEditor();

        ProductDescription pd = new BasicProductDescription(Desc);

        products.put(name, pd.productDetails());

        textEditor.setRepositoryStrings(products);
        textEditor.writeToTextFile(repoPath + "Products.txt");

        pd.print();
        return name + "registered ";
    }

    public Map<String, Map<String, String>> getAvailableProducts() {
        return availableProducts;
    }

    public Map<String, Map<String, String>> getProducts() {
        return products;
    }


    public void print() {

    }


    /**
     * Map<String, Map<String, String>> productShipped,    key: product name  value: productDetails (Map<String, String>)
     * <p>
     * where the productDetails Map has two 2 elements as follow:
     * <p>
     * 1) key: "quantity" (exact string)
     * value: actual quantity number
     * <p>
     * 2) key: "description" (exact string)
     * value: describe the product in one or two words (like clothing or footwear)
     *
     * @param productShipped
     */
    public void unLoadShipment(Map<String, Map<String, String>> productShipped) {
        String pname;
        String Desc;
//        int quantity;
        String quantityStr;

        Map<String, String> pdetail;

        textEditor = new PoorTextEditor();

        for (Map.Entry<String, Map<String, String>> entry : productShipped.entrySet()) {

            pname = entry.getKey();
            pdetail = entry.getValue();
            quantityStr = pdetail.get("quantity");
//            quantity = Integer.parseInt(pdetail.get("quantity"));
            Desc = pdetail.get("description");

            if (quantityStr == null || quantityStr.isEmpty()) {
                System.out.println("Missing or invalid quantity for product: " + pname);
                continue;
            }
            int quantity = Integer.parseInt(quantityStr);
            if (!products.containsKey(pname)) {
                registerProduct(pname, Desc);
            }
            if (availableProducts.containsKey(pname)) {
                String countStr = availableProducts.get(pname).get("count");
                int ncount = Integer.parseInt(availableProducts.get(pname).get("count"));
                ncount = ncount + quantity;
                availableProducts.get(pname).put("count", String.valueOf(ncount));
            } else {
                //default price could be changed
                double price = 10.0;
                Product p = new Product(price, quantity);
                availableProducts.put(pname, p.getPdetails());
            }
        }
        textEditor.setRepositoryStrings(availableProducts);
        textEditor.writeToTextFile(repoPath + "AvailableProducts.txt");
    }


}
