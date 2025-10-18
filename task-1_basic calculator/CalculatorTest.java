/**
 * Test class for Calculator
 * Demonstrates various test cases including edge cases
 */
public class CalculatorTest {
    
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        
        System.out.println("=== Calculator Test Suite ===\n");
        
        // Test Addition
        System.out.println("Testing Addition:");
        System.out.println("5 + 3 = " + calc.add(5, 3));
        System.out.println("10.5 + 2.3 = " + calc.add(10.5, 2.3));
        System.out.println("-5 + 8 = " + calc.add(-5, 8));
        System.out.println("0 + 0 = " + calc.add(0, 0));
        
        // Test Subtraction
        System.out.println("\nTesting Subtraction:");
        System.out.println("10 - 4 = " + calc.subtract(10, 4));
        System.out.println("15.7 - 3.2 = " + calc.subtract(15.7, 3.2));
        System.out.println("5 - 8 = " + calc.subtract(5, 8));
        System.out.println("0 - 5 = " + calc.subtract(0, 5));
        
        // Test Multiplication
        System.out.println("\nTesting Multiplication:");
        System.out.println("6 * 7 = " + calc.multiply(6, 7));
        System.out.println("2.5 * 4 = " + calc.multiply(2.5, 4));
        System.out.println("-3 * 5 = " + calc.multiply(-3, 5));
        System.out.println("0 * 100 = " + calc.multiply(0, 100));
        
        // Test Division
        System.out.println("\nTesting Division:");
        System.out.println("15 / 3 = " + calc.divide(15, 3));
        System.out.println("22.5 / 4.5 = " + calc.divide(22.5, 4.5));
        System.out.println("-10 / 2 = " + calc.divide(-10, 2));
        System.out.println("7 / 2 = " + calc.divide(7, 2));
        
        // Test Division by Zero (Edge Case)
        System.out.println("\nTesting Division by Zero (Edge Case):");
        try {
            double result = calc.divide(10, 0);
            System.out.println("10 / 0 = " + result);
        } catch (ArithmeticException e) {
            System.out.println("10 / 0 = " + e.getMessage());
        }
        
        try {
            double result = calc.divide(0, 0);
            System.out.println("0 / 0 = " + result);
        } catch (ArithmeticException e) {
            System.out.println("0 / 0 = " + e.getMessage());
        }
        
        System.out.println("\n=== All tests completed ===");
    }
}