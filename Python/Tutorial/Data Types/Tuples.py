print("=== Tuples ===")

names = ("Drew", "NotDrew", "NewDog")

print(names[0])
print(names.index("Drew"))
print(len(names))
print("Drew" in names)
print(names[0:2])

sorted_tuple = sorted(names)
print(sorted_tuple)

newTuple = names + ("OldDog", "YoungDog")
print(newTuple)