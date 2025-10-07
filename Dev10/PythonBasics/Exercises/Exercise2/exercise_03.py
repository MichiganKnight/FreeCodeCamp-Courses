# Sum Between
# ===========

# 1. Define a function that sums numbers between
# the first parameter (inclusive)
# and the second parameter (exclusive).

# Docstring
# """
# Sums numbers between values.
# Start at the first parameter and loop by 1 until the second parameter.
# The "current" value is added to the sum.
#
# Parameters:
# min_inclusive (int): start of the loop
# max_exclusive (int): when max condition is met, terminate the loop
#
# Returns:
# int: sum of range
# """

def sum_between(min_inclusive, max_exclusive):
    sum = 0
    for i in range(min_inclusive, max_exclusive):
        sum += i

    return sum


# Example
result = sum_between(2, 7)
print(result)  # 20

print(sum_between(1, 100))  # 4950
print(sum_between(100, 0))  # 0
