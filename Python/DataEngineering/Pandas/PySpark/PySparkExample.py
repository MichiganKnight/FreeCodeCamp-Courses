import os
import pyspark

pyspark_dir = os.path.dirname(pyspark.__file__)
jars_dir = os.path.join(pyspark_dir, "jars")

print("pyspark_dir =", pyspark_dir)
print("jars_dir    =", jars_dir)
print("jars exists =", os.path.isdir(jars_dir))
if os.path.isdir(jars_dir):
    print("jars count  =", len(os.listdir(jars_dir)))