package src.Sales.src;


import src.Sales.src.interfaces.Retailer;
import src.Sales.src.interfaces.SalesManagement;
import src.TextEditor.PoorTextEditor;

import java.io.File;
import java.util.*;

public class BasicSalesManage implements SalesManagement {

    // id to details
    private Map<String, Map<String, String>> retailers;

    // id to details
    private Map<String, Map<String, String>> orders;

    private SalesInventoryRequest sir = new SalesInventoryRequest();
    private SaleAnalyzer sa = new SaleAnalyzer();

    private String location;
    private String repoPath = "src/Sales/repository/";

    PoorTextEditor textEditor;

    public BasicSalesManage() {
        this.retailers = new HashMap<>();
        this.orders = new HashMap<>();
        location = "Main";

        set();
    }

    private void set() {
        String rPath = repoPath + "Retailers.txt";
        String oPath = repoPath + "Orders.txt";

        try {
            File rFile = new File(rPath);
            if (rFile.exists()) {
                textEditor = new PoorTextEditor();
                textEditor.processTextFile(rPath);
                retailers = textEditor.getRepositoryString();
            }
        } catch (Exception e) {
            System.out.println("Error processing Retailers.txt");

        }
        try {
            File oFile = new File(oPath);
            if (oFile.exists()) {
                textEditor = new PoorTextEditor();
                textEditor.processTextFile(oPath);
                orders = textEditor.getRepositoryString();
            }
        } catch (Exception e) {
            System.out.println("Error processing Retailers.txt");
        }
    }


    public void addOrder(int rid, String date, Map<String, Integer> products) {

        int size = orders.size();
        String id = String.valueOf(rid);
        if (retailers.containsKey(id)) {

            Map<String, String> odetail = sir.addPToOrder(products);
            odetail.put("retailerId", String.valueOf(rid));
            odetail.put("date", date);

            odetail.put("appliedRewardPoints", "0");

            int avaPoints = getRetailerPoints(rid);
            System.out.println("You have " + avaPoints + " reward points");

            String wantToApply = "";

            Scanner sc = new Scanner(System.in);
            System.out.println("Do you want use any points to pay this order (yes/no): ");
            wantToApply = sc.nextLine();

            if (wantToApply.equals("yes")) {
                System.out.println("How many Points do you want to use?");

                int pointUse = sc.nextInt();
                sc.nextLine();
                while (!removePointRetailer(pointUse, rid)) {
                    System.out.println("Requested Points exceed the available points as you only have " + avaPoints);
                    System.out.println("Enter Points with in available limit to apply");
                    pointUse = sc.nextInt();
                    sc.nextLine();
                }
                odetail.put("appliedRewardPoints", String.valueOf(pointUse));

            } else {
                System.out.println("Proceeding with out reward points");
            }

            orders.put(String.valueOf(size + 1), odetail);

            System.out.println("order placed by id: " + (size + 1));
            int points = calReward(odetail);
            System.out.println("Earned Reward Points with this order :" + points);

            System.out.println(" Reward Scheme: every 100 bucks purchase earn 5 reward points where 1 point equivalent to 1 buck");
            System.out.println(" You can use these points for any of your future orders.");
            textEditor = new PoorTextEditor();
            textEditor.setRepositoryStrings(orders);
            textEditor.writeToTextFile(repoPath + "Orders.txt");

            // to update reward points and order details
            set();
            addPointRetailer(points, rid);

            System.out.println("use command to print receipt if needed using the order id");
        } else {
            System.out.println("Retailer id does not exist. Register the retailer!");
        }

    }

    public void NumOfProductSold(String pname) {

        System.out.println(" The total quantity of "+pname +" sold across all sales is "+sa.NumOfProductSold(orders, pname));
    }

    public void NumOfProductInDay(String pname, String date) {

        System.out.println(" The total quantity of "+pname +" sold on " + date +" is "+sa.NumOfProductInDay(orders, pname, date));
    }

    public void NumOfProductInMonth(String pname, int month, int year) {
        System.out.println(" The total quantity of "+pname +" sold during " + month +" month "+ year+ " year is "+sa.NumOfProductInMonth(orders, pname, month, year));
    }

    public void NumOfProductInYear(String pname, int year) {
        System.out.println(" The total quantity of "+pname +" sold during " + year +" year is "+sa.NumOfProductInYear(orders, pname, year));
    }

    public void MonthSaleReport(int month, int year) {
        sa.MonthSaleReport(orders, month, year);
    }

    public void DaySaleReport(String date) {
        sa.DaySaleReport(orders, date);
    }

    public void YearSaleReport(int year) {
        sa.YearSaleReport(orders, year);
    }
    public void priceManipulate(String pname)
    {
        sa.priceManipulate(orders, pname);
    }

    public void trackRetailerReward(int rid)
    {
        RewardRetailer rewardRetailer = new RewardRetailer();
        Scanner sc = new Scanner(System.in);

        System.out.println("Specify how you want to track rewards (By 'order' or 'month' or 'year'): ");
        String opt =sc.nextLine();
        String wantToCont;
       do{
           switch (opt){
               case "order":
                   System.out.println("Enter order ID for reward analysis :");
                   int oid = sc.nextInt();
                   sc.nextLine();
                   rewardRetailer.rewardByOrderId(rid,oid,orders);
                   break;
               case "month":
                   System.out.println("Enter the Month(MM) for reward analysis :");
                   int month = sc.nextInt();
                   sc.nextLine();
                   System.out.println("Enter the Year(YYYY) for reward analysis :");
                   int year = sc.nextInt();
                   sc.nextLine();
                   rewardRetailer.rewardByMonth(rid,month,year,orders);
                   break;
               case "year":
                   System.out.println("Enter the Year(YYYY) for reward analysis :");
                   int year1 = sc.nextInt();
                   sc.nextLine();
                   rewardRetailer.rewardByYear(rid,year1,orders);
                   break;
               default:
                   System.out.println("Select correct option('order' or 'month' or 'year'): ");
                   opt =sc.nextLine();
                   wantToCont = "yes";

           }
           System.out.println("Do you want analyse rewards with other scales (yes/no)?");
           wantToCont =sc.nextLine();
           if(wantToCont.equals("yes")) {
               System.out.println("Specify how you want to track rewards (By 'order' or 'month' or 'year'): ");
               opt = sc.nextLine();
           }
       }while(wantToCont.equals("yes"));
    }

    public int getRetailerPoints(int rid) {
        return Integer.parseInt(retailers.get(String.valueOf(rid)).get("rewardPoints"));
    }

    private int calReward(Map<String, String> odetail) {

        return sir.orderTotal(odetail)[1];
    }

    public void returnOrder(int rid, int oid, Map<String, Integer> rproducts, int days) {
        String rId = String.valueOf(rid);
        String oId = String.valueOf(oid);
        if (retailers.containsKey(rId)) {

            if (orders.containsKey(oId)) {
                if (checkReturnTime(days, oid)) {
                    sir.removePToOrder(rproducts);
                    System.out.println("return successful and products sent to inventory");
                } else {
                    System.out.println("Order passes return period co cannot be returned");
                }

            } else {
                System.out.println("Order id does not exist!");
            }
        } else {
            System.out.println("Retailer id does not exist!");
        }
    }

    public void addPointRetailer(int points, int id) {
        int curPoints = Integer.parseInt(retailers.get(String.valueOf(id)).get("rewardPoints")) + points;
        retailers.get(String.valueOf(id)).put("rewardPoints", String.valueOf(curPoints));
        textEditor = new PoorTextEditor();
        textEditor.setRepositoryStrings(retailers);
        textEditor.writeToTextFile(repoPath + "Retailers.txt");
    }

    public boolean removePointRetailer(int points, int id) {

        int curPoints = Integer.parseInt(retailers.get(String.valueOf(id)).get("rewardPoints"));

        if (curPoints >= points) {

            curPoints = Integer.parseInt(retailers.get(String.valueOf(id)).get("rewardPoints")) - points;
            retailers.get(String.valueOf(id)).put("rewardPoints", String.valueOf(curPoints));
            textEditor = new PoorTextEditor();
            textEditor.setRepositoryStrings(retailers);
            textEditor.writeToTextFile(repoPath + "Retailers.txt");

            return true;
        } else {

            return false;
        }
    }


    public void registerRetailer(String name, String retailerLocation) {

        Retailer rd = new BasicRetailer(name, retailerLocation);
        String size = String.valueOf(retailers.size() + 1);

        retailers.put(size, rd.getRDetails());

        textEditor = new PoorTextEditor();
        textEditor.setRepositoryStrings(retailers);
        textEditor.writeToTextFile(repoPath + "Retailers.txt");
        rd.print();

        System.out.println("Retailer registered with id:" + size);
    }

    public boolean checkReturnTime(int days, int orderId) {
        return days <= 15;
    }


    public void printReceipt(int orderId) {
        Map<String, String> odetails = new HashMap<>(orders.get(String.valueOf(orderId)));
        sir.receiptPrint(odetails);
    }


    public void viewRetailers() {
        print(retailers, "Registered Retailers");
    }


    public String getRetailerById(int rid) {
        return retailers.get(String.valueOf(rid)).get("name");
    }

    public void viewOrders() {
        print(orders, "Orders");
    }

    public void print(Map<String, Map<String, String>> items, String whichItem) {
        System.out.println("-----------------------------------------------------");
        System.out.println(whichItem);
        for (Map.Entry<String, Map<String, String>> itms : items.entrySet()) {
            System.out.println(itms.getKey() + ":");
            for (Map.Entry<String, String> sitm : itms.getValue().entrySet()) {
                System.out.printf("%-30s %s%n", sitm.getKey(), sitm.getValue());
            }
        }
        System.out.println("-----------------------------------------------------");
    }
}
