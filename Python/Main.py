import random


def get_choices():
    player_choice = input("Enter a Choice (Rock, Paper, Scissors): ")

    options = ["Rock", "Paper", "Scissors"]

    computer_choice = random.choice(options)

    choices = {"Player": player_choice, "Computer": computer_choice}

    return choices

def check_win(player_choice, computer_choice):
    print(f"Player Chose: {player_choice}")
    print(f"Computer Chose: {computer_choice}")

    if player_choice == computer_choice:
        return "It's a Tie..."
    elif player_choice == "Rock":
        if computer_choice == "Scissors":
            return "Rock Beats Scissors. You Win!"
        else:
            return "Paper Beats Rock. You Lose..."
    elif player_choice == "Paper":
        if computer_choice == "Rock":
            return "Paper Beats Rock. You Win!"
        else:
            return "Scissors Beats Paper. You Lose..."
    elif player_choice == "Scissors":
        if computer_choice == "Paper":
            return "Scissors Beats Paper. You Win!"
        else:
            return "Rock Beats Scissors. You Lose..."

choices = get_choices()

result = check_win(choices["Player"], choices["Computer"])

print(result)