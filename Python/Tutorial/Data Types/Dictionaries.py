print("=== Dictionaries ===")

# Key: # Value

dog = { "name": "Drew", "age": 26, "color": "red" }

print(dog["name"])

dog["age"] = 27

print(dog)
print(dog.get("name"))
print(dog.get("color", "red")) # Red is default value
print("color" in dog)
print(len(dog))

dog["favorite food"] = "Meat"
print(dog)

del dog["color"]
print(dog)

dog_copy = dog.copy()
print(dog_copy)

print("\n=== Get Keys & Values ===")

print(dog.keys())
print(list(dog.keys()))
print(dog.values())
print(list(dog.values()))
print(dog.items())
print(list(dog.items()))

print("\n=== Pop Functions ===")

print(dog.popitem()) # Remove last item added
print(dog.pop("name")) # Return and delete item
print(dog)