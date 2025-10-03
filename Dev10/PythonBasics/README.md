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

