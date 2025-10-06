# Menu
# ===============

menu = """1. Add a recipe.
2. Update a recipe.
3. Delete a recipe.
4. Exit.
Select [1-4]: """

option = input(menu)

if option == "1":
    print("Recipe added!")
elif option == "2":
    print("Recipe updated!")
elif option == "3":
    print("Recipe deleted!")
elif option == "4":
    print("Goodbye.")
else:
    print("I don't understand that option.")

# 1. Use an if/elif/else statement to evaluate the `option`'s
# value and print a message based on the condition.
# 1 should print "Recipe added!"
# 2 should print "Recipe updated!"
# 3 should print "Recipe deleted!"
# 4 should print "Goodbye."
# Any other value should print "I don't understand that option."

# 2. Challenge: use a `match` statement to implement the same rules.
