print("=== Lists ===")

dogs = ["Drew", "NotDrew"]
dogs2 = ["Drew", 1, "NotDrew", True]
#dogs3 = [] Empty List
dogs4 = ["Drew", 1, "NotDrew", False, "NewDog", 7]

print("Drew" in dogs)
print("Andy" in dogs2)

print("")

print(dogs2[2])

dogs2[2] = "NewDog"

print(dogs2)
print(dogs2[-1]) # Starts From the End

print("")

print(dogs4[2:4])
print(dogs4[2:])
print(dogs4[:3])

print("\n=== List Methods ===")

dogs4.append("OldDog")

print(dogs4)
print(len(dogs4))

dogs4.extend(["YoungDog", 5])
print(dogs4)

dogs4 += ["BigDog", 8]
print(dogs4)

dogs4.remove("NewDog")
print(dogs4)

print(dogs4.pop()) # Return and Remove Last Item in List
print(dogs4)

print("\n=== Add at Specific Spot ===")

items = ["Drew", 1, "NotDrew", False, "NewDog", 7]
items.insert(2, "NewDog")

print(items)

items[1:1] = ["OldDog", "YoungDog"] # Insert at Index 1
print(items)

print("\n=== Sort Lists ===")

sortable_list = ["Drew", "NotDrew", "OldDog", "NewDog"]
sortable_copy = sortable_list[:]

sortable_list.sort()

print("Sorted List: ", sortable_list) # Sort Defaults Uppercase First
print("Unsorted List Copy: ", sortable_copy)

sortable_list2 = ["Drew", "NotDrew", "OldDog", "NewDog", "dog"]
print(sorted(sortable_list2, key=str.lower))

#sortable_list2.sort(key=str.lower)

print(sortable_list2)