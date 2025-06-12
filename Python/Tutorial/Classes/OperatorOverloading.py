print("=== Operator Overloading ===")

class Dog:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def __gt__(self, other):
        return True if self.age > other.age else False

dog = Dog("Drew", 26)
not_drew = Dog("Not Drew", 28)

print(dog > not_drew)
print(dog < not_drew)

# __add__() Respond to the + Operator
# __sub__() Respond to the - Operator
# __mul__() Respond to the * Operator
# __truediv__() Respond to the / Operator
# __floordiv__() Respond to the // Operator
# __mod__() Respond to the % Operator
# __pow__() Respond to the ** Operator
# __rshift__() Respond to the >> Operator
# __lshift__() Respond to the << Operator
# __and__() Respond to the & Operator
# __or__() Respond to the | Operator
# __xor__() Respond to the ^ Operator