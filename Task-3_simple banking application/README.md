# Simple Banking System

A professional Java-based banking application that provides secure banking operations with proper error handling and user-friendly console interface.

## 🏦 Features

- **Account Management**

  - Create new bank accounts with initial deposits
  - Secure account access with account number validation
  - Account information display

- **Banking Operations**

  - Deposit money with validation
  - Withdraw money with insufficient funds protection
  - Transfer money between accounts
  - Real-time balance checking

- **Transaction Management**

  - Complete transaction history tracking
  - Recent transactions view (last 10)
  - Detailed transaction records with timestamps and IDs

- **Security & Validation**
  - Thread-safe operations for concurrent access
  - Comprehensive input validation
  - Proper error handling and user feedback
  - BigDecimal precision for monetary calculations

## 🚀 Getting Started

### Prerequisites

- Java 8 or higher
- Command line terminal

### Running the Application

1. **Compile the Application**

   ```bash
   # Navigate to the project directory
   cd "d:\Codevita\Task-3_simple banking application"

   # Compile all Java files
   javac -d bin -cp src\main\java src\main\java\com\banking\*.java src\main\java\com\banking\model\*.java src\main\java\com\banking\service\*.java src\main\java\com\banking\console\*.java src\main\java\com\banking\util\*.java src\main\java\com\banking\exception\*.java
   ```

2. **Run the Application**

   ```bash
   # Run the main application
   java -cp bin com.banking.BankingApplication
   ```

3. **Run Tests**

   ```bash
   # Compile tests
   javac -d bin -cp "bin;src\test\java" src\test\java\com\banking\test\*.java

   # Run tests
   java -cp bin test.java.com.banking.test.BankingSystemTest
   ```

## 📱 How to Use

### Main Menu Options

1. **Create New Account** - Set up a new bank account with initial deposit
2. **Access Existing Account** - Login to an existing account using account number
3. **System Information** - View application details and statistics
4. **Exit Application** - Safely close the application

### Account Operations

1. **Check Balance** - View current account balance
2. **Deposit Money** - Add funds to the account
3. **Withdraw Money** - Remove funds (with balance validation)
4. **Transfer Money** - Send money to another account
5. **View Transaction History** - See all transactions
6. **View Recent Transactions** - See last 10 transactions
7. **Account Information** - Display detailed account info
8. **Logout** - Return to main menu

## 🏗️ Architecture

The application follows a layered architecture with proper separation of concerns:

```
com.banking/
├── model/               # Data models
│   ├── BankAccount.java
│   ├── Transaction.java
│   └── TransactionType.java
├── service/             # Business logic
│   └── BankingService.java
├── console/             # User interface
│   └── BankingConsole.java
├── util/                # Utilities
│   └── InputValidator.java
├── exception/           # Custom exceptions
│   └── InsufficientFundsException.java
└── BankingApplication.java  # Main entry point
```

## 🔒 Key Design Features

- **Thread Safety**: All balance operations are synchronized
- **Data Precision**: BigDecimal for accurate monetary calculations
- **Immutable Transactions**: Transaction records cannot be modified
- **Comprehensive Validation**: Input validation at multiple levels
- **Professional UI**: Clean console interface with proper formatting
- **Error Handling**: Graceful error handling with user-friendly messages

## 📊 Sample Usage

```java
// Create account
BankAccount account = new BankAccount("ACC001001", "John Doe", new BigDecimal("1000.00"));

// Deposit money
account.deposit(new BigDecimal("250.50"));

// Withdraw money
account.withdraw(new BigDecimal("100.00"));

// Check balance
BigDecimal balance = account.getBalance();
```

## 🧪 Testing

The application includes comprehensive testing:

- Unit tests for core banking operations
- Error handling validation
- Edge case testing (insufficient funds, invalid inputs)
- Transaction history verification

## 📈 Future Enhancements

- Database integration for persistent storage
- Account types (Savings, Checking, etc.)
- Interest calculation
- Account statements generation
- Mobile/Web interface
- ATM simulation
- Multi-currency support

## 👥 Author

**Banking System Team**

- Version: 1.0
- Date: October 18, 2025

## 📄 License

This project is developed for educational purposes as part of the CodeVita Task 3 assignment.

---

_Professional Banking Application with Robust Error Handling and Security Features_
