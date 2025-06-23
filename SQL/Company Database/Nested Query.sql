-- Find Names of All Employees Who Have Sold Over 30000 to a Single Client
SELECT Employee.First_Name, Employee.Last_Name
FROM Employee
WHERE Employee.EMP_ID IN (
	SELECT Works_With.EMP_ID
	FROM Works_With
	WHERE Works_With.Total_Sales > 30000
);

-- Find All Clients Who Are Handled By Michael Scott's Branch
SELECT Client.Client_Name
FROM Client
WHERE Client.Branch_ID = (
	SELECT Branch.Branch_ID
	FROM Branch
	WHERE Branch.MGR_ID = 102
    LIMIT 1
);