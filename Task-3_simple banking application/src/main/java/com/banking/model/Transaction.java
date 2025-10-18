package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a banking transaction with all relevant details
 * Immutable class to ensure transaction integrity
 */
public class Transaction {
    private final TransactionType type;
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime timestamp;
    private final BigDecimal balanceAfterTransaction;
    private final String transactionId;

    private static long transactionCounter = 1000000; // Starting transaction ID

    /**
     * Constructor for creating a new transaction
     * 
     * @param type                    Type of transaction (DEPOSIT or WITHDRAWAL)
     * @param amount                  Transaction amount
     * @param description             Transaction description
     * @param balanceAfterTransaction Account balance after this transaction
     */
    public Transaction(TransactionType type, BigDecimal amount, String description,
            BigDecimal balanceAfterTransaction) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.transactionId = generateTransactionId();
    }

    /**
     * Gets transaction type
     * 
     * @return TransactionType enum
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Gets transaction amount
     * 
     * @return BigDecimal amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Gets transaction description
     * 
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets transaction timestamp
     * 
     * @return LocalDateTime timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets balance after transaction
     * 
     * @return BigDecimal balance
     */
    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    /**
     * Gets unique transaction ID
     * 
     * @return String transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Formats transaction for display
     * 
     * @return Formatted string representation
     */
    public String getFormattedTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sign = type == TransactionType.DEPOSIT ? "+" : "-";

        return String.format("ID: %s | %s | %s$%.2f | %s | Balance: $%.2f",
                transactionId,
                timestamp.format(formatter),
                sign,
                amount,
                description,
                balanceAfterTransaction);
    }

    /**
     * Generates a unique transaction ID
     * 
     * @return String transaction ID
     */
    private synchronized String generateTransactionId() {
        return "TXN" + (++transactionCounter);
    }

    @Override
    public String toString() {
        return getFormattedTransaction();
    }
}