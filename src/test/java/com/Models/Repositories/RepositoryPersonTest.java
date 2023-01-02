package com.Models.Repositories;

import com.Debug.InitDatabase;
import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Address;
import com.Models.Entities.Person;
import com.Models.Entities.TelephoneNumber;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryPersonTest {

    RepositoryPerson repositoryPerson;
    ConnectionForDatabase connectionForDatabase;

    @BeforeEach
    void init(){

        FileReader reader = null;
        try {
            reader = new FileReader(ConnectionForDatabase.FILENAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        connectionForDatabase = new ConnectionForDatabase(reader);
        connectionForDatabase.executeQuery(InitDatabase.queryText, null);
        repositoryPerson = new RepositoryPerson(connectionForDatabase);
    }

    @Test
    void getAll() {

        Set<Person> setOfPersons = repositoryPerson.getAll();
        assertEquals(setOfPersons.size(), 4);

    }

    @Test
    void get() {
        int id = 2;

        Person person = repositoryPerson.get(id);
        assertEquals(person.getName(), "Tom Doe");
        assertEquals(person.getAddresses().size(), 2);
        assertEquals(person.getTelephoneNumbers().size(), 1);
    }

    @Test
    void insert_WhenIdExist() {

        Person person = new Person(1, "John Smith");
        person.getAddresses().add(new Address(5, "Novosibirsk"));
        person.getTelephoneNumbers().add(new TelephoneNumber(7, "9999999", person));
        person.getTelephoneNumbers().add(new TelephoneNumber(6, "0000000", person));
        repositoryPerson.insert(person);

    }


    @Test
    void insert_WhenIdNotExist() {

        Person person = new Person("Popov Alexey");
        person.getAddresses().add(new Address("Novosibirsk"));
        person.getTelephoneNumbers().add(new TelephoneNumber("9999999", person));
        person.getTelephoneNumbers().add(new TelephoneNumber("0000000", person));
        repositoryPerson.insert(person);

    }
    @Test
    void delete() {

        int id = 1;
        repositoryPerson.delete(id);
        Person person = repositoryPerson.get(id);

        assertNull(person);

    }

    @Test
    void deleteALL() {

        repositoryPerson.deleteALL();
        Set<Person> personSet = repositoryPerson.getAll();
        assertEquals(personSet.size(), 0);
    }

    @After
    void closeALL(){
        connectionForDatabase.close();
    }
}