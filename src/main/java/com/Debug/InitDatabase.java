package com.Debug;

public class InitDatabase {


    public static final String queryText = "DROP TABLE IF EXISTS AddressesOfPersons;\n" +
            "DROP TABLE IF EXISTS TelephonesOfPersons;\n" +
            "DROP TABLE IF EXISTS Addresses;\n" +
            "DROP TABLE IF EXISTS Persons;\n" +
            "\n" +
            "CREATE TABLE Persons (\n" +
            "   id SERIAL,\n" +
            "   Name VARCHAR(50) NOT NULL,\n" +
            "   PRIMARY KEY(id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE Addresses (\n" +
            "   id SERIAL,\n" +
            "   Address VARCHAR(50) NOT NULL,\n" +
            "   PRIMARY KEY(id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE AddressesOfPersons (\n" +
            "   Person_id int,\n" +
            "   Address_id int,\n" +
            "   PRIMARY KEY(Person_id,Address_id),\n" +
            "\tFOREIGN KEY(Person_id) REFERENCES public.Persons(id) on DELETE CASCADE,\n" +
            "\t\tFOREIGN KEY(Address_id) REFERENCES public.Addresses(id) on DELETE CASCADE\n" +
            ");\n" +
            "CREATE TABLE TelephonesOfPersons (\n" +
            "   id SERIAL,\n" +
            "   Person_id int,\n" +
            "\tTelephone VARCHAR(15) NOT NULL,\n" +
            "   PRIMARY KEY(id),\n" +
            "FOREIGN KEY(Person_id) REFERENCES public.persons(id) on DELETE CASCADE\n" +
            ");\n" +
            "\n" +
            "\n" +
            "INSERT INTO Persons VALUES (1, 'John Smith');\n" +
            "INSERT INTO Persons VALUES (2, 'Tom Doe');\n" +
            "INSERT INTO Persons VALUES (3, 'Jane Doe');\n" +
            "INSERT INTO Persons VALUES (4, 'Baiden Joe');\n" +
            "\n" +
            "INSERT INTO Addresses VALUES (1, 'Moscow');\n" +
            "INSERT INTO Addresses VALUES (2, 'Krasanoyarsk');\n" +
            "INSERT INTO Addresses VALUES (3, 'Zelenogorsk');\n" +
            "INSERT INTO Addresses VALUES (4, 'St. Petersburg');\n" +
            "\n" +
            "INSERT INTO AddressesOfPersons VALUES (1, 1);\n" +
            "INSERT INTO AddressesOfPersons VALUES (1, 3);\n" +
            "INSERT INTO AddressesOfPersons VALUES (1, 2);\n" +
            "INSERT INTO AddressesOfPersons VALUES (2, 4);\n" +
            "INSERT INTO AddressesOfPersons VALUES (2, 1);\n" +
            "INSERT INTO AddressesOfPersons VALUES (3, 2);\n" +
            "INSERT INTO AddressesOfPersons VALUES (4, 4);\n" +
            "INSERT INTO AddressesOfPersons VALUES (4, 3);\n" +
            "\n" +
            "INSERT INTO TelephonesOfPersons VALUES (1, 1, '11111111');\n" +
            "INSERT INTO TelephonesOfPersons VALUES (2, 1, '22222222');\n" +
            "INSERT INTO TelephonesOfPersons VALUES (3, 1, '33333333');\n" +
            "INSERT INTO TelephonesOfPersons VALUES (4, 2, '44444444');\n" +
            "INSERT INTO TelephonesOfPersons VALUES (5, 3, '55555555');\n" +
            "INSERT INTO TelephonesOfPersons VALUES (6, 4, '66666666');";



}
