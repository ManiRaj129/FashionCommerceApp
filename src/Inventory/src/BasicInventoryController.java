package src.Inventory.src;
import src.Inventory.src.interfaces.InventoryController;
import src.Inventory.src.interfaces.StorageManagement;


import src.App;
import src.Inventory.src.interfaces.*;
import src.Security.src.SecurityRequestScheduler;
import src.Security.src.SecurityRequests;
import src.Security.src.SecuritySchedulerController;

import java.io.File;
import java.util.Scanner;

/**
 * Handles user interaction for inventory management.
 * Provides options to register products and retailers,
 * and to add or remove product quantities from storage.
 *
 * @author Mani Raj
 */

public class BasicInventoryController implements InventoryController {

    private BasicStorageManage storage ;

    private String StorageLocation;
    public BasicInventoryController() {}

    /**
     * Runs the inventory management system.
     */
    public void run() {
        System.out.println("Welcome to the Inventory Management System");


        Scanner scan = new Scanner(System.in);
        boolean isExist = false;

        System.out.println("Enter Storage Location:");
        StorageLocation = scan.nextLine();
        validateStoreLoc(StorageLocation);


        boolean exit = false;
        while (!exit) {
            System.out.println("1. Register New Product");
            System.out.println("2. Register New Retailer");
            System.out.println("3. Register New Storage");
            System.out.println("4. Add Product Quantity");
            System.out.println("5. Remove Product Quantity");
            System.out.println("6. Re-Enter Storage Location");
            SecurityRequestScheduler scheduler = new SecurityRequestScheduler();
            scheduler.optionsPrint();
            System.out.println("7. Exit Program");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:

                    System.out.println("Enter Product Name:");
                    String productName = scan.nextLine();

                    System.out.println("Enter Product description:");
                    String productDesc = scan.nextLine();

                    registerProduct(productName,productDesc);
                    break;


                case 3:
                    System.out.println("Enter Storage Location:");
                    String location = scan.nextLine();
                    File directory = new File("src/inventory/repository/"+location);
                    if (directory.mkdirs()) {
                        StorageLocation=location;
                        System.out.println("Storage location registered successfully!");
                    } else {
                        System.out.println("Failed to register new storage location!");
                    }
                    break;
                case 4:
                    System.out.println("Enter Product Name to add quantity:");
                    String addProductName = scan.nextLine();
                    System.out.println("Enter Quantity to Add:");
                    int addQuantity = scan.nextInt();

                    addProductQuantity(addProductName, addQuantity);
                    break;
                case 5:
                    System.out.println("Enter Product Name to remove quantity:");
                    String removeProductName = scan.nextLine();
                    System.out.println("Enter Quantity to Remove:");
                    int removeQuantity = scan.nextInt();

                    removeProductQuantity(removeProductName, removeQuantity);
                    break;

                case 6:
                    System.out.println("Enter Storage Location:");
                    StorageLocation = scan.nextLine();
                    validateStoreLoc(StorageLocation);
                    break;

                case 111:
                    scheduler.addSecurityRequest();
                    break;

                case 112:
                    scheduler.showAllSecurityRequests();
                    break;

                case 113:
                    scheduler.deleteScheduleByID();
                    break;

                case 7:
                    System.out.println("Exiting program...");
                    exit = true;
                    try{
                        App.prompt();
                    }
                    catch (Exception e)
                    {

                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scan.close();

    }

    private Boolean validateStoreLoc(String loc)
    {
        File directory = new File("src/inventory/repository/"+loc);
        // Check if the directory exists and is a directory
        if (!(directory.exists() && directory.isDirectory())) {
            System.out.println("The Storage location does not exist. Register the storage location if needed");
            return false;
        }
        return true;
    }


    // Method to register a new product
    public void registerProduct(String name, String desc) {
        getStorageInstance().registerProduct(name,desc);
    }


    public void addProductQuantity(String pname, int quantity) {
        getStorageInstance().addProductCount(pname, quantity);
    }

    public void removeProductQuantity(String pname, int quantity) {
        getStorageInstance().removeProductCount(pname, quantity);
    }

    // Singleton method to get the Inventory instance
    private StorageManagement getStorageInstance() {
        if (storage == null) {
            storage = new BasicStorageManage(StorageLocation);
        }
        return storage;
    }

}

