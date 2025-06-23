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