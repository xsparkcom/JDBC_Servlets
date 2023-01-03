package com.Controllers.Servlets.TelephoneServices;

import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Repositories.RepositoryTelephone;

import java.util.ArrayList;

public class ServiceDeleteTelephone {

        ConnectionForDatabase connection;
        RepositoryTelephone repository;


    public ServiceDeleteTelephone(ConnectionForDatabase connection) {
        this.connection = connection;
        this.repository = new RepositoryTelephone(connection);
    }

    public void execute(ArrayList<Integer> idList){

            for (Integer id : idList) {
                repository.delete(id);
            }

        }




}
