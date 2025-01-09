package src.Sales.src;

import java.util.Scanner;

public class DateManagement {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the first date
        System.out.println("Enter the first date in the format DD-MM-YYYY:");
        String date1 = scanner.nextLine();

        // Input the second date
        System.out.println("Enter the second date in the format DD-MM-YYYY:");
        String date2 = scanner.nextLine();

        int daysGap = daysGap(date1, date2);

        System.out.println(daysGap);
    }

    /**
     * Function to calculate the number of days in a given month
     */
    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid month");
        }
    }

    /**
     * Function to check if a year is a leap year
     */
    public static boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            }
            return true;
        }
        return false;
    }


    public boolean isInYear(String date, int year)
    {
        String[] dateParts = date.split("-");
        int actualYear= Integer.parseInt(dateParts[2]);

        return actualYear == year;
    }


    public boolean isInMonth(String date, int month, int year)
    {
        String[] dateParts = date.split("-");
        int actualMonth= Integer.parseInt(dateParts[1]);
        int actualYear = Integer.parseInt(dateParts[2]);

        return (actualMonth == month && actualYear == year);
    }



    /**
     * Function to calculate the difference in days between two dates
     */
    public static int daysGap(String date1, String date2) {
        String[] firstDateParts = date1.split("-");
        int day1 = Integer.parseInt(firstDateParts[0]);
        int month1 = Integer.parseInt(firstDateParts[1]);
        int year1 = Integer.parseInt(firstDateParts[2]);

        String[] secondDateParts = date2.split("-");
        int day2 = Integer.parseInt(secondDateParts[0]);
        int month2 = Integer.parseInt(secondDateParts[1]);
        int year2 = Integer.parseInt(secondDateParts[2]);

        int totalDays = 0;

        //handle months counted twice and unnecessary months
        if (year1 == year2) {
            for(int month= month1; month < month2; month++) {
                totalDays += getDaysInMonth(month, year1);
            }
            // Subtract days already passed in the first month, exclude the current day
            totalDays -= (day1-1);
            // Add days passed in the last month
            totalDays += day2;
        }else{
            // Add days for the full years in between
            for (int year = year1+1; year < year2; year++) {
                totalDays += isLeapYear(year) ? 366 : 365;
            }

            // Add days for months in the first year
            for (int month = month1; month <= 12; month++) {
                totalDays += getDaysInMonth(month, year1);
            }

            // Subtract days already passed in the first month, exclude the current day
            totalDays -= (day1-1);

            // Add days for months in the second year
            for (int month = 1; month < month2; month++) {
                totalDays += getDaysInMonth(month, year2);
            }

            // Add days passed in the last month
            totalDays += day2;
        }

        return totalDays;
    }

    /**
     * Function to check if the first date is earlier than the second date
     */
    public static boolean isEarlier(int day1, int month1, int year1, int day2, int month2, int year2) {

        if (year1 < year2) return true;
        if (year1 > year2) {
            System.out.println("Enter correct dates the second date must be after the first date");
            return false;
        }
        if (month1 < month2) return true;
        if (month1 > month2) {
            System.out.println("Enter correct dates the second date must be after the first date");
            return false;
        }
        if (day1 < day2) {
            return true;
        }
        if (day1 > day2) {
            System.out.println("Enter correct dates the second date must be after the first date");
            return false;
        }

        // when the dates are same
        return true;
    }
}

