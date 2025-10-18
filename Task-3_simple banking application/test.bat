@echo off
echo =======================================
echo    Simple Banking System - Testing
echo =======================================
echo.

if not exist "bin\test\java\com\banking\test\BankingSystemTest.class" (
    echo ✗ Tests not compiled! Please run compile.bat first.
    pause
    exit /b 1
)

echo Running Banking System Tests...
echo.
java -cp bin test.java.com.banking.test.BankingSystemTest

echo.
echo =======================================
echo Testing completed!
echo =======================================
pause