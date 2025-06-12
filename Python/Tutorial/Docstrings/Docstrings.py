"""Dog Module

This module does ... lorem ipsum and provides the
following classes:

- Dog
...
"""

print("=== Docstrings ===")

def increment(n):
    """Increment a Number"""
    return n + 1

class Dog:
    """A Class Representing a Dog"""
    def __init__(self, name, age):
        """Initialize a Dog"""
        self.name = name
        self.age = age

    def bark(self):
        """Let the Dog Bark"""
        print("Woof!")

print(help(Dog))