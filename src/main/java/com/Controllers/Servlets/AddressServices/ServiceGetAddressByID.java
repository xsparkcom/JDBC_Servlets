package com.Controllers.Servlets.AddressServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Address;
import com.Models.Repositories.RepositoryAddress;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.HashSet;
import java.util.Set;

public class ServiceGetAddressByID {


    ConnectionForDatabase connection;
    RepositoryAddress repositoryAddress;

    public ServiceGetAddressByID(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repositoryAddress = new RepositoryAddress(connection);
    }



    public String execute(String[] ids){

        Set<Address> addressSet = new HashSet<>();

        for (int i = 0; i < ids.length; i++) {
            try{
                int idInt = Integer.parseInt(ids[i]);
                addressSet.add(repositoryAddress.get(idInt));
            } catch (Exception e) {

            }
        }

        JsonArrayBuilder json = Json.createArrayBuilder();

        for (Address address: addressSet) {
            json.add(address.toJson());
        }

        return json.build().toString();
    }

}
