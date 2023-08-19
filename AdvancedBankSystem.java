import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    protected double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
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

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
                "\nAccount Holder: " + accountHolder +
                "\nBalance: " + balance;
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double balance, double interestRate) {
        super(accountNumber, accountHolder, balance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        balance += balance * interestRate / 100;
        System.out.println("Interest applied. New balance: " + balance);
    }
}

class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolder, double balance, double overdraftLimit) {
        super(accountNumber, accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }
}

class Bank {
    private HashMap<String, BankAccount> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}

public class AdvancedBankSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("1. Create Savings Account");
            System.out.println("2. Create Checking Account");
            System.out.println("3. Perform Transaction");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 4) {
                System.out.println("Exiting the system.");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String savingsAccountNumber = scanner.nextLine();
                    System.out.print("Enter account holder name: ");
                    String accountHolderName = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();
                    System.out.print("Enter interest rate: ");
                    double interestRate = scanner.nextDouble();
                    bank.addAccount(new SavingsAccount(savingsAccountNumber, accountHolderName, initialBalance, interestRate));
                    System.out.println("Savings Account created.");
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    String checkingAccountNumber = scanner.nextLine();
                    System.out.print("Enter account holder name: ");
                    String checkingAccountHolderName = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double checkingInitialBalance = scanner.nextDouble();
                    System.out.print("Enter overdraft limit: ");
                    double overdraftLimit = scanner.nextDouble();
                    bank.addAccount(new CheckingAccount(checkingAccountNumber, checkingAccountHolderName, checkingInitialBalance, overdraftLimit));
                    System.out.println("Checking Account created.");
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    String transactionAccountNumber = scanner.nextLine();
                    BankAccount transactionAccount = bank.findAccount(transactionAccountNumber);

                    if (transactionAccount == null) {
                        System.out.println("Account not found.");
                        break;
                    }

                    System.out.println("1. Deposit");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Check Balance");
                    System.out.print("Enter transaction choice: ");
                    int transactionChoice = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    switch (transactionChoice) {
                        case 1:
                            System.out.print("Enter amount to deposit: ");
                            double depositAmount = scanner.nextDouble();
                            transactionAccount.deposit(depositAmount);
                            break;
                        case 2:
                            System.out.print("Enter amount to withdraw: ");
                            double withdrawAmount = scanner.nextDouble();
                            transactionAccount.withdraw(withdrawAmount);
                            break;
                        case 3:
                            System.out.println(transactionAccount);
                            break;
                        default:
                            System.out.println("Invalid transaction choice.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}
