print("=== Classes ===")

class Animal:
    def walk(self):
        print("Walking...")

class Dog(Animal): # Animal class is inherited
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def bark(self): # Self points to current object instance. Similar to 'this' I believe
        print("Woof!")

drew = Dog("Drew", 26)

print(type(drew))
print(drew.name)
print(drew.age)

drew.bark()
drew.walk()