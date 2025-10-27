import subprocess
import time

def wait_for_postgres(host, max_retries=5, delay_seconds=5):
    retries = 0

    while retries < max_retries:
        try:
            result = subprocess.run(
                ["pg_isready", "-h", host],
                check=True,
                capture_output=True,
                text=True
            )
            
            if "accepting connections" in result.stdout:
                print(f"PostgreSQL Running On: {host}")
                return True
        except subprocess.CalledProcessError:
            print(f"PostgreSQL Error Connection: {host}")
            retries += 1
            
            print(f"Retrying in {delay_seconds} seconds... (Attempt: {retries}/{max_retries})")
            time.sleep(delay_seconds)

    print(f"Max Retries Reached - Exiting...")
    return False

if not wait_for_postgres(host="source_postgres"):
    exit(1)

print("Starting ELT Script...")

