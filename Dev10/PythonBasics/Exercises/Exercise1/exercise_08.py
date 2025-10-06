needle = input("Needle: ")
haystack = input("Haystack: ")

# 1. Collect user input: needle and haystack.
# 2. Use an if/else statement to decide if the needle is
# a substring of haystack. Print the appropriate message.
# (Hint: use the `in` operator.)


if needle in haystack:
    print(f'"{needle}" found in "{haystack}"')
else:
    print(f'"{needle}" NOT found in "{haystack}"')

#        needle |      haystack |        prints
# ---------------------------------------------------------
#       station | space station | "station" found in "space station"
#          stat | space station | "stat" found in "space station"
#            St | space station | "St" NOT found in "space station"
#           1.0 |  Version 11.0 | "1.0" found in "Version 11.0"
# space station |       station | "space station" NOT found in "station"