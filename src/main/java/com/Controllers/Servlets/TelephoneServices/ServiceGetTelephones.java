package com.Controllers.Servlets.TelephoneServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.TelephoneNumber;
import com.Models.Repositories.RepositoryTelephone;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.Set;

public class ServiceGetTelephones {

    ConnectionForDatabase connection;
    RepositoryTelephone repository;


    public ServiceGetTelephones(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repository = new RepositoryTelephone(connection);
    }

    public String execute(){

        Set<TelephoneNumber> addresses = repository.getAll();
        JsonArrayBuilder jsonPersons = Json.createArrayBuilder();

        for (TelephoneNumber telephoneNumber: addresses) {
            jsonPersons.add(telephoneNumber.toJson());
        }

        return jsonPersons.build().toString();
    }

}
