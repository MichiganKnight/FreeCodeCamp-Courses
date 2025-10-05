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

## Lesson: [Variables, Types, Operators, Expressions](VariableTypes/Program.py)
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

## Lesson: [Strings](Strings/Strings.py)
### Introduction:
* String `str` - Represents text as a sequence of characters
  * Used to print messages in a user interface
  * Read and write files
  * Analyze words and sentences
* Common Escape Characters
  * `\n` - New Line
  * `\t` - Tab
  * `\"` - Double Quote
  * `\r` - Carriage Return
  * `\b` - Backslash
* String Operators
  * `+` - Concatenation
  * `*` - Repetition
  * `in` - Membership
  * `not in` - Non-membership
* String Methods - Functions that operate on strings
  * `len()` - Returns the length of a string
  * `replace()` - Replaces one substring with another in a string
  * `strip()`
    * Without Arguments - Removes whitespace from the front and back of a string
    * With Arguments - Removes specific characters from the front and back of a string
  * `upper()` - Converts all characters in a string to uppercase
  * `lower()` - Converts all characters in a string to lowercase
  * `capitalize()` - Converts the first character of a string to uppercase
  * `title()` - Converts the first character of each word in a string to uppercase
* String Indexing and Slicing
  * Character Index Syntax = `value[char_index]`
  * Slicing Syntax = `value[start:end:step]`
    * `start` - Starting Index (Optional)
    * `end` - Ending index (Optional)
    * `step` - Step size (Optional)

## Lesson: [Terminal Input and Output](Terminal/Terminal.py)
### Introduction:
* Terminal - Text-based user interface
  * Aliases - Command-Line / Console / Shell
* Standard Input - Allows application to capture text typed in the terminal
* Standard Output - Allows application to print text to the terminal window
* `input()` - Collects user text from the terminal when the user presses Enter
  * Returns a `str`
* `print()` - Prints text to the terminal window
* F-Strings:
  * Literal String Interpolation - Pairs with the `print()` function
  * Allows embedding of variables, values, and expressions in a string
  * Values display in a default format
  * Values can be formatted using the following syntax `{value: format}`
    * `{value:.2f}` - Format a float to two decimal places
    * `{value:.1%` - Format a number as a percentage with one decimal place
    * `{value:,}` - Format a number with commas as thousands, millions, etc, comma placeholder
    * `{value:03}` - Format a number padding with zeroes, up to a maximum of three digits
    * `{value:<12}` - Align left with a minimum of 12 characters
    * `{value:>12}` - Align right with a minimum of 12 characters
    * `{value:^12}` - Align center with a minimum of 12 characters
    * `{value:s}` - Format a number as a string
    * `{value:d}` - Format a number as an integer
    * `{value:f}` - Format a number as a floating point number
    * `{value:c}` - Format a number as a character
    * `{value:e}` - Format a number as an exponential notation
    * `{value:d}` - Format a number as a decimal number`
    * `{value:b}` - Format a number as a binary number
    * `{value:x}` - Format a number as a hexadecimal number