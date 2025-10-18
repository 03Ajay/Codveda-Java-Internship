package com.library.model;

import java.time.LocalDateTime;

/**
 * Book model class representing library books
 */
public class Book {
    private int bookId;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private String shelfLocation;
    private LocalDateTime addedDate;
    private boolean isActive;

    // Default constructor
    public Book() {
        this.addedDate = LocalDateTime.now();
        this.isActive = true;
        this.totalCopies = 1;
        this.availableCopies = 1;
    }

    // Constructor with essential fields
    public Book(String isbn, String title, String author, String category) {
        this();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    // Constructor with most fields
    public Book(String isbn, String title, String author, String publisher,
            int publicationYear, String category, int totalCopies, String shelfLocation) {
        this();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.shelfLocation = shelfLocation;
    }

    // Constructor with all fields
    public Book(int bookId, String isbn, String title, String author, String publisher,
            int publicationYear, String category, int totalCopies, int availableCopies,
            String shelfLocation, LocalDateTime addedDate, boolean isActive) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.shelfLocation = shelfLocation;
        this.addedDate = addedDate;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Business logic methods
    public boolean isAvailable() {
        return isActive && availableCopies > 0;
    }

    public boolean borrowBook() {
        if (isAvailable()) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public boolean returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            return true;
        }
        return false;
    }

    public int getBorrowedCopies() {
        return totalCopies - availableCopies;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publicationYear=" + publicationYear +
                ", category='" + category + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                ", shelfLocation='" + shelfLocation + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Book book = (Book) obj;
        return bookId == book.bookId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(bookId);
    }
}