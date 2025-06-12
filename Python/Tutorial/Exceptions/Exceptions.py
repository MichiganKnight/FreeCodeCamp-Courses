print("=== Exceptions ===")

#try:
    # Lines of Code
#except <ERROR1>:
    # Handle ERROR1
#except <ERROR2>:
    # Handle ERROR2
#except:
    # No Error Types
#else:
    # No Errors
#finally:
    # Always Ran

try:
    result = 2 / 0
except ZeroDivisionError:
    print("Cannot Divide by Zero")
finally:
    result = 1

print(result)

try:
    raise Exception("An Error")
except Exception as error:
    print(error)

class DogNotFoundException(Exception):
    print("Inside DogNotFoundException")
    pass # Nothing

try:
    raise DogNotFoundException()
except DogNotFoundException:
    print("Dog not Found")