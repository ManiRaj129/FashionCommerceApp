package src.Manufacturing.src;

import src.App;
import src.Design.src.FinalDesign;
import src.Inventory.src.BasicStorageManage;
import src.Inventory.src.interfaces.InventoryController;

import java.util.*;

public class ManufacturingController {

    private ManufacturingManager manager;
    private HeadOfManufacturing headOfManufacturing;
    private Machines machine;
    private boolean isReady = false;
    private boolean productCreated = false;
    private final ManufacturingFileManager fileManager = new ManufacturingFileManager();
    private Map<String, Object> selectedDesign = null;
    private Map<String, Object> selectedCSTMDesign = null;
    private String designType = null;
    private Map<String, Object> newFinalDesign = null;
    private BasicStorageManage storageManage;
    private ManufacturingReport manufacturingReport;
    private final String manRepo = "Manufacturing/repository/";


    public ManufacturingController() {
        this.headOfManufacturing = new HeadOfManufacturing();
        this.machine = new Machines();
        this.manager = new ManufacturingManager();
        this.manufacturingReport = new ManufacturingReport();
        this.storageManage = new BasicStorageManage();


    }

    public void run() throws Exception {

        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        System.out.println("Welcome to Manufacturing System");

        while (!exit) {
            System.out.println("Please enter your choice");
            System.out.println("1. Receive and send specifications for Final Design");
            System.out.println("2. Receive and send specifications for Custom Design");
            System.out.println("3. Collect Raw Materials for Designs");
            System.out.println("4. Verify Raw Materials for Designs");
            System.out.println("5. Create Product and store in Inventory");
            System.out.println("6. View Manufacturing Report");
            System.out.println("7. Exit");

            int choice = sc.nextInt();
            sc.nextLine();
            Map<String, Object> collectedMaterials = new HashMap<>();

            switch (choice) {
                case 1:
                    System.out.println("Accessing Final Designs from repository...");
                    System.out.println(".   .   .");
                    Map<String, Object> storedDesigns = fileManager.getFinalDesign();
                    if (storedDesigns == null || storedDesigns.isEmpty()) {
                        System.out.println("No final design are found");
                        return;
                    }
                    System.out.println("Available Final Designs: ");
                    List<String> finalDesignNames = new ArrayList<>(storedDesigns.keySet());
                    for (int i = 0; i < finalDesignNames.size(); i++) {
                        System.out.println((i + 1) + ". " + finalDesignNames.get(i));
                    }
                    System.out.println("Select a Final Design: ");
                    int finalDesignChoice = sc.nextInt() - 1;
                    sc.nextLine();
                    if (finalDesignChoice < 0 || finalDesignChoice > finalDesignNames.size()) {
                        System.out.println("Invalid choice");
                        return;
                    }

                    String selectedDesignName = finalDesignNames.get(finalDesignChoice);
                    selectedDesign = (Map<String, Object>) storedDesigns.get(selectedDesignName);
                    System.out.println("Selected: " + selectedDesignName);
                    selectedDesign.forEach((key, value) -> {
                        System.out.println(key + ": " + value);
                    });
                    //give information to the manager to then collect the raw materials properly.
                    manager.setSelectedDesign(selectedDesignName, selectedDesign);
                    System.out.println("Final Design has been passed to the manager...");
                    System.out.println(".   .   .");
                    break;

                case 2:
                    System.out.println("Accessing Custom Designs from repository...");
                    System.out.println(".   .   .");
                    Map<String, Object> storedCustomDesigns = fileManager.getCustomDesign();
                    if (storedCustomDesigns == null || storedCustomDesigns.isEmpty()) {
                        System.out.println("No final design are found");
                        return;
                    }
                    System.out.println("Available Custom Designs: ");
                    List<String> customDesignNames = new ArrayList<>(storedCustomDesigns.keySet());
                    for (int i = 0; i < customDesignNames.size(); i++) {
                        System.out.println((i + 1) + ". " + customDesignNames.get(i));
                    }
                    System.out.println("Select a Custom Design: ");
                    int customDesignChoice = sc.nextInt() - 1;
                    sc.nextLine();
                    if (customDesignChoice < 0 || customDesignChoice > customDesignNames.size()) {
                        System.out.println("Invalid choice");
                        return;
                    }
                    String selectedCustomDesignName = customDesignNames.get(customDesignChoice);
                    selectedCSTMDesign = (Map<String, Object>) storedCustomDesigns.get(selectedCustomDesignName);
                    System.out.println("Selected: " + selectedCustomDesignName);
                    selectedCSTMDesign.forEach((key, value) -> {
                        System.out.println(key + ": " + value);
                    });

                    manager.setSelectedDesign(selectedCustomDesignName, selectedCSTMDesign);
                    System.out.println("Custom Design has been passed to the manager...");
                    System.out.println(".   .   .");
                    break;


                case 3:
                    System.out.println("Collecting the Raw Materials from Storage...");
                    System.out.println("Select the design type to collect raw materials");
                    System.out.println("1. Final Design");
                    System.out.println("2. Custom Design");
                    int designChoice = sc.nextInt();
                    sc.nextLine();
                    Map<String, Object> activeDesign = manager.getSelectedDesignDetails();

                    if (activeDesign == null) {
                        System.out.println("No active design was given to the manager to collect the materials");
                        return;
                    }
                    //make sure it is the correct type
                    if (designChoice == 1) {
                        System.out.println("Accessing Final Designs from repository...");
                        Map<String, Object> finalDesigns = fileManager.getFinalDesign();
                        if (finalDesigns == null || finalDesigns.isEmpty()) {
                            System.out.println("No final design are found");
                            return;
                        }
                        System.out.println("Selected Design: ");
                        activeDesign.forEach((key, value) -> {
                            System.out.println(key + ": " + value);
                        });
                        Object requiredRawMaterials = activeDesign.get("rawMaterials");
                        List<String> rawMaterialsList = requiredRawMaterials instanceof String ?
                                List.of(((String) requiredRawMaterials).split(",")) : new ArrayList<>();
                    } else if (designChoice == 2) {
                        System.out.println("Accessing Custom Designs from repository...");
                        Map<String, Object> customDesigns = fileManager.getCustomDesign();
                        if (customDesigns == null || customDesigns.isEmpty()) {
                            System.out.println("No final design are found");
                            return;
                        }
                        System.out.println("Selected Custom Design: ");
                        activeDesign.forEach((key, value) -> {
                            System.out.println(key + ": " + value);
                        });
                        Object requiredCustomMaterials = customDesigns.get("customDesign");
                        List<String> customMaterialList = requiredCustomMaterials instanceof String
                                ? List.of(((String) requiredCustomMaterials).split(",")) : new ArrayList<>();
                    }

                    while (true) {
                        System.out.println("Enter 'done' to exit prompt at any time");
                        System.out.println("Collect the type of Raw Material based on the Design: ");
                        String rawMaterial = sc.nextLine().trim();
                        if (rawMaterial.equalsIgnoreCase("done")) {
                            break;
                        }

                        System.out.println("Select Quantity of Raw Materials based on the Design: " + rawMaterial);
                        String quantity = sc.nextLine();
                        collectedMaterials.put(rawMaterial, quantity);
                        System.out.println("You have " + quantity + " items collected of " + rawMaterial);
                        //Enter the time at which the raw materials were collected
                        System.out.println("Enter the current time at which the Raw Materials were collected.");
                        System.out.println("(yyyy-MM-dd HH:mm:ss): )");
                        String collectingTime = sc.nextLine();
                        manufacturingReport.setCollectingMaterialsTime(collectingTime);
                        System.out.println("Time has been set.");
                        String reportFilePathCollect = manRepo + "ManufacturingReport.txt";
                        manufacturingReport.saveReportToFile(reportFilePathCollect, activeDesign.toString());
                    }

                    manager.collectRawMaterials(collectedMaterials);
                    System.out.println("Raw Materials Collected:");
                    collectedMaterials.forEach((rawMaterial, quantity) -> {
                        System.out.println(quantity + " items of " + rawMaterial);
                    });
                    Map<String, Map<String, String>> formattedMaterials = new HashMap<>();
                    Map<String, Object> finalData = fileManager.getFinalDesign();
                    Map<String, Object> customData = fileManager.getCustomDesign();
                    if (finalData != null || customData != null || finalData.isEmpty() || customData.isEmpty()) {
                        finalData.forEach((key, value) -> {
                            if (value instanceof Map) {
                                @SuppressWarnings("unchecked")
                                Map<String, String> details = (Map<String, String>) value;
                                formattedMaterials.put(key, details);
                            }
                        });
                        customData.forEach((key, value) -> {
                            if (value instanceof Map) {
                                @SuppressWarnings("unchecked")
                                Map<String, String> details = (Map<String, String>) value;
                                formattedMaterials.put(key, details);
                            }
                        });
                    }
                    collectedMaterials.forEach((rawMaterial, quantity) -> {
                        Map<String, String> details = new HashMap<>();
                        formattedMaterials.computeIfAbsent(rawMaterial, k -> new HashMap<>()).put("Quantity", String.valueOf(quantity));
//                        formattedMaterials.put(rawMaterial, details);
                    });

                    fileManager.saveToFile("RawMaterials.txt", new HashMap<>(formattedMaterials));
                    System.out.println("Raw Materials Saved to repository");

                    break;
                case 4:
                    System.out.println("Verify the Raw Materials from Storage");

                    Map<String, Object> storedRawMaterials = fileManager.getRawMaterials();
                    if (storedRawMaterials == null || storedRawMaterials.isEmpty()) {
                        System.out.println("You have no items collected yet.");
                        return;
                    }

                    System.out.println("Stored Raw Materials: ");
                    storedRawMaterials.forEach((key, value) -> {
                        System.out.println("Material: " + key + ", Quantity: " + value);
                    });

//                    headOfManufacturing.viewRawMaterials(manager.getCollectedMaterials());
                    for (Map.Entry<String, Object> entry : storedRawMaterials.entrySet()) {
                        String rawMaterial = entry.getKey();
                        String quantity = entry.getValue().toString();

                        System.out.println("Do you want to verify " + quantity + " items of " + rawMaterial + "? (Y/N)");
                        String verifyRawMaterial = sc.nextLine();

                        if (verifyRawMaterial.equals("Y")) {
                            headOfManufacturing.selectRawMaterial(quantity, rawMaterial);
                            System.out.println("You have " + quantity + " items of " + rawMaterial + " verified.");
                            collectedMaterials.put(rawMaterial, quantity);
                            isReady = true;
                            //Enter the verified time for which the materials were verified.
                            System.out.println("Enter the time at which the Raw Materials were verified.");
                            System.out.println("(yyyy-MM-dd HH:mm:ss): )");
                            String verifiedTime = sc.nextLine();
                            manufacturingReport.setVerifiedMaterialsTime(verifiedTime);
                            System.out.println("Time has been set.");
                            String reportFilePathRaw = manRepo + "ManufacturingReport.txt";
                            manufacturingReport.saveReportToFile(reportFilePathRaw, rawMaterial);


                        } else if (verifyRawMaterial.equals("N")) {
                            System.out.println("Material " + rawMaterial + " is not verified");
                            isReady = false;
                        } else {
                            System.out.println("Invalid input, select Y or N");
                        }
                    }
//                    fileManager.saveToFile("RawMaterials.txt", collectedMaterials);
                    break;
                case 5:

                    System.out.println("Start to create a product");
                    System.out.println("Select the type of product to create.");
                    System.out.println("1. Product");
                    System.out.println("2. Custom Product");
                    int productChoice = sc.nextInt();
                    sc.nextLine();
                    if (!isReady) {
                        System.out.println("the raw materials have not been verified yet");
                        break;
                    }
                    if (!machine.isRunning()) {
                        System.out.println("Machines are not currently running.");
                        System.out.println("Starting the machines....");
                        machine.startMachine();
                    }
                    if (!machine.isRunning()) {
                        System.out.println("Machines are not currently running.");
                        return;
                    }
//                    FinalDesign design = createFinalDesign(selectedDesign);
                    System.out.println("Machine is running");
                    System.out.println("....................................................");
                    System.out.println("Product is being created...");
                    System.out.println("....................................................");
                    System.out.println("Enter the start time for production: ");
                    System.out.println("(yyyy-MM-dd HH:mm:ss): )");
                    String startTime = sc.nextLine();
                    manufacturingReport.setStartTime(startTime);
                    System.out.println("Time has been set.");
                    String reportFilePathStart = manRepo + "ManufacturingReport.txt";
                    manufacturingReport.saveReportToFile(reportFilePathStart, "designName");
                    Map<String, Map<String, String>> productShipped = new HashMap<>();
                    if (productChoice == 1) {
                        System.out.println("Log Product Details for Inventory: ");
                        if (selectedDesign == null) {
                            System.out.println("No design was selected, select one first");
                            break;
                        }
                        selectedDesign.forEach((key, value) -> {
                            System.out.println(key + ": " + value);
                        });
                        System.out.println("Begin listing Product information: ");
                        System.out.println("Enter Product Name: ");
                        String productName = sc.nextLine();
                        System.out.println("Enter Product Description: ");
                        String productDescription = sc.nextLine();
                        System.out.println("Enter Product Quantity: ");
                        String productQuantity = sc.nextLine();
                        System.out.println("Enter Product Category: ");
                        String productCategory = sc.nextLine();
                        System.out.println("Enter Product Price: ");
                        String productPrice = sc.nextLine();

                        Product product = new Product(productName);
                        product.setName(productName);
                        product.setDescription(productDescription);
                        product.setQuantity(productQuantity);
                        product.setCategory(productCategory);
                        product.displayProducts();
                        product.setPrice(productPrice);
                        manager.setProducts(product);

                        Map<String, Object> productDetails = new HashMap<>();
                        productDetails.put("Name", product.getName());
                        productDetails.put("Description", product.getDescription());
                        productDetails.put("Quantity", product.getQuantity());
                        productDetails.put("Category", product.getCategory());
                        productDetails.put("Price", product.getPrice());

                        //send to Inventory for productShipped map
                        Map<String, String> productShippedDetails = new HashMap<>();
                        productShippedDetails.put("quantity", product.getQuantity());
                        productShippedDetails.put("description", product.getDescription());
                        productShipped.put(product.getName(), productShippedDetails);
                        storageManage.unLoadShipment(productShipped);//call to inventory

                        fileManager.saveToFile("Products.txt", productDetails);
                        productCreated = manager.createProduct(collectedMaterials);
                        System.out.println("Product created");
                        System.out.println("Enter the end time of Production: ");
                        System.out.println("(yyyy-MM-dd HH:mm:ss): )");
                        String endTime = sc.nextLine();
                        manufacturingReport.setEndTime(endTime);
                        System.out.println("Time has been set.");
                        String reportFilePath1 = manRepo + "ManufacturingReport.txt";
                        manufacturingReport.saveReportToFile(reportFilePath1,product.getName());
                    } else if (productChoice == 2) {
                        System.out.println("Log Custom Product Details for Inventory: ");
                        if (selectedCSTMDesign == null) {
                            System.out.println("No design was selected, select one first");
                            break;
                        }
                        selectedCSTMDesign.forEach((key, value) -> {
                            System.out.println(key + ": " + value);
                        });
                        System.out.println("Enter Product Name: ");
                        String productName = sc.nextLine();
                        System.out.println("Enter Product Description: ");
                        String productDescription = sc.nextLine();
                        System.out.println("Enter Product Quantity: ");
                        String productQuantity = sc.nextLine();
                        System.out.println("Enter Product Category: ");
                        String productCategory = sc.nextLine();
                        System.out.println("Enter Product Price: ");
                        String productPrice = sc.nextLine();
                        CustomProduct customProduct = new CustomProduct(productName);
                        customProduct.setName(productName);
                        customProduct.setDescription(productDescription);
                        customProduct.setQuantity(productQuantity);
                        customProduct.setCategory(productCategory);
                        customProduct.setPrice(productPrice);
                        customProduct.displayProducts();

                        manager.setCustomProducts(customProduct);
                        Map<String, Object> customProductDetails = new HashMap<>();
                        customProductDetails.put("Name", customProduct.getName());
                        customProductDetails.put("Description", customProduct.getDescription());
                        customProductDetails.put("Quantity", customProduct.getQuantity());
                        customProductDetails.put("Category", customProduct.getCategory());
                        customProductDetails.put("Price", customProduct.getPrice());
                        //sending to inventory
                        Map<String, String> productShippedDetails = new HashMap<>();
                        productShippedDetails.put("quantity", customProduct.getQuantity());
                        productShippedDetails.put("description", customProduct.getDescription());
                        productShipped.put(customProduct.getName(), productShippedDetails);
                        storageManage.unLoadShipment(productShipped);//call to inventory

                        fileManager.saveToFile("CustomProducts.txt", customProductDetails);
                        productCreated = manager.createProduct(collectedMaterials);
                        System.out.println("Custom Product created");
                        System.out.println("Enter the end time of production for a Custom Product: ");
                        System.out.println("(yyyy-MM-dd HH:mm:ss): )");
                        String endTime = sc.nextLine();
                        manufacturingReport.setEndTime(endTime);
                        System.out.println("Time has been set.");
                        String reportFilePath2 = manRepo + "ManufacturingReport.txt";
                        manufacturingReport.saveReportToFile(reportFilePath2,customProduct.getName());

                    }
                    break;
                case 6:
                    System.out.println("Manufacturing Report List");
                    System.out.println("-------------------------------------------------------");
                    manufacturingReport.displayManufacturingReport();
                    break;

                case 7:
                    System.out.println("Exit Program");
                    exit = true;
                    App.prompt();
                    break;

                default:
                    System.out.println("Please enter a valid choice");
            }
        }

        sc.close();
    }
}
