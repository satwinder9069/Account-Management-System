# 🏦 Bank Account Management System - Java OOP Implementation
## Task 5 : Bank Account Simulation
A clean, console-based banking system that demonstrates solid OOP design with an immutable Transaction model, a clear service layer, and a simple CLI. It supports account creation, deposits, withdrawals, transfers, account summaries, and printable statements.

---
## ⚡Features
- 💳 Account Management: Create new accounts with unique 12-digit account numbers.
- 💰 Deposit Operations: Secure money deposits with real-time transaction logging.
- 💸 Withdrawal Operations: Controlled withdrawals with balance validation and overdraft protection.
- 🔄 Money Transfers: Inter-account transfers with complete audit trails and validation.
- 📊 Transaction History: Detailed transaction records with timestamps and descriptions.
- 🔍 Account Lookup: Find and view account details instantly with comprehensive statements.
- 📋 Account Summaries: Overview of all accounts with transaction counts and balances.
- 🛡️ Data Integrity: Immutable transaction records and comprehensive input validation.
- 🎯 User Experience: Intuitive console-based interface with clear error messages.

---
## 📁 Project Structure
task5_BankAccSimultation/

  ├── Account.java          # Core account class with transaction management
  
  ├── Transaction.java     # Immutable transaction records
  
  ├── Bank.java            # Bank management system with account operations
  
  └── BankSimulation.java  # Main CLI loop
  
---

## 🧱 Architecture and Flow
- **BankSimulation** (CLI) → Reads input, Shows menu
- **BankService** (use-cases) → The "manager" that decides what to do. It tells the Bank to perform operations.
- **Bank** (accounts registry) → Stores all accounts, creates account numbers, and does banking operations.
- **Account** (domain model) → Represents a single bank account. It changes the balance and keeps a history of transactions.
- **Transaction** (value object) → A record of one operation (like deposit/withdraw). It cannot be changed after creation (immutable).

```
flowchart LR
  UI[BankSimulation CLI] --> S[BankService]
  S --> B[Bank]
  B -->|find/create| A[Account]
  A -->|append| T[Transaction]
```

---
## 🧩 Class Responsibilities
- Transaction
    - Immutable record: Type (DEPOSIT/WITHDRAWAL), amount, timestamp, balanceAfter, description.
    - All fields final, no setters. Friendly toString for statements.

- Account
    - Fields: accountNumber, accountHolder, balance, List<Transaction> history, overdraftLimit (default 100.0; not yet used in checks).
    - Methods: deposit(amount, desc), withdraw(amount, desc), getBalance(), getTransactions() [defensive copy], printTransactionHistory().
    - Validations: amount must be positive; withdrawals check sufficient balance.

- Bank
    - Stores accounts (List<Account>).
    - Generates unique account numbers: PREFIX="1234", TOTAL_LENGTH=12.
    - Operations: createAccount, findAccount, deposit, withdraw, transfer, getAccountDetails, getAllAccountsSummary.
      
- BankService
    - CLI-facing orchestration. Reads input, calls Bank methods.
    - Methods: createNewAccount, depositMoney, withdrawMoney, transferMoney, viewAccountDetails, viewAllAccounts.

- BankSimulation
    - Main loop and menu. Instantiates Bank and BankService, seeds a few sample accounts, handles exceptions, and keeps the app running.

---
## 🔄 Program Flow (per menu option)
**1. Create New Account**
    -> BankService.createNewAccount()
    -> Bank.createAccount(holder, initialDeposit)
    -> Account is created; initial deposit recorded as first Transaction.

**2. Deposit Money**

    -> BankService.depositMoney()
    -> Bank.deposit(accountNumber, amount, description)
    -> Account.deposit() updates balance and appends Transaction.

**3. Withdraw Money**

    -> BankService.withdrawMoney()
    -> Bank.withdraw(accountNumber, amount, description)
    -> Account.withdraw() validates funds, updates balance, and appends Transaction.

**4. Transfer Money**

    -> BankService.transferMoney()
    -> Bank.transfer(from, to, amount, description)
    -> Source: withdraw("Transfer to ...")
    -> Destination: deposit("Transferred from ...")
    -> Two Transactions are written (one per account).

**5. View Account Details**

    -> BankService.viewAccountDetails()
    -> Bank.getAccountDetails(accountNumber)
    -> Prints Account.toString() + Account.printTransactionHistory().

**6. View All Accounts**

    -> BankService.viewAllAccounts()
    -> Bank.getAllAccountsSummary() prints a compact table.
    
---
## ⚙️ Technologies & Design Choices
- ☕ Java 11+
- OOP: Encapsulation, composition, immutability
- Collections:
    - Account history → ArrayList (preserves chronological order ⏳)
    - Bank accounts → ArrayList (simple). For O(1) lookups, a Map can be introduced later.
- Errors: 
   - IllegalArgumentException for bad input ❌ 
   - IllegalStateException can be used for business rule failures. ⚠️
- Currency: double for learning purposes 🎓 (consider BigDecimal in real apps).

---
## ▶️ How to Run
1. 📥 Clone or Download the Project
```
git clone https://github.com/satwinder9069/Account-Management-System.git
cd task5_BankAccSimulation
```
2. ⚙️ Compile the Java Files
(Make sure you’re in the task5_BankAccSimulation folder)
```
javac *.java
```
3. ▶️ Run the Application
```
java BankSimulation
```

4. 💻 Sample Output:
```
State Bank of Mine
========================================
1. Create New Account
2. Deposit Money
3. Withdraw Money
4. Transfer Money
5. View Account Details
6. View All Accounts
7. Exit
Enter your choice: 1
Enter account holder name: Rohan Kumar
Enter initial deposit amount: ₹5000
Account created successfully!
Account number : 1234XXXXXXXX
Current balance: ₹5000.0

Enter your choice: 4
Enter source account number: 1234XXXXXXXX
Enter destination account number: 1234YYYYYYYY
Enter transfer amount: ₹2000
Enter description: Rent
Transferred ₹2000.00 from account 1234XXXXXXXX to 1234YYYYYYYY

Enter your choice: 5
Enter account number: 1234XXXXXXXX

 Account Details
------------------------------------------------------------
Account [ 1234XXXXXXXX ]Balance: 3000.0

 Transaction History For 1234XXXXXXXX
----------------------------------------------------------------------------------------------------
Date/Time           Type       Amount       Balance After  Description
----------------------------------------------------------------------------------------------------
2025-01-31 10:15:42 DEPOSIT    ₹5000.00     ₹5000.00       Initial deposit
2025-01-31 11:02:08 WITHDRAWAL ₹2000.00     ₹3000.00       Transfer to 1234YYYYYYYY: Rent
----------------------------------------------------------------------------------------------------

```
## 🔍 Validation Rules (current behavior)
- Deposit: amount must be positive (negative amounts rejected)
- Withdraw: amount must be positive and ≤ current balance
- Transfer: both accounts must exist; source must have sufficient funds
- Account holder name required (collected in CLI)
- Unknown account numbers → “Account not found” error
- CLI catches exceptions and keeps the app running

---

## 🔧 Public API (BankService)
```
void createNewAccount();
void depositMoney();
void withdrawMoney();
void transferMoney();
void viewAccountDetails();
void viewAllAccounts();
```
Internally delegates to:
```
// Bank
Account createAccount(String holder, double initialDeposit);
Account findAccount(String accountNumber);
void deposit(String accountNumber, double amount, String description);
void withdraw(String accountNumber, double amount, String description);
void transfer(String fromAccount, String toAccount, double amount, String description);
void getAccountDetails(String accountNumber);
void getAllAccountsSummary();
```
 
