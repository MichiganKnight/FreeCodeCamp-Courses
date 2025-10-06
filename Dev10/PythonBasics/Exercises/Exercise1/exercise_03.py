# 1. Run this file.
# Collect the user's (your) name.
name = input("What's your name?: ")
print(f"Name: {name}")

# 2. Collect the user's favorite music genre.
genre = input("Favorite music genre?: ")
print(f"Genre: {genre}")

# 3. Collect the user's favorite genre rating, 0-100.
# (No need to validate, but be sure to enter a valid integer.)
rating = int(input("Favorite genre rating?: "))
print(f"Rating: {rating}")

other_genre = bool(input("Do you enjoy other genres?: "))
print(f"Other Genres?: {other_genre}")

# 4. Ask the user if they enjoy other genres.
# The variable should be a boolean.
# You can either convert the `input` function string to a bool(input("prompt"))
# using the boolean literal "True" or "False"
# or compare strings: yes/no, y/n, etc.

# 5. Move all `input` operations to the top of the file.
# Collect the four questions.
# Print at the end.
