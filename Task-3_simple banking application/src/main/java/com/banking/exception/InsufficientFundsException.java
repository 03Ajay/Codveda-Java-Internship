package com.banking.exception;

/**
 * Custom exception for insufficient funds scenarios
 * Extends RuntimeException for easier handling in banking operations
 */
public class InsufficientFundsException extends Exception {

    /**
     * Constructs a new InsufficientFundsException with default message
     */
    public InsufficientFundsException() {
        super("Insufficient funds in account");
    }

    /**
     * Constructs a new InsufficientFundsException with specified message
     * 
     * @param message Detail message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }

    /**
     * Constructs a new InsufficientFundsException with specified message and cause
     * 
     * @param message Detail message
     * @param cause   The cause of this exception
     */
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InsufficientFundsException with specified cause
     * 
     * @param cause The cause of this exception
     */
    public InsufficientFundsException(Throwable cause) {
        super(cause);
    }
}