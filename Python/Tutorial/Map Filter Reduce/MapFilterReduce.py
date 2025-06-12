print("=== Map Filter Reduce ===")
print("=== Map ===")

from functools import reduce

numbers = [1, 2, 3]

def double(a):
    return a * 2

result = map(double, numbers)

print(list(result))

double = lambda a: a * 2

result = map(double, numbers)
print(list(result))

result = map(lambda a: a * 2, numbers)
print(list(result))

print("\n=== Filter ===")

numbers = [1, 2, 3, 4, 5, 6]

def isEven(num):
    return num % 2 == 0

result = filter(isEven, numbers)
print(list(result))

result = filter(lambda num: num % 2 == 0, numbers)
print(list(result))

print("\n=== Reduce ===")

expenses = [
    ('Dinner', 80),
    ('Car Repair', 120)
]

sum = 0
for expense in expenses:
    sum += expense[1]

print(sum)

sum_reduce = reduce(lambda a, b: a[1] + b[1], expenses)
print(sum_reduce)