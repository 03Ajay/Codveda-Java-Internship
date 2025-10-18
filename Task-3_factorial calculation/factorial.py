def factorial(n):
    """
    Recursive function to compute the factorial of a number.
    Handles edge cases for 0 and negative numbers.
    """
    if n < 0:
        raise ValueError("Factorial is not defined for negative numbers.")
    elif n == 0:
        return 1
    else:
        return n * factorial(n - 1)

if __name__ == "__main__":
    try:
        num = int(input("Enter a number to calculate its factorial: "))
        result = factorial(num)
        print(f"Factorial of {num} is {result}")
    except ValueError as e:
        print(e)
