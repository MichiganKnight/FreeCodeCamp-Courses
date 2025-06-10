print("=== Control Statements ===")

condition = False

if condition == True:
    print("The condition")
    print("was true")
else:
    print("The condition")
    print("was false")

print("\nOutside If\n")

name = "NoName"

if condition == True:
    print("The condition")
    print("was true")
elif name == "Drew":
    print("Hello Drew")
elif name == "Andy":
    print("Hello Andy")
elif name == "Bev":
    print("Hello Bev")
else:
    print("The condition")
    print("was false")