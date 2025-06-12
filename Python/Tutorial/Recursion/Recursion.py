print("=== Recursion ===")

# Function can run itself

# 3! = 3 * 2 * 1 = 6
# 4! = 4 * 3 * 2 * 1 = 24
# 5! = 5 * 4 * 3 * 2 * 1 = 120

def factorial(n):
    if n == 1:
        return 1
    return n * factorial(n - 1)

print(factorial(3))
print(factorial(4))
print(factorial(5))