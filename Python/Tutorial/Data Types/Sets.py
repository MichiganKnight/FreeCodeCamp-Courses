print("=== Sets ===")

# Each item has to be unique

set1 = {"Drew", "Not Drew"}
set2 = {"Drew"}

intersect = set1 & set2
print(intersect)

mod = set1 | set2
print(mod)

diff = set1 - set2
print(diff)

superset = set1 > set2
print(superset)

subset = set1 < set2
print(subset)

print(list(set1))