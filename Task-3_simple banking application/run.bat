@echo off
echo =======================================
echo    Simple Banking System - Running
echo =======================================
echo.

if not exist "bin\com\banking\BankingApplication.class" (
    echo ✗ Application not compiled! Please run compile.bat first.
    pause
    exit /b 1
)

echo Starting Banking Application...
echo.
java -cp bin com.banking.BankingApplication