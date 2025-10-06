import random

#while True:
    #print("This Message is Repeated Forever")

while False:
    print("This Message is Never Printed")

count = 10
print("Countdown:")

while count > 0:
    print(count)
    count -= 1

print("Blast Off!")

message = "Visit Mars"
index = 0

while index < len(message):
    print(message[index])
    index = index + 1 # Or index += 1

a = 1
b = 2
c = 3

while a < 50 or b % 2 == 0 or c % 3 != 0:
    print(f"{a}, {b}, {c}")

    a = random.randrange(100)
    b = random.randrange(100)
    c = random.randrange(100)

word = ""

#while word != "magic":
    #word = input('Enter the "Magic" Word: ').strip().lower()

print("You Got It!")

word = "Looping..."

for char in word:
    print(char)

sum = 0
# Sum numbers less than or equal to 10
for n in range(11):
    sum += n

print(f"Sum: {sum}")

print("Countdown:")
for i in range(10, 0, -1):
    print(i)

print("Blast off!")

# Count by 5, starting at 4, less than 100
for i in range(4, 100, 5):
    print(i)

message = "Grouper, Halibut, and Trout"
vowels = "aeiou"
result = ""

# Remove all vowels from `message`
for i in range(len(message)):
    if vowels.find(message[i]) < 0:
        result += message[i]

print(f"Result: {result}")

message = "Grouper, Halibut, and Trout"
vowels = "aeiou"
result = ""

for char in message:
    if vowels.find(char) < 0:
        result += char

print(f"Result: {result}")

# for i in range(10000):
#     print(f"First Line in the Block: {i}")
#
#     # A 10% change to break out of the loop
#     if random.random() > 0.9:
#         print("Breaking...")
#         break
#
#     print(f"Last Line in the Block: {i}")

value = ""
one_through_five = "12345"

#while True:
    #value = input("Enter 1-5: ")
    #if len(value) == 1 and one_through_five.find(value) >= 0:
        #break

#value = ""
#one_through_five = "12345"
#is_valid = False

#while not is_valid:
    #value = input("Enter 1-5: ")
    #if len(value) == 1 and one_through_five.find(value) >= 0:
        #is_valid = True

# Print All Even Numbers Under 100
n = 0
while n < 100:
    if n % 2 == 1:
        n += 1
        continue # Skip Remaining Code

    print(n)
    n += 1

# Print 20% of the Numbers Under 100, Randomly
for n in range(100):
    if random.random() < 0.80:
        continue

    print(f"Lucky Number: {n}")

for row in range(5):
    for column in range(5):
        if column == 3:
            break

        print(f"Row: {row}, Column: {column}")

n = 0
while n < 7:
    print(n)
    n += 1

for n in range(7):
    print(n)

word = "curling"
result = ""

for char in word:
    result += char

print(result)

word = "curling"
result = ""
index = 0

while index < len(word):
    char = word[index]
    result += char
    index += 1

print(result)