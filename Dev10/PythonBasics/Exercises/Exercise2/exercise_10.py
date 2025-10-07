# Are In Order Descending
# =======================

# 1. Define a function that takes an integer list as an argument
# and decides if the list is in order descending. Return a bool.
# If two values are equal, they are still in order.
#
# Name: are_in_order_desc
# Inputs: list[int]
# Output: bool, True if in order descending, False if not

def are_in_order_desc(int_list):
    return all(int_list[i] >= int_list[i + 1] for i in range(len(int_list) - 1))

# Example
print(are_in_order_desc([])) # True
print(are_in_order_desc([9, 9, 8, 5, 1])) # True
print(are_in_order_desc([9, 5, 8, 9, 1])) # False
print(are_in_order_desc([1, 2])) # False
