CREATE TABLE Trigger_Test (
	message VARCHAR (100)
);

DELIMITER $$
CREATE
	TRIGGER MyTrigger BEFORE INSERT
    ON Employee
    FOR EACH ROW BEGIN
		INSERT INTO Trigger_Test VALUES ('Added New Employye');
	END$$
DELIMITER ;

INSERT INTO Employee VALUES (109, 'Oscar', 'Martinez', '1968-02-19', 'M', 69000, 106, 3);

SELECT * FROM Trigger_Test;