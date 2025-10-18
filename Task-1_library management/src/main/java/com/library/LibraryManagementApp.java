package com.library;

import com.library.service.LibraryService;
import com.library.model.User;
import com.library.model.Book;
import com.library.model.Transaction;

import java.util.List;
import java.util.Scanner;

/**
 * Main application class for Library Management System
 */
public class LibraryManagementApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryService libraryService = new LibraryService();

    public static void main(String[] args) {
        System.out.println("=== Welcome to the Library Management System ===");
        if (!libraryService.testConnection()) {
            System.err.println("Database connection failed. Please check configuration.");
            return;
        }
        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    listAllBooks();
                    break;
                case 6:
                    listAllUsers();
                    break;
                case 7:
                    listUserBorrowings();
                    break;
                case 8:
                    listOverdueBooks();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the Library Management System!");
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Register User");
        System.out.println("2. Add Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. List All Books");
        System.out.println("6. List All Users");
        System.out.println("7. List User Borrowings");
        System.out.println("8. List Overdue Books");
        System.out.println("0. Exit");
    }

    private static void addUser() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter full name: ");
            String fullName = scanner.nextLine();
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine();
            System.out.print("Enter user type (STUDENT/FACULTY/STAFF): ");
            String typeStr = scanner.nextLine().toUpperCase();
            User.UserType userType = User.UserType.valueOf(typeStr);
            int userId = libraryService.registerUser(username, email, fullName, phone, userType);
            System.out.println("User registered successfully. User ID: " + userId);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void addBook() {
        try {
            System.out.print("Enter ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            System.out.print("Enter author: ");
            String author = scanner.nextLine();
            System.out.print("Enter publisher: ");
            String publisher = scanner.nextLine();
            int year = getIntInput("Enter publication year: ");
            System.out.print("Enter category: ");
            String category = scanner.nextLine();
            int totalCopies = getIntInput("Enter total copies: ");
            System.out.print("Enter shelf location: ");
            String shelf = scanner.nextLine();
            int bookId = libraryService.addBook(isbn, title, author, publisher, year, category, totalCopies, shelf);
            System.out.println("Book added successfully. Book ID: " + bookId);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void borrowBook() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter ISBN of book to borrow: ");
            String isbn = scanner.nextLine();
            if (libraryService.borrowBook(username, isbn)) {
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println("Failed to borrow book.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void returnBook() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter ISBN of book to return: ");
            String isbn = scanner.nextLine();
            if (libraryService.returnBook(username, isbn)) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Failed to return book.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void listAllBooks() {
        try {
            List<Book> books = libraryService.getAvailableBooks();
            System.out.println("\n--- Available Books ---");
            for (Book book : books) {
                System.out.println(book);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void listAllUsers() {
        try {
            List<User> users = libraryService.getAllActiveUsers();
            System.out.println("\n--- Active Users ---");
            for (User user : users) {
                System.out.println(user);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void listUserBorrowings() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            List<Transaction> borrowings = libraryService.getUserActiveBorrowings(username);
            System.out.println("\n--- Active Borrowings for " + username + " ---");
            for (Transaction t : borrowings) {
                System.out.println(t);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void listOverdueBooks() {
        try {
            List<Transaction> overdue = libraryService.getOverdueTransactions();
            System.out.println("\n--- Overdue Books ---");
            for (Transaction t : overdue) {
                System.out.println(t);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
