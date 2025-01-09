package src.HR.src;

import java.util.Scanner;

public class valueHandling {
    public String inputValidator(boolean validate) {
        String input;
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();

        if(input.equals(" ") || input.isEmpty() || input.equals("\n")) {
            System.out.println("Incorrect input, please try again: ");
            input = scanner.nextLine();
        }
        if(validate) {
            System.out.println("Is " + input + " correct? (y/n): ");
            String answer = scanner.nextLine();
            if(answer.equals("n") || answer.equals("N")) {
                System.out.println("Please re-enter information: ");
                input = scanner.nextLine();
            }
        }
        return input;
    }
}
