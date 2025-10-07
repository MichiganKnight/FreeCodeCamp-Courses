# 1. Add code to this function definition
# that converts gallons to liters.
#
# fyi: the `pass` keyword is an empty statement.
# It serves as a placeholder because Python cannot
# have _truly_ empty statements.
# Replace `pass` with a returned value.
def gallons_to_liters(gallons):
    return gallons * 3.78541


result = gallons_to_liters(1.5)  # (Roughly) 5.6781179999999996
print(result)

print(gallons_to_liters(5))  # 18.92706
print(gallons_to_liters(0.75))  # 2.8390589999999998
