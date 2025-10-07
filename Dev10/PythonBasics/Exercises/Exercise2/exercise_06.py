continents = [
    "Africa",
    "Antarctica",
    "Asia",
    "Europe",
    "North America",
    "Oceania",
    "South America",
]

# 1. Use a list index to print Africa, Asia, and South America.
print(continents[0], continents[2], continents[6])

# 2. Loop over all continents and print them.
for continent in continents:
    print(continent)

# 3. Use list slice syntax to print Antarctica - Europe.
print(continents[1:4])
