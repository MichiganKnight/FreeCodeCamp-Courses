import pandas as pd

print("=== General Pandas Examples ===")

df = pd.read_csv("../Data/transactions.csv")

print("\n=== DataFrame Head ===")
print("=== First 5 Rows (Default) ===")
print(df.head())
print("=== First Row ===")
print(df.head(1))

print("\n=== DataFrame Tail ===")
print("=== Last 5 Rows (Default) ===")
print(df.tail())
print("=== Last Row ===")
print(df.tail(1))

print("\n=== DataFrame Info ===")
print(df.info())

print("\n=== DataFrame Describe ===")
print(df.describe())

print("\n=== DataFrame Shape ===")
print(df.shape)

print("\n=== DataFrame Columns ===")
print(df.columns)

print("\n=== Selecting Columns ===")
print(df[["user_id", "amount"]])

print("\n=== Filtering Rows ===")
print(df[df["amount"] > 100])

print("\n=== Multiple Conditions ===")
print(df[(df["amount"] > 100) & (df["user_id"] == 1)])

print("\n=== GroupBy ===")
print(df.groupby("user_id")["amount"].mean())