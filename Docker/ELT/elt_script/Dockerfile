FROM python:3.10-slim

# Install PostgreSQL Command-Line Tools
RUN apt-get update && apt-get install -y postgresql-client-17

# Copy ELT Script 
COPY elt_script.py .

# Set the Default Command to Run the ELT Script
CMD ["python", "elt_script.py"]