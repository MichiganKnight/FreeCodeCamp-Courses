# PlateStack.py
# Plate Stacking Assessment

# === ANSI Color Codes ===
RED = "\033[31m"
CYAN = "\033[36m"
GREEN = "\033[32m"
YELLOW = "\033[33m"
RESET = "\033[0m"

# === Plate Storage ===
plates = []

def print_menu(color = RED, reset = RESET):
    """
    Display the Main Menu with Colored Text
    :param color: Color to be Used
    :param reset: Default Color Reset
    """

    menu_text = (
        f"{color}"
        "=== Main Menu ===\n"
        "0. Add Plate\n"
        "1. Remove Plate\n"
        "2. Show Plates\n"
        "3. Exit"
        f"{reset}"
    )

    print(menu_text)

def main():
    """
    Main Program Loop
    """

    while True:
        print_menu()
        choice = input(f"{CYAN}Select Your Choice [0-3]: {RESET}").strip()

        match choice:
            case "0":
                # TODO: Add Plate Logic
                print(f"{GREEN}Add Plate Selected{RESET}\n")
            case "1":
                # TODO: Remove Plate Logic
                print(f"{YELLOW}Remove Plate Selected{RESET}\n")
            case "2":
                # TODO: Show Plates Logic
                print(f"{CYAN}Show Plates Selected{RESET}\n")
            case "3":
                print(f"{RED}Goodbye!{RESET}")
                break
            case _:
                print(f"{RED}Invalid Choice. Please Try Again.{RESET}")

if __name__ == "__main__":
    main()