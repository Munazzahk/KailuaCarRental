 -- ÅBNES I ROOT --
CREATE USER IF NOT EXISTS 'kailua'@'localhost' IDENTIFIED BY '123';

DROP DATABASE IF EXISTS kailua_rental;
CREATE DATABASE kailua_rental;
USE kailua_rental;

DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS renter;
DROP TABLE IF EXISTS contract;

CREATE TABLE car (
	plate_number VARCHAR(10) PRIMARY KEY NOT NULL,
	category ENUM('Luxury', 'Family', 'Sport') NOT NULL,
    brand VARCHAR(25) NOT NULL,
    fuel ENUM('DIESEL','ELECTRIC','GAS') NOT NULL,
    registration_date DATE NOT NULL,
    mileage DECIMAL(10,2) NOT NULL
); 
CREATE TABLE renter (
	license_id INT PRIMARY KEY NOT NULL,
    fullname VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    zip_code INT NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(25) NOT NULL,
    cellphone INT NOT NULL,
    phone INT,
    email VARCHAR(50) NOT NULL,
    license_date DATE NOT NULL
);
CREATE TABLE contract(
    contract_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    plate_number VARCHAR(20),
    license_id INT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    max_km INT,
    contract_mileage INT,
    CONSTRAINT contract_FK_car
        FOREIGN KEY (plate_number)
        REFERENCES car (plate_number),
    CONSTRAINT contract_FK_renter
        FOREIGN KEY (license_id)
        REFERENCES renter (license_id)
);

DELIMITER $$
-- trigger that makes sure that the dates don't overlap before creating a contract. 
CREATE TRIGGER check_rental_overlap BEFORE INSERT ON contract
FOR EACH ROW
BEGIN
    DECLARE overlap_count INT;

    SELECT COUNT(*)
    INTO overlap_count
    FROM contract
    WHERE plate_number = NEW.plate_number
    AND NEW.start_date < end_date
    AND NEW.end_date > start_date;
    -- if the count is above 0 then raise an error
    IF overlap_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot insert overlapping rental periods for the same car';
    END IF;
END$$

DELIMITER ;

CREATE TABLE employees (
employee_username VARCHAR(25) PRIMARY KEY NOT NULL,
employee_fullname VARCHAR(50) NOT NULL
);

INSERT INTO employees VALUES
('Emma123', 'Emma Pasquer'), ('Nanna456','Nanna Petersen'), ('Munazzah789','Munazzah Khurshid'),('Arfi101','Arfi Jabril');





INSERT INTO car(plate_number, category, brand, fuel, registration_date, mileage)
VALUES ('ELN 666', 'Luxury', 'Tesla Cybertruck', 'Electric', '2023-11-08', 5984),
('BRBI 255', 'Luxury', 'Rolls Royce', 'Diesel', '2024-01-21', 34),
('CLWN 101', 'Family', 'Clow Car', 'Gas', '2000-04-01', 1001),
('KDS 4EVA', 'Family', 'School Bus', 'Electric', '2020-03-12', 0),
('BATMAN1', 'Sport', 'Bat Mobile', 'Diesel', '1939-05-01', 9999),
('BND 007', 'Sport', 'Aston Martin DB5', 'Gas', '2007-10-07', 9999);

SELECT * FROM car;

INSERT INTO renter(license_id, fullname, address, zip_code, city, state, cellphone, phone, email, license_date)
VALUES (98765432, 'Barbie', '1959 Malibu Way', 90263, 'Malibu', 'CA', '0000000000', NULL, 'barbieLovesKen@gmail.com', "1978-01-14"),
(1234567, 'Peter McCallister', '671 Lincoln Boulevard', 60093, 'Winnekta','IL', '123456789', NULL, 'whereIsKevin@aol.com', "1980-12-21"),
(8008135, 'Mr. Pool, Dead', 'Sister Margarets School for Wayward Girls', 66666, 'New York City', 'NY', '8008135', NULL, 'ScrewUHughJackman@ImBetterThanYou.dp', "1985-11-22");

SELECT * FROM renter;

INSERT INTO contract(contract_id, plate_number, license_id, start_date, end_date, max_km, contract_mileage)
VALUES (DEFAULT, 'BRBI 255', 98765432, '2022-08-12', '2022-08-19', 500, (SELECT mileage from car Where plate_number = 'BRBI 255') + 25),
(DEFAULT, 'KDS 4EVA', 1234567, '1990-12-21', '1990-12-24', 1000, (SELECT mileage from car Where plate_number = 'KDS 4EVA') + 999),
(DEFAULT, 'BATMAN1', 8008135, '2024-02-05', '2024-03-05', 1000, (SELECT mileage from car Where plate_number = 'BATMAN1') + 3);

SELECT * FROM contract;


DROP DATABASE IF EXISTS kailua_employees;
CREATE DATABASE  kailua_employees;
use kailua_employees;
CREATE TABLE employees (
employee_username VARCHAR(25) PRIMARY KEY NOT NULL,
employee_password VARCHAR(25) NOT NULL
);

INSERT INTO employees VALUES
('Emma123', '123'), ('Nanna456','456'), ('Munazzah789','789'),('Arfi101','101');

-- Admin -- 

GRANT ALL PRIVILEGES ON kailua_employees.* TO 'kailua'@'localhost';
GRANT ALL PRIVILEGES ON kailua_rental.* TO 'kailua'@'localhost';


 -- Create users --
CREATE USER IF NOT EXISTS 'Emma123'@'localhost' IDENTIFIED BY '123';
CREATE USER IF NOT EXISTS'Nanna456'@'localhost' IDENTIFIED BY '456';
CREATE USER IF NOT EXISTS'Munazzah789'@'localhost' IDENTIFIED BY '789';
CREATE USER IF NOT EXISTS'Arfi101'@'localhost' IDENTIFIED BY '101';

GRANT SELECT, CREATE, UPDATE, INSERT, DELETE ON kailua_rental.* TO 'Emma123'@'localhost';
GRANT SELECT, CREATE, UPDATE, INSERT, DELETE ON kailua_rental.* TO 'Nanna456'@'localhost';
GRANT SELECT, CREATE, UPDATE, INSERT, DELETE ON kailua_rental.* TO 'Munazzah789'@'localhost';
GRANT SELECT, CREATE, UPDATE, INSERT, DELETE ON kailua_rental.* TO 'Arfi101'@'localhost';

SELECT * FROM employees;


