package task5_BankAccSimulation;

import java.util.*;

public class Bank {
    private final String bankName;
    private final List<Account> accounts;

    private static final Random random = new Random();  // For generating random digits
    private static final String PREFIX = "1234";  // Bank/branch prefix (customize as needed)
    private static final int TOTAL_LENGTH = 12;   // Total account number length

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new ArrayList<>();
    }

    // Create Account
    public Account createAccount(String accountHolder, double initialDeposit) {
        String accountNumber = generateAccountNumber();

        Account newAccount = new Account(accountNumber, accountHolder, initialDeposit);
        accounts.add(newAccount);

        return newAccount;

    }

    //Find by account number
    public Account findAccount(String accountNumber) {
        for (Account acc: accounts) {
            if(acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        throw new IllegalArgumentException("Account not found: " + accountNumber);
    }
    // Deposit money to account
    public void deposit(String accountNumber, double amount, String description) {
        Account account = findAccount(accountNumber);
        account.deposit(amount, description);
        System.out.printf("Deposited ₹%.2f to account %s%n", amount, accountNumber);
    }

    // Withdraw money from account
    public void withdraw(String accountNumber, double amount,  String description) {
        Account account = findAccount(accountNumber);
        account.withdraw(amount, description);
        System.out.printf("Withdrew ₹%.2f from account %s%n", amount, accountNumber);
    }

    // transaction
    public void transfer(String fromAccount, String toAccount, double amount, String description) {
        Account source = findAccount(fromAccount);
        Account destination = findAccount(toAccount);

        source.withdraw(amount, "Transfer to " + toAccount + ": " + description);
        destination.deposit(amount, "Transferred from " + fromAccount + description);

        System.out.printf("Transferred ₹%.2f from account %s to %s%n", amount, fromAccount, toAccount);
    }

    public String generateAccountNumber() {
        Set<String> existingNumbers = new HashSet<>(); // temp: quick lookup of existing account numbers

        for(Account acc: accounts) {
            existingNumbers.add(acc.getAccountNumber());
        }

        String accountNumber;
        do {
            StringBuilder sb = new StringBuilder(PREFIX);
            int remainingLength = TOTAL_LENGTH - PREFIX.length();
            for(int i = 0 ; i < remainingLength ; i++) {
                sb.append(random.nextInt(10));
            }

            accountNumber = sb.toString();
        } while (existingNumbers.contains(accountNumber));

        return accountNumber;
    }

    // Get account details with transaction history
    public void getAccountDetails(String accountNumber) {
        Account account = findAccount(accountNumber);
        System.out.println("\n Account Details");
        System.out.println("-".repeat(60));
        System.out.println(account);
        account.printTransactionHistory();
    }
    // Get all accounts summary
    public void getAllAccountsSummary() {
        System.out.println("\n" + bankName + "- ACCOUNT SUMMARY");
        System.out.println("-".repeat(65));
        System.out.printf("%-15s %-20s %-15s %s%n",
                "Account", "Holder", "Balance", "Transactions");
        System.out.println("-".repeat(65));
        for(Account acc: accounts) {
            System.out.printf("%-15s %-20s ₹%-14.2f %d%n", acc.getAccountNumber(),
                    acc.getAccountHolder(),
                    acc.getBalance(),
                    acc.getTransactions().size());
        }
        System.out.println("-".repeat(65));
    }

    //Getters
    public String getBankName() {
        return bankName;
    }
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    //for testing
    public void printAllAccounts() {
        System.out.println("Bank: " + bankName + " - Accounts:");
        for (Account acc : accounts) {
            System.out.println("Account: " + acc.getAccountNumber() +
                    ", Holder: " + acc.getAccountHolder() +
                    ", Balance: ₹" + acc.getBalance());
        }
    }
}
