package com.library.dao;

import com.library.model.Transaction;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Transaction Data Access Object interface
 * Defines CRUD operations for Transaction entity
 */
public interface TransactionDAO {

    /**
     * Add a new transaction to the database
     * 
     * @param transaction Transaction object to add
     * @return Generated transaction ID
     * @throws SQLException if database operation fails
     */
    int addTransaction(Transaction transaction) throws SQLException;

    /**
     * Update an existing transaction
     * 
     * @param transaction Transaction object with updated information
     * @return true if update successful
     * @throws SQLException if database operation fails
     */
    boolean updateTransaction(Transaction transaction) throws SQLException;

    /**
     * Find transaction by ID
     * 
     * @param transactionId Transaction ID to search
     * @return Transaction object or null if not found
     * @throws SQLException if database operation fails
     */
    Transaction findTransactionById(int transactionId) throws SQLException;

    /**
     * Get all transactions for a user
     * 
     * @param userId User ID
     * @return List of transactions for the user
     * @throws SQLException if database operation fails
     */
    List<Transaction> getTransactionsByUserId(int userId) throws SQLException;

    /**
     * Get all transactions for a book
     * 
     * @param bookId Book ID
     * @return List of transactions for the book
     * @throws SQLException if database operation fails
     */
    List<Transaction> getTransactionsByBookId(int bookId) throws SQLException;

    /**
     * Get active borrowing transactions for a user
     * 
     * @param userId User ID
     * @return List of active borrowing transactions
     * @throws SQLException if database operation fails
     */
    List<Transaction> getActiveBorrowingsByUserId(int userId) throws SQLException;

    /**
     * Get active borrowing transaction for a specific book by user
     * 
     * @param userId User ID
     * @param bookId Book ID
     * @return Active transaction or null if not found
     * @throws SQLException if database operation fails
     */
    Transaction getActiveBorrowing(int userId, int bookId) throws SQLException;

    /**
     * Get all active borrowing transactions
     * 
     * @return List of all active borrowing transactions
     * @throws SQLException if database operation fails
     */
    List<Transaction> getAllActiveBorrowings() throws SQLException;

    /**
     * Get overdue transactions
     * 
     * @return List of overdue transactions
     * @throws SQLException if database operation fails
     */
    List<Transaction> getOverdueTransactions() throws SQLException;

    /**
     * Get transactions by date range
     * 
     * @param startDate Start date
     * @param endDate   End date
     * @return List of transactions in date range
     * @throws SQLException if database operation fails
     */
    List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException;

    /**
     * Get transactions by type
     * 
     * @param transactionType Type of transaction
     * @return List of transactions of specified type
     * @throws SQLException if database operation fails
     */
    List<Transaction> getTransactionsByType(Transaction.TransactionType transactionType) throws SQLException;

    /**
     * Get transactions by status
     * 
     * @param status Transaction status
     * @return List of transactions with specified status
     * @throws SQLException if database operation fails
     */
    List<Transaction> getTransactionsByStatus(Transaction.TransactionStatus status) throws SQLException;

    /**
     * Update transaction status
     * 
     * @param transactionId Transaction ID
     * @param status        New status
     * @return true if update successful
     * @throws SQLException if database operation fails
     */
    boolean updateTransactionStatus(int transactionId, Transaction.TransactionStatus status) throws SQLException;

    /**
     * Count active borrowings for a user
     * 
     * @param userId User ID
     * @return Number of active borrowings
     * @throws SQLException if database operation fails
     */
    int countActiveBorrowings(int userId) throws SQLException;

    /**
     * Get borrowing history for a user
     * 
     * @param userId User ID
     * @param limit  Maximum number of records
     * @return List of borrowing history
     * @throws SQLException if database operation fails
     */
    List<Transaction> getBorrowingHistory(int userId, int limit) throws SQLException;

    /**
     * Get all transactions with user and book details
     * 
     * @return List of transactions with related objects
     * @throws SQLException if database operation fails
     */
    List<Transaction> getAllTransactionsWithDetails() throws SQLException;
}