package com.Controllers.Servlets.AddressServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Address;
import com.Models.Repositories.RepositoryAddress;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.Set;

public class ServiceGetAddresses {


    ConnectionForDatabase connection;
    RepositoryAddress repositoryAddress;

    public ServiceGetAddresses(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repositoryAddress = new RepositoryAddress(connection);
    }


    public String execute(){

        Set<Address> addresses = repositoryAddress.getAll();
        JsonArrayBuilder jsonPersons = Json.createArrayBuilder();

        for (Address address: addresses) {
            jsonPersons.add(address.toJson());
        }

        return jsonPersons.build().toString();
    }

}
