import os
from pathlib import Path


def filter_images(directory, exclude_terms):
    folder = Path(directory)
    extensions = ('.png', '.jpg', '.jpeg')

    exclude_string = [term.lower() for term in exclude_terms]

    count = 0
    print(f"Searching: {folder.resolve()}")
    print("=" * 30)

    for file in folder.iterdir():
        if file.is_file() and file.suffix.lower() in extensions:
            if not any(term in file.name.lower() for term in exclude_string):
                print(f"Match: {file.name}")
                count += 1

    print("=" * 30)
    print(f"Total Images Found: {count}")

def main():
    target_folder = "C:\\Users\\flemi\\OneDrive\\Desktop\\Games\\ToTheFullest\\Game\\wwwroot\\images\\travelOLD"
    search_terms = ["sarah", "noelle"]

    filter_images(target_folder, search_terms)

if __name__ == '__main__':
    main()