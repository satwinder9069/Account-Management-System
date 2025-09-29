package task5_BankAccSimulation;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String accountNumber;
    private final String accountHolder;
    private double balance;
    private final List<Transaction> transactions;
    private double overdraftLimit;

    public Account(String accountNumber, String accountHolder , double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
        this.transactions = new ArrayList<>();
        this.overdraftLimit = 100.0;

        // Record initial deposit as first transaction
        if (initialDeposit > 0) {
            transactions.add(new Transaction(
                    Transaction.Type.DEPOSIT,
                    initialDeposit,
                    balance,
                    "Initial deposit"
            ));
        }
    }

    //Deposit money
    public void deposit(double amount, String description) {
        if(amount < 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        balance += amount;
        transactions.add(new Transaction(
                Transaction.Type.DEPOSIT,
                amount,
                balance,
                description
        ));
    }

    //Withdraw money
    public void withdraw(double amount, String description) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount should not be negative or zero");
        }

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        balance -= amount;
        transactions.add(new Transaction(
                Transaction.Type.WITHDRAWAL,
                amount,
                balance,
                description
        ));

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    // Set overdraft limit
    public void setOverdraftLimit(double limit) {
        if(limit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative");
        }

        this.overdraftLimit = limit;
    }

    public void printTransactionHistory() {
        System.out.println("\n Transaction History For " + accountNumber);
        System.out.println("-".repeat(100));
        System.out.printf("%-15s %-20s %-15s %s%n", "Date/Time ", "Type", "Amount", "Balance After", "Description");
        System.out.println("-".repeat(100));

        for(Transaction t : transactions) {
            System.out.printf("%-20s %-10s ₹%-9.2f ₹%-14.2f %s%n", t.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    t.getType(),
                    t.getAmount(),
                    t.getBalanceAfter(),
                    t.getDescription());
        }
        System.out.println("-".repeat(100));

    }

    @Override
    public String toString() {
        return String.format("Account [ " + accountNumber + " ]" + "Balance: " + balance);
    }

}
