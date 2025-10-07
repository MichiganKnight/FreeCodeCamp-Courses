import random

haystack = [None] * 100
haystack[random.randrange(len(haystack))] = "needle"

# We have a 100 element `None` list (absence of a value).
# A "needle" is randomly placed in the haystack (list).

# 1. Loop over elements in the haystack.
# When you find the needle, print its index.
# (Hint: use the enumerate function.)

for index, value in enumerate(haystack):
    if value == "needle":
        print(f"Found needle at index {index}.")
        break