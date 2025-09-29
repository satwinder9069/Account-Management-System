package task5_BankAccSimulation;

import java.util.Scanner;

public class BankService {
    private Bank bank;

    private static Scanner input = new Scanner(System.in);
    public BankService(Bank bank) {
        this.bank = bank;
    }

    // 1st method from menu :)
    public void createNewAccount() {
        System.out.print("Enter account holder name: ");
        String name = input.nextLine();
        System.out.print("Enter initial deposit amount: ₹");
        double deposit = input.nextDouble();
        input.nextLine();

        Account account = bank.createAccount(name, deposit);
        System.out.println("Account created successfully!");
        System.out.println("Account number : " + account.getAccountNumber());
        System.out.println("Current balance: ₹" + account.getBalance());
    }

    public void depositMoney() {
        System.out.print("Enter account number: ");
        String accountNumber = input.nextLine();
        System.out.print("Enter amount to deposit: ₹");
        double amount = input.nextDouble();
        input.nextLine();

        System.out.print("Enter description: ");
        String desc = input.nextLine();

        bank.deposit(accountNumber, amount,desc);
    }

    public void withdrawMoney() {
        System.out.print("Enter account number: ");
        String accountNumber = input.nextLine();
        System.out.print("Enter amount to withdraw: ₹");
        double amount = input.nextDouble();
        input.nextLine();

        System.out.println("Enter description: ");
        String desc = input.nextLine();

        bank.withdraw(accountNumber,amount, desc);
    }

    public void transferMoney() {
        System.out.print("Enter source account number: ");
        String fromAcc = input.nextLine();
        System.out.print("Enter destination account number: ");
        String toAcc = input.next();
        System.out.print("Enter transfer amount: ₹");
        double amount = input.nextDouble();
        input.nextLine();
        System.out.print("Enter description: ");
        String desc = input.nextLine();

        bank.transfer(fromAcc, toAcc, amount, desc);
    }

    public void viewAccountDetails() {
        System.out.print("Enter account number: ");
        String accountNumber = input.nextLine();

        bank.getAccountDetails(accountNumber);
    }

    public void viewAllAccounts() {
        bank.getAllAccountsSummary();
    }
}
