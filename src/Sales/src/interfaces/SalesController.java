package src.Sales.src.interfaces;

import java.util.Map;

public interface SalesController {

    public void run();
    public void registerRetailer(String name, String location);
    public void addOrder(int rid,String date, Map<String, Integer> products);
    public void printReceipt(int orderId);
    public void returnOrder(int rid, int oid, Map<String, Integer> rproducts, int days);
    public void viewRetailers();
    public void viewOrders();
    public void viewAvaProducts();
    public void viewRegisteredProducts();
    public void changePrice(int price, String pname);
    public void NumOfProductSold(String pname);
    public void NumOfProductInDay(String pname, String date);
    public void NumOfProductInMonth(String pname, int month, int year);
    public void NumOfProductInYear(String pname, int year);
    public void MonthSaleReport(int month, int year);
    public void DaySaleReport(String date);
    public void YearSaleReport(int year);
    public void priceManipulate(String pname);
    public void trackRetailerReward(int rid);
    public void getRetailerPoints(int rid);
}
