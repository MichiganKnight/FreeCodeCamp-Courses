print("=== Polymorphism ===")

class Dog:
    def eat(self):
        print("Eating Dog Food")

class Cat:
    def eat(self):
        print("Eating Cat Food")

animal1 = Dog()
animal2 = Cat()

animal1.eat()
animal2.eat()