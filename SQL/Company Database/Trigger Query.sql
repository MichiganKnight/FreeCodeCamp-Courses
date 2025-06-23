CREATE TABLE Trigger_Test (
	message VARCHAR (100)
);

DELIMITER $$
CREATE
	TRIGGER MyTrigger BEFORE INSERT
    ON Employee
    FOR EACH ROW BEGIN
		INSERT INTO Trigger_Test VALUES ('Added New Employee');
	END$$
DELIMITER ;

INSERT INTO Employee VALUES (109, 'Oscar', 'Martinez', '1968-02-19', 'M', 69000, 106, 3);

SELECT * FROM Trigger_Test;

DELIMITER $$
CREATE
	TRIGGER MyTrigger1 BEFORE INSERT
    ON Employee
    FOR EACH ROW BEGIN
		INSERT INTO Trigger_Test VALUES (NEW.First_Name);
	END$$
DELIMITER ;

INSERT INTO Employee VALUES (110, 'Kevin', 'Malone', '1978-02-19', 'M', 69000, 106, 3);

SELECT * FROM Trigger_Test;

DELIMITER $$
CREATE
	TRIGGER MyTrigger2 BEFORE INSERT
    ON Employee
	FOR EACH ROW BEGIN
		IF NEW.Sex = 'M' Then
			INSERT INTO Trigger_Test VALUES ('Added Male Employee');
		ELSEIF NEW.Sex = 'F' Then
			INSERT INTO Trigger_Test VALUES ('Added Female Employee');
		ELSE
			INSERT INTO Trigger_Test VALUES ('Added Other Employee');
		END IF;
	END$$
DELIMITER $$;