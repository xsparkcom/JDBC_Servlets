package com.Models.DataBase;

import com.Debug.InitDatabase;
import org.junit.jupiter.api.Test;

import javax.sql.rowset.CachedRowSet;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionForDatabaseTest {

    @Test
    void readConnectionParameters() {


        FileReader reader = null;
        try {
            reader = new FileReader(ConnectionForDatabase.FILENAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            HashMap<String, String> config = ConnectionForDatabase.readConnectionParameters(reader);
        } catch (XPathExpressionException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void executeQuery() {


        FileReader reader = null;
        try {
            reader = new FileReader(ConnectionForDatabase.FILENAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ConnectionForDatabase connectionForDatabase = new ConnectionForDatabase(reader);

//        String queryText = "SELECT * FROM Persons; SELECT * FROM TelephonesOfPersons;";
        String queryText = InitDatabase.queryText;

        ArrayList<CachedRowSet> resultSet = connectionForDatabase.executeQuery(queryText, null);


    }
}