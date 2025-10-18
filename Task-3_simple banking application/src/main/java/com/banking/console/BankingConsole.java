package com.banking.console;

import com.banking.service.BankingService;
import com.banking.model.BankAccount;
import com.banking.model.Transaction;
import com.banking.exception.InsufficientFundsException;
import com.banking.util.InputValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface for the banking application
 * Provides interactive menu system for banking operations
 */
public class BankingConsole {

    private final BankingService bankingService;
    private final Scanner scanner;
    private String currentAccountNumber;

    public BankingConsole() {
        this.bankingService = new BankingService();
        this.scanner = new Scanner(System.in);
        this.currentAccountNumber = null;
    }

    /**
     * Starts the banking console application
     */
    public void start() {
        displayWelcomeMessage();

        while (true) {
            try {
                if (currentAccountNumber == null) {
                    showMainMenu();
                    handleMainMenuChoice();
                } else {
                    showAccountMenu();
                    handleAccountMenuChoice();
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
                pressEnterToContinue();
            }
        }
    }

    /**
     * Displays welcome message and application info
     */
    private void displayWelcomeMessage() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  SIMPLE BANKING SYSTEM                    ║");
        System.out.println("║                     Version 1.0                           ║");
        System.out.println("║              Professional Banking Application             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Welcome to the Simple Banking System!");
        System.out.println("This application provides secure banking operations with proper error handling.");
        System.out.println();
    }

    /**
     * Shows main menu when no account is selected
     */
    private void showMainMenu() {
        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.println("│                        MAIN MENU                          │");
        System.out.println("├────────────────────────────────────────────────────────────┤");
        System.out.println("│  1. Create New Account                                     │");
        System.out.println("│  2. Access Existing Account                               │");
        System.out.println("│  3. System Information                                    │");
        System.out.println("│  4. Exit Application                                      │");
        System.out.println("└────────────────────────────────────────────────────────────┘");
        System.out.println();
    }

    /**
     * Shows account menu when an account is selected
     */
    private void showAccountMenu() {
        BankAccount account = bankingService.findAccount(currentAccountNumber).orElse(null);
        if (account != null) {
            System.out.println("┌────────────────────────────────────────────────────────────┐");
            System.out.printf("│  Account: %-10s | Holder: %-25s │%n",
                    account.getAccountNumber(),
                    truncateName(account.getAccountHolderName(), 25));
            System.out.printf("│  Current Balance: $%-40.2f │%n", account.getBalance());
            System.out.println("├────────────────────────────────────────────────────────────┤");
            System.out.println("│                    ACCOUNT OPERATIONS                      │");
            System.out.println("├────────────────────────────────────────────────────────────┤");
            System.out.println("│  1. Check Balance                                         │");
            System.out.println("│  2. Deposit Money                                         │");
            System.out.println("│  3. Withdraw Money                                        │");
            System.out.println("│  4. Transfer Money                                        │");
            System.out.println("│  5. View Transaction History                              │");
            System.out.println("│  6. View Recent Transactions                              │");
            System.out.println("│  7. Account Information                                   │");
            System.out.println("│  8. Logout (Return to Main Menu)                         │");
            System.out.println("└────────────────────────────────────────────────────────────┘");
            System.out.println();
        }
    }

    /**
     * Handles main menu user choices
     */
    private void handleMainMenuChoice() {
        int choice = InputValidator.readValidChoice(scanner, "Please enter your choice (1-4): ", 1, 4);

        switch (choice) {
            case 1:
                createNewAccount();
                break;
            case 2:
                accessExistingAccount();
                break;
            case 3:
                showSystemInformation();
                break;
            case 4:
                exitApplication();
                break;
        }
    }

    /**
     * Handles account menu user choices
     */
    private void handleAccountMenuChoice() {
        int choice = InputValidator.readValidChoice(scanner, "Please enter your choice (1-8): ", 1, 8);

        switch (choice) {
            case 1:
                checkBalance();
                break;
            case 2:
                depositMoney();
                break;
            case 3:
                withdrawMoney();
                break;
            case 4:
                transferMoney();
                break;
            case 5:
                viewTransactionHistory();
                break;
            case 6:
                viewRecentTransactions();
                break;
            case 7:
                showAccountInformation();
                break;
            case 8:
                logout();
                break;
        }
    }

    /**
     * Creates a new bank account
     */
    private void createNewAccount() {
        System.out.println("\n═══ CREATE NEW ACCOUNT ═══");

        String name = InputValidator.readValidName(scanner, "Enter account holder name: ");
        BigDecimal initialBalance = InputValidator.readValidAmount(scanner, "Enter initial deposit amount ($): ");

        try {
            BankAccount account = bankingService.createAccount(name, initialBalance);

            System.out.println("\n✓ Account created successfully!");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Holder: " + account.getAccountHolderName());
            System.out.printf("Initial Balance: $%.2f%n", account.getBalance());

            boolean autoLogin = InputValidator.confirmAction(scanner, "Would you like to access this account now?");
            if (autoLogin) {
                currentAccountNumber = account.getAccountNumber();
                System.out.println("✓ Logged into account: " + currentAccountNumber);
            }

        } catch (Exception e) {
            System.out.println("✗ Failed to create account: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    /**
     * Accesses an existing account
     */
    private void accessExistingAccount() {
        System.out.println("\n═══ ACCESS EXISTING ACCOUNT ═══");

        String accountNumber = InputValidator.readValidAccountNumber(scanner, "Enter account number: ");

        if (bankingService.accountExists(accountNumber)) {
            currentAccountNumber = accountNumber;
            BankAccount account = bankingService.findAccount(accountNumber).get();
            System.out.println("✓ Successfully logged into account: " + accountNumber);
            System.out.println("Welcome back, " + account.getAccountHolderName() + "!");
        } else {
            System.out.println("✗ Account not found: " + accountNumber);
            System.out.println("Please check the account number and try again.");
        }

        pressEnterToContinue();
    }

    /**
     * Checks and displays current balance
     */
    private void checkBalance() {
        System.out.println("\n═══ BALANCE INQUIRY ═══");

        try {
            BigDecimal balance = bankingService.getBalance(currentAccountNumber);
            BankAccount account = bankingService.findAccount(currentAccountNumber).get();

            System.out.println("Account: " + currentAccountNumber);
            System.out.println("Holder: " + account.getAccountHolderName());
            System.out.printf("Current Balance: $%.2f%n", balance);

        } catch (Exception e) {
            System.out.println("✗ Error retrieving balance: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    /**
     * Handles money deposit operation
     */
    private void depositMoney() {
        System.out.println("\n═══ DEPOSIT MONEY ═══");

        try {
            BigDecimal currentBalance = bankingService.getBalance(currentAccountNumber);
            System.out.printf("Current Balance: $%.2f%n", currentBalance);

            BigDecimal amount = InputValidator.readValidAmount(scanner, "Enter deposit amount ($): ");

            boolean confirm = InputValidator.confirmAction(scanner,
                    String.format("Confirm deposit of $%.2f?", amount));

            if (confirm) {
                boolean success = bankingService.deposit(currentAccountNumber, amount);
                if (success) {
                    BigDecimal newBalance = bankingService.getBalance(currentAccountNumber);
                    System.out.println("✓ Deposit successful!");
                    System.out.printf("Amount Deposited: $%.2f%n", amount);
                    System.out.printf("New Balance: $%.2f%n", newBalance);
                }
            } else {
                System.out.println("Deposit cancelled.");
            }

        } catch (Exception e) {
            System.out.println("✗ Deposit failed: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    /**
     * Handles money withdrawal operation
     */
    private void withdrawMoney() {
        System.out.println("\n═══ WITHDRAW MONEY ═══");

        try {
            BigDecimal currentBalance = bankingService.getBalance(currentAccountNumber);
            System.out.printf("Current Balance: $%.2f%n", currentBalance);

            BigDecimal amount = InputValidator.readValidAmount(scanner, "Enter withdrawal amount ($): ");

            if (amount.compareTo(currentBalance) > 0) {
                System.out.printf("✗ Insufficient funds. Available balance: $%.2f%n", currentBalance);
                return;
            }

            boolean confirm = InputValidator.confirmAction(scanner,
                    String.format("Confirm withdrawal of $%.2f?", amount));

            if (confirm) {
                boolean success = bankingService.withdraw(currentAccountNumber, amount);
                if (success) {
                    BigDecimal newBalance = bankingService.getBalance(currentAccountNumber);
                    System.out.println("✓ Withdrawal successful!");
                    System.out.printf("Amount Withdrawn: $%.2f%n", amount);
                    System.out.printf("New Balance: $%.2f%n", newBalance);
                }
            } else {
                System.out.println("Withdrawal cancelled.");
            }

        } catch (InsufficientFundsException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Withdrawal failed: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    /**
     * Handles money transfer operation
     */
    private void transferMoney() {
        System.out.println("\n═══ TRANSFER MONEY ═══");

        try {
            BigDecimal currentBalance = bankingService.getBalance(currentAccountNumber);
            System.out.printf("Current Balance: $%.2f%n", currentBalance);

            String toAccountNumber = InputValidator.readValidAccountNumber(scanner,
                    "Enter destination account number: ");

            if (!bankingService.accountExists(toAccountNumber)) {
                System.out.println("✗ Destination account not found: " + toAccountNumber);
                return;
            }

            if (toAccountNumber.equals(currentAccountNumber)) {
                System.out.println("✗ Cannot transfer to the same account.");
                return;
            }

            BankAccount toAccount = bankingService.findAccount(toAccountNumber).get();
            System.out.println("Destination Account Holder: " + toAccount.getAccountHolderName());

            BigDecimal amount = InputValidator.readValidAmount(scanner, "Enter transfer amount ($): ");

            if (amount.compareTo(currentBalance) > 0) {
                System.out.printf("✗ Insufficient funds. Available balance: $%.2f%n", currentBalance);
                return;
            }

            boolean confirm = InputValidator.confirmAction(scanner,
                    String.format("Confirm transfer of $%.2f to %s (%s)?",
                            amount, toAccountNumber, toAccount.getAccountHolderName()));

            if (confirm) {
                boolean success = bankingService.transfer(currentAccountNumber, toAccountNumber, amount);
                if (success) {
                    BigDecimal newBalance = bankingService.getBalance(currentAccountNumber);
                    System.out.println("✓ Transfer successful!");
                    System.out.printf("Amount Transferred: $%.2f%n", amount);
                    System.out.printf("To Account: %s (%s)%n", toAccountNumber, toAccount.getAccountHolderName());
                    System.out.printf("New Balance: $%.2f%n", newBalance);
                }
            } else {
                System.out.println("Transfer cancelled.");
            }

        } catch (InsufficientFundsException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Transfer failed: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    /**
     * Views complete transaction history
     */
    private void viewTransactionHistory() {
        System.out.println("\n═══ TRANSACTION HISTORY ═══");

        try {
            List<Transaction> transactions = bankingService.getTransactionHistory(currentAccountNumber);

            if (transactions.isEmpty()) {
                System.out.println("No transactions found for this account.");
            } else {
                System.out.printf("Total Transactions: %d%n%n", transactions.size());

                for (Transaction transaction : transactions) {
                    System.out.println(transaction.getFormattedTransaction());
                }
            }

        } catch (Exception e) {
            System.out.println("✗ Error retrieving transaction history: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    /**
     * Views recent transactions (last 10)
     */
    private void viewRecentTransactions() {
        System.out.println("\n═══ RECENT TRANSACTIONS ═══");

        try {
            List<Transaction> transactions = bankingService.getRecentTransactions(currentAccountNumber);

            if (transactions.isEmpty()) {
                System.out.println("No transactions found for this account.");
            } else {
                System.out.println("Last 10 transactions:");
                System.out.println();

                for (Transaction transaction : transactions) {
                    System.out.println(transaction.getFormattedTransaction());
                }
            }

        } catch (Exception e) {
            System.out.println("✗ Error retrieving recent transactions: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    /**
     * Shows detailed account information
     */
    private void showAccountInformation() {
        System.out.println("\n═══ ACCOUNT INFORMATION ═══");

        try {
            BankAccount account = bankingService.findAccount(currentAccountNumber).get();
            List<Transaction> transactions = bankingService.getTransactionHistory(currentAccountNumber);

            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Holder: " + account.getAccountHolderName());
            System.out.printf("Current Balance: $%.2f%n", account.getBalance());
            System.out.printf("Total Transactions: %d%n", transactions.size());

            if (!transactions.isEmpty()) {
                Transaction firstTransaction = transactions.get(0);
                Transaction lastTransaction = transactions.get(transactions.size() - 1);

                System.out.println("Account Created: " + firstTransaction.getTimestamp().toLocalDate());
                System.out.println("Last Transaction: " + lastTransaction.getTimestamp().toLocalDate());
            }

        } catch (Exception e) {
            System.out.println("✗ Error retrieving account information: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    /**
     * Shows system information
     */
    private void showSystemInformation() {
        System.out.println("\n═══ SYSTEM INFORMATION ═══");
        System.out.println("Simple Banking System v1.0");
        System.out.println("Developed with Java");
        System.out.println("Features:");
        System.out.println("• Account creation and management");
        System.out.println("• Secure deposit and withdrawal operations");
        System.out.println("• Money transfer between accounts");
        System.out.println("• Complete transaction history tracking");
        System.out.println("• Robust error handling and validation");
        System.out.println("• Thread-safe operations");
        System.out.printf("• Total Accounts in System: %d%n", bankingService.getTotalAccounts());

        pressEnterToContinue();
    }

    /**
     * Logs out from current account
     */
    private void logout() {
        System.out.println("\n✓ Successfully logged out from account: " + currentAccountNumber);
        currentAccountNumber = null;
        pressEnterToContinue();
    }

    /**
     * Exits the application
     */
    private void exitApplication() {
        System.out.println("\n═══ THANK YOU ═══");
        System.out.println("Thank you for using the Simple Banking System!");
        System.out.println("Your banking session has ended securely.");
        System.out.println("Have a great day!");

        scanner.close();
        System.exit(0);
    }

    /**
     * Helper method to pause execution and wait for user input
     */
    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        clearScreen();
    }

    /**
     * Clears the console screen (simulated)
     */
    private void clearScreen() {
        // Print multiple newlines to simulate screen clearing
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }

    /**
     * Truncates name if too long for display
     */
    private String truncateName(String name, int maxLength) {
        if (name.length() <= maxLength) {
            return name;
        }
        return name.substring(0, maxLength - 3) + "...";
    }
}