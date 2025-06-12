print("=== Loops ===")
print("=== While Loops ===")

condition = True
while condition == True: # Repeat until condition is false
    print("Condition is True")
    condition = False

count = 0
while count < 10:
    print("Condition is True:", count)
    count += 1

print("After the Loop")

print("\n=== For Loops ===")

items = [1, 2, 3, 4]
for item in items:
    print(item)

for index, item in enumerate(items):
    print(index, ":", item)

items2 = ["Drew", "NotDrew", "OtherDog"]
for index, item in enumerate(items2):
    print(index, ":", item)

for item in range(15):
    print(item)

print("\n=== Break and Continue ===")

items = [1, 2, 3, 4]
for item in items:
    if item == 2:
        continue # Skip Iteration
    print(item)

for item in items:
    if item == 2:
        break # Break out of loop
    print(item)