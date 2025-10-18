package com.banking.service;

import com.banking.model.BankAccount;
import com.banking.model.Transaction;
import com.banking.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class to manage banking operations and account management
 * Implements business logic and provides a service layer for banking operations
 */
public class BankingService {
    private final Map<String, BankAccount> accounts;
    private static long accountNumberCounter = 1000;

    /**
     * Constructor initializes the banking service
     */
    public BankingService() {
        this.accounts = new ConcurrentHashMap<>();
    }

    /**
     * Creates a new bank account
     * 
     * @param accountHolderName Name of the account holder
     * @param initialBalance    Initial balance for the account
     * @return Created BankAccount object
     * @throws IllegalArgumentException if parameters are invalid
     */
    public BankAccount createAccount(String accountHolderName, BigDecimal initialBalance) {
        String accountNumber = generateAccountNumber();
        BankAccount account = new BankAccount(accountNumber, accountHolderName, initialBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    /**
     * Finds an account by account number
     * 
     * @param accountNumber Account number to search for
     * @return Optional containing the account if found
     */
    public Optional<BankAccount> findAccount(String accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    /**
     * Performs a deposit operation
     * 
     * @param accountNumber Account number
     * @param amount        Amount to deposit
     * @return true if deposit is successful
     * @throws IllegalArgumentException if account not found or amount is invalid
     */
    public boolean deposit(String accountNumber, BigDecimal amount) {
        BankAccount account = getAccountOrThrow(accountNumber);
        return account.deposit(amount);
    }

    /**
     * Performs a withdrawal operation
     * 
     * @param accountNumber Account number
     * @param amount        Amount to withdraw
     * @return true if withdrawal is successful
     * @throws IllegalArgumentException   if account not found or amount is invalid
     * @throws InsufficientFundsException if insufficient balance
     */
    public boolean withdraw(String accountNumber, BigDecimal amount) throws InsufficientFundsException {
        BankAccount account = getAccountOrThrow(accountNumber);
        return account.withdraw(amount);
    }

    /**
     * Gets account balance
     * 
     * @param accountNumber Account number
     * @return Current balance
     * @throws IllegalArgumentException if account not found
     */
    public BigDecimal getBalance(String accountNumber) {
        BankAccount account = getAccountOrThrow(accountNumber);
        return account.getBalance();
    }

    /**
     * Gets transaction history for an account
     * 
     * @param accountNumber Account number
     * @return List of transactions
     * @throws IllegalArgumentException if account not found
     */
    public List<Transaction> getTransactionHistory(String accountNumber) {
        BankAccount account = getAccountOrThrow(accountNumber);
        return account.getTransactionHistory();
    }

    /**
     * Gets recent transactions for an account
     * 
     * @param accountNumber Account number
     * @return List of recent transactions
     * @throws IllegalArgumentException if account not found
     */
    public List<Transaction> getRecentTransactions(String accountNumber) {
        BankAccount account = getAccountOrThrow(accountNumber);
        return account.getRecentTransactions();
    }

    /**
     * Gets total number of accounts
     * 
     * @return Number of accounts
     */
    public int getTotalAccounts() {
        return accounts.size();
    }

    /**
     * Checks if an account exists
     * 
     * @param accountNumber Account number to check
     * @return true if account exists
     */
    public boolean accountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    /**
     * Transfers money between accounts
     * 
     * @param fromAccountNumber Source account number
     * @param toAccountNumber   Destination account number
     * @param amount            Amount to transfer
     * @return true if transfer is successful
     * @throws IllegalArgumentException   if accounts not found or amount is invalid
     * @throws InsufficientFundsException if insufficient balance in source account
     */
    public boolean transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount)
            throws InsufficientFundsException {

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        BankAccount fromAccount = getAccountOrThrow(fromAccountNumber);
        BankAccount toAccount = getAccountOrThrow(toAccountNumber);

        // Perform transfer (atomic operation using synchronized blocks)
        synchronized (fromAccount) {
            synchronized (toAccount) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
            }
        }

        return true;
    }

    // Private helper methods

    /**
     * Generates a unique account number
     * 
     * @return Generated account number
     */
    private synchronized String generateAccountNumber() {
        return "ACC" + String.format("%06d", ++accountNumberCounter);
    }

    /**
     * Gets account or throws exception if not found
     * 
     * @param accountNumber Account number
     * @return BankAccount object
     * @throws IllegalArgumentException if account not found
     */
    private BankAccount getAccountOrThrow(String accountNumber) {
        return findAccount(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));
    }
}