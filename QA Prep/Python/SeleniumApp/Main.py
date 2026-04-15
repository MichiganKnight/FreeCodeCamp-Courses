import sys

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.edge.options import Options as EdgeOptions

def browser_menu():
    print("1. Chrome")
    print("2. Firefox")
    print("3. Edge")
    print("Q. Quit")

def main():
    print("=== Selenium App ===")
    browser = None

    while browser is None:
        browser_menu()
        print()
        user_input = input("Enter Your Choice: ").strip().lower()

        if user_input == "q":
            print("Quitting...")
            sys.exit()

        try:
            choice = int(user_input)

            is_headless = input("Run in Headless Mode? (Y/N): ").lower() == "y"

            if choice == 1:
                print()
                options = ChromeOptions()
                if is_headless: options.add_argument("--headless=new")
                browser = webdriver.Chrome(options=options)
                print(f"Opening Chrome (Headless: {is_headless})...")

            elif choice == 2:
                print()
                options = FirefoxOptions()
                if is_headless: options.add_argument("--headless")
                browser = webdriver.Firefox(options=options)
                print(f"Opening Firefox (Headless: {is_headless})...")

            elif choice == 3:
                print()
                options = EdgeOptions()
                if is_headless: options.add_argument("--headless=new")
                browser = webdriver.Edge(options=options)
                print(f"Opening Edge (Headless: {is_headless})...")
            else:
                print()
                print("Invalid Choice!")
        except ValueError:
            print()
            print("Invalid Input! Please Enter a Number or 'Q'")

    browser.get("https://www.google.com")
    print(f"Navigating to: {browser.current_url}")

    search_field = browser.find_element(By.NAME, "q")
    search_input = input("Enter Search Query: ")

    search_field.send_keys(search_input)
    search_field.submit()

    browser.save_screenshot(f"{search_input}.png")
    print(f"Screenshot Saved as: {search_input}.png")

    browser.quit()

if __name__ == "__main__":
    main()