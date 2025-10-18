# Task 2: File Handling - Reading and Writing

This project demonstrates professional file handling in Java with comprehensive error handling and text processing capabilities.

## 📋 Project Overview

The project includes two Java programs that showcase different approaches to file handling:

1. **FileProcessor.java** - Advanced file processing with detailed analysis
2. **SimpleFileDemo.java** - Basic file handling demonstration

## 🎯 Objectives Achieved

✅ **Read text files and process content**

- Count words, lines, and characters
- Analyze text statistics
- Handle blank lines and special cases

✅ **Write processed data to output files**

- Generate detailed analysis reports
- Create formatted output with insights
- Maintain operation logs

✅ **Handle file-related exceptions**

- FileNotFoundException
- IOException
- SecurityException
- Generic exception handling

## 🚀 How to Run

### Method 1: Using FileProcessor (Advanced)

```bash
# Compile the program
javac FileProcessor.java

# Run the program
java FileProcessor
```

**Output Files Generated:**

- `input.txt` - Sample input file (auto-generated)
- `output_report.txt` - Detailed analysis report
- `processing_log.txt` - Operation log
- `error_log.txt` - Error log (if errors occur)

### Method 2: Using SimpleFileDemo (Basic)

```bash
# Compile the program
javac SimpleFileDemo.java

# Run the program
java SimpleFileDemo
```

**Output Files Generated:**

- `sample_input.txt` - Sample input file (auto-generated)
- `analysis_result.txt` - Basic analysis results

## 📊 Features Demonstrated

### File Reading Operations

- Using `BufferedReader` for efficient reading
- Line-by-line processing
- Character and word counting
- Blank line detection

### File Writing Operations

- Using `PrintWriter` for formatted output
- Creating detailed reports
- Logging operations with timestamps

### Text Processing

- Word counting with regex splitting
- Line statistics calculation
- Character analysis
- Average calculations

### Exception Handling

- **FileNotFoundException**: Handles missing input files
- **IOException**: Manages read/write errors
- **SecurityException**: Handles permission issues
- **Generic Exception**: Catches unexpected errors

## 🔧 Code Structure

### FileProcessor.java Components

```
├── Main processing logic
├── FileAnalysisResult class (data holder)
├── File creation methods
├── Text processing algorithms
├── Report generation
├── Logging system
└── Comprehensive exception handlers
```

### Key Methods

- `processFile()` - Main file processing logic
- `writeResultsToFile()` - Generate formatted reports
- `createSampleInputFile()` - Auto-generate test data
- `logOperation()` - Operation logging
- Exception handling methods for different error types

## 📈 Sample Output

The programs generate detailed reports including:

```
==========================================================
           FILE ANALYSIS REPORT
==========================================================

📊 STATISTICAL ANALYSIS
------------------------------
Total Lines:              15
Non-blank Lines:          12
Blank Lines:              3
Total Words:              89
Total Characters:         672

📈 DERIVED METRICS
------------------------------
Average Words per Line:   7.42
Average Chars per Line:   44.80
Longest Line Length:      65 characters

💡 INSIGHTS
------------------------------
• 20.0% of lines are blank
• Text has moderate word density per line
```

## 🛡️ Error Handling Examples

The programs handle various error scenarios:

```java
// File not found
catch (FileNotFoundException e) {
    System.err.println("❌ ERROR: File not found!");
    // Provide solution guidance
}

// IO operations
catch (IOException e) {
    System.err.println("❌ ERROR: Input/Output operation failed!");
    // Log error details
}

// Security restrictions
catch (SecurityException e) {
    System.err.println("❌ ERROR: Security restriction encountered!");
    // Handle permission issues
}
```

## 💡 Professional Features

1. **Robust Error Handling**: Comprehensive exception management
2. **Logging System**: Operation and error logging with timestamps
3. **Data Validation**: Input validation and edge case handling
4. **Performance**: Efficient file I/O using buffered streams
5. **Documentation**: Extensive JavaDoc comments
6. **User Experience**: Clear console output with status indicators
7. **Maintainability**: Modular code structure with separate classes

## 🎓 Learning Outcomes

This project demonstrates:

- Professional Java file handling techniques
- Exception handling best practices
- Text processing algorithms
- Report generation and formatting
- Logging and debugging strategies
- Code documentation and structure

## 📝 Notes

- Both programs auto-generate sample input files for testing
- All file operations use modern Java NIO.2 API
- Error messages include helpful solutions
- Output files are formatted for readability
- Logging provides audit trail for operations
