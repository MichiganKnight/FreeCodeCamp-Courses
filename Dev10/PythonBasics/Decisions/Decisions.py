if True:
    print("True")

if False:
    print("False")

miles_per_hour = 67
speed_limit = 55
knows_officer = False

if miles_per_hour > speed_limit:
    print("You Are Speeding")

if miles_per_hour > speed_limit + 5 and not knows_officer:
    print("You Get a Speeding Ticket")

if miles_per_hour > speed_limit + 5:
    print("Hmmmm...")
    print("You Get a Speeding Ticket")

    expensive_ticket_threshold = speed_limit + 15

    if miles_per_hour > expensive_ticket_threshold:
        print("You Get an Expensive Ticket")
        print("Better Luck Next Time")

value = None
print(type(value))

if "":
    print("Does Not Print")

if "A Phrase":
    print("Prints: 'A Phrase' is True")

if 0 or 0.0:
    print("Does Not Print")

if 2.2:
    print("Prints: 2.2 is True")

if -30:
    print("Prints: -30 is True")

if None:
    print("Does Not Print")

value = None
if value is None:
    print("Prints: value is None is True")

if True:
    print("Prints")
else:
    print("Does Not Print")

if False:
    print("Does Not Print")
else:
    print("Prints")

number = 17

if number % 2 == 0:
    print("Even Number")

if number % 2 == 1:
    print("Odd Number")

if number % 2 == 0:
    print("Even Number")
else:
    print("Odd Number")

can_fly = True
has_feathers = False

if can_fly and has_feathers:
    print("Likely a Bird")

else:
    print("May Still Fly or Have Feathers, But Not Both")

    if can_fly:
        print("Rocket?")
        print("Time?")
    else:
        print("An Ostrich?")
        print("Galoshes?")

    if has_feathers:
        print("A Penguin?")
        print("A Boa?")

package_weight = 0.55

if package_weight > 100.0:
    print("Too Big for Standard Shipping")
elif package_weight < 1.0:
    print("Too Small. Send a Letter")
else:
    print("Can Ship")

#######################

if package_weight > 100.0:
    print("Too Big for Standard Shipping")
else:
    if package_weight < 1.0:
        print("Too Small. Send a Letter")
    else:
        print("Can Ship")

color = "orange"

if color == "red":
    print("Red's Compliment is Green")
else:
    if color == "blue":
        print("Blue's Compliment is Orange")
    else:
        if color == "yellow":
            print("Yellow's Compliment is Purple")
        else:
            if color == "green":
                print("Green's Compliment is Red")
            else:
                if color == "orange":
                    print("Orange's Compliment is Blue")
                else:
                    if color == "purple":
                        print("Purple's Compliment is Yellow")
                    else:
                        print("Unknown Color")

##########################################################

if color == "red":
    print("Red's Compliment is Green")
elif color == "blue":
    print("Blue's Compliment is Orange")
elif color == "yellow":
    print("Yellow's Compliment is Purple")
elif color == "green":
    print("Green's Compliment is Red")
elif color == "orange":
    print("Orange's Compliment is Blue")
elif color == "purple":
    print("Purple's Compliment is Yellow")
else:
    print("Unknown Color")

age = 64
is_gold_member = True

## BAD CODE:
if age > 59:
    print("Senior Discount")
elif age > 59 and is_gold_member:
    print("Senior AND Gold Member Discount")
elif age <= 18:
    print("Student Discount")
elif age < 5:
    print("Kids Eat Free")
else:
    print("Normal Discount")

## GOOD CODE:
if age > 59 and is_gold_member:
    print("Senior AND Gold Member Discount")
elif age > 59:
    print("Senior Discount")
elif age < 5:
    print("Kids Eat Free")
elif age <= 18:
    print("Student Discount")
else:
    print("Normal Discount")

place = 2
ribbon_color = None

match place:
    case 1:
        ribbon_color = "blue"
        print("First Place!")
    case 2:
        ribbon_color = "red"
        print("Second Place!")
    case 3:
        ribbon_color = "white"
        print("Third Place!")
    case _: # Underscore - Default Case
        ribbon_color = "unknown"

print(ribbon_color)

color = input("Enter a Color: ").strip().lower()

match color:
    case "red" | "yellow" | "blue":
        print("Primary Color")
    case "green" | "purple" | "orange":
        print("Secondary Color")
    case _:
        print("Regular Color")