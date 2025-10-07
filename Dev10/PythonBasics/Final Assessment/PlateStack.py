# Dev10 Plate Stack Assessment

plates = []

def read_required_string(prompt):
    """
    Reads the User Input and Checks for Blank Values
    :param prompt: Custom Prompt Message
    :return: String Value
    """

    value = ""
    while not value:
        value = input(prompt).strip()
        if not value:
            print("Error: Value is Required")
            print("")
    return value

def add_plate():
    """
    Add a Plate to the Plate Stack
    """

    print("")
    print("Add a Plate")
    print("===========")

    try:
        plate_size = int(read_required_string("Enter Plate Size: "))

        if plate_size <= 0:
            print("Error: Plate Size Must be a Positive Integer")
            print("")
            return

        if len(plates) == 0:
            plates.append(plate_size)
        elif plate_size <= plates[-1]:
            plates.append(plate_size)
        else:
            print(f"Plate with Size: '{plate_size}' Cannot Be Larger than the Plate Size Below It: '{plates[-1]}'")
            print("")
            return

        print("Successfully Added Plate to Plate Stack")

    except ValueError:
        print("Error: Plate Size Must be a Positive Integer")
        print("")
        return

    print("")

def display_plates():
    """
    Displays the Plates in the Plate Stack as a Pyramid
    """

    print("")
    print("Print Plates")
    print("============")

    if not plates:
        print("No Plates Found")
        print("")
        return

    max_width = max(plates)

    for plate in reversed(plates):
        print(("#" * plate).center(max_width))

    print("")

def remove_plates():
    """
    Remove 'n' Plates from the Plate Stack
    """

    print("")
    print("Remove Plates")
    print("=============")

    try:
        if len(plates) <= 0:
            print("Error: No Plates to Remove")
            print("")
            return

        plate_count = int(read_required_string("Enter Amount of Plates to Remove: "))

        if plate_count <= 0:
            print("Error: Plate Count Must be a Positive Integer")
            print("")
            return

        if plate_count > len(plates):
            print(f"Error: Plate Count Cannot Exceed Number of Plates - Plate Count: {plate_count}, Number of Plates: {len(plates)}")
            print("")
            return

        for _ in range(plate_count):
            plates.pop()

        print(f"Successfully Removed {plate_count} Plates")
    except ValueError:
        print("Error: Plate Count Must be a Positive Integer")
        print("")
        return

    print("")

def run():
    """
    Main Loop Function
    """

    print("=== Plate Stack Assessment ===")

    while True:
        print("Main Menu")
        print("=================")
        print("1. Add a Plate")
        print("2. Display Plates")
        print("3. Remove Plates")
        print("4. [Exit]")

        try:
            option = int(read_required_string("Select [1-4]: "))

            match option:
                case 1:
                    add_plate()
                case 2:
                    display_plates()
                case 3:
                    remove_plates()
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