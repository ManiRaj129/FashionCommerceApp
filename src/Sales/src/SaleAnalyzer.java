package src.Sales.src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class SaleAnalyzer {

    SalesInventoryRequest sir = new SalesInventoryRequest();

    private int thresholdCount = 10;
    DateManagement dm = new DateManagement();

    public int NumOfProductSold(Map<String, Map<String, String>> orders, String pname) {
        int count = 0;
        for (Map.Entry<String, Map<String, String>> item : orders.entrySet()) {
            Map<String, String> odetail = item.getValue();
            if (odetail.containsKey(pname)) {
                count += Integer.parseInt(odetail.get(pname));
            }
        }
        return count;

    }

    public int NumOfProductInDay(Map<String, Map<String, String>> orders, String pname, String date) {
        int count = 0;
        for (Map.Entry<String, Map<String, String>> item : orders.entrySet()) {
            Map<String, String> odetail = item.getValue();
            if (odetail.get("date").equals(date)) {
                if (odetail.containsKey(pname)) {
                    count += Integer.parseInt(odetail.get(pname));
                }
            }
        }
        return count;
    }

    public int NumOfProductInMonth(Map<String, Map<String, String>> orders, String pname, int month, int year) {
        int count = 0;
        for (Map.Entry<String, Map<String, String>> item : orders.entrySet()) {
            Map<String, String> odetail = item.getValue();
            if (dm.isInMonth(odetail.get("date"), month, year)) {
                if (odetail.containsKey(pname)) {
                    count += Integer.parseInt(odetail.get(pname));
                }
            }
        }
        return count;
    }

    public int NumOfProductInYear(Map<String, Map<String, String>> orders, String pname, int year) {
        int count = 0;

        for (Map.Entry<String, Map<String, String>> item : orders.entrySet()) {
            Map<String, String> odetail = item.getValue();
            if (dm.isInYear(odetail.get("date"), year)) {
                if (odetail.containsKey(pname)) {
                    count += Integer.parseInt(odetail.get(pname));
                }
            }
        }
        return count;
    }

    public void MonthSaleReport(Map<String, Map<String, String>> orders, int month, int year) {

        Map<String, Map<String, String>> regProds = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : sir.regProd().entrySet()) {
            regProds.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }

        int prevCount;
        int totalCount;
        for (Map.Entry<String, Map<String, String>> item : orders.entrySet()) {
            Map<String, String> odetail = item.getValue();
            if (dm.isInMonth(odetail.get("date"), month, year)) {

                for (Map.Entry<String, String> pdetail : odetail.entrySet()) {
                    String pname = pdetail.getKey();
                    if (regProds.containsKey(pname)) {
                        if(regProds.get(pname).containsKey("count")){
                            prevCount =Integer.parseInt(regProds.get(pname).get("count"));
                            totalCount= prevCount+Integer.parseInt(pdetail.getValue());
                            regProds.get(pname).put("count",String.valueOf(totalCount));
                        }else {
                            regProds.get(pname).put("count", pdetail.getValue());
                        }
                    }

                }

            }
        }
        printSaleReport(regProds, "Sale Report for " + month + " month " + year + " year");
    }

    public void DaySaleReport(Map<String, Map<String, String>> orders, String date) {

        Map<String, Map<String, String>> regProds = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : sir.regProd().entrySet()) {
            regProds.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        int prevCount;
        int totalCount;
        for (Map.Entry<String, Map<String, String>> item : orders.entrySet()) {
            Map<String, String> odetail = item.getValue();
            if (odetail.get("date").equals(date)) {

                for (Map.Entry<String, String> pdetail : odetail.entrySet()) {
                    String pname = pdetail.getKey();

                    if (regProds.containsKey(pname)) {
                        if(regProds.get(pname).containsKey("count")){
                            prevCount =Integer.parseInt(regProds.get(pname).get("count"));
                            totalCount= prevCount+Integer.parseInt(pdetail.getValue());
                            regProds.get(pname).put("count",String.valueOf(totalCount));
                        }else {
                            regProds.get(pname).put("count", pdetail.getValue());
                        }
                    }
                }

            }
        }
        printSaleReport(regProds, "Sale Report for sales on " + date);
    }

    public void YearSaleReport(Map<String, Map<String, String>> orders, int year) {

        Map<String, Map<String, String>> regProds = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : sir.regProd().entrySet()) {
            regProds.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        int prevCount;
        int totalCount;
        for (Map.Entry<String, Map<String, String>> item : orders.entrySet()) {
            Map<String, String> odetail = item.getValue();
            if (dm.isInYear(odetail.get("date"), year)) {

                for (Map.Entry<String, String> pdetail : odetail.entrySet()) {
                    String pname = pdetail.getKey();
                    if (regProds.containsKey(pname)) {
                        if(regProds.get(pname).containsKey("count")){
                            prevCount =Integer.parseInt(regProds.get(pname).get("count"));
                            totalCount= prevCount+Integer.parseInt(pdetail.getValue());
                            regProds.get(pname).put("count",String.valueOf(totalCount));
                        }else {
                            regProds.get(pname).put("count", pdetail.getValue());
                        }
                    }

                }

            }
        }

        printSaleReport(regProds, "Sale Report for " + year + " year");
    }

    public void printSaleReport(Map<String, Map<String, String>> regProds, String title) {
        String maxSoldname = "";
        String minSoldname = "";
        int maxSoldCount = Integer.MIN_VALUE;
        int minSoldCount = Integer.MAX_VALUE;
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%40s %n", title);
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-30s%n", "product name", "quantity sold");
        for (Map.Entry<String, Map<String, String>> pdeatil : regProds.entrySet()) {
            String pname = pdeatil.getKey();
            String count = pdeatil.getValue().get("count");
            if(count == null || count.isEmpty()){
                count = "0";
            }
            if (Integer.parseInt(count) > maxSoldCount) {
                maxSoldname = pname;
                maxSoldCount = Integer.parseInt(count);
            }
            if (Integer.parseInt(count) < minSoldCount) {
                minSoldname = pname;
                minSoldCount = Integer.parseInt(count);
            }
            System.out.printf("%-30s %-30s%n", pname, count);
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%40s %n", "Report Analysis");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s -----> %-10s%n", "Name of one of the Highest Demand Product : " + maxSoldname, "quantity sold : " + maxSoldCount);
        System.out.printf("%-30s -----> %-10s%n", "Name of one of the Lowest Demand Product : " + minSoldname, "quantity sold : " + minSoldCount);
        System.out.println("----------------------------------------------------------------------------------------------------------");

        saveSaleReportToFile(regProds, title);
    }


    public void saveSaleReportToFile(Map<String, Map<String, String>> regProds, String title) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Sales/repository/SaleReports/" + toCamelCase(title) + ".txt"))) {
            String maxSoldName = "";
            String minSoldName = "";
            int maxSoldCount = Integer.MIN_VALUE;
            int minSoldCount = Integer.MAX_VALUE;

            writer.write("----------------------------------------------------------------------------------------------------------\n");
            writer.write(String.format("%40s %n", title));
            writer.write("----------------------------------------------------------------------------------------------------------\n");
            writer.write(String.format("%-30s %-30s%n", "product name", "quantity sold"));

            for (Map.Entry<String, Map<String, String>> pDetail : regProds.entrySet()) {
                String pName = pDetail.getKey();
                String count = pDetail.getValue().get("count");
                if(count == null || count.isEmpty()){
                    count = "0";
                }
                if (Integer.parseInt(count) > maxSoldCount) {
                    maxSoldName = pName;
                    maxSoldCount = Integer.parseInt(count);
                }
                if (Integer.parseInt(count) < minSoldCount) {
                    minSoldName = pName;
                    minSoldCount = Integer.parseInt(count);
                }
                writer.write(String.format("%-30s %-30s%n", pName, count));
            }

            writer.write("----------------------------------------------------------------------------------------------------------\n");
            writer.write(String.format("%40s %n", "Report Analysis"));
            writer.write("----------------------------------------------------------------------------------------------------------\n");
            writer.write(String.format("%-30s -----> %-10s%n", "Name of one of the Highest Demand Product : " + maxSoldName, "quantity sold : " + maxSoldCount));
            writer.write(String.format("%-30s -----> %-10s%n", "Name of one of the Lowest Demand Product : " + minSoldName, "quantity sold : " + minSoldCount));
            writer.write("----------------------------------------------------------------------------------------------------------\n");

        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }


    public void priceManipulate(Map<String, Map<String, String>> orders, String pname) {
        System.out.println(" The following recommendation considered entire sales history" +
                "and default threshold count (10) to make decision : ");
        if (NumOfProductSold(orders, pname) >= thresholdCount) {
            System.out.println("Recommended to increase the price");

        } else {
            System.out.println("Recommended to increase the price");
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println("Note : Threshold Count is the minimum count of product required to be sold to rise the price " +
                "or if does not reach the count drop the price. ");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        System.out.println("Do you want recommendation on customised sales range and threshold count? (yes/no)");
        Scanner sc = new Scanner(System.in);
        String wantToContinue = sc.nextLine();

        while (wantToContinue.equals("yes")) {
            System.out.println("Specify the range of sales to be considered (day or month or year) ");
            String range = sc.nextLine();
            System.out.println("Specify the Threshold count for making recommendation :");
            thresholdCount = sc.nextInt();
            sc.nextLine();

            switch (range) {

                case "day":
                    System.out.println("Enter the date(DD-MM-YYYY) for sale analysis :");
                    String date = sc.nextLine();
                    System.out.println(" The following recommendation considered sales on" + date +
                            "and used threshold count (" + thresholdCount + ") to make decision : ");
                    if (NumOfProductInDay(orders, pname, date) >= thresholdCount) {
                        System.out.println("Recommended to increase the price");

                    } else {
                        System.out.println("Recommended to increase the price");
                    }
                    break;
                case "month":
                    System.out.println("Enter the Month(MM) for sale analysis :");
                    int month = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the Year(YYYY) for sale analysis :");
                    int year = sc.nextInt();
                    sc.nextLine();
                    System.out.println(" The following recommendation considered sales during" + month + "/" + year +
                            "and used threshold count (" + thresholdCount + ") to make decision : ");
                    if (NumOfProductInMonth(orders, pname, month, year) >= thresholdCount) {
                        System.out.println("Recommended to increase the price");

                    } else {
                        System.out.println("Recommended to increase the price");
                    }
                    break;
                case "year":
                    System.out.println("Enter the Year(YYYY) for sale analysis :");
                    int year1 = sc.nextInt();
                    sc.nextLine();
                    System.out.println(" The following recommendation considered sales during" + year1 +
                            "and used threshold count (" + thresholdCount + ") to make decision : ");
                    if (NumOfProductInYear(orders, pname, year1) >= thresholdCount) {
                        System.out.println("Recommended to increase the price");

                    } else {
                        System.out.println("Recommended to increase the price");
                    }

            }
            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.println("Note : Threshold Count is the minimum count of product required to be sold to rise the price " +
                    "or if does not reach the count drop the price. ");
            System.out.println("----------------------------------------------------------------------------------------------------------");

            System.out.println("Do you want recommendation on other customised sales range and threshold count? (yes/no)");
            wantToContinue = sc.nextLine();
        }

    }

    public static String toCamelCase(String input) {
        // Split the input string into words based on spaces
        String[] words = input.split("\\s+");

        // Initialize a StringBuilder for the result
        StringBuilder camelCase = new StringBuilder();

        // Process each word
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase(); // Convert to lowercase
            if (i == 0) {
                // First word should start with a lowercase letter
                camelCase.append(word);
            } else {
                // Capitalize the first letter of subsequent words
                camelCase.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            }
        }

        return camelCase.toString();
    }


}
