print("=== Functions ===")

def hello(): # No Parameter
    print("Hello")
def hello2(name): # Parameter
    print("Hello", name)
def hello3(name = "NotDrew"): # Default Parameter
    print("Hello " + name)

def greeting(name, age):
    print("Hello", name + "!", "You are", age, "years old")

hello()
hello2("Drew")
hello3()

greeting("Drew", 26)

def change(value):
    value = 2

val = 1
change(val)

print(val)

def change2(value):
    value["name"] = "NotDrew"

val2 = {"name": "Drew"}
change2(val2)

print(val2)

print("\n=== Return Statements ===")

def hello(name):
    print("Hello", name + "!")

    return name

name = hello("Drew")
print(name)

def hello2(name):
    if not name:
        return

    print("Hello", name + "!")

hello2("Drew")

print("\n=== Multiple Returns ===")

def hello(name):
    print("Hello", name + "!")

    return name, "NotDrew", 26

print(hello("Drew"))

#age = 26

def test():
    age = 26
    print(age)

#print(age)
test()