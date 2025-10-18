@echo off
echo =======================================
echo    Simple Banking System - Compiler
echo =======================================
echo.

echo Creating bin directory...
if not exist "bin" mkdir bin

echo Compiling Java source files...
javac -d bin -cp src\main\java src\main\java\com\banking\*.java src\main\java\com\banking\model\*.java src\main\java\com\banking\service\*.java src\main\java\com\banking\console\*.java src\main\java\com\banking\util\*.java src\main\java\com\banking\exception\*.java

if %ERRORLEVEL% == 0 (
    echo.
    echo ✓ Compilation successful!
    echo.
    echo Compiling test files...
    javac -d bin -cp "bin;src\test\java" src\test\java\com\banking\test\*.java
    
    if %ERRORLEVEL% == 0 (
        echo ✓ Test compilation successful!
        echo.
        echo =======================================
        echo Available Commands:
        echo =======================================
        echo 1. Run Application: run.bat
        echo 2. Run Tests: test.bat
        echo =======================================
    ) else (
        echo ✗ Test compilation failed!
    )
) else (
    echo ✗ Compilation failed!
)

pause