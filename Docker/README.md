# Custom ELT Project

This repository contains a custom Extract, Load, Transform (ETL) project that utilizes Docker and PostgreSQL to demonstrate a simple ELT process.

# Table of Contents:
[Repository Structure](#repository-structure)

## Repository Structure

1. [**docker-compose.yaml**](docker-compose.yaml): This file contains the configuration for Docker Compose, which is used to orchestrate multiple Docker Containers. It defines three services:
    - `source_postgres`: The source PostgreSQL database
    - `destination_postgres`: The destination PostgreSQL database
    = `elt_script`: The service that runs the ELT script

2. [**elt_script/Dockerfile**](elt_script/Dockerfile): This Dockerfile sets up a Python environment and installs the PostgreSQL client. It also copies the ELT script into the container and sets it as the default command