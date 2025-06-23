-- Find All Employees
SELECT * FROM Employee;

-- Find All Clients
SELECT * FROM Client;

-- Find All Eployees Ordered by Salary
SELECT * FROM Employee ORDER BY Salary DESC;

-- Find All Employees Ordered by Sex And Name
SELECT * FROM Employee ORDER BY Sex, First_Name, Last_Name;

-- Find First 5 Employees
SELECT * FROM Employee LIMIT 5;

-- Find First and Last Name of All Employees
SELECT First_Name, Last_Name FROM Employee;
SELECT First_Name AS Forename, Last_Name AS Surname FROM Employee;

-- Find All Genders
SELECT DISTINCT Sex FROM Employee;

-- Find Number of Employees
SELECT COUNT(EMP_ID) AS 'Number of Employees' FROM Employee;
SELECT COUNT(Super_ID) FROM Employee;

-- Find Number of Female Employees Born After 1970
SELECT COUNT(EMP_ID) FROM Employee WHERE Sex = 'F' AND Birth_Day > '1971-01-01';

-- Find Average of All Employee Salaries
SELECT AVG(Salary) FROM Employee WHERE Sex = 'M';

-- Find Sum of All Employee Salaries
SELECT SUM(Salary) FROM Employee;

SELECT COUNT(SEX), Sex FROM Employee GROUP BY Sex;

-- Total Sales of Each Salesman
SELECT SUM(Total_Sales), EMP_ID FROM Works_With GROUP BY EMP_ID;
SELECT SUM(Total_Sales), Client_ID FROM Works_With GROUP BY Client_ID;

-- WILDCARDS
-- Find Any Clients in LLC
SELECT * FROM Client WHERE Client_Name LIKE '%LLC';

-- Find Branch Suppliers in Label Business
SELECT * FROM Branch_Supplier WHERE Supplier_Name LIKE '% Label%';

-- Find Employees Born in October
SELECT * FROM Employee WHERE Birth_Day LIKE '____-10%';
SELECT * FROM Client WHERE Client_Name LIKE '%school%';

-- UNIONS
-- Find a List of Employee and Branch Names
SELECT First_Name AS 'Company Names' FROM Employee UNION SELECT Branch_Name FROM Branch UNION SELECT Client_Name FROM Client;

-- Find List of All Clients and Branch Supplier Names
SELECT Client_Name, Client.Branch_ID FROM Client UNION SELECT Supplier_Name, Branch_Supplier.Branch_ID FROM Branch_Supplier;

-- Find List of All Money Spent or Earned by Company
SELECT Salary FROM Employee UNION SELECT Total_Sales FROM Works_With;

-- JOINS
INSERT INTO Branch VALUES(4, 'Buffalo', NULL, NULL);

-- Find All Branches and Names of Managers
SELECT Employee.EMP_ID, Employee.First_Name, Branch.Branch_Name FROM Employee JOIN Branch ON Employee.EMP_ID = Branch.MGR_ID;
SELECT Employee.EMP_ID, Employee.First_Name, Branch.Branch_Name FROM Employee LEFT JOIN Branch ON Employee.EMP_ID = Branch.MGR_ID;
SELECT Employee.EMP_ID, Employee.First_Name, Branch.Branch_Name FROM Employee RIGHT JOIN Branch ON Employee.EMP_ID = Branch.MGR_ID;