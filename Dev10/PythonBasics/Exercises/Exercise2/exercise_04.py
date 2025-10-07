# Read Required String
# ====================

# 1. Define a function that reads a value from a user
# and issues a warning if the value is blank (whitespace) or empty.
# Re-prompt the user.
# If the value is not blank or empty, return it.
# (Hint: use a loop.)
# 
# Name: read_required_string
# Inputs: str (prompt to the user)
# Output: str (a non-blank, non-empty string)

def read_required_string(prompt):
    value = input(prompt).strip()
    while not value:
        print("Err: Value is Required")
        value = input(prompt).strip()

    return value

# Example
value = read_required_string("Favorite food?: ")
#
# Favorite food?:
# Value is required.
# Favorite food?:
# Value is required.
# Favorite food?:
# Value is required.
# Favorite food?: spaghetti
