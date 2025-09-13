import java.util.Scanner;

public class localatm {
    
    public static void main(String[] args) {
        
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Welcome to your Local ATM!");
        System.out.println("Options:");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw Cash");
        System.out.println("3. Change PIN");

        int select = inputScanner.nextInt();

        switch (select) {
            case 1 -> {
                int balance = 5000;
                System.out.println("Your available balance as of now is: $" + balance);
            }
            case 2 -> {
                System.out.println("What is your desired amount?:");
                int amount = inputScanner.nextInt();

                if (amount < 100) {
                    System.out.println("Sorry, we cannot dispense the requested amount");
                } else {

                    int thousandCount = 0;
                    int fiveHundredCount = 0;
                    int hundredCount = 0;

                    while (amount >= 100) {
                        if (amount >= 1000) {
                            thousandCount++;
                            amount -= 1000;
                        } else if (amount >= 500) {
                            fiveHundredCount++;
                            amount -= 500;
                        } else {
                            hundredCount++;
                            amount -= 100;
                        }

                    }
                    System.out.println("Here are the bills:");
                    System.out.println("Thousand Bills:" + thousandCount);
                    System.out.println("Five Hundred Bills:" + fiveHundredCount);
                    System.out.println("Hundred Bills:" + hundredCount);
                    int total = (thousandCount * 1000) + (fiveHundredCount * 500) + (hundredCount * 100);
                    System.out.println("Total Dispensed:" + total);
                }
            }
            case 3 -> {
                inputScanner.nextLine();
                System.out.println("Enter old PIN:");
                String oldPin = inputScanner.nextLine();
                String newPin, confirmPin;
                do {
                    System.out.println("Enter new PIN:");
                    newPin = inputScanner.nextLine();
                    System.out.println("Confirm PIN:");
                    confirmPin = inputScanner.nextLine();
                    if (!newPin.equals(confirmPin)) {
                        System.out.println("PIN does not match. Please try again!");
                    }
                } while (!newPin.equals(confirmPin));
                System.out.println("You have successfully changed your PIN!");
            }
            default -> System.out.println("Sorry, we don't have an option for that.");
        }
        inputScanner.close();
    }
}
