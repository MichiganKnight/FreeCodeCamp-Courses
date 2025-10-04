# Python Basics
## This Project Contains Dev10 Modules

## Lesson: Programming and Code
### Introduction:
* Programming - Act of telling a computer what to do
* Code - Computer instructions written in text
    * Formal Language - AKA Programming Language

Sample Python Code:
```
import re

def remove_vowels:
    result = re.sub(r'[aeiou]', '', text, flags = re.IGNORECASE)
    return result
```

Sample Java Code:
```
public static String reverse(String input) {
    if (input == null) {
        return null;
    }
    
    char[] values = new char[input.length()];
    for (int i = 0; i < input.length(); i++) {
        values[input.length() - i - 1] = input.charAt(i);
    }
    return new String(values);
}
```

### Running Code:
* Compiler - Application that converts one formal language into another
    * Read code from one or more files, translates it, and stores the result as an executable application    
* Interpreter - Application that converts code to processor instructions and executes the instructions it's running
    * Code is translated and executed immediately    
  ![Compiler Illustration](https://daerwfnmm5gpa.cloudfront.net/prework/python/assets/compiler.png)<br/>
  *Compiled code isn't executed immediately*<br/>
  ![Interpreter Illustration](https://daerwfnmm5gpa.cloudfront.net/prework/python/assets/interpreter.png)<br/>
  *Interpreted code is executed immediately*<br/>
#### Python is an interpreted language

## Lesson: Terminal, Command-Line, Shell, Console
### Introduction:

## Lesson: Python Syntax
### Introduction:
Python has a small, formal, and rigid syntax
* Identifiers - Descriptive labels that identify a piece of data or a definition
* Keywords - English words that have a special meaning in Python
* Statements - Grammatically correct sequences of symbols, values, and words
* Code Blocks - Group statements together in related chunks
* Whitespace - Spaces, tabs, and newlines
* Comments - Text that is ignored by the Python interpreter

## Lesson: Variables, Types, Operators, Expressions
### Introduction:
* Variable - Identifier that labels a value
  * Data can change
  * Naming is crucial
* Data Type - Type of data that a variable can hold
  * Integer - Whole number
  * Float - Number with decimal places
  * String
  * Boolean - True or False
  * None - No value
  * List
  * Tuple
  * Dictionary
  * Set
* Operators - Symbol that performs an operation on two values
  * Assignment - `=`
  * Arithmetic - `+`, `-`, `*`, `/`, `//`, `%`
    * `//` - Floor Division - Rounds down to the nearest whole number
    * `%` - Modulus - Remainder after division
  * Increment - `+=`, `-=`, `*=`, `/=`, `//=`, `%=`  
  * Logical - `and`, `or`, `not`
    ```
    result = True and False  # Result is False
    result = False and False # Result is False
    result = False and True  # Result is False
    result = True and True   # Result is True  
    ```
    ```
    result = True or False  # Result is True
    result = False or False # Result is False
    result = False or True  # Result is True
    result = True or True   # result is True
    ```
    ```
    result = not True       # Result is False
    result = not False      # Result is True
    ```
    ```
    yes = True
    no = False
    result = not(yes and no or (yes or True)) # Result is False
    result = not yes and no or (yes or True)  # Result is True 
    ```
  * Comparison - `==`, `!=`, `<`, `<=`, `>`, `>=`
  * Identity - `is`, `is not`
  * Membership - `in`, `not in`
* Expressions - Combination of values, operators, and variables that can be evaluated to produce a single value
* Statement - One or more lines of code that does not produce a value

## Lesson: Strings
### Introduction:
* String - Sequence of characters
* String Methods - Functions that operate on strings
  * `len()` - Returns the length of a string
  * `upper()` - Converts all characters in a string to uppercase
  * `lower()` - Converts all characters in a string to lowercase
  * `capitalize()` - Converts the first character of a string to uppercase
  * `title()` - Converts the first character of each word in a string to uppercase
  * `strip()` - Removes whitespace from the beginning and end of a string
  * `replace()` - Replaces one substring with another in a string


