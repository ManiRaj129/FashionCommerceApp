package src.Sales.src;

import java.util.Map;

public class RewardRetailer {

    SalesInventoryRequest sir = new SalesInventoryRequest();
    BasicSalesManage bsm = new BasicSalesManage();
    DateManagement dm = new DateManagement();

    public void rewardByOrderId(int rid, int oid, Map<String, Map<String, String>> orders) {

        String rname = bsm.getRetailerById(rid);
        Map<String, String> odetail = orders.get(String.valueOf(oid));

        int[] rewardDetail = sir.orderTotal(odetail);

        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%40s %n", "Reward details of " + rname + " associated with " + " order id " + oid);
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-30s %-30s %s%n", "Order ID", "Order Total", "Points Earned", "Points Used");
        System.out.printf("%-30s %-30s %-30s %s%n", oid, rewardDetail[0], rewardDetail[1], rewardDetail[2]);
        System.out.println("----------------------------------------------------------------------------------------------------------");

    }

    public void rewardByMonth(int rid, int month, int year, Map<String, Map<String, String>> orders) {

        int orderCount=0;
        String rname = bsm.getRetailerById(rid);
        int[] rewardDetail;
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%40s %n", "Reward details of " + rname + " in " +month +" month "+ year + " year");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-30s %-30s %s%n", "Order ID", "Order Total", "Points Earned", "Points Used");
        for (Map.Entry<String, Map<String, String>> order : orders.entrySet()) {
            String oid = order.getKey();
            Map<String, String> odetail = order.getValue();
            int ridInOrder = Integer.parseInt(odetail.get("retailerId"));

            if (rid == ridInOrder) {
                if (dm.isInMonth(odetail.get("date"), month, year)) {
                     ++orderCount;
                    rewardDetail = sir.orderTotal(odetail);

                    System.out.printf("%-30s %-30s %-30s %s%n", oid, rewardDetail[0], rewardDetail[1], rewardDetail[2]);
                }
            }
        }
        if(orderCount == 0)
        {
            System.out.println(rname+" does not placed any orders in " +month +" month "+ year + " year");
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");


    }

    public void rewardByYear(int rid, int year, Map<String, Map<String, String>> orders) {
        int orderCount=0;
        String rname = bsm.getRetailerById(rid);
        int[] rewardDetail;
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%40s %n", "Reward details of " + rname + " in " + year + " year");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-30s %-30s %s%n", "Order ID", "Order Total", "Points Earned", "Points Used");
        for (Map.Entry<String, Map<String, String>> order : orders.entrySet()) {
            String oid = order.getKey();
            Map<String, String> odetail = order.getValue();
            int ridInOrder = Integer.parseInt(odetail.get("retailerId"));

            if (rid == ridInOrder) {
                if (dm.isInYear(odetail.get("date"), year)) {
                    ++orderCount;
                    rewardDetail = sir.orderTotal(odetail);

                    System.out.printf("%-30s %-30s %-30s %s%n", oid, rewardDetail[0], rewardDetail[1], rewardDetail[2]);
                }
            }
        }
        if(orderCount == 0)
        {
            System.out.println(rname+" does not placed any orders in " + year + " year");
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");

    }

}
