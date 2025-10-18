package com.banking.model;

import com.banking.exception.InsufficientFundsException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a bank account with basic banking operations
 * Implements proper encapsulation and thread-safety for balance operations
 */
public class BankAccount {
    private final String accountNumber;
    private final String accountHolderName;
    private volatile BigDecimal balance;
    private final List<Transaction> transactionHistory;
    private final Object balanceLock = new Object();

    // Constants for validation
    private static final BigDecimal MIN_DEPOSIT_AMOUNT = new BigDecimal("0.01");
    private static final BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("100000.00");
    private static final BigDecimal MIN_BALANCE = BigDecimal.ZERO;

    /**
     * Constructor to create a new bank account
     * 
     * @param accountNumber     Unique account identifier
     * @param accountHolderName Name of the account holder
     * @param initialBalance    Initial balance (must be non-negative)
     * @throws IllegalArgumentException if parameters are invalid
     */
    public BankAccount(String accountNumber, String accountHolderName, BigDecimal initialBalance) {
        validateAccountCreation(accountNumber, accountHolderName, initialBalance);

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance.setScale(2, RoundingMode.HALF_UP);
        this.transactionHistory = Collections.synchronizedList(new ArrayList<>());

        // Record initial balance as opening transaction
        if (initialBalance.compareTo(BigDecimal.ZERO) > 0) {
            addTransaction(TransactionType.DEPOSIT, initialBalance, "Account opening deposit");
        }
    }

    /**
     * Deposits money into the account
     * 
     * @param amount Amount to deposit (must be positive)
     * @throws IllegalArgumentException if amount is invalid
     * @return true if deposit is successful
     */
    public boolean deposit(BigDecimal amount) {
        validateDepositAmount(amount);

        synchronized (balanceLock) {
            BigDecimal roundedAmount = amount.setScale(2, RoundingMode.HALF_UP);
            this.balance = this.balance.add(roundedAmount);
            addTransaction(TransactionType.DEPOSIT, roundedAmount, "Cash deposit");
        }

        return true;
    }

    /**
     * Withdraws money from the account
     * 
     * @param amount Amount to withdraw (must be positive and not exceed balance)
     * @throws IllegalArgumentException   if amount is invalid
     * @throws InsufficientFundsException if insufficient balance
     * @return true if withdrawal is successful
     */
    public boolean withdraw(BigDecimal amount) throws InsufficientFundsException {
        validateWithdrawAmount(amount);

        synchronized (balanceLock) {
            BigDecimal roundedAmount = amount.setScale(2, RoundingMode.HALF_UP);

            if (this.balance.compareTo(roundedAmount) < 0) {
                throw new InsufficientFundsException(
                        String.format("Insufficient funds. Available balance: $%.2f, Requested: $%.2f",
                                this.balance, roundedAmount));
            }

            this.balance = this.balance.subtract(roundedAmount);
            addTransaction(TransactionType.WITHDRAWAL, roundedAmount, "Cash withdrawal");
        }

        return true;
    }

    /**
     * Gets the current account balance
     * 
     * @return Current balance as BigDecimal
     */
    public BigDecimal getBalance() {
        synchronized (balanceLock) {
            return this.balance.setScale(2, RoundingMode.HALF_UP);
        }
    }

    /**
     * Gets account number
     * 
     * @return Account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Gets account holder name
     * 
     * @return Account holder name
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * Gets transaction history
     * 
     * @return Unmodifiable list of transactions
     */
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(new ArrayList<>(transactionHistory));
    }

    /**
     * Gets recent transactions (last 10)
     * 
     * @return List of recent transactions
     */
    public List<Transaction> getRecentTransactions() {
        List<Transaction> allTransactions = getTransactionHistory();
        int size = allTransactions.size();
        int fromIndex = Math.max(0, size - 10);
        return allTransactions.subList(fromIndex, size);
    }

    // Private helper methods

    private void validateAccountCreation(String accountNumber, String accountHolderName, BigDecimal initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }

        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be null or empty");
        }

        if (initialBalance == null || initialBalance.compareTo(MIN_BALANCE) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
    }

    private void validateDepositAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Deposit amount cannot be null");
        }

        if (amount.compareTo(MIN_DEPOSIT_AMOUNT) < 0) {
            throw new IllegalArgumentException("Deposit amount must be at least $0.01");
        }

        if (amount.compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
            throw new IllegalArgumentException("Deposit amount cannot exceed $100,000.00 per transaction");
        }
    }

    private void validateWithdrawAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Withdrawal amount cannot be null");
        }

        if (amount.compareTo(MIN_DEPOSIT_AMOUNT) < 0) {
            throw new IllegalArgumentException("Withdrawal amount must be at least $0.01");
        }

        if (amount.compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot exceed $100,000.00 per transaction");
        }
    }

    private void addTransaction(TransactionType type, BigDecimal amount, String description) {
        Transaction transaction = new Transaction(type, amount, description, getBalance());
        transactionHistory.add(transaction);
    }

    @Override
    public String toString() {
        return String.format("BankAccount{accountNumber='%s', accountHolderName='%s', balance=%.2f}",
                accountNumber, accountHolderName, balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        BankAccount that = (BankAccount) obj;
        return accountNumber.equals(that.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}