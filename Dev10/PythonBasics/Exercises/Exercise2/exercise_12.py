favorites = []

# 1. Copy your read_required_string function
# into this file.

def read_required_string(prompt):
    value = input(prompt).strip()
    while not value:
        print("Err: Value is Required")
        value = input(prompt).strip()

    return value

# 2. Define a function that uses read_required_string
# to add a favorite (food, books, music, color, animals, whatever...)
# to the `favorites` list.

def add_favorite(prompt):
    value = read_required_string(prompt)
    favorites.append(value)

# 3. Define a function that prints `favorites` to the terminal.

def print_favorites():
    print(favorites)

# 4. Define a function that removes 1 to n `favorites`
# from the end of the list.
# If the argument is larger than the list length, display an error message.
# Otherwise, remove n items from the end of the list.

def remove_favorite(prompt):
    value = read_required_string(prompt)
    if int(value) > len(favorites):
        print("Err: Value is too large")
    else:
        for i in range(int(value)):
            favorites.pop()

# 5. Define a function that displays a menu:
# ==============
# Menu
# 1. Add Favorite
# 2. Print Favorites
# 3. Remove Favorites
# 4. Exit
# Select [1-4]:
# ==============
# Use menu decisions to execute functions.
# Exit terminates the program.

def display_menu():
    print("Menu")
    print("1. Add Favorite")
    print("2. Print Favorites")
    print("3. Remove Favorites")
    print("4. Exit")
    choice = int(input("Select [1-4]: "))

    match choice:
        case 1:
            add_favorite("Enter Favorite: ")
            display_menu()
        case 2:
            print_favorites()
            display_menu()
        case 3:
            remove_favorite("Enter Number of Favorites to Remove: ")
            display_menu()
        case 4:
            print("Goodbye.")

display_menu()
