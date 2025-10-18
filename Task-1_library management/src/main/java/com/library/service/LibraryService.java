package com.library.service;

import com.library.dao.impl.UserDAOImpl;
import com.library.dao.impl.BookDAOImpl;
import com.library.dao.impl.TransactionDAOImpl;
import com.library.model.User;
import com.library.model.Book;
import com.library.model.Transaction;
import com.library.util.DatabaseConnection;

import java.sql.SQLException;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

/**
 * Library Service class containing business logic
 */
public class LibraryService {

    private final UserDAOImpl userDAO;
    private final BookDAOImpl bookDAO;
    private final TransactionDAOImpl transactionDAO;
    private final DatabaseConnection dbConnection;

    public LibraryService() {
        this.userDAO = new UserDAOImpl();
        this.bookDAO = new BookDAOImpl();
        this.transactionDAO = new TransactionDAOImpl();
        this.dbConnection = DatabaseConnection.getInstance();
    }

    // User Management Methods

    /**
     * Register a new user
     */
    public int registerUser(String username, String email, String fullName,
            String phoneNumber, User.UserType userType) throws SQLException {

        // Validate user data
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }

        // Check if username already exists
        if (userDAO.usernameExists(username)) {
            throw new SQLException("Username already exists");
        }

        // Check if email already exists
        if (userDAO.emailExists(email)) {
            throw new SQLException("Email already exists");
        }

        User user = new User(username, email, fullName, userType);
        user.setPhoneNumber(phoneNumber);

        return userDAO.addUser(user);
    }

    /**
     * Find user by username
     */
    public User findUserByUsername(String username) throws SQLException {
        return userDAO.findUserByUsername(username);
    }

    /**
     * Get all active users
     */
    public List<User> getAllActiveUsers() throws SQLException {
        return userDAO.getAllActiveUsers();
    }

    // Book Management Methods

    /**
     * Add a new book to the library
     */
    public int addBook(String isbn, String title, String author, String publisher,
            int publicationYear, String category, int totalCopies,
            String shelfLocation) throws SQLException {

        // Validate book data
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }

        // Check if ISBN already exists
        if (bookDAO.isbnExists(isbn)) {
            throw new SQLException("Book with this ISBN already exists");
        }

        Book book = new Book(isbn, title, author, publisher, publicationYear,
                category, totalCopies, shelfLocation);

        return bookDAO.addBook(book);
    }

    /**
     * Search books by title
     */
    public List<Book> searchBooksByTitle(String title) throws SQLException {
        return bookDAO.searchBooksByTitle(title);
    }

    /**
     * Search books by author
     */
    public List<Book> searchBooksByAuthor(String author) throws SQLException {
        return bookDAO.searchBooksByAuthor(author);
    }

    /**
     * Get all available books
     */
    public List<Book> getAvailableBooks() throws SQLException {
        return bookDAO.getAvailableBooks();
    }

    /**
     * Find book by ISBN
     */
    public Book findBookByIsbn(String isbn) throws SQLException {
        return bookDAO.findBookByIsbn(isbn);
    }

    // Transaction Management Methods

    /**
     * Borrow a book
     */
    public boolean borrowBook(String username, String isbn) throws SQLException {

        // Find user
        User user = userDAO.findUserByUsername(username);
        if (user == null) {
            throw new SQLException("User not found");
        }

        if (!user.isActive()) {
            throw new SQLException("User account is inactive");
        }

        // Find book
        Book book = bookDAO.findBookByIsbn(isbn);
        if (book == null) {
            throw new SQLException("Book not found");
        }

        if (!book.isAvailable()) {
            throw new SQLException("Book is not available for borrowing");
        }

        // Check if user has reached borrowing limit
        int activeBorrowings = transactionDAO.countActiveBorrowings(user.getUserId());
        if (activeBorrowings >= user.getMaxBooksAllowed()) {
            throw new SQLException("User has reached maximum borrowing limit");
        }

        // Check if user already has this book
        Transaction existingTransaction = transactionDAO.getActiveBorrowing(
                user.getUserId(), book.getBookId());
        if (existingTransaction != null) {
            throw new SQLException("User already has this book borrowed");
        }

        // Calculate due date (14 days from today)
        LocalDate dueDate = LocalDate.now().plusDays(14);

        // Create borrowing transaction
        Transaction transaction = new Transaction(user.getUserId(), book.getBookId(), dueDate);

        try {
            // Start transaction
            int transactionId = transactionDAO.addTransaction(transaction);

            // Update book availability
            if (bookDAO.updateBookAvailability(book.getBookId(),
                    book.getAvailableCopies() - 1)) {
                return true;
            } else {
                throw new SQLException("Failed to update book availability");
            }

        } catch (SQLException e) {
            throw new SQLException("Failed to borrow book: " + e.getMessage(), e);
        }
    }

    /**
     * Return a book
     */
    public boolean returnBook(String username, String isbn) throws SQLException {

        // Find user
        User user = userDAO.findUserByUsername(username);
        if (user == null) {
            throw new SQLException("User not found");
        }

        // Find book
        Book book = bookDAO.findBookByIsbn(isbn);
        if (book == null) {
            throw new SQLException("Book not found");
        }

        // Find active borrowing transaction
        Transaction transaction = transactionDAO.getActiveBorrowing(
                user.getUserId(), book.getBookId());
        if (transaction == null) {
            throw new SQLException("No active borrowing found for this user and book");
        }

        // Calculate fine if overdue
        BigDecimal fineAmount = BigDecimal.ZERO;
        if (transaction.isOverdue()) {
            double finePerDay = dbConnection.getFinePerDay();
            int gracePeriodDays = dbConnection.getGracePeriodDays();
            fineAmount = transaction.calculateFine(finePerDay, gracePeriodDays);
        }

        // Update transaction
        transaction.setReturnDate(LocalDate.now());
        transaction.setFineAmount(fineAmount);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);

        try {
            // Update transaction
            if (!transactionDAO.updateTransaction(transaction)) {
                throw new SQLException("Failed to update transaction");
            }

            // Create return transaction record
            Transaction returnTransaction = new Transaction(
                    user.getUserId(), book.getBookId(),
                    Transaction.TransactionType.RETURN,
                    LocalDate.now(), fineAmount);
            transactionDAO.addTransaction(returnTransaction);

            // Update book availability
            if (!bookDAO.updateBookAvailability(book.getBookId(),
                    book.getAvailableCopies() + 1)) {
                throw new SQLException("Failed to update book availability");
            }

            return true;

        } catch (SQLException e) {
            throw new SQLException("Failed to return book: " + e.getMessage(), e);
        }
    }

    /**
     * Get user's active borrowings
     */
    public List<Transaction> getUserActiveBorrowings(String username) throws SQLException {
        User user = userDAO.findUserByUsername(username);
        if (user == null) {
            throw new SQLException("User not found");
        }

        return transactionDAO.getActiveBorrowingsByUserId(user.getUserId());
    }

    /**
     * Get user's borrowing history
     */
    public List<Transaction> getUserBorrowingHistory(String username, int limit) throws SQLException {
        User user = userDAO.findUserByUsername(username);
        if (user == null) {
            throw new SQLException("User not found");
        }

        return transactionDAO.getBorrowingHistory(user.getUserId(), limit);
    }

    /**
     * Get all overdue transactions
     */
    public List<Transaction> getOverdueTransactions() throws SQLException {
        return transactionDAO.getOverdueTransactions();
    }

    /**
     * Get all active borrowings
     */
    public List<Transaction> getAllActiveBorrowings() throws SQLException {
        return transactionDAO.getAllActiveBorrowings();
    }

    /**
     * Test database connection
     */
    public boolean testConnection() {
        return dbConnection.testConnection();
    }
}