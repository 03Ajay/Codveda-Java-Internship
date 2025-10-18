package com.banking.util;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Utility class for input validation and parsing
 * Provides common validation methods for banking operations
 */
public class InputValidator {

    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^ACC\\d{6}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]{2,50}$");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("1000000.00");
    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");

    /**
     * Validates account number format
     * 
     * @param accountNumber Account number to validate
     * @return true if valid format
     */
    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }

    /**
     * Validates account holder name
     * 
     * @param name Name to validate
     * @return true if valid name
     */
    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validates monetary amount
     * 
     * @param amount Amount to validate
     * @return true if valid amount
     */
    public static boolean isValidAmount(BigDecimal amount) {
        return amount != null
                && amount.compareTo(MIN_AMOUNT) >= 0
                && amount.compareTo(MAX_AMOUNT) <= 0;
    }

    /**
     * Parses and validates monetary amount from string
     * 
     * @param amountStr String representation of amount
     * @return BigDecimal amount
     * @throws NumberFormatException    if invalid format
     * @throws IllegalArgumentException if amount is out of valid range
     */
    public static BigDecimal parseAmount(String amountStr) {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Amount cannot be empty");
        }

        try {
            BigDecimal amount = new BigDecimal(amountStr.trim());
            if (!isValidAmount(amount)) {
                throw new IllegalArgumentException(
                        String.format("Amount must be between $%.2f and $%.2f", MIN_AMOUNT, MAX_AMOUNT));
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format. Please enter a valid number.");
        }
    }

    /**
     * Reads and validates a monetary amount from Scanner
     * 
     * @param scanner Scanner object for input
     * @param prompt  Prompt message for user
     * @return Valid BigDecimal amount
     */
    public static BigDecimal readValidAmount(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return parseAmount(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    /**
     * Reads and validates account holder name from Scanner
     * 
     * @param scanner Scanner object for input
     * @param prompt  Prompt message for user
     * @return Valid name string
     */
    public static String readValidName(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String name = scanner.nextLine().trim();

            if (isValidName(name)) {
                return name;
            } else {
                System.out.println("Error: Name must contain only letters and spaces, and be 2-50 characters long.");
                System.out.println("Please try again.");
            }
        }
    }

    /**
     * Reads and validates account number from Scanner
     * 
     * @param scanner Scanner object for input
     * @param prompt  Prompt message for user
     * @return Valid account number string
     */
    public static String readValidAccountNumber(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String accountNumber = scanner.nextLine().trim().toUpperCase();

            if (isValidAccountNumber(accountNumber)) {
                return accountNumber;
            } else {
                System.out.println("Error: Invalid account number format. Expected format: ACC######");
                System.out.println("Please try again.");
            }
        }
    }

    /**
     * Reads a valid integer choice from Scanner
     * 
     * @param scanner Scanner object for input
     * @param prompt  Prompt message for user
     * @param min     Minimum valid value
     * @param max     Maximum valid value
     * @return Valid integer choice
     */
    public static int readValidChoice(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);

                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.printf("Error: Please enter a number between %d and %d.%n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    /**
     * Confirms user action with yes/no prompt
     * 
     * @param scanner Scanner object for input
     * @param prompt  Confirmation prompt
     * @return true if user confirms (y/yes)
     */
    public static boolean confirmAction(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y") || response.equals("yes")) {
                return true;
            } else if (response.equals("n") || response.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}