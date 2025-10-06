value = 7 / 3
# 1. Use the variable above to print this
# expected output:
#
# Value: 2.3333333333333335
# Two decimals: 2.33
# Percent, one decimal: 233.3%

print(f"Value: {value}")
print(f"Two decimals: {value:.2f}")
print(f"Percent, one decimal: {value:.1%}")

red = "red"
blue = "blue"
yellow = "yellow"
# 2. Use the three variables above to print this
# expected output:
#
# ----------------------------------
# |       red|      blue|    yellow|
# |red       |blue      |yellow    |
# |   red    |   blue   |  yellow  |
# ----------------------------------

print("----------------------------------")
for i in range(3):
    print("|", end="")
    for j in range(3):
        print(f" {red if i == j else ' '}{blue if i == j else ' '}{yellow if i == j else ' '}", end="")
    print("|")
print("----------------------------------")
