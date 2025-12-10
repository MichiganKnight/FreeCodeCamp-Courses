IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'DatabaseProjectDB')
BEGIN
    CREATE DATABASE DatabaseProjectDB;
    USE DatabaseProjectDB;
END