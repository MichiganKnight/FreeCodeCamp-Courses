# Declare Variables
import random

print("=== Number Guessing Game ===")

def generate_random_number():
    return random.randint(1, 100)

def get_number():
    while True:
        guessing_number = input("Enter a Number Between 1 and 100: ")
        if guessing_number.isdigit():
            number = int(guessing_number)
            if 1 <= number <= 100:
                return number
        print("Invalid Number. Please Enter a Number Between 1 and 100")

def check_win(guessed_number, target_number):
    if guessed_number == target_number:
        return "You Win!"
    elif guessed_number > target_number:
        return "Number Too High!"
    elif guessed_number < target_number:
        return "Number Too Low!"
    return ""

while True:
    random_number = generate_random_number()
    guesses = 0

    while True:
        guessing_number = get_number()
        guesses += 1
        result = check_win(guessing_number, random_number)

        print(result)

        if result == "You Win!":
            print(f"The Number Was: {random_number}\nTotal Guesses: {guesses}")
            break

    play_again = input("Play again? (Y/N): ").strip().lower()
    if play_again != "y":
        print("Thanks For Playing!")
        break