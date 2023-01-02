package com.Controllers.Servlets.AddressServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Address;
import com.Models.Entities.Person;
import com.Models.Repositories.RepositoryAddress;
import com.Models.Repositories.RepositoryPerson;

import java.util.ArrayList;

public class ServiceInsertAddresses {

    ConnectionForDatabase connection;
    RepositoryAddress repositoryAddress;

    public ServiceInsertAddresses(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repositoryAddress = new RepositoryAddress(connection);
    }

    public ArrayList<Integer> execute(ArrayList<Address> addressList){

        ArrayList<Integer> listOfId = new ArrayList<>();

        for (Address address : addressList) {
            listOfId.add(repositoryAddress.insert(address));
        }

        return listOfId;

    }


}
