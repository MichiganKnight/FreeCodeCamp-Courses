print("=== Booleans ===")

done = True

print(type(done) == bool)

if done:
    print("Yes")
else:
    print("No")

print("\n=== Any Function ===")

book_1_read = True
book_2_read = True

read_any_book = any([book_1_read, book_2_read]) # Returns True if ANY are True
print(read_any_book)

print("\n=== All Function ===")

ingredients_purchases = True
meal_cooked = False

ready_to_serve = all([ingredients_purchases, meal_cooked]) # Returns True if ALL are True
print(ready_to_serve)