package com.Controllers.Servlets.PersonsServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Repositories.RepositoryPerson;

import java.util.ArrayList;

public class ServiceDeletePersons {


    ConnectionForDatabase connection;
    RepositoryPerson repository;


    public ServiceDeletePersons(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repository = new RepositoryPerson(connection);
    }

    public void execute(ArrayList<Integer> idList) {

        for (Integer id : idList) {
            repository.delete(id);
        }

    }


}
