print("\x1b[1;31mHello \x1b[2;37;41mWorld\x1b[0m")
print("\033[31mHello Redditors\033[0m")
print("\033[92mHello \033[91mWorld\033[0m")

class EscapeCodes:
    OCTAL = '\033['
    UNICODE = '\u001b['
    HEXADECIMAL = '\x1b['
    ESC = '\x1b['
    CSI = '\x9b['
    DSC = '\x90['
    OSC = '\x9d['

class BasicColors:
    RED = f'{EscapeCodes.OCTAL}31m'
    GREEN = f'{EscapeCodes.OCTAL}32m'
    YELLOW = f'{EscapeCodes.OCTAL}33m'
    BLUE = f'{EscapeCodes.OCTAL}34m'
    MAGENTA = f'{EscapeCodes.OCTAL}35m'
    CYAN = f'{EscapeCodes.OCTAL}36m'
    WHITE = f'{EscapeCodes.OCTAL}37m'
    RESET = f'{EscapeCodes.OCTAL}0m'

class GraphicsMode:
    BOLD = f'{EscapeCodes.OCTAL}1m'
    DIM = f'{EscapeCodes.OCTAL}2m'
    ITALIC = f'{EscapeCodes.OCTAL}3m'
    UNDERLINE = f'{EscapeCodes.OCTAL}4m'
    BLINK = f'{EscapeCodes.OCTAL}5m'
    REVERSE = f'{EscapeCodes.OCTAL}7m'
    HIDDEN = f'{EscapeCodes.OCTAL}8m'
    STRIKETHROUGH = f'{EscapeCodes.OCTAL}9m'
    RESET = f'{EscapeCodes.OCTAL}0m'

print(BasicColors.RED + "Hello World" + BasicColors.RESET)
print(f"{BasicColors.RED}Hello World{BasicColors.RESET}")

print(f"{EscapeCodes.OCTAL}38;5;{55}mTest{GraphicsMode.RESET}")
print(f"{EscapeCodes.OCTAL}3;{55}mTest{GraphicsMode.RESET}")

print(f"{EscapeCodes.OCTAL}1;3;38;5;{55}mTest{GraphicsMode.RESET}")
print(f"{EscapeCodes.OCTAL}1;3;38;5;{55}mTest\033[0m")