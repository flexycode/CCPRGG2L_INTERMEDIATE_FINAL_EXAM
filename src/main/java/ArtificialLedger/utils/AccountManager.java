package ArtificialLedger.utils;

import ArtificialLedger.components.BankAccount;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages bank accounts for the ArtificialLedger application.
 * It provides functionality for loading, saving, and managing account data.

 * Key features:
 * 1. Maintains a collection of registered bank accounts.
 * 2. Handles loading account data from files.
 * 3. Provides methods to check account existence and retrieve account information.
 * 4. Manages the registration of new accounts.
 * 5. Handles saving account data to files.

 * This class acts as a central manager for all account-related operations,
 * providing a layer of abstraction between the data storage and the application logic.
 * Guys need pa to ayusin hindi pa masyado goods to.
 */

public class AccountManager {
    private static final Map<String, BankAccount> registeredAccounts = new HashMap<>();
    private static final String ACCOUNT_DATA_DIR = "src/main/resources/account-details/";

    /**
     * Loads account data for a specific user from a file.
     * This method clears existing data and populates the registeredAccounts map
     * with account information read from the user's account details file.
     *
     * @param username The username of the account holder
     */
    public static void loadAccountData(String username) {
        registeredAccounts.clear();
        String fileName = username + "_details.txt";
        String filePath = ACCOUNT_DATA_DIR + fileName;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] accountDetails = line.split(",");
                if (accountDetails.length == 3) {
                    String accountNumber = accountDetails[0];
                    double balance = Double.parseDouble(accountDetails[1]);
                    String ownerName = accountDetails[2];
                    BankAccount account = new BankAccount(accountNumber, balance, ownerName);
                    registeredAccounts.put(accountNumber, account);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if an account with the given account number exists.
     *
     * @param accountNumber The account number to check
     * @return true if the account exists, false otherwise
     */
    public static boolean accountExists(String accountNumber) {
        return registeredAccounts.containsKey(accountNumber);
    }

    /**
     * Retrieves a BankAccount object for the given account number.
     *
     * @param accountNumber The account number of the account to retrieve
     * @return The BankAccount object if found, null otherwise
     */
    public static BankAccount getAccount(String accountNumber) {
        return registeredAccounts.get(accountNumber);
    }

    /**
     * Registers a new bank account in the system.
     *
     * @param account The BankAccount object to register
     */
    public static void registerAccount(BankAccount account) {
        registeredAccounts.put(account.getAccountNumber(), account);
    }

    /**
     * Saves the account data for a specific user to a file.
     * This method writes the account details to the user's account file.
     *
     * @param username The username of the account holder
     * @param account The BankAccount object containing the account details to save
     */
    public static void saveAccountData(String username, BankAccount account) {
        String fileName = username + "_details.txt";
        String filePath = ACCOUNT_DATA_DIR + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(account.getAccountNumber() + "," + account.getBalance() + "," + account.getOwnerName());
            writer.newLine(); // Add a newline character after each account entry
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
