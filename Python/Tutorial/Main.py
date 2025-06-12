import os
import subprocess
import platform
import sys

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
EXCLUDED_DIRS = {".idea", ".venv", "__pycache__"}

def is_pycharm():
    return "PYCHARM_HOSTED" in os.environ or "pydevd" in sys.modules

def clear_console():
    if is_pycharm():
        print("\n" * 100) # PyCharm Fallback
    elif platform.system() == "Windows":
        os.system("cls")
    else:
        os.system("clear")

def discover_tutorials():
    tutorials = {}
    index = 1

    for root, dirs, files in os.walk(BASE_DIR):
        dirs[:] = [d for d in dirs if d not in EXCLUDED_DIRS]

        for file in files:
            if file.endswith(".py") and file != "Main.py" and file != "Dog.py" and file != "__init__.py":
                rel_path = os.path.relpath(os.path.join(root, file), BASE_DIR)
                title = os.path.splitext(file)[0]
                tutorials[str(index)] = (title, rel_path)
                index += 1

    return tutorials

def show_menu(tutorials):
    print("\n=== Python Tutorial ===")
    for key, (title, path) in tutorials.items():
        print(f"{key}: {title} ({path})")
    print("Q. Quit")

def run_tutorial(choice, tutorials):
    if choice in tutorials:
        _, file_path = tutorials[choice]
        full_path = os.path.join(BASE_DIR, file_path)

        if os.path.exists(file_path):
            print(f"\nRunning {file_path}...\n")
            subprocess.run(["python", full_path])
        else:
            print(f"File Not Found: {full_path}")
    else:
        print("Invalid Choice. Please Try Again.")

# === Main Program ===
if __name__ == "__main__":
    tutorials = discover_tutorials()

    if not tutorials:
        print("No Tutorials Found")
    else:
        while True:
            show_menu(tutorials)
            user_input = input("Select Lesson Number to Run (Or 'Q' to Quit): ").strip().lower()

            if user_input == "q":
                print("Quitting...")
                break
            else:
                clear_console()
                run_tutorial(user_input, tutorials)
                break