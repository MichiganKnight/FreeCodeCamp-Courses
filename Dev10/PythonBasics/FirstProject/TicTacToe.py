import random

def read_required_string(prompt):
    """
    Prompt a user and collect input
    :param prompt: Message displayed to user
    :return: Required Value
    """

    value = input(prompt).strip()
    while not value:
        print("Value is Required")
        value = input(prompt).strip()

    return value

def read_position(prompt):
    """
    Prompt a user to enter an integer, 1-3
    :param prompt: Message displayed to user
    :return: Integer 0-2
    """

    value = read_required_string(prompt)
    while value != "1" and value != "2" and value != "3":
        print("Please enter 1, 2 or 3")
        value = read_required_string(prompt)

    return int(value) - 1

def print_board(board):
    """
    Prints a board based on a list
    :param board: Representation of the board
    :return: None
    """

    board_template = f"""       Column
           1   2   3

    Row 1: {board[0]} | {board[1]} | {board[2]}
           _   _   _
    Row 2: {board[3]} | {board[4]} | {board[5]}
           _   _   _
    Row 3: {board[6]} | {board[7]} | {board[8]}
    """

    print(board_template)

def is_game_over(name, row, col, board):
    """
    Confirms if the tic-tac-toe game is over
    :param name: Name of the player that played the last move
    :param row: The last move's row
    :param col: The last move's column
    :param board: Representation of the board
    :return: bool: True if game over, False if not
    """

    symbol = board[row * 3 + col]

    # Check Row Win
    row_win = (
        board[row * 3] == board[row * 3 + 1] and board[row * 3] == board[row * 3 + 2]
    )

    # Check Column Win
    col_win = board[col] == board[3 + col] and board[col] == board[6 + col]

    # Check Diagonal Win
    diagonal_down_win = symbol == board[0] and symbol == board[4] and symbol == board[8]
    diagonal_up_win = symbol == board[6] and symbol == board[4] and symbol == board[2]

    if row_win or col_win or diagonal_down_win or diagonal_up_win:
        print(f"{name} Wins!")
        return True

    if any(s == " " for s in board):
        return False

    print("It's a Tie!")
    return True

def move(name, symbol, board):
    """
    Makes a player move
    :param name: Name of the current player
    :param symbol: The current symbol, X or O
    :param board: Representation of the board
    :return: bool: True if game is *not* over, False if not
    """

    print_board(board)
    print(f"{name}'s Turn")

    row = read_position("Select a Row [1-3]: ")
    col = read_position("Select a Column [1-3]: ")

    while board[row * 3 + col] != " ":
        print("Position Already Taken, Try Again")

        row = read_position("Select a Row [1-3]: ")
        col = read_position("Select a Column [1-3]: ")

    board[row * 3 + col] = symbol

    is_over = is_game_over(name, row, col, board)

    if is_over:
        print_board(board)

    return not is_over

def play():
    """
    Plays a full game of tic-tac-toe
    :return: None
    """

    player_one = read_required_string("Player #1, Enter Your Name: ")
    player_two = read_required_string("Player #2, Enter Your Name: ")

    board = [" ", " ", " ", " ", " ", " ", " ", " ", " "]
    is_player_ones_turn = random.choice([True, False])
    current_player = player_one if is_player_ones_turn else player_two
    symbol = "X"

    print(f"{current_player} Goes First")

    while move(current_player, symbol, board):
        is_player_ones_turn = not is_player_ones_turn
        current_player = player_one if is_player_ones_turn else player_two
        symbol = "O" if symbol == "O" else "O"

def run():
    """
    Runs one or more games of tic-tac-toe
    :return: None
    """

    print("Welcome to Tic-Tac-Toe!")
    again = True
    while again:
        play()
        again = read_required_string("Play Again? [Y/N]: ").lower() == "y"

    print("Thanks for Playing!")

run()