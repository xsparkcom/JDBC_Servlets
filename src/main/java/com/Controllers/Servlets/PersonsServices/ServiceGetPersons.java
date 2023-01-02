package com.Controllers.Servlets.PersonsServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Person;
import com.Models.Repositories.RepositoryPerson;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.Set;

public class ServiceGetPersons {


    ConnectionForDatabase connection;
    RepositoryPerson repository;


    public ServiceGetPersons(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repository = new RepositoryPerson(connection);
    }


    public String execute(){

        Set<Person> persons = repository.getAll();
        JsonArrayBuilder jsonPersons = Json.createArrayBuilder();

        for (Person person: persons) {
            jsonPersons.add(person.toJson());
        }

        return jsonPersons.build().toString();
    }

}
