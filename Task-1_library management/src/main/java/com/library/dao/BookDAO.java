package com.library.dao;

import com.library.model.Book;
import java.sql.SQLException;
import java.util.List;

/**
 * Book Data Access Object interface
 * Defines CRUD operations for Book entity
 */
public interface BookDAO {

    /**
     * Add a new book to the database
     * 
     * @param book Book object to add
     * @return Generated book ID
     * @throws SQLException if database operation fails
     */
    int addBook(Book book) throws SQLException;

    /**
     * Update an existing book
     * 
     * @param book Book object with updated information
     * @return true if update successful
     * @throws SQLException if database operation fails
     */
    boolean updateBook(Book book) throws SQLException;

    /**
     * Delete a book by ID (soft delete - mark as inactive)
     * 
     * @param bookId Book ID to delete
     * @return true if deletion successful
     * @throws SQLException if database operation fails
     */
    boolean deleteBook(int bookId) throws SQLException;

    /**
     * Find book by ID
     * 
     * @param bookId Book ID to search
     * @return Book object or null if not found
     * @throws SQLException if database operation fails
     */
    Book findBookById(int bookId) throws SQLException;

    /**
     * Find book by ISBN
     * 
     * @param isbn ISBN to search
     * @return Book object or null if not found
     * @throws SQLException if database operation fails
     */
    Book findBookByIsbn(String isbn) throws SQLException;

    /**
     * Get all active books
     * 
     * @return List of active books
     * @throws SQLException if database operation fails
     */
    List<Book> getAllActiveBooks() throws SQLException;

    /**
     * Get all books
     * 
     * @return List of all books
     * @throws SQLException if database operation fails
     */
    List<Book> getAllBooks() throws SQLException;

    /**
     * Search books by title (partial match)
     * 
     * @param title Title to search (can be partial)
     * @return List of matching books
     * @throws SQLException if database operation fails
     */
    List<Book> searchBooksByTitle(String title) throws SQLException;

    /**
     * Search books by author (partial match)
     * 
     * @param author Author to search (can be partial)
     * @return List of matching books
     * @throws SQLException if database operation fails
     */
    List<Book> searchBooksByAuthor(String author) throws SQLException;

    /**
     * Get books by category
     * 
     * @param category Category to filter
     * @return List of books in specified category
     * @throws SQLException if database operation fails
     */
    List<Book> getBooksByCategory(String category) throws SQLException;

    /**
     * Get available books (books with available copies > 0)
     * 
     * @return List of available books
     * @throws SQLException if database operation fails
     */
    List<Book> getAvailableBooks() throws SQLException;

    /**
     * Update book availability (when borrowed or returned)
     * 
     * @param bookId          Book ID
     * @param availableCopies New available copies count
     * @return true if update successful
     * @throws SQLException if database operation fails
     */
    boolean updateBookAvailability(int bookId, int availableCopies) throws SQLException;

    /**
     * Check if ISBN exists
     * 
     * @param isbn ISBN to check
     * @return true if ISBN exists
     * @throws SQLException if database operation fails
     */
    boolean isbnExists(String isbn) throws SQLException;

    /**
     * Get all unique categories
     * 
     * @return List of unique categories
     * @throws SQLException if database operation fails
     */
    List<String> getAllCategories() throws SQLException;

    /**
     * Search books by multiple criteria
     * 
     * @param title    Title (can be null)
     * @param author   Author (can be null)
     * @param category Category (can be null)
     * @param isbn     ISBN (can be null)
     * @return List of matching books
     * @throws SQLException if database operation fails
     */
    List<Book> searchBooks(String title, String author, String category, String isbn) throws SQLException;
}