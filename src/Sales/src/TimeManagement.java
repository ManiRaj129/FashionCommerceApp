package src.Sales.src;

import java.util.Scanner;

public class TimeManagement {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter the first time in the format HH:MM:SS:");


        System.out.println("Enter the second time in the format HH:MM:SS:");

    }

    // Function to calculate the difference between two timestamps
    public static int[] calculateTimeDifference(String startTime, String endTime) {

        String[] firstTimeParts = startTime.split(":");
        int hour1 = Integer.parseInt(firstTimeParts[0]);
        int minute1 = Integer.parseInt(firstTimeParts[1]);
        int second1 = Integer.parseInt(firstTimeParts[2]);

        String[] secondTimeParts = endTime.split(":");
        int hour2 = Integer.parseInt(secondTimeParts[0]);
        int minute2 = Integer.parseInt(secondTimeParts[1]);
        int second2 = Integer.parseInt(secondTimeParts[2]);

        // Convert both timestamps to total seconds
        int totalSeconds1 = hour1 * 3600 + minute1 * 60 + second1;
        int totalSeconds2 = hour2 * 3600 + minute2 * 60 + second2;

        int differenceInSeconds = totalSeconds2 - totalSeconds1;

        // Convert the difference back to hours, minutes, and seconds
        int hours = differenceInSeconds / 3600;
        differenceInSeconds %= 3600;
        int minutes = differenceInSeconds / 60;
        int seconds = differenceInSeconds % 60;

        return new int[] { hours, minutes, seconds };
    }
}

