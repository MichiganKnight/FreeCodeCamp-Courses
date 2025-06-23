UPDATE Student
SET Student_Major = 'CS'
WHERE Student_Major = 'Computer Science';

UPDATE Student
SET Student_Major = 'CS'
WHERE Student_ID = 4;

UPDATE Student
SET Student_Major = 'Biochemistry'
WHERE Student_Major = 'Bio' OR Student_Major = 'Chemistry';

UPDATE Student
SET Student_Name = 'Tom', Student_Major = 'Undecided'
WHERE Student_ID = 1;