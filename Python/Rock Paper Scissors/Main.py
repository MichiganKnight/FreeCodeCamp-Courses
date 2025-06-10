import random

score = {"Wins": 0, "Losses": 0, "Ties": 0}
options = ["Rock", "Paper", "Scissors"]

def get_choices():
    while True:
        player_choice = input("Enter a Choice (Rock, Paper, Scissors): ")
        if player_choice in options:
            break
        else:
            print("Invalid Choice. Please Choose Either Rock, Paper, Scissors")

    computer_choice = random.choice(options)

    return {"Player": player_choice, "Computer": computer_choice}

def check_win(player_choice, computer_choice):
    print(f"Player Chose: {player_choice}")
    print(f"Computer Chose: {computer_choice}")

    if player_choice == computer_choice:
        score["Ties"] += 1
        return "It's a Tie..."
    elif player_choice == "Rock":
        if computer_choice == "Scissors":
            score["Wins"] += 1
            return "Rock Beats Scissors. You Win!"
        else:
            score["Losses"] += 1
            return "Paper Beats Rock. You Lose..."
    elif player_choice == "Paper":
        if computer_choice == "Rock":
            score["Wins"] += 1
            return "Paper Beats Rock. You Win!"
        else:
            score["Losses"] += 1
            return "Scissors Beats Paper. You Lose..."
    elif player_choice == "Scissors":
        if computer_choice == "Paper":
            score["Wins"] += 1
            return "Scissors Beats Paper. You Win!"
        else:
            score["Losses"] += 1
            return "Rock Beats Scissors. You Lose..."
    return ""

print("=== Welcome to Rock Paper Scissors ===")

while True:
    # Returns a Dictionary
    choices = get_choices()
    result = check_win(choices["Player"], choices["Computer"])
    print(result)

    # Display Current Score
    print(f"\nScore - Wins: {score['Wins']} | Losses: {score['Losses']} | Ties: {score['Ties']}")

    play_again = input("Play Again? (Y/N): ").lower()

    if play_again != "y":
        print("\nThanks for Playing! Final Score:")
        print(f"Wins: {score['Wins']} | Losses: {score['Losses']} | Ties: {score['Ties']}")
        break