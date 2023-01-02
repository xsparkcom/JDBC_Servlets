package com.Controllers.Servlets.PersonsServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Person;
import com.Models.Repositories.RepositoryPerson;

import java.util.ArrayList;

public class ServiceInsertPersons {


    ConnectionForDatabase connection;
    RepositoryPerson repository;


    public ServiceInsertPersons(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repository = new RepositoryPerson(connection);
    }
    public ArrayList<Integer> execute(ArrayList<Person> personList){

        ArrayList<Integer> listOfId = new ArrayList<>();

        for (Person person : personList) {
            listOfId.add(repository.insert(person));
        }

        return listOfId;

    }


}
