import pandas as pd
import sqlalchemy

print("=== Pipeline Example ===")

df = pd.read_csv("../Data/transactions.csv")

df["amount"] = df["amount"] * 1.1  # Add Tax

engine = sqlalchemy.create_engine("postgresql://user:pass@localhost/db")
df.to_sql("transactions_processed", engine, if_exists="replace")