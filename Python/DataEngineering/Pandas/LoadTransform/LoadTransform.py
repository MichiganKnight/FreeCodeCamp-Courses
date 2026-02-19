import pandas as pd

print("=== Load / Transform ===")

df = pd.read_csv("../Data/transactions.csv")

df["timestamp"] = pd.to_datetime(df["timestamp"])

summary = df.groupby("user_id")["amount"].sum().reset_index()

print(summary)