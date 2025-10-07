import random
from statistics import stdev


def create_random_int_list(count):
    result = []
    for _ in range(count):
        result.append(random.randrange(100))
    return result


# 1. Use the create_random_int_list function to build
# a random 25 element integer list.
result = create_random_int_list(25)

# 2. Calculate the sum. (Results will vary because of randomness.)
print(sum(result))

# 3. Challenge: calculate the average.
average = sum(result) / len(result)
print(average)

# 4. Challenge, Challenge: calculate the standard deviation.

standard_deviation = stdev(result)
print(standard_deviation)
