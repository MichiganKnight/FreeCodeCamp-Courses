from pathlib import Path


def replace_in_filenames(directory, old_str, new_str):
    base_dir = Path(directory)

    print("Step 1: Deleting ._ Files...")
    for item in base_dir.rglob("._*"):
        if item.is_file():
            item.unlink()
            print(f"Deleted: {item}")

    print("\nStep 2: Renaming Files and Directories...")
    all_items = list(base_dir.rglob("*"))

    all_items.sort(key=lambda p: len(p.parts), reverse=True)

    for item in all_items:
        if old_str in item.name:
            updated_name = item.name.replace(old_str, new_str)
            new_path = item.with_name(updated_name)

            try:
                item.rename(new_path)
                print(f"Renamed: {item} -> {new_path}")
            except Exception as e:
                print(f"Error Renaming {item}: {e}")

if __name__ == '__main__':
    target_folder = r"G:\Games\ToTheFullest\Game"
    search_for = "christel"
    replace_with = "christal"

    replace_in_filenames(target_folder, search_for, replace_with)
