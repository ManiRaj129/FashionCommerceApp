package src.Inventory;

import src.Inventory.src.BasicInventoryController;
import src.Inventory.src.interfaces.InventoryController;

public class InventoryDepartment {

//     public static void main(String args[])
//     {
//         InventoryController c=new BasicInventoryController();
//         c.run();
//     }
    public void start() {

        InventoryController c=new BasicInventoryController();
        c.run();
    }
}
