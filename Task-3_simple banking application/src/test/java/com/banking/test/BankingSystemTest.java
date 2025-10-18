package test.java.com.banking.test;

import com.banking.model.BankAccount;
import com.banking.exception.InsufficientFundsException;
import java.math.BigDecimal;

/**
 * Simple test class to verify banking functionality
 * This demonstrates the core banking operations
 */
public class BankingSystemTest {

    public static void main(String[] args) {
        System.out.println("=== Simple Banking System Test ===\n");

        try {
            // Test 1: Create account
            System.out.println("1. Testing Account Creation:");
            BankAccount account1 = new BankAccount("ACC001001", "John Doe", new BigDecimal("1000.00"));
            BankAccount account2 = new BankAccount("ACC001002", "Jane Smith", new BigDecimal("500.00"));

            System.out.println("✓ Created account: " + account1.getAccountNumber() +
                    " for " + account1.getAccountHolderName() +
                    " with balance: $" + account1.getBalance());
            System.out.println("✓ Created account: " + account2.getAccountNumber() +
                    " for " + account2.getAccountHolderName() +
                    " with balance: $" + account2.getBalance());

            // Test 2: Deposit money
            System.out.println("\n2. Testing Deposit:");
            System.out.println("Before deposit - Balance: $" + account1.getBalance());
            account1.deposit(new BigDecimal("250.50"));
            System.out.println("After depositing $250.50 - Balance: $" + account1.getBalance());

            // Test 3: Withdraw money
            System.out.println("\n3. Testing Withdrawal:");
            System.out.println("Before withdrawal - Balance: $" + account1.getBalance());
            account1.withdraw(new BigDecimal("100.00"));
            System.out.println("After withdrawing $100.00 - Balance: $" + account1.getBalance());

            // Test 4: Check balance
            System.out.println("\n4. Testing Balance Check:");
            System.out.println("Current balance for " + account1.getAccountHolderName() +
                    ": $" + account1.getBalance());

            // Test 5: Test insufficient funds
            System.out.println("\n5. Testing Insufficient Funds Error:");
            try {
                account2.withdraw(new BigDecimal("1000.00"));
            } catch (InsufficientFundsException e) {
                System.out.println("✓ Correctly caught insufficient funds: " + e.getMessage());
            }

            // Test 6: Test invalid amounts
            System.out.println("\n6. Testing Invalid Amount Validation:");
            try {
                account1.deposit(new BigDecimal("-50.00"));
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Correctly caught invalid deposit amount: " + e.getMessage());
            }

            // Test 7: Transaction history
            System.out.println("\n7. Testing Transaction History:");
            System.out.println("Transaction history for " + account1.getAccountHolderName() + ":");
            account1.getTransactionHistory()
                    .forEach(transaction -> System.out.println("  " + transaction.getFormattedTransaction()));

            System.out.println("\n=== All Tests Passed Successfully! ===");

        } catch (Exception e) {
            System.err.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}