# This is a Comment

name = "Drew" # Inline Comment
age = 26
age2 = 26.6
#age3 = float(26.6) - Not Using
#age4 = int("26") - Cast int from str

print(f"Name: {name} - Age: {age}")
print(f"Name: {type(name)} - Age: {type(age)}\n")

print(f"type(name) Example - {type(name) == str}")
print(f"isinstance(name, str) Example - {isinstance(name, str)}\n")

print(f"Age: {age} | {type(age)} - {isinstance(age, int)}")
print(f"Age: {age2} | {type(age2)} - {isinstance(age2, float)}\n")