package com.Controllers.Servlets.TelephoneServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.TelephoneNumber;
import com.Models.Repositories.RepositoryTelephone;

import java.util.ArrayList;

public class ServiceInsertTelephone {



    ConnectionForDatabase connection;
    RepositoryTelephone repository;


    public ServiceInsertTelephone(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repository = new RepositoryTelephone(connection);
    }

    public ArrayList<Integer> execute(ArrayList<TelephoneNumber> telephoneList){

        ArrayList<Integer> listOfId = new ArrayList<>();

        for (TelephoneNumber telephoneNumber : telephoneList) {
            listOfId.add(repository.insert(telephoneNumber));
        }

        return listOfId;

    }


}
