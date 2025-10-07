dogs = []

def read_required_string(prompt):
    value = ""
    while not value:
        value = input(prompt).strip()
        if not value:
            print("Error: Value is Required")
    return value

def add_dog():
    print("")

    if len(dogs) >= 25:
        print("Error: Maximum Number of Dogs Reached")
        return

    name = read_required_string("Enter Dog Name: ")
    while name in dogs:
        print("Error: Dog Name Already Exists")
        name = read_required_string("Enter Dog Name: ")
    dogs.append(name)

    print("")

def display_dogs():
    print("")
    print("Dogs:")

    if len(dogs) == 0:
        print("No Dogs Found")

    for index, name in enumerate(dogs):
        print(f"{index + 1}: {name}")
    print("")

def remove_dog():
    print("")

    name = read_required_string("Enter Dog Name to Remove: ")
    if name in dogs:
        dogs.remove(name)
        print(f"Dog: {name} Removed")
        print("")
    else:
        print("Error: Dog Name does not Exist")
        print("")

def run():
    print("=== Cur-Ious Hounds ===")

    while True:
        print("Main Menu")
        print("1. Add Dog")
        print("2. Display Dogs")
        print("3. Remove Dog")
        print("4. Exit")

        try:
            option = int(read_required_string("Select [1-4]: "))

            match option:
                case 1:
                    add_dog()
                case 2:
                    display_dogs()
                case 3:
                    print("Update Dog")
                case 4:
                    print("Goodbye!")
                    break
                case _:
                    print("Invalid Option - Please Select a Number Between 1 and 4")
                    print("")
        except ValueError:
            print("Invalid Option - Please Select a Number Between 1 and 4")
            print("")

if __name__ == "__main__":
    run()