import random

train = [5, 3.14, True, "Maple"]
print(train)

train.pop()
train.pop()
print(train)

train.insert(0, 9)
train.append(0.75)
print(train)

train = [5, 3.14, True, "Maple"]
print(train)

del train[2]
print(train)

train = train[:1] + [-1.5, "Elm"] + train[1:]
print(train)

planets = [
    "Mercury",
    "Venus",
    "Earth",
    "Mars",
    "Jupiter",
    "Saturn",
    "Uranus",
    "Neptune"
]
numbers = [1, 2, 3, 4, 5.5, 5.6, 5.7]
two_bools = [True, False]

message = "Visit Mars!"
chars = list(message)
print(chars)

zero_to_ten = list(range(11))
print(zero_to_ten)

planets = ["Mercury", "Venus", "Mars", "Jupiter"]
print(planets[0])

planets[2] = "Earth"
planets[3] = "Mars"

planets.append("Jupiter")
planets.append("Saturn")
planets.append("Uranus")
planets.append("Neptune")

print(planets[2])
print(planets[5])

planets = [
    "Mercury",
    "Venus",
    "Earth",
    "Mars",
    "Jupiter",
    "Saturn",
    "Uranus",
    "Neptune"
]

print(planets)

print(planets[3:])
print(planets[:3])
print(planets[2:6])
print(planets[-3:-1])

print(planets[::2])

print(planets[-5::-1])

jupiter_moons = ["Io", "Europa", "Ganymede", "Callisto"]
for moon in jupiter_moons:
    print(moon)

for moon in reversed(jupiter_moons):
    print(moon)

for index in range(len(jupiter_moons)):
    jupiter_moons[index] = jupiter_moons[index].lower()

print(jupiter_moons)

for index, moon in enumerate(jupiter_moons):
    jupiter_moons[index] = moon.lower()
    print(f"{index} - Original: {moon} - Modified: {jupiter_moons[index]}")

print(jupiter_moons)

values = [68, -72, -67, 82, -6, -49, 48, -36, 73, -27, 0, 3]

sum = 0
for value in values:
    sum += value

average = sum / len(values)

print(f"Sum: {sum}")
print(f"Average: {average}")

values = []

for n in range(1000000):
    values.append(random.randrange(100))

sum = 0
for value in values:
    sum += value

average = sum / len(values)

print(f"Sum: {sum}")
print(f"Average: {average}")

padded_name = " Frank Ocean "
name = padded_name.strip()
lowercase_name = name.lower()

print(
    f'''Padded Name: {padded_name}
Name: {name}
Lowercase Name: {lowercase_name}'''
)

birds = []

birds.append("Coopers's Hawk")
birds.append("Bobolink")
birds.append("Great Horned Owl")

print(len(birds))

water_birds = ["Black Skimmer", "White-Faced Ibis"]

birds.extend(water_birds)

print(len(birds))

birds.insert(1, "Cedar Waxwing")

print(birds)

birds.extend(("Gray Catbird", "King Rail", "Chimney Swift"))

print(len(birds))


bird = birds.pop()
print(bird)
print(len(birds))

bird = birds.pop(3)

print(bird)
print(len(birds))

birds.remove("Bobolink")
print(len(birds))

del birds[3]
print(len(birds))

del birds[-3:]
print(len(birds))

birds.clear()
del birds[:]

print(len(birds))

even = [2, 4, 6]
odd = [1, 3, 5]

one_through_six = even + odd

print(len(even))
print(len(odd))
print(one_through_six)

one_through_six.sort()
print(one_through_six)

print(["a", "b"] + [1, 2, 3])

triple_even = even * 3
print(triple_even)

print([None] * 10)