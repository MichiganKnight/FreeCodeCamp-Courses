def main():
    print("=== Video Duration Calculator ===")
    hours = int(input("Enter Total Hours: "))
    minutes = int(input("Enter Total Minutes: "))
    seconds = int(input("Enter Total Seconds: "))

    playback_speed = float(input("Enter Playback Speed: "))

    duration_seconds = (hours * 3600) + (minutes * 60) + seconds
    new_duration_seconds = duration_seconds / playback_speed

    new_hours = int(new_duration_seconds // 3600)
    new_minutes = int((new_duration_seconds % 3600) // 60)
    new_seconds = int(new_duration_seconds % 60)

    print(f"\nNew Duration: {new_hours} Hours, {new_minutes} Minutes, {new_seconds} Seconds")

if __name__ == '__main__':
    main()