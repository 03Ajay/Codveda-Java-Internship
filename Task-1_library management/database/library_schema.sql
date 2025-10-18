-- Library Management System Database Schema
-- Create Database
CREATE DATABASE IF NOT EXISTS library_management;
USE library_management;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;

-- Create Users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15),
    address TEXT,
    user_type ENUM('STUDENT', 'FACULTY', 'STAFF') NOT NULL DEFAULT 'STUDENT',
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    max_books_allowed INT DEFAULT 5
);

-- Create Books table
CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(13) UNIQUE NOT NULL,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(150) NOT NULL,
    publisher VARCHAR(100),
    publication_year INT,
    category VARCHAR(50),
    total_copies INT NOT NULL DEFAULT 1,
    available_copies INT NOT NULL DEFAULT 1,
    shelf_location VARCHAR(50),
    added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    INDEX idx_isbn (isbn),
    INDEX idx_title (title),
    INDEX idx_author (author),
    INDEX idx_category (category)
);

-- Create Transactions table
CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    transaction_type ENUM('BORROW', 'RETURN') NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date DATE,
    return_date DATE NULL,
    fine_amount DECIMAL(10,2) DEFAULT 0.00,
    status ENUM('ACTIVE', 'COMPLETED', 'OVERDUE') DEFAULT 'ACTIVE',
    notes TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_book_id (book_id),
    INDEX idx_transaction_date (transaction_date),
    INDEX idx_status (status)
);

-- Insert sample data for testing
INSERT INTO users (username, email, full_name, phone_number, user_type, max_books_allowed) VALUES
('john_doe', 'john.doe@email.com', 'John Doe', '9876543210', 'STUDENT', 5),
('jane_smith', 'jane.smith@email.com', 'Jane Smith', '9876543211', 'FACULTY', 10),
('admin_user', 'admin@library.com', 'Library Admin', '9876543212', 'STAFF', 20);

INSERT INTO books (isbn, title, author, publisher, publication_year, category, total_copies, available_copies, shelf_location) VALUES
('9780134685991', 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 2018, 'Programming', 3, 3, 'A1-CS-001'),
('9780596009205', 'Head First Design Patterns', 'Eric Freeman', 'O''Reilly Media', 2004, 'Programming', 2, 2, 'A1-CS-002'),
('9780132350884', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 2008, 'Programming', 4, 4, 'A1-CS-003'),
('9780201633610', 'Design Patterns', 'Gang of Four', 'Addison-Wesley', 1994, 'Programming', 2, 2, 'A1-CS-004'),
('9780134757599', 'Java: The Complete Reference', 'Herbert Schildt', 'McGraw-Hill', 2020, 'Programming', 3, 3, 'A1-CS-005');

-- Create views for common queries
CREATE VIEW active_borrowings AS
SELECT 
    t.transaction_id,
    u.username,
    u.full_name,
    b.title,
    b.author,
    t.transaction_date,
    t.due_date,
    DATEDIFF(CURDATE(), t.due_date) as days_overdue,
    t.fine_amount
FROM transactions t
JOIN users u ON t.user_id = u.user_id
JOIN books b ON t.book_id = b.book_id
WHERE t.transaction_type = 'BORROW' 
AND t.status = 'ACTIVE'
AND t.return_date IS NULL;

CREATE VIEW book_availability AS
SELECT 
    b.book_id,
    b.isbn,
    b.title,
    b.author,
    b.total_copies,
    b.available_copies,
    (b.total_copies - b.available_copies) as borrowed_copies
FROM books b
WHERE b.is_active = TRUE;

-- Create stored procedures
DELIMITER //

CREATE PROCEDURE GetUserBorrowingHistory(IN p_user_id INT)
BEGIN
    SELECT 
        t.transaction_id,
        b.title,
        b.author,
        t.transaction_date,
        t.due_date,
        t.return_date,
        t.status,
        t.fine_amount
    FROM transactions t
    JOIN books b ON t.book_id = b.book_id
    WHERE t.user_id = p_user_id
    ORDER BY t.transaction_date DESC;
END //

CREATE PROCEDURE GetOverdueBooks()
BEGIN
    SELECT 
        t.transaction_id,
        u.username,
        u.full_name,
        u.email,
        b.title,
        t.due_date,
        DATEDIFF(CURDATE(), t.due_date) as days_overdue
    FROM transactions t
    JOIN users u ON t.user_id = u.user_id
    JOIN books b ON t.book_id = b.book_id
    WHERE t.transaction_type = 'BORROW'
    AND t.status IN ('ACTIVE', 'OVERDUE')
    AND t.return_date IS NULL
    AND t.due_date < CURDATE();
END //

DELIMITER ;