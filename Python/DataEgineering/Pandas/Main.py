import os, sys, platform
import pandas as pd

def is_pycharm():
    return "PYCHARM_HOSTED" in os.environ or "pydevd" in sys.modules

def clear_console():
    if is_pycharm():
        print("\n" * 100) # PyCharm Fallback
    elif platform.system() == "Windows":
        os.system("cls")
    else:
        os.system("clear")

def main():
    print("=== Data Analysis Using Pandas ===\n")

    while True:
        print("1. Italy Covid Data")
        user_input = input("Select What to Do (Or 'Q' to Quit): ").strip().lower()

        if user_input == 'q':
            print("Quitting...")
            break
        else:
            clear_console()

            print("\n=== Italy Covid Data ===\n")

            covid_df = pd.read_csv("italy-covid.csv")

            print(covid_df.tail())
            break

if __name__ == '__main__':
    main()