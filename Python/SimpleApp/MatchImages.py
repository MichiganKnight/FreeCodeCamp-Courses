from pathlib import Path


def match_images(directory1, directory2):
    dir1 = Path(directory1)
    dir2 = Path(directory2)
    extensions = ('.png', '.jpg', '.jpeg')

    def get_files(path):
        return {f.name for f in path.iterdir()
                if f.is_file() and f.suffix.lower() in extensions}

    files1 = get_files(dir1)
    files2 = get_files(dir2)

    unique_to_dir1 = files1 - files2
    unique_to_dir2 = files2 - files1

    print(f"Comparison: \nDirectory 1: {dir1}\nDirectory 2: {dir2}")

    for name in unique_to_dir1:
        print(f"Only in OLD: {name}")

    for name in unique_to_dir2:
        print(f"Only in NEW: {name}")

    total_unique = len(unique_to_dir1) + len(unique_to_dir2)
    print("=" * 30)
    print(f"Total Unique Images: {total_unique}")

def main():
    directory1 = "C:\\Users\\flemi\\OneDrive\\Desktop\\Games\\ToTheFullest\\Game\\wwwroot\\images\\travelOLD"
    directory2 = "C:\\Users\\flemi\\OneDrive\\Desktop\\Games\\ToTheFullest\\Game\\wwwroot\\images\\travel"

    match_images(directory1, directory2)

if __name__ == '__main__':
    main()