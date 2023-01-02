package com.Controllers.Servlets.PersonsServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Person;
import com.Models.Repositories.RepositoryPerson;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.HashSet;
import java.util.Set;

public class ServiceGetPersonByID {


    ConnectionForDatabase connection;
    RepositoryPerson repository;


    public ServiceGetPersonByID(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repository = new RepositoryPerson(connection);
    }

    public String execute(String[] ids){

        Set<Person> personSet = new HashSet<>();

        for (int i = 0; i < ids.length; i++) {
            try{
                int idInt = Integer.parseInt(ids[i]);
                personSet.add(repository.get(idInt));
            } catch (Exception e) {

            }
        }

        JsonArrayBuilder jsonPersons = Json.createArrayBuilder();

        for (Person person: personSet) {
            jsonPersons.add(person.toJson());
        }

        return jsonPersons.build().toString();
    }

}
