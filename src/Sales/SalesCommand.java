package src.Sales;

import src.App;
import src.Sales.src.BasicSalesController;
import src.Sales.src.interfaces.SalesController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SalesCommand {
    Map<String, Runnable> commands = new HashMap<>();
    SalesController sc = new BasicSalesController();

    public SalesCommand() {
        setCommands();
    }

    private void setCommands() {
        Scanner scan = new Scanner(System.in);

        commands.put("Register Retailer", () -> {
            System.out.println("Enter Retailer Name:");
            String retailerName = scan.nextLine();
            System.out.println("Enter Retailer Location:");
            String retailerLocation = scan.nextLine();
            sc.registerRetailer(retailerName, retailerLocation);
        });

        commands.put("Add Order", () -> {
            System.out.println("Enter Retailer id:");
            int rid = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter the date of order in the format DD-MM-YYYY:");
            String date = scan.nextLine();

            Map<String, Integer> products = new HashMap<>();
            boolean flag = true;
            while (flag) {
                System.out.println("Enter product name:");
                String pname = scan.nextLine();
                System.out.println("Enter quantity");
                int quan = scan.nextInt();
                scan.nextLine();
                products.put(pname, quan);

                System.out.println("Do you want to still add (yes/no):");
                String response = scan.nextLine();

                if (response.equals("no")) {
                    flag = false;
                }
            }
            sc.addOrder(rid, date, products);
        });
        commands.put("Print Receipt", () -> {
            System.out.println("Enter Order id:");
            int oid = scan.nextInt();
            scan.nextLine();
            sc.printReceipt(oid);
        });
        commands.put("Change Price", () -> {
            System.out.println("Enter Product Name:");
            String pname = scan.nextLine();
            System.out.println("Enter new price");
            int price = scan.nextInt();
            scan.nextLine();

            sc.changePrice(price, pname);
        });
        commands.put("View Retailers", () -> {
            sc.viewRetailers();
        });
        commands.put("View Orders", () -> {
            sc.viewOrders();
        });
        commands.put("View Registered Products", () -> {
            sc.viewRegisteredProducts();
        });
        commands.put("View Available Products", () -> {
            sc.viewAvaProducts();
        });
        commands.put("Return Products", () -> {
            System.out.println("Enter Retailer id:");
            int rid = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter order id:");
            int oid = scan.nextInt();
            scan.nextLine();
            System.out.println("How many days has it been since the purchase? :");
            int days = scan.nextInt();
            scan.nextLine();
            Map<String, Integer> products = new HashMap<>();
            boolean flag = true;
            while (flag) {
                System.out.println("Enter product name:");
                String pname = scan.nextLine();
                System.out.println("Enter quantity");
                int quan = scan.nextInt();
                scan.nextLine();
                products.put(pname, quan);

                System.out.println("Do you want to still add (yes/no):");
                String response = scan.nextLine();

                if (response.equals("no")) {
                    flag = false;
                }
            }
            sc.returnOrder(rid, oid, products, days);
        });

        commands.put("NumOfProductSold", () -> {
            System.out.println("Enter the product Name :");
            String pname = scan.nextLine();
            sc.NumOfProductSold(pname);

        });
        commands.put("NumOfProductInDay", () -> {
            System.out.println("Enter the product Name :");
            String pname = scan.nextLine();
            System.out.println("Enter the Date (DD-MM-YYYY) :");
            String date = scan.nextLine();
            sc.NumOfProductInDay(pname,date);
        });
        commands.put("NumOfProductInMonth", () -> {
            System.out.println("Enter the product Name :");
            String pname = scan.nextLine();
            System.out.println("Enter the Month(MM) :");
            int month = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter the Year(YYYY) :");
            int year = scan.nextInt();
            scan.nextLine();
            sc.NumOfProductInMonth(pname,month,year);
        });
        commands.put("NumOfProductInYear", () -> {
            System.out.println("Enter the product Name :");
            String pname = scan.nextLine();
            System.out.println("Enter the Year(YYYY) :");
            int year = scan.nextInt();
            scan.nextLine();
            sc.NumOfProductInYear(pname,year);
        });
        commands.put("MonthSaleReport", () -> {
            System.out.println("Enter the Month(MM) :");
            int month = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter the Year(YYYY) :");
            int year = scan.nextInt();
            scan.nextLine();
            sc.MonthSaleReport(month,year);
        });
        commands.put("DaySaleReport", () -> {
            System.out.println("Enter the Date (DD-MM-YYYY) :");
            String date = scan.nextLine();
            sc.DaySaleReport(date);
        });
        commands.put("YearSaleReport", () -> {
            System.out.println("Enter the Year(YYYY) :");
            int year = scan.nextInt();
            scan.nextLine();
            sc.YearSaleReport(year);
        });
        commands.put("priceManipulate", () -> {
            System.out.println("Enter the product Name :");
            String pname = scan.nextLine();
            sc.priceManipulate(pname);
        });
        commands.put("trackRetailerReward", () -> {
            System.out.println("Enter the Retailer Id :");
            int rid = scan.nextInt();
            sc.trackRetailerReward(rid);
        });

        commands.put("AvailablePoints",() ->{
            System.out.println("Enter the Retailer Id :");
            int rid = scan.nextInt();
            sc.getRetailerPoints(rid);
        });

        commands.put("help", () -> {
            System.out.println("Available Commands:");
            System.out.printf("%-25s - %s\n", "'Register Retailer'", "Register a new retailer.");
            System.out.printf("%-25s - %s\n", "'Add Order'", "Add a new order.");
            System.out.printf("%-25s - %s\n", "'Print Receipt'", "Print a receipt for an order.");
            System.out.printf("%-25s - %s\n", "'Change Price'", "Change the price of a product.");
            System.out.printf("%-25s - %s\n", "'View Retailers'", "View the list of registered retailers.");
            System.out.printf("%-25s - %s\n", "'View Registered Products'", "View all products registered in the system.");
            System.out.printf("%-25s - %s\n", "'View Available Products'", "View all currently available products.");
            System.out.printf("%-25s - %s\n", "'View Orders'", "View the list of orders.");
            System.out.printf("%-25s - %s\n", "'Return Products'", "Handle product returns.");
            System.out.printf("%-25s - %s\n", "'NumOfProductSold'", "Finds the specified product sold count across all sales");
            System.out.printf("%-25s - %s\n", "'NumOfProductInDay'", "Finds the specified product sold count on particular day");
            System.out.printf("%-25s - %s\n", "'NumOfProductInMonth'", "Finds the specified product sold count in a particular month");
            System.out.printf("%-25s - %s\n", "'NumOfProductInYear'", "Finds the specified product sold count in a particular year");
            System.out.printf("%-25s - %s\n", "'MonthSaleReport'", "Generates sale report for a particular month");
            System.out.printf("%-25s - %s\n", "'DaySaleReport'", "Generates sale report for a particular day");
            System.out.printf("%-25s - %s\n", "'YearSaleReport'", "Generates sale report for a particular year");
            System.out.printf("%-25s - %s\n", "'priceManipulate'", "Gives suggestion on price manipulation of a product");
            System.out.printf("%-25s - %s\n", "'trackRetailerReward'", "Provides retailer reward history on different scales");
            System.out.printf("%-25s - %s\n", "'AvailablePoints'", "Returns the available reward points of a retailer");
        });

        commands.put("exit", () -> {
            try {
                App.prompt();
            } catch (Exception e) {

            }
        });
    }

    public void perform(String command) {
        Runnable action = commands.get(command.trim());
        if (action != null) {
            action.run();
        } else {
            System.out.println("Unknown command: " + command);
        }
    }
}
