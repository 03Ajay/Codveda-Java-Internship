import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int lowerBound = 1;
        int upperBound = 100;
        int numberToGuess = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        int maxAttempts = 7;
        int attempts = 0;
        boolean guessedCorrectly = false;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between " + lowerBound + " and " + upperBound + ".");
        System.out.println("You have " + maxAttempts + " attempts to guess it.");

        while (attempts < maxAttempts && !guessedCorrectly) {
            System.out.print("Enter your guess: ");
            String input = scanner.nextLine();
            int guess;
            try {
                guess = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                continue;
            }
            attempts++;
            if (guess < lowerBound || guess > upperBound) {
                System.out.println("Please guess a number between " + lowerBound + " and " + upperBound + ".");
                continue;
            }
            if (guess < numberToGuess) {
                System.out.println("Too low!");
            } else if (guess > numberToGuess) {
                System.out.println("Too high!");
            } else {
                System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                guessedCorrectly = true;
            }
        }
        if (!guessedCorrectly) {
            System.out.println("Sorry, you've used all attempts. The number was: " + numberToGuess);
        }
        scanner.close();
    }
}
