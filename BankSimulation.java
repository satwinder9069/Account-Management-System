package task5_BankAccSimulation;

import java.util.Scanner;

public class BankSimulation {
    private static final Bank bank = new Bank("State Bank of Mine");

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BankService service = new BankService(bank);

        // Create some sample accounts
        bank.createAccount("Rohan Kumar", 5000.0);
        bank.createAccount("Priya Sharma", 3000.0);
        bank.createAccount("Amit Patel", 10000.0);

        while (true) {
            System.out.println(bank.getBankName());
            System.out.println("=".repeat(40));
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Account Details");
            System.out.println("6. View All Accounts");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice;

            try {
                choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1:
                        service.createNewAccount();
                        break;
                    case 2:
                        service.depositMoney();
                        break;
                    case 3:
                        service.withdrawMoney();
                        break;
                    case 4:
                        service.transferMoney();
                        break;
                    case 5:
                        service.viewAccountDetails();
                        break;
                    case 6:
                        service.viewAllAccounts();
                        break;
                    case 7:
                        System.out.println("Thank you for banking with us!");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                input.nextLine();
            }
        }
    }
}
