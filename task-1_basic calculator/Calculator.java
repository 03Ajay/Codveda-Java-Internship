import java.util.Scanner;

/**
 * Basic Calculator Class
 * Performs basic arithmetic operations: addition, subtraction, multiplication, division
 * Handles edge cases like division by zero
 */
public class Calculator {
    
    /**
     * Addition method
     * @param a first number
     * @param b second number
     * @return sum of a and b
     */
    public double add(double a, double b) {
        return a + b;
    }
    
    /**
     * Subtraction method
     * @param a first number
     * @param b second number
     * @return difference of a and b
     */
    public double subtract(double a, double b) {
        return a - b;
    }
    
    /**
     * Multiplication method
     * @param a first number
     * @param b second number
     * @return product of a and b
     */
    public double multiply(double a, double b) {
        return a * b;
    }
    
    /**
     * Division method
     * @param a dividend
     * @param b divisor
     * @return quotient of a divided by b
     * @throws ArithmeticException if divisor is zero
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Error: Division by zero is not allowed!");
        }
        return a / b;
    }
    
    /**
     * Display available operations menu
     */
    public void displayMenu() {
        System.out.println("\n=== Basic Calculator ===");
        System.out.println("1. Addition (+)");
        System.out.println("2. Subtraction (-)");
        System.out.println("3. Multiplication (*)");
        System.out.println("4. Division (/)");
        System.out.println("5. Exit");
        System.out.print("Choose an operation (1-5): ");
    }
    
    /**
     * Get number input from user with validation
     * @param scanner Scanner object for input
     * @param prompt Message to display to user
     * @return valid double number
     */
    public double getNumberInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number!");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    /**
     * Main method - Entry point of the application
     */
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculation = true;
        
        System.out.println("Welcome to Basic Calculator!");
        
        while (continueCalculation) {
            try {
                calculator.displayMenu();
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1: // Addition
                        double num1 = calculator.getNumberInput(scanner, "Enter first number: ");
                        double num2 = calculator.getNumberInput(scanner, "Enter second number: ");
                        double sum = calculator.add(num1, num2);
                        System.out.printf("Result: %.2f + %.2f = %.2f\n", num1, num2, sum);
                        break;
                        
                    case 2: // Subtraction
                        num1 = calculator.getNumberInput(scanner, "Enter first number: ");
                        num2 = calculator.getNumberInput(scanner, "Enter second number: ");
                        double difference = calculator.subtract(num1, num2);
                        System.out.printf("Result: %.2f - %.2f = %.2f\n", num1, num2, difference);
                        break;
                        
                    case 3: // Multiplication
                        num1 = calculator.getNumberInput(scanner, "Enter first number: ");
                        num2 = calculator.getNumberInput(scanner, "Enter second number: ");
                        double product = calculator.multiply(num1, num2);
                        System.out.printf("Result: %.2f * %.2f = %.2f\n", num1, num2, product);
                        break;
                        
                    case 4: // Division
                        num1 = calculator.getNumberInput(scanner, "Enter dividend: ");
                        num2 = calculator.getNumberInput(scanner, "Enter divisor: ");
                        try {
                            double quotient = calculator.divide(num1, num2);
                            System.out.printf("Result: %.2f / %.2f = %.2f\n", num1, num2, quotient);
                        } catch (ArithmeticException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                        
                    case 5: // Exit
                        System.out.println("Thank you for using Basic Calculator!");
                        continueCalculation = false;
                        break;
                        
                    default:
                        System.out.println("Error: Invalid choice! Please select 1-5.");
                        break;
                }
                
                // Ask if user wants to perform another calculation
                if (continueCalculation) {
                    System.out.print("\nWould you like to perform another calculation? (y/n): ");
                    scanner.nextLine(); // Clear buffer
                    String response = scanner.nextLine().toLowerCase();
                    if (!response.equals("y") && !response.equals("yes")) {
                        System.out.println("Thank you for using Basic Calculator!");
                        continueCalculation = false;
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Error: Invalid input! Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        scanner.close();
    }
}