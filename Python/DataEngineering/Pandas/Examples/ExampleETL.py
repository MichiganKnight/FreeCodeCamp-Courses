import pandas as pd

print("=== Example ETL Script ===")

def extract():
    return pd.read_csv("../Data/transactions.csv")

def transform(df):
    print(df[df["amount"] > 100])

def load(df):
    df.to_csv("output.csv", index=False)

def main():
    df = extract()
    transform(df)
    load(df)

main()