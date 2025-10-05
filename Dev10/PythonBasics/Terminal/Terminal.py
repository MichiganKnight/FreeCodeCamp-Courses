name = input("Enter Your Name: ")
print(f"Your Name is: {name}")

grocery = input("Enter a Grocery Item: ")
value = input("Enter Cost: ")

cost = float(value)

inventory = int(input("How Many Are Available?: "))

is_taxable = bool(input("Is It Taxable? (True/False): "))

print()
print("=======================")
print(f"{grocery}: ${cost}")
print(f"Current Inventory: {inventory}")
print(f"Is Taxable: {is_taxable}")
print("=======================")

name = "Naomi"
pet_count = 3
species = "Dogs"
vertical = 37.68

print(f"{name} Has {pet_count} {species} and Can Jump {vertical} Inches Vertically")

stock_price = 87.6549513216
print(f"Stock Price: ${stock_price}")
print(f"{stock_price:.2f}")
print(f"{stock_price:.4f}")
print(f"{stock_price:010.3f}")

carrion = "Carrion Beetles", "Silphidae", 9.7
darkling = "Darkling Beetles", "Tenebrionidae", 10.0
firefly = "Fireflies", "Lampyridae", 9.98
weevil = "Fungus Weevils", "Anthribidae", 9.45
ladybug = "Ladybugs", "Coccinellidae", 9.62

print("Beetle Families")
print("-" * 51)
print(f"| {'Common Name':<20} | {'Latin Name':<15} | {'Rating':>6} |")
print("-" * 51)

print(f"| {carrion[0]:<20} | {carrion[1]:<15} | {carrion[2]:>6} |")
print(f"| {darkling[0]:<20} | {darkling[1]:<15} | {darkling[2]:>6} |")
print(f"| {firefly[0]:<20} | {firefly[1]:<15} | {firefly[2]:>6} |")
print(f"| {weevil[0]:<20} | {weevil[1]:<15} | {weevil[2]:>6} |")
print(f"| {ladybug[0]:<20} | {ladybug[1]:<15} | {ladybug[2]:>6} |")

print("-" * 51)

first_name = "Husein"
last_name = "Vaan"

full_name = f"{first_name} {last_name}"
occupation = "Game Developer"

title = f"Name: {full_name} - Occupation: {occupation}"