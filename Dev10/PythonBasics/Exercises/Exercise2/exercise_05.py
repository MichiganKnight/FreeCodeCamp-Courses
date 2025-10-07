# Mad Libs
# https://en.wikipedia.org/wiki/Mad_Libs

# 1. Copy your read_required_string function
# definition into this file.

# 2. Define a mad_lib function.
# Collect three required strings from the user:
# `adjective`, `verb`, and `noun`.
# Use them to print this message.
# f"The {adjective} squirrel {verb} away with the {noun}."
# (You can certainly get more creative with your Mad Libs.)

# 3. Call the mad_lib function at least three times.

def read_required_string(prompt):
    value = input(prompt).strip()
    while not value:
        print("Err: Value is Required")
        value = input(prompt).strip()

    return value

def mad_lib():
    adjective = read_required_string("Adjective: ")
    verb = read_required_string("Verb: ")
    noun = read_required_string("Noun: ")
    print(f"The {adjective} squirrel {verb} away with the {noun}.")

mad_lib()
