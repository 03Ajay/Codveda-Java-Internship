# Basic Calculator - Java Console Application

## Description
A simple Java console application that performs basic arithmetic operations including addition, subtraction, multiplication, and division. The application handles edge cases such as division by zero and provides input validation.

## Features
- **Addition**: Add two numbers
- **Subtraction**: Subtract two numbers
- **Multiplication**: Multiply two numbers
- **Division**: Divide two numbers with zero-division error handling
- **Input Validation**: Handles invalid number inputs
- **User-friendly Menu**: Interactive menu system
- **Continuous Operation**: Option to perform multiple calculations

## Files
- `Calculator.java` - Main calculator application with interactive menu
- `CalculatorTest.java` - Test suite demonstrating all functionality
- `README.md` - This documentation file

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command prompt or terminal access

### Compilation
Open command prompt and navigate to the project directory, then compile:
```cmd
javac Calculator.java
javac CalculatorTest.java
```

### Running the Interactive Calculator
```cmd
java Calculator
```

### Running the Test Suite
```cmd
java CalculatorTest
```

## Usage Examples

### Interactive Mode
When you run the calculator, you'll see a menu like this:
```
=== Basic Calculator ===
1. Addition (+)
2. Subtraction (-)
3. Multiplication (*)
4. Division (/)
5. Exit
Choose an operation (1-5): 
```

### Sample Interaction
```
Choose an operation (1-5): 1
Enter first number: 15.5
Enter second number: 2.3
Result: 15.50 + 2.30 = 17.80

Would you like to perform another calculation? (y/n): y
```

## Edge Cases Handled
- **Division by Zero**: Throws ArithmeticException with descriptive message
- **Invalid Input**: Prompts user to re-enter valid numbers
- **Negative Numbers**: Properly handles negative number operations
- **Decimal Numbers**: Supports floating-point arithmetic

## Class Structure

### Calculator Class Methods
- `add(double a, double b)` - Addition operation
- `subtract(double a, double b)` - Subtraction operation
- `multiply(double a, double b)` - Multiplication operation
- `divide(double a, double b)` - Division operation with zero-check
- `displayMenu()` - Shows operation menu
- `getNumberInput(Scanner scanner, String prompt)` - Input validation helper
- `main(String[] args)` - Application entry point

## Error Handling
- Division by zero results in an ArithmeticException
- Invalid number inputs are caught and user is prompted to re-enter
- Menu choice validation ensures only valid options are accepted

## Future Enhancements
- Add more advanced operations (power, square root, etc.)
- Implement calculation history
- Add support for mathematical expressions
- Create GUI version using JavaFX or Swing