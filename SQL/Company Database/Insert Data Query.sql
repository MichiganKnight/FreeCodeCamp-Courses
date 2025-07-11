-- Corporate
INSERT INTO Employee VALUES (100, 'David', 'Wallace', '1967-11-17', 'M', 250000, NULL, NULL);

INSERT INTO Branch VALUES (1, 'Corporate', 100, '2006-09-09');

UPDATE Employee
SET Branch_ID = 1
WHERE EMP_ID = 100;

INSERT INTO Employee VALUES (101, 'Jan', 'Levinson', '1961-05-11', 'F', 110000, 100, 1);

-- Scranton
INSERT INTO Employee VALUES (102, 'Michael', 'Scott', '1964-03-15', 'M', 75000, 100, NULL);

INSERT INTO Branch VALUES (2, 'Scranton', 102, '1992-04-06');

UPDATE Employee
SET Branch_ID = 2
WHERE EMP_ID = 102;

INSERT INTO Employee VALUES (103, 'Angela', 'Martin', '1971-06-25', 'F', 63000, 102, 2);
INSERT INTO Employee VALUES (104, 'Kelly', 'Kapoor', '1980-02-05', 'F', 55000, 102, 2);
INSERT INTO Employee VALUES (105, 'Stanley', 'Hudson', '1958-02-19', 'M', 69000, 102, 2);

-- Stamford
INSERT INTO Employee VALUES (106, 'Josh', 'Porter', '1969-09-05', 'M', 78000, 100, NULL);

INSERT INTO Branch VALUES (3, 'Stamford', 106, '1998-02-13');

UPDATE Employee
SET Branch_ID = 3
WHERE EMP_ID = 106;

INSERT INTO Employee VALUES (107, 'Andy', 'Bernard', '1973-07-22', 'M', 65000, 106, 3);
INSERT INTO Employee VALUES (108, 'Jim', 'Halpert', '1978-10-01', 'M', 71000, 106, 3);

-- Branch Supplier
INSERT INTO Branch_Supplier VALUES (2, 'Hammer Mill', 'Paper');
INSERT INTO Branch_Supplier VALUES (2, 'Uni-ball', 'Writing Utensils');
INSERT INTO Branch_Supplier VALUES (3, 'Patriot Paper', 'Paper');
INSERT INTO Branch_Supplier VALUES (2, 'J.T. Forms & Labels', 'Custom Forms');
INSERT INTO Branch_Supplier VALUES (3, 'Uni-ball', 'Writing Utensils');
INSERT INTO Branch_Supplier VALUES (3, 'Hammer Mill', 'Paper');
INSERT INTO Branch_Supplier VALUES (3, 'Stamford Lables', 'Custom Forms');

-- Client
INSERT INTO Client VALUES (400, 'Dunmore Highschool', 2);
INSERT INTO Client VALUES (401, 'Lackawana Country', 2);
INSERT INTO Client VALUES (402, 'FedEx', 3);
INSERT INTO Client VALUES (403, 'John Daly Law, LLC', 3);
INSERT INTO Client VALUES (404, 'Scranton Whitepapers', 2);
INSERT INTO Client VALUES (405, 'Times Newspaper', 3);
INSERT INTO Client VALUES (406, 'FedEx', 2);

-- Works_With
INSERT INTO Works_With VALUES (105, 400, 55000);
INSERT INTO Works_With VALUES (102, 401, 267000);
INSERT INTO Works_With VALUES (108, 402, 22500);
INSERT INTO Works_With VALUES (107, 403, 5000);
INSERT INTO Works_With VALUES (108, 403, 12000);
INSERT INTO Works_With VALUES (105, 404, 33000);
INSERT INTO Works_With VALUES (107, 405, 26000);
INSERT INTO Works_With VALUES (102, 406, 15000);
INSERT INTO Works_With VALUES (105, 406, 130000);