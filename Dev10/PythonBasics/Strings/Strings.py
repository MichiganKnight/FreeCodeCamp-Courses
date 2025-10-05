message = "My Dog Has Fleas"
print(message)

message = "My dog has fleas\nand \"charm\""
print(message)

shrug = "¯\\_(ツ)_/¯"
print(shrug)

glued_message = "School buses are kind of " + "orange"
print(glued_message)

red_and_blue = "Red, Blue, "
primary_colors = red_and_blue + "Yellow"
print(primary_colors)

name1 = "Fifine"
name2 = "Bendex"

adjective1 = "Hopeful"
adjective2 = "Churlish"

print(name1 + " is " + adjective2)
print(name2 + " is " + adjective1)
print(name1 + " will be " + adjective1 + " soon")

dozen = 12
likes_walks = True
n = -9.75

message = str(dozen)
print(message)

message = "Do you feel like a walk? " + str(likes_walks)
print(message)

message = "The retail value is $" + str(n) + "."
print(message)

message = f"{dozen} is my favorite number."
print(message)

message = f"Do you feel like a walk? {likes_walks}"
print(message)

message = f"The retail value is ${n}."
print(message)

red_and_blue = "Red, Blue, "
print(f"{red_and_blue}Yellow")

name1 = "Fifine"
name2 = "Bendex"

adjective1 = "Hopeful"
adjective2 = "Churlish"

print(f"{name1} is {adjective2}")
print(f"{name2} is {adjective1}")
print(f"{name1} will be {adjective1} soon")

elements = "Hydrogen Helium Lithium"
helium = "Helium"

elements_contain_helium = helium in elements
print(elements_contain_helium)

elements_contain_helium = elements in helium
print(elements_contain_helium)

print("fox" in "The quick brown fox jumps over the lazy dog")

print("Quick" in "The quick brown fox jumps over the lazy dog")

f = 3.14
i = 100
b = False

print(type(f))
print(type(i))
print(type(b))

s = str(f)
print(s)
print(type(s))

s = str(i)
print(s)
print(type(s))

s = str(b)
print(s)
print(type(s))

message = "Baked Apples"
character_count = len(message)
print(character_count)
print(type(character_count))

a_becomes_i = message.replace("a", "i")
print(a_becomes_i)

fresh = message.replace(" ", "-Fresh-")
print(fresh)

cookies = message.replace("Apples", "Cookies")
print(cookies)

value = "  \nnoodles\t  \r\n"
print(value)

stripped = value.strip()
print(stripped)

value = "aaanoodlesbbb"
print(value)

stripped = value.strip("ab")
print(stripped)

strip_front = value.lstrip("ab")
print(strip_front)

strip_back = value.rstrip("ab")
print(strip_back)

message = "Baked Apples"

fifth_char = message[4]
print(fifth_char)

eighth_char = message[7]
print(eighth_char)

first_char = message[0]
print(first_char)

substring = message[:5]
print(substring)

substring = message[6:]
print(substring)

substring = message[1:4]
print(substring)

substring = message[-5:-2]
print(substring)

print(message[1:6:2]) # ae
print(message[2::3]) # k ps
print(message[::-1]) # Reverse String

a = "Turnip"
b = "Squash Turnip"
c = "Turnip Squash"

print(a == b)
print(b == c)
print(a == c)