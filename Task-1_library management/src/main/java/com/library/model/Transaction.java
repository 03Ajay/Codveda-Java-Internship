package com.library.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Transaction model class representing book borrowing and returning
 * transactions
 */
public class Transaction {
    private int transactionId;
    private int userId;
    private int bookId;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BigDecimal fineAmount;
    private TransactionStatus status;
    private String notes;

    // Related objects for convenience (not stored in DB)
    private User user;
    private Book book;

    public enum TransactionType {
        BORROW, RETURN
    }

    public enum TransactionStatus {
        ACTIVE, COMPLETED, OVERDUE
    }

    // Default constructor
    public Transaction() {
        this.transactionDate = LocalDateTime.now();
        this.fineAmount = BigDecimal.ZERO;
        this.status = TransactionStatus.ACTIVE;
    }

    // Constructor for new borrow transaction
    public Transaction(int userId, int bookId, LocalDate dueDate) {
        this();
        this.userId = userId;
        this.bookId = bookId;
        this.transactionType = TransactionType.BORROW;
        this.dueDate = dueDate;
    }

    // Constructor for return transaction
    public Transaction(int userId, int bookId, TransactionType transactionType,
            LocalDate returnDate, BigDecimal fineAmount) {
        this();
        this.userId = userId;
        this.bookId = bookId;
        this.transactionType = transactionType;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
        this.status = TransactionStatus.COMPLETED;
    }

    // Constructor with all fields
    public Transaction(int transactionId, int userId, int bookId,
            TransactionType transactionType, LocalDateTime transactionDate,
            LocalDate dueDate, LocalDate returnDate, BigDecimal fineAmount,
            TransactionStatus status, String notes) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
        this.status = status;
        this.notes = notes;
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // Business logic methods
    public boolean isOverdue() {
        if (dueDate == null || returnDate != null) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);
    }

    public long getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }

    public BigDecimal calculateFine(double finePerDay, int gracePeriodDays) {
        long daysOverdue = getDaysOverdue();
        if (daysOverdue <= gracePeriodDays) {
            return BigDecimal.ZERO;
        }

        long fineDays = daysOverdue - gracePeriodDays;
        return BigDecimal.valueOf(fineDays * finePerDay);
    }

    public boolean isActive() {
        return status == TransactionStatus.ACTIVE && returnDate == null;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", transactionType=" + transactionType +
                ", transactionDate=" + transactionDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", fineAmount=" + fineAmount +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Transaction that = (Transaction) obj;
        return transactionId == that.transactionId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(transactionId);
    }
}