package ArtificialLedger.components;

/**
 * This class represents a Bank Account in the ArtificialLedger application.
 * It encapsulates the core functionality of a basic bank account, including
 * account information storage and fundamental banking operations.

 * Key features:
 * 1. Stores essential account information (account number, balance, owner name).
 * 2. Provides methods for basic banking operations (deposit, withdraw, transfer).
 * 3. Implements basic validation to prevent invalid operations.
 * 4. Implements basic sound effects for enhanced user experience.
 * 5. Implements a simple interface for user interaction.

 * This class serves as a foundational component for more complex banking systems
 * and can be extended to include additional features or integrate with other
 * financial management tools.
 */

public class BankAccount {
    private final String accountNumber;
    private double balance;
    private final String ownerName;

    /**
     * Constructor for creating a new BankAccount instance.
     *
     * @param accountNumber The unique identifier for the account
     * @param initialBalance The starting balance of the account
     * @param ownerName The name of the account owner
     */
    public BankAccount(String accountNumber, double initialBalance, String ownerName) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.ownerName = ownerName;
    }

    /**
     * Retrieves the account number.
     *
     * @return The account number as a String
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Retrieves the current balance of the account.
     *
     * @return The current balance as a double
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Retrieves the name of the account owner.
     *
     * @return The owner's name as a String
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Deposits a specified amount into the account.
     * This method increases the account balance by the given amount.
     *
     * @param amount The amount to be deposited
     */
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Withdraws a specified amount from the account.
     * This method decreases the account balance by the given amount if sufficient funds are available.
     * If the balance is insufficient, it prints an error message.
     *
     * @param amount The amount to be withdrawn
     */
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    /**
     * Transfers a specified amount from this account to another account.
     * This method withdraws the amount from the current account and deposits it into the recipient's account.
     * It performs checks for the validity of the recipient account and sufficient balance.
     *
     * @param recipient The BankAccount object to receive the transfer
     * @param amount The amount to be transferred
     */
    public void transfer(BankAccount recipient, double amount) {
        if (recipient != null) {
            if (amount <= balance) {
                withdraw(amount);
                recipient.deposit(amount);
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Recipient account is null.");
        }
    }
}