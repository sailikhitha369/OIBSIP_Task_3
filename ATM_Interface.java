import java.util.Scanner;

// Transaction class to store transaction history
class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

// Account class to manage account balance and transactions
class Account {
    private String userId;
    private String userPin;
    private double balance;
    private Transaction[] transactionHistory;
    private int transactionCount;

    public Account(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new Transaction[100];
        this.transactionCount = 0;
    }

    public boolean validateUser(String userId, String userPin) {
        return this.userId.equals(userId) && this.userPin.equals(userPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory[transactionCount++] = new Transaction("Deposit", amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory[transactionCount++] = new Transaction("Withdrawal", amount);
            return true;
        }
        return false;
    }

    public boolean transfer(Account receiver, double amount) {
        if (amount <= balance) {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory[transactionCount++] = new Transaction("Transfer to " + receiver.getUserId(), amount);
            return true;
        }
        return false;
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (int i = 0; i < transactionCount; i++) {
            Transaction transaction = transactionHistory[i];
            System.out.println(transaction.getType() + ": " + transaction.getAmount());
        }
        System.out.println();
    }

    public String getUserId() {
        return userId;
    }
}

// ATM class to manage ATM operations
class ATM {
    private Account account;

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM!");

        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        System.out.print("User PIN: ");
        String userPin = scanner.nextLine();

        account = new Account("user1", "1234");

        if (account.validateUser(userId, userPin)) {
            System.out.println("Login successful!");
            showMenu(scanner);
        } else {
            System.out.println("Invalid user ID or PIN.");
        }
    }

    private void showMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("ATM Menu");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    account.printTransactionHistory();
                    break;
                case 2:
                    performWithdrawal(scanner);
                    break;
                case 3:
                    performDeposit(scanner);
                    break;
                case 4:
                    performTransfer(scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void performWithdrawal(Scanner scanner) {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        boolean success = account.withdraw(amount);
        if (success) {
            System.out.println("Withdrawal successful. Remaining balance: " + account.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
        System.out.println();
    }

    private void performDeposit(Scanner scanner) {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
        System.out.println("Deposit successful. Current balance: " + account.getBalance());
        System.out.println();
    }

    private void performTransfer(Scanner scanner) {
        System.out.print("Enter the recipient's user ID: ");
        String recipientUserId = scanner.next();
        Account receiver = new Account(recipientUserId, "");
        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();
        boolean success = account.transfer(receiver, amount);
        if (success) {
            System.out.println("Transfer successful. Remaining balance: " + account.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
        System.out.println();
    }
}

// Main class to start the ATM system
public class ATM_Interface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
