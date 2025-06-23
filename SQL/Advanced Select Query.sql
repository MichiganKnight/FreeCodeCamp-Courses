SELECT Student.Student_Name, Student.Student_Major
FROM Student
ORDER BY Student_Name;

SELECT *
FROM Student
ORDER BY Student_ID ASC;

SELECT *
FROM Student
ORDER BY Student_Major, Student_ID DESC;

SELECT *
FROM Student
ORDER BY Student_ID DESC
LIMIT 2;

SELECT Student_Name, Student_Major
FROM Student
WHERE Student_Major = 'Chemistry' OR Student_Name = 'Kate';