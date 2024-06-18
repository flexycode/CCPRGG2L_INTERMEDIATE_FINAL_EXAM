package ArtificialLedger.utils;

import ArtificialLedger.components.BankAccount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private static final Map<String, BankAccount> registeredAccounts = new HashMap<>();
    private static final String ACCOUNT_DATA_DIR = "src/main/resources/account-details/";

    public static void loadAccountData(String fileName) {
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

    public static boolean accountExists(String accountNumber) {
        return registeredAccounts.containsKey(accountNumber);
    }

    public static BankAccount getAccount(String accountNumber) {
        return registeredAccounts.get(accountNumber);
    }

    public static void registerAccount(BankAccount account) {
        registeredAccounts.put(account.getAccountNumber(), account);
    }



    // Other methods as needed
    public static void main(String[] args) {
        // Load account data from the "EimiJavascript_details.txt" file
        AccountManager.loadAccountData("EimiJavascript_details.txt");

        // Now you can use the AccountManager methods to work with accounts
        String accountNumber = "2024100000";
        if (AccountManager.accountExists(accountNumber)) {
            BankAccount account = AccountManager.getAccount(accountNumber);
            // Do something with the account
        } else {
            // Account doesn't exist, create a new one
            BankAccount newAccount = new BankAccount(accountNumber, 500.0, "Eimi Fukada");
            AccountManager.registerAccount(newAccount);
        }
    }
}
