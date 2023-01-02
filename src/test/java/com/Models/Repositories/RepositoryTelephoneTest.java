package com.Models.Repositories;

import com.Debug.InitDatabase;
import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Address;
import com.Models.Entities.Person;
import com.Models.Entities.TelephoneNumber;
import jdk.nashorn.internal.runtime.JSErrorType;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTelephoneTest {


    RepositoryTelephone repository;
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
        repository = new RepositoryTelephone(connectionForDatabase);
    }


    @Test
    void getAll() {

        Set<TelephoneNumber> set = repository.getAll();
        assertEquals(set.size(), 6);

    }

    @Test
    void get_WheIDExist() {

        int id = 2;

        TelephoneNumber telephoneNumber = repository.get(id);
        assertEquals(telephoneNumber.getTelephoneNumber(), "22222222");

    }

    @Test
    void get_WheIDNotExist() {

        int id = 10;

        TelephoneNumber telephoneNumber = repository.get(id);
        assertNull(telephoneNumber);

    }

    @Test
    void insert_newTelephone_WhenIdTelephoneNotExist_PersonExist() {

        Person person = new Person(4, "Baiden Joe");
        TelephoneNumber telephoneNumber = new TelephoneNumber("56565656", person);
        Integer id = repository.insert(telephoneNumber);

        assertEquals(id, 7);

    }
    @Test
    void insert_newTelephone_WhenIdTelephoneNotExist_PersonNotExist() {

        Person person = new Person(5, "Fedorov Alexey");
        TelephoneNumber telephoneNumber = new TelephoneNumber("787878", person);
        Integer id = repository.insert(telephoneNumber);
        assertNull(id);

    }


    @Test
    void insert_newTelephone_WhenIdTelephoneExist_PersonExist() {

        Person person = new Person(4, "Baiden Joe");
        TelephoneNumber telephoneNumber = new TelephoneNumber("787878", person);
        Integer id = repository.insert(telephoneNumber);
        assertEquals(id, 7);

    }



    @Test
    void delete() {

        int id = 1;
        repository.delete(id);
        TelephoneNumber telephoneNumber = repository.get(id);
        assertNull(telephoneNumber);

    }

    @Test
    void deleteALL() {

        repository.deleteALL();
        Set<TelephoneNumber> set = repository.getAll();
        assertEquals(set.size(), 0);
    }


    @After
    void closeALL(){
        connectionForDatabase.close();
    }
}