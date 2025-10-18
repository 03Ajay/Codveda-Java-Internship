import java.io.*;
import java.nio.file.*;

/**
 * SimpleFileDemo - A straightforward demonstration of file handling
 * 
 * This program shows basic file operations:
 * - Reading from a text file
 * - Processing the content (counting words and lines)
 * - Writing results to an output file
 * - Handling file exceptions
 */
public class SimpleFileDemo {

    public static void main(String[] args) {
        String inputFile = "sample_input.txt";
        String outputFile = "analysis_result.txt";

        try {
            // Create a sample input file
            createSampleFile(inputFile);

            // Read and process the file
            processFile(inputFile, outputFile);

            System.out.println("✅ File processing completed successfully!");
            System.out.println("📄 Check '" + outputFile + "' for results");

        } catch (FileNotFoundException e) {
            System.err.println("❌ File not found: " + e.getMessage());
            System.err.println("💡 Make sure the input file exists");
        } catch (IOException e) {
            System.err.println("❌ IO Error: " + e.getMessage());
            System.err.println("💡 Check file permissions and disk space");
        } catch (Exception e) {
            System.err.println("❌ Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Creates a sample input file for testing
     */
    private static void createSampleFile(String fileName) throws IOException {
        String content = """
                Hello World! This is a sample text file.

                This file contains multiple lines of text.
                We will count the words and lines in this file.

                Some interesting facts:
                - Java is a powerful programming language
                - File handling is an important concept
                - Exception handling ensures robust programs

                End of sample text.
                """;

        Files.write(Paths.get(fileName), content.getBytes());
        System.out.println("📝 Created sample file: " + fileName);
    }

    /**
     * Reads input file, processes content, and writes results
     */
    private static void processFile(String inputFile, String outputFile) throws IOException {
        int lineCount = 0;
        int wordCount = 0;
        int characterCount = 0;
        int blankLines = 0;

        // Read and process input file
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                characterCount += line.length();

                if (line.trim().isEmpty()) {
                    blankLines++;
                } else {
                    // Count words (split by whitespace)
                    String[] words = line.trim().split("\\s+");
                    wordCount += words.length;
                }
            }
        }

        // Write results to output file
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(outputFile)))) {
            writer.println("========================================");
            writer.println("       FILE ANALYSIS RESULTS");
            writer.println("========================================");
            writer.println();
            writer.println("Input File: " + inputFile);
            writer.println();
            writer.println("📊 Statistics:");
            writer.println("  Total Lines: " + lineCount);
            writer.println("  Text Lines: " + (lineCount - blankLines));
            writer.println("  Blank Lines: " + blankLines);
            writer.println("  Total Words: " + wordCount);
            writer.println("  Total Characters: " + characterCount);
            writer.println();

            if (lineCount > 0) {
                double avgWordsPerLine = (double) wordCount / (lineCount - blankLines);
                writer.printf("  Average Words per Line: %.2f%n", avgWordsPerLine);
            }

            writer.println();
            writer.println("========================================");
            writer.println("Analysis completed successfully!");
            writer.println("========================================");
        }

        System.out.println("📊 Processing Summary:");
        System.out.println("  Lines processed: " + lineCount);
        System.out.println("  Words counted: " + wordCount);
        System.out.println("  Characters read: " + characterCount);
    }
}