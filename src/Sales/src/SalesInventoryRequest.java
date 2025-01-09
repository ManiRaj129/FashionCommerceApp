package src.Sales.src;

import src.Inventory.src.BasicStorageManage;

import java.util.HashMap;

import java.util.Map;

public class SalesInventoryRequest {

    Map<String, Map<String, String>> products;
    private Map<String, Map<String, String>> availableProducts;
    BasicStorageManage sm;

    public SalesInventoryRequest() {
        sm = new BasicStorageManage();
        products = sm.getProducts();
        availableProducts = sm.getAvailableProducts();
    }

    public void removePQ(String pname, int quantityRemoved) {
        sm.removeProductCount(pname, quantityRemoved);
    }

    public void addPQ(String pname, int quantityRemoved) {
        sm.addProductCount(pname, quantityRemoved);
    }

    public void changePrice(int price, String pname) {
        sm.changePrice(price, pname);
    }

    public int getPrice(String pname) {
        return Integer.parseInt(availableProducts.get(pname).get("price"));
    }

    public boolean checkAvailability(Map<String, Integer> orderProducts) {

        for (Map.Entry<String, Integer> op : orderProducts.entrySet()) {
            String pname = op.getKey();
            if (availableProducts.containsKey(pname)) {
                int avaCount = Integer.parseInt(availableProducts.get(op.getKey()).get("count"));
                if (avaCount < op.getValue()) {
                    System.out.println("Insufficient quantity, available quantity is " + avaCount + " for " + pname);
                }
            } else {
                System.out.println(pname + " not available currently");
            }
        }
        return true;
    }


    public Map<String, String> addPToOrder(Map<String, Integer> products) {
        Map<String, String> cOrder = new HashMap<>();

        if (checkAvailability(products)) {
            String pname;
            int quan;
            for (Map.Entry<String, Integer> p : products.entrySet()) {
                pname = p.getKey();
                quan = p.getValue();
                cOrder.put(pname, String.valueOf(quan));
                removePQ(pname, quan);
            }
        }
        return cOrder;
    }

    public void removePToOrder(Map<String, Integer> products) {
        String pname;
        int quan;
        for (Map.Entry<String, Integer> p : products.entrySet()) {
            pname = p.getKey();
            quan = p.getValue();
            addPQ(pname, quan);

        }
    }

    public void receiptPrint(Map<String, String> odetails) {
        String rid = odetails.remove("retailerId");
        String date=odetails.remove("date");
        String appliedRewardPoints= odetails.remove("appliedRewardPoints");

        int total = 0;

        String pname;
        String quan;
        int price;
        int ptotal;
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%60s %n", "Receipt");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println("Retailer id :" + rid);
        System.out.println("Order date :" + date);
        System.out.printf("%-30s %-30s %-30s %s%n", "product name", "quantity", "per quantity", "total");
        for (Map.Entry<String, String> itms : odetails.entrySet()) {
            pname = itms.getKey();
            quan = itms.getValue();
            price = getPrice(pname);
            ptotal = price * Integer.parseInt(quan);
            total += ptotal;
            System.out.printf("%-30s %-30s %-30d %d%n", pname, quan, price, ptotal);
        }

        System.out.println("Actual Total Amount :" + total);
        System.out.println("Applied Reward points :" + appliedRewardPoints);
        System.out.println("Paid Amount :" + (total- Integer.parseInt(appliedRewardPoints)));

        System.out.println("----------------------------------------------------------------------------------------------------------");
    }

    public int[] orderTotal(Map<String, String> orderDetails)
    {
        Map<String, String> odetails = new HashMap<>(orderDetails);

        String rid = odetails.remove("retailerId");
        String date=odetails.remove("date");
        String appliedRewardPoints= odetails.remove("appliedRewardPoints");

        int total = 0;
        String pname;
        String quan;
        int price;
        int ptotal;
        for (Map.Entry<String, String> itms : odetails.entrySet()) {
            pname = itms.getKey();
            quan = itms.getValue();
            price = getPrice(pname);
            ptotal = price * Integer.parseInt(quan);
            total += ptotal;
        }

        int[] rewardDetail =new int[3];
        // order total
        rewardDetail[0] = total;
        // reward point earned with the order
        rewardDetail[1] = total/20;
        // reward point applied for the order
        rewardDetail[2] = Integer.parseInt(appliedRewardPoints);

        return rewardDetail;
    }

    public void viewAvaProducts() {

        print(availableProducts, "Available products at Storage");
    }

    public void viewProducts() {
        print(products, "registered products at Storage");
    }

    public Map<String, Map<String, String>> regProd()
    {
        return products;
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
