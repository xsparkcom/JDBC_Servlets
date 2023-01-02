package com.Models.Repositories;

import com.Debug.InitDatabase;
import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Address;
import com.Models.Entities.Person;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryAddressTest {


    RepositoryAddress repositoryAddress;
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
        repositoryAddress = new RepositoryAddress(connectionForDatabase);
    }


    @Test
    void getAll() {

        Set<Address> setOfAddresses = repositoryAddress.getAll();
        assertEquals(setOfAddresses.size(), 4);

    }

    @Test
    void get() {

        int id = 2;

        Address address = repositoryAddress.get(id);
        assertEquals(address.getAddress(), "Krasanoyarsk");

    }

    @Test
    void insert_newAddressWhenIdNull() {

        Address address = new Address("Lugansk");
        int id = repositoryAddress.insert(address);
        assertEquals(id, 5);

    }

    @Test
    void insert_newAddressWhenIdExist() {

        Address address = new Address(8, "Kiev");
        int id = repositoryAddress.insert(address);
        assertEquals(id, 8);
    }

    @Test
    void delete() {

        int id = 1;
        repositoryAddress.delete(id);
        Address address = repositoryAddress.get(id);

        assertNull(address);

    }

    @Test
    void deleteALL() {

        repositoryAddress.deleteALL();
        Set<Address> addressSet = repositoryAddress.getAll();
        assertEquals(addressSet.size(), 0);

    }

    @After
    void closeALL(){
        connectionForDatabase.close();
    }
}