package src.Sales.src.interfaces;

import java.util.Map;

/**
 * @author Mani Raj
 */
public interface SalesManagement {

    public void addOrder(int rid, String date, Map<String, Integer> products);
    public void registerRetailer(String name, String retailerLocation);
    public void returnOrder(int rid, int oid, Map<String, Integer> rproducts, int days);
    public void printReceipt(int orderId);
    public void viewRetailers();
    public void viewOrders();
    public void NumOfProductSold(String pname);
    public void NumOfProductInDay(String pname, String date);
    public void NumOfProductInMonth(String pname, int month, int year);
    public void NumOfProductInYear(String pname, int year);
    public void MonthSaleReport(int month, int year);
    public void DaySaleReport(String date);
    public void YearSaleReport(int year);
    public void priceManipulate(String pname);
    public void trackRetailerReward(int rid);
    public int getRetailerPoints(int rid);
}
