package com.Controllers.Servlets.TelephoneServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.TelephoneNumber;
import com.Models.Repositories.RepositoryTelephone;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.HashSet;
import java.util.Set;

public class ServiceGetTelephoneByID {


    ConnectionForDatabase connection;
    RepositoryTelephone repository;


    public ServiceGetTelephoneByID(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repository = new RepositoryTelephone(connection);
    }

    public String execute(String[] ids){

        Set<TelephoneNumber> addressSet = new HashSet<>();

        for (int i = 0; i < ids.length; i++) {
            try{
                int idInt = Integer.parseInt(ids[i]);
                addressSet.add(repository.get(idInt));
            } catch (Exception e) {

            }
        }

        JsonArrayBuilder json = Json.createArrayBuilder();

        for (TelephoneNumber telephoneNumber: addressSet) {
            json.add(telephoneNumber.toJson());
        }

        return json.build().toString();
    }

}
