package src.Sales;

import src.Inventory.src.BasicInventoryController;
import src.Inventory.src.interfaces.InventoryController;
import src.Sales.src.BasicSalesController;
import src.Sales.src.interfaces.SalesController;

public class SalesDepartment {

         public static void main(String args[])
     {
          SalesController c=new BasicSalesController();
         c.run();
     }


    public static void start() {

        SalesController c=new BasicSalesController();
        c.run();
    }
}
