import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
// ...existing code...

/**
 * FileProcessor - A comprehensive file handling program
 * 
 * This program demonstrates:
 * - Reading data from input files
 * - Processing content (word count, line count, character analysis)
 * - Writing processed results to output files
 * - Robust exception handling for file operations
 * 
 * @author CodeVita Task 2
 * @version 1.0
 */
public class FileProcessor {

    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_FILE = "output_report.txt";
    private static final String LOG_FILE = "processing_log.txt";

    public static void main(String[] args) {
        FileProcessor processor = new FileProcessor();

        try {
            // Create sample input file if it doesn't exist
            processor.createSampleInputFile();

            // Process the file
            FileAnalysisResult result = processor.processFile(INPUT_FILE);

            // Write results to output file
            processor.writeResultsToFile(result, OUTPUT_FILE);

            // Log the operation
            processor.logOperation("File processing completed successfully", LOG_FILE);

            System.out.println("✓ File processing completed successfully!");
            System.out.println("✓ Check '" + OUTPUT_FILE + "' for detailed analysis");
            System.out.println("✓ Check '" + LOG_FILE + "' for operation log");

        } catch (FileNotFoundException e) {
            handleFileNotFound(e);
        } catch (IOException e) {
            handleIOException(e);
        } catch (SecurityException e) {
            handleSecurityException(e);
        } catch (Exception e) {
            handleGenericException(e);
        }
    }

    /**
     * Creates a sample input file for demonstration
     */
    private void createSampleInputFile() throws IOException {
        if (!Files.exists(Paths.get(INPUT_FILE))) {
            String sampleContent = """
                    Welcome to the File Processing Demo!

                    This is a sample text file that contains multiple lines of text.
                    We will analyze this content to demonstrate file handling capabilities.

                    Key features being demonstrated:
                    - Reading from files
                    - Processing text content
                    - Counting words and lines
                    - Writing results to output files
                    - Handling various file exceptions

                    The program will count:
                    1. Total number of lines
                    2. Total number of words
                    3. Total number of characters
                    4. Number of blank lines
                    5. Average words per line

                    This comprehensive analysis showcases professional file handling in Java.
                    Thank you for using our File Processor!
                    """;

            Files.write(Paths.get(INPUT_FILE), sampleContent.getBytes());
            System.out.println("✓ Sample input file created: " + INPUT_FILE);
        }
    }

    /**
     * Processes the input file and returns analysis results
     */
    private FileAnalysisResult processFile(String inputFileName) throws IOException {
        System.out.println("📖 Reading and processing file: " + inputFileName);

        FileAnalysisResult result = new FileAnalysisResult();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                result.totalLines++;
                result.totalCharacters += line.length();

                if (line.trim().isEmpty()) {
                    result.blankLines++;
                } else {
                    // Count words in non-blank lines
                    String[] words = line.trim().split("\\s+");
                    result.totalWords += words.length;

                    // Track longest line
                    if (line.length() > result.longestLineLength) {
                        result.longestLineLength = line.length();
                        result.longestLine = line;
                    }
                }
            }

            // Calculate derived statistics
            result.nonBlankLines = result.totalLines - result.blankLines;
            result.averageWordsPerLine = result.nonBlankLines > 0 ? (double) result.totalWords / result.nonBlankLines
                    : 0.0;
            result.averageCharactersPerLine = result.totalLines > 0
                    ? (double) result.totalCharacters / result.totalLines
                    : 0.0;

        }

        System.out.println("✓ File processing completed");
        return result;
    }

    /**
     * Writes the analysis results to an output file
     */
    private void writeResultsToFile(FileAnalysisResult result, String outputFileName) throws IOException {
        System.out.println("📝 Writing results to: " + outputFileName);

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(outputFileName)))) {
            writer.println("=".repeat(60));
            writer.println("           FILE ANALYSIS REPORT");
            writer.println("=".repeat(60));
            writer.println();
            writer.println("Generated on: " + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("Input file: " + INPUT_FILE);
            writer.println();

            writer.println("📊 STATISTICAL ANALYSIS");
            writer.println("-".repeat(30));
            writer.printf("Total Lines:              %,d%n", result.totalLines);
            writer.printf("Non-blank Lines:          %,d%n", result.nonBlankLines);
            writer.printf("Blank Lines:              %,d%n", result.blankLines);
            writer.printf("Total Words:              %,d%n", result.totalWords);
            writer.printf("Total Characters:         %,d%n", result.totalCharacters);
            writer.println();

            writer.println("📈 DERIVED METRICS");
            writer.println("-".repeat(30));
            writer.printf("Average Words per Line:   %.2f%n", result.averageWordsPerLine);
            writer.printf("Average Chars per Line:   %.2f%n", result.averageCharactersPerLine);
            writer.printf("Longest Line Length:      %,d characters%n", result.longestLineLength);
            writer.println();

            if (result.longestLine != null && !result.longestLine.trim().isEmpty()) {
                writer.println("📝 LONGEST LINE PREVIEW");
                writer.println("-".repeat(30));
                writer.println(result.longestLine.length() > 80 ? result.longestLine.substring(0, 77) + "..."
                        : result.longestLine);
                writer.println();
            }

            writer.println("💡 INSIGHTS");
            writer.println("-".repeat(30));

            if (result.blankLines > 0) {
                double blankPercentage = (double) result.blankLines / result.totalLines * 100;
                writer.printf("• %.1f%% of lines are blank%n", blankPercentage);
            }

            if (result.averageWordsPerLine > 0) {
                if (result.averageWordsPerLine > 15) {
                    writer.println("• Text has high word density per line");
                } else if (result.averageWordsPerLine < 5) {
                    writer.println("• Text has low word density per line");
                } else {
                    writer.println("• Text has moderate word density per line");
                }
            }

            writer.println();
            writer.println("=".repeat(60));
            writer.println("Report generated by FileProcessor v1.0");
            writer.println("=".repeat(60));
        }

        System.out.println("✓ Results written successfully");
    }

    /**
     * Logs operations to a log file
     */
    private void logOperation(String message, String logFileName) {
        try (PrintWriter logger = new PrintWriter(
                Files.newBufferedWriter(Paths.get(logFileName),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {

            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logger.printf("[%s] %s%n", timestamp, message);

        } catch (IOException e) {
            System.err.println("⚠️  Warning: Could not write to log file: " + e.getMessage());
        }
    }

    // Exception Handling Methods

    private static void handleFileNotFound(FileNotFoundException e) {
        System.err.println("❌ ERROR: File not found!");
        System.err.println("   File: " + e.getMessage());
        System.err.println("   Solution: Ensure the input file exists in the current directory");
        logError("FileNotFoundException: " + e.getMessage());
    }

    private static void handleIOException(IOException e) {
        System.err.println("❌ ERROR: Input/Output operation failed!");
        System.err.println("   Details: " + e.getMessage());
        System.err.println("   Solution: Check file permissions and disk space");
        logError("IOException: " + e.getMessage());
    }

    private static void handleSecurityException(SecurityException e) {
        System.err.println("❌ ERROR: Security restriction encountered!");
        System.err.println("   Details: " + e.getMessage());
        System.err.println("   Solution: Check file access permissions");
        logError("SecurityException: " + e.getMessage());
    }

    private static void handleGenericException(Exception e) {
        System.err.println("❌ ERROR: Unexpected error occurred!");
        System.err.println("   Type: " + e.getClass().getSimpleName());
        System.err.println("   Details: " + e.getMessage());
        System.err.println("   Solution: Contact support with error details");
        logError("Exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
    }

    private static void logError(String errorMessage) {
        try (PrintWriter errorLogger = new PrintWriter(
                Files.newBufferedWriter(Paths.get("error_log.txt"),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {

            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            errorLogger.printf("[%s] ERROR: %s%n", timestamp, errorMessage);

        } catch (IOException logError) {
            System.err.println("⚠️  Critical: Could not write to error log: " + logError.getMessage());
        }
    }
}

/**
 * Data class to hold file analysis results
 */
class FileAnalysisResult {
    int totalLines = 0;
    int nonBlankLines = 0;
    int blankLines = 0;
    int totalWords = 0;
    int totalCharacters = 0;
    int longestLineLength = 0;
    String longestLine = "";
    double averageWordsPerLine = 0.0;
    double averageCharactersPerLine = 0.0;
}