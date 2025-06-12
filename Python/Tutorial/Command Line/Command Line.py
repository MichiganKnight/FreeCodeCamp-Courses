print("=== Command Line ===")

import argparse
import sys

name = sys.argv[1]

print(sys.argv)
print("Hello", name)

print("\n=== Argument Parser ===")

parser = argparse.ArgumentParser(
    description="Print Dog Names"
)

parser.add_argument('-c', '--color', metavar='color', required=True, choices={'red', 'green'}, help='Color to Search For')
args = parser.parse_args()

print(args.color)