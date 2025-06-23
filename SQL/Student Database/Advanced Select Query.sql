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

SELECT Student_Name, Student_Major
FROM Student
WHERE Student_Major <> 'Chemistry';

SELECT *
FROM Student
WHERE Student_ID <= 3 AND Student_Name <> 'Jack';

SELECT *
FROM Student
WHERE Student_Name IN ('Claire', 'Kate', 'Mike');

SELECT *
FROM Student
WHERE Student_Major IN ('Biology', 'Chemistry');

SELECT *
FROM Student
WHERE Student_Major IN ('Biology', 'Chemistry') AND Student_ID > 2;