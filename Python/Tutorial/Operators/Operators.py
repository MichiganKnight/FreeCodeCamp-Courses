# === Arithmatic Operators ===
print("=== Arithmatic Operators ===")

# 1 + 1 = 2
# 1 + -1 = 0
# 2 - 1 = 1
# 2 * 2 = 4
# 4 / 2 = 2
# 4 % 3 = 1
# 4 ** 2 = 16
# 5 // 2 = 2

age = 8
#age += 8
age *= 8
print(age)

currentAge = 26
yearsToAdd = 8

finalAge = currentAge + yearsToAdd

print(f"I am {currentAge} years old and will be {finalAge} in {yearsToAdd} years\n")

# a = 1
# b = 2

# === Comparison Operators ===
print("=== Comparison Operators ===\n")

# a == b FALSE
# a != b TRUE
# a > b FALSE
# a <= b TRUE

# === Boolean Operators ===
print("=== Boolean Operators ===")

# condition1 = True
# condition2 = False

# not condition1 FALSE
# condition1 and condition2 FALSE
# condition1 or condition2 TRUE

print(0 or 1) # 1
print(False or "Hey") # Hey
print("Hi" or "Hey") # Hi
print([] or False) # False
print(False or []) # []
print("")
print(0 and 1) # 0
print(1 and 0) # 0
print(False and "Hey") # False
print("Hi" and "Hey") # Hey
print([] and False) # []
print(False and []) # False

# === Bitwise Operators ===
print("=== Bitwise Operators ===\n")

# & Performs Binary AND
# | Performs Binary OR
# ^ Performs a Binary XOR Operation
# ~ Performs a Binary NOT Operation
# << Shift Operation Left
# >> Shift Operation Right

# === Is and In Operators ===
print("=== Is and In Operators ===\n")

# is - Identity Operator
# in - Membership Operator

# === Ternary Operators ===
print("=== Ternary Operators ===")

age = 18

def is_adult(age):
    if age > 18:
        return True
    else:
        return False

def is_adult2(age):
    return True if age > 18 else False

print(is_adult(age))
print(is_adult2(age))