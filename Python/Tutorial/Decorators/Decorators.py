print("=== Decorators ===")

def logtime(func):
    def wrapper():
        print("Before")
        val = func()
        print("After")
        return val
    return wrapper

@logtime
def hello():
    print("Hello")

hello()