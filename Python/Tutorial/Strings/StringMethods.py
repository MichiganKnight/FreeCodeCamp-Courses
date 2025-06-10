print("=== String Methods ===")

print("Drew".upper())
print("DrEW".lower())
print("DrEW person".title())
print("person".islower())

# isalpha() - Check if a string contains only characters and is not empty
# isalnum() - Check if a string contains characters or digits and is not empty
# isdecimal() - Check if a string contains digits and is not empty
# lower() - Get a lowercase version of a string
# islower() - Check is a string is lowercase
# upper() - Get an uppercase version of a string
# isupper() - Checks if a string is uppercase
# title() - Get a capitalized version of a string
# startswith() - Check if the string starts with a specific substring
# endswith() - Check if the string ends with a specific substring
# replace() - Replace part of a string
# split() - Split a string on a specific character separator
# strip() - Trim the whitespace to a string
# join() - Append new letters to a string
# find() - Find the position of a substring

print("\n=== String Methods Part 2 ===")

name = "Drew"
print(name.lower())
print(name)
print(len(name))
print("re" in name)

print("\n=== Escaping Strings ===")

name2 = "Dr\"ew"
name3 = 'Dr"\'ew'
name4 = 'Dr\new'
name5 = 'Dr\\ew'

print(name2)
print(name3)
print(name4)
print(name5)

print("\n=== Get Character From String ===")

name = "Drew"
name2 = "Drew is Cool"

print(name[0])
print(name[1])
print(name[2])
print(name[3])

print("")

print(name[1:3])
print(name2[1:7])
print(name2[:7])
print(name2[5:])
