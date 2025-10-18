package com.banking;

import com.banking.console.BankingConsole;

/**
 * Main application class for the Simple Banking System
 * Entry point for the banking application
 * 
 * @author Banking System Team
 * @version 1.0
 * @since 2025-10-18
 */
public class BankingApplication {

    /**
     * Main method - entry point of the application
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Initialize and start the banking console
            BankingConsole bankingConsole = new BankingConsole();
            bankingConsole.start();

        } catch (Exception e) {
            System.err.println("Fatal error occurred while starting the banking application:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}