import random

def read_required_string(prompt):
    value = ""
    while not value:
        value = input(prompt).strip()
    return value

first_name = read_required_string("Enter Your First Name: ")
last_name = read_required_string("Enter Your Last Name: ")
favorite_food = read_required_string("Enter Your Favorite Food: ")
music_genre = read_required_string("Enter Your Favorite Music Genre: ")
video_game = read_required_string("Enter Your Favorite Video Game: ")

print(f"Name: {first_name} {last_name}")
print(f"Favorite Food: {favorite_food}")
print(f"Favorite Music Genre: {music_genre}")
print(f"Favorite Video Game: {video_game}")

def create_hello(value):
    return f"Hello, {value}"

name = "Guillermo"

message = create_hello(name)
print(message)

message = create_hello("Avivah")
print(message)

honorary = "Dr. "
print(create_hello(honorary + "Itch"))

def average(a, b, c):
    return (a + b + c) / 3

result = average(1, 2, 3)
print(result)
print(average(1000, 0, -1000))
print(average(255, 255, 255))

def random_letter():
    letters = "abcdefghijklmnopqrstuvwxyz"
    return random.choice(letters)

letter = random_letter()
print(letter)
print(random_letter())
print(random_letter())

def repeat_print(message, count):
    for _ in range(count):
        print(message)

print("Your Garden: ")
repeat_print("Hosta", 3)
repeat_print("Peony", 2)
repeat_print("Dandelion", 3)
repeat_print("Ghostflower", -9)

def read_required_string(prompt):
    value = input(prompt).strip()
    while not value:
        print("Err: Value is Required")
        value = input(prompt).strip()

    return value

def read_int(prompt):
    value = read_required_string(prompt)
    while not value.isnumeric():
        print("Err: Value must be an integer")
        value = read_required_string(prompt)

    return int(value)

def run():
    name = read_required_string("Enter Your Name: ")
    cookbook_count = read_int("Enter Your Cookbook Count: ")
    self_help_count = read_int("Enter Your Self Help Count: ")
    total_books = cookbook_count + self_help_count

    print(name)
    print(f'You Own {total_books} "Books"')

run()