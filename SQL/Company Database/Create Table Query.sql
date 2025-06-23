CREATE TABLE Employee (
	EMP_ID INT PRIMARY KEY,
    First_Name VARCHAR (40),
    Last_Name VARCHAR (40),
    Birth_Day DATE,
    Sex VARCHAR (1),
    Salary INT,
    Super_ID INT,
    Branch_ID INT
);

CREATE TABLE Branch (
	Branch_ID INT PRIMARY KEY,
    Branch_Name VARCHAR (40),
    MGR_ID INT,
    Start_Date DATE,
    FOREIGN KEY (Mgr_ID) REFERENCES Employee (EMP_ID) ON DELETE SET NULL
);

CREATE TABLE Client (
	Client_ID INT PRIMARY KEY,
    Client_Name VARCHAR (40),
    Branch_ID INT,
    FOREIGN KEY (Branch_ID) REFERENCES Branch (Branch_ID) ON DELETE SET NULL
);

CREATE TABLE Works_With (
	EMP_ID INT,
    Client_ID INT,
    Total_Sales INT,
    PRIMARY KEY (EMP_ID, Client_ID),
    FOREIGN KEY (EMP_ID) REFERENCES Employee (EMP_ID) ON DELETE CASCADE,
    FOREIGN KEY (Client_ID) REFERENCES Client (Client_ID) ON DELETE CASCADE
);

CREATE TABLE Branch_Supplier (
	Branch_ID INT,
    Supplier_Name VARCHAR (40),
    Supply_Type VARCHAR (40),
    PRIMARY KEY (Branch_ID, Supplier_Name),
    FOREIGN KEY (Branch_ID) REFERENCES Branch (Branch_ID) ON DELETE CASCADE
);