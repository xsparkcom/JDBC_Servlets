DROP TABLE IF EXISTS AddressesOfPersons;
DROP TABLE IF EXISTS Addresses;
DROP TABLE IF EXISTS Persons;
DROP TABLE IF EXISTS TelephonesOfPersons;

CREATE TABLE Persons (
   id SERIAL,
   Name VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE Addresses (
   id SERIAL,
   Address VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE AddressesOfPersons (
   Person_id int,
   Address_id int,
   PRIMARY KEY(Person_id,Address_id),
	FOREIGN KEY(Person_id) REFERENCES public.Persons(id) on DELETE CASCADE,
		FOREIGN KEY(Address_id) REFERENCES public.Addresses(id) on DELETE CASCADE
);
CREATE TABLE TelephonesOfPersons (
   id SERIAL,
   Person_id int,
	Telephone VARCHAR(15) NOT NULL,
   PRIMARY KEY(id),
FOREIGN KEY(Person_id) REFERENCES public.persons(id) on DELETE CASCADE
);


INSERT INTO Persons VALUES (1, 'John Smith');
INSERT INTO Persons VALUES (2, 'Tom Doe');
INSERT INTO Persons VALUES (3, 'Jane Doe');
INSERT INTO Persons VALUES (4, 'Baiden Joe');

INSERT INTO Addresses VALUES (1, 'Moscow');
INSERT INTO Addresses VALUES (2, 'Krasanoyarsk');
INSERT INTO Addresses VALUES (3, 'Zelenogorsk');
INSERT INTO Addresses VALUES (4, 'St. Petersburg');

INSERT INTO AddressesOfPersons VALUES (1, 1);
INSERT INTO AddressesOfPersons VALUES (1, 3);
INSERT INTO AddressesOfPersons VALUES (1, 2);
INSERT INTO AddressesOfPersons VALUES (2, 4);
INSERT INTO AddressesOfPersons VALUES (2, 1);
INSERT INTO AddressesOfPersons VALUES (3, 2);
INSERT INTO AddressesOfPersons VALUES (4, 4);
INSERT INTO AddressesOfPersons VALUES (4, 3);

INSERT INTO TelephonesOfPersons VALUES (1, 1, '11111111');
INSERT INTO TelephonesOfPersons VALUES (2, 1, '22222222');
INSERT INTO TelephonesOfPersons VALUES (3, 1, '33333333');
INSERT INTO TelephonesOfPersons VALUES (4, 2, '44444444');
INSERT INTO TelephonesOfPersons VALUES (5, 3, '55555555');
INSERT INTO TelephonesOfPersons VALUES (6, 4, '66666666');