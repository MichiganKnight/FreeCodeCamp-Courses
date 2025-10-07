player_one_wins = 0
player_two_wins = 0
ties = 0
games = 0
history = []

print("=== Rock Paper Scissors ===")

choices = ["Rock", "Paper", "Scissors"]
winning_combos = [("Rock", "Scissors"), ("Paper", "Rock"), ("Scissors", "Paper")]

def read_required_string(prompt):
    value = input(prompt).strip()
    while not value:
        print("Err: Value is Required")
        value = input(prompt).strip()

    return value

def apply_rules(result, player_one_name, player_two_name, player_one_choice, player_two_choice):
    global player_one_wins, player_two_wins, ties, games, history

    games += 1

    if result == "Win":
        player_one_wins += 1
        history.append(f"{player_one_name} Beat {player_two_name} - ({player_one_choice} Beats {player_two_choice})")
    elif result == "Loss":
        player_two_wins += 1
        history.append(f"{player_two_name} Beat {player_one_name} - ({player_two_choice} Beats {player_one_choice})")
    elif result == "Tie":
        ties += 1
        history.append(f"Tie - ({player_one_choice} Ties {player_two_choice})")

def validation(player_one_choice, player_two_choice, player_one_name, player_two_name):
    player_one = choices[int(player_one_choice) - 1]
    player_two = choices[int(player_two_choice) - 1]

    if player_one == player_two:
        print("It's a Tie!")
        apply_rules("Tie", player_one_name, player_two_name, player_one, player_two)
    elif (player_one, player_two) in winning_combos:
        print(f"{player_one_name} Wins!")
        apply_rules("Win", player_one_name, player_two_name, player_one, player_two)
    else:
        print(f"{player_two_name} Wins!")
        apply_rules("Loss", player_one_name, player_two_name, player_one, player_two)

def play_game(player_one_name, player_two_name):
    while True:
        print(f"{player_one_name} - Choose")
        for i, choice in enumerate(choices, start = 1):
            print(f"{i}: {choice}")

        player_one_choice = read_required_string("Enter Choice [1-3]: ")
        while player_one_choice not in ["1", "2", "3"]:
            print("Invalid Choice!")
            player_one_choice = read_required_string("Enter Choice [1-3]: ")
        print("")

        print(f"{player_two_name} - Choose")
        for i, choice in enumerate(choices, start = 1):
            print(f"{i}: {choice}")

        player_two_choice = read_required_string("Enter Choice [1-3]: ")
        while player_two_choice not in ["1", "2", "3"]:
            print("Invalid Choice!")
            player_two_choice = read_required_string("Enter Choice [1-3]: ")
        print("")

        print(f"{player_one_name} Chose {choices[int(player_one_choice) - 1]}")
        print(f"{player_two_name} Chose {choices[int(player_two_choice) - 1]}")
        validation(player_one_choice, player_two_choice, player_one_name, player_two_name)

        play_again = read_required_string("Play Again? [Y/N]: ")
        if play_again.lower() != "y":
            print("")
            print(f"Total Games: {games}")
            print(f"{player_one_name}: {player_one_wins}")
            print(f"{player_two_name}: {player_two_wins}")
            print(f"Ties: {ties}")
            print("\n=== Match History ===")
            for i, result in enumerate(history, start = 1):
                print(f"{i}: {result}")

            print("Goodbye!")
            break

        print("")

def run():
    player_one_name = read_required_string("Player #1 - Enter Your Name: ")
    player_two_name = read_required_string("Player #2 - Enter Your Name: ")
    print("")

    play_game(player_one_name, player_two_name)
run()