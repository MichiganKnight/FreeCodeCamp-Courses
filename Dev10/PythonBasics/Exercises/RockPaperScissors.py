import random

print("=== Rock Paper Scissors ===")

name = input("Enter Your Name: ").strip()
print(f"{name}, Choose")
print("1. Rock")
print("2. Paper")
print("3. Scissors")

while True:
    choice = input("Enter Choice [1-3]: ")
    if choice == "1":
        print(f"{name} Chose Rock")

        computer_choice = random.randint(1, 3)
        if computer_choice == 1:
            print("Computer Chose Rock")
            print("It's a Tie!")
        elif computer_choice == 2:
            print("Computer Chose Paper")
            print("Computer Wins!")
        else:
            print("Computer Chose Scissors")
            print(f"{name} Wins!")
        break
    elif choice == "2":
        print(f"{name} Chose Paper")

        computer_choice = random.randint(1, 3)
        if computer_choice == 1:
            print("Computer Chose Rock")
            print(f"{name} Wins!")
        elif computer_choice == 2:
            print("Computer Chose Paper")
            print("It's a Tie!")
        else:
            print("Computer Chose Scissors")
            print("Computer Wins!")
        break
    elif choice == "3":
        print(f"{name} Chose Scissors")

        computer_choice = random.randint(1, 3)
        if computer_choice == 1:
            print("Computer Chose Rock")
            print("Computer Wins!")
        elif computer_choice == 2:
            print("Computer Chose Paper")
            print(f"{name} Wins!")
        else:
            print("Computer Chose Scissors")
            print("It's a Tie!")
        break
    else:
        print("Invalid Choice!")
