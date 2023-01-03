package com.Models.DataBase;


import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ConnectionForDatabase {

//    public static final String FILENAME = "src\\webapp\\config.xml";
//public static final String FILENAME = "main/resources/application.properties";
    public static final String FILENAME = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                                        + "application.properties";


    public static final String WEB_FILENAME = "/main/resources/application.properties";
    static final String CONFIG_DRIVER = "driver";
    static final String CONFIG_URL_PREFIX = "url_prefix";
    static final String CONFIG_DATABASE_NAME = "databaseName";
    static final String CONFIG_LOGIN = "login";
    static final String CONFIG_PASSWORD = "password";

    private final Connection connection;
    private final HashMap<String, String> config;

    public ConnectionForDatabase(FileReader reader) {

        try {
            config = readConnectionParameters(reader); // перечитываем параметры соединения
        } catch (XPathExpressionException | IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Class.forName(config.get(CONFIG_DRIVER));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(config.get(CONFIG_DATABASE_NAME), config.get(CONFIG_LOGIN), config.get(CONFIG_PASSWORD));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static HashMap<String,String> readConnectionParameters(FileReader reader) throws XPathExpressionException, FileNotFoundException, IOException {

        HashMap<String,String> config = new HashMap<>();
//        XPathExpression query = XPathFactory.newInstance().newXPath().compile("/config/*");
//        NodeList listConfig = (NodeList) query.evaluate( new InputSource(reader), XPathConstants.NODESET);
//
//        for(int i = 0;i < listConfig.getLength();i++){
//            config.put(listConfig.item(i).getNodeName(),listConfig.item(i).getTextContent());
//
//        }

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        Properties appProps = new Properties();
        appProps.load(new FileReader(appConfigPath));

        config.put("driver", appProps.getProperty("driver"));
        config.put("databaseName", appProps.getProperty("databaseName"));
        config.put("login", appProps.getProperty("login"));
        config.put("password", appProps.getProperty("password"));

        reader.close();
        return config;
    }

    public ArrayList<CachedRowSet> executeQuery(String queryText, ArrayList<Object> listOfParameterOfQuery){

        ArrayList<CachedRowSet> list = new ArrayList<CachedRowSet>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryText)){
            RowSetFactory factory = RowSetProvider.newFactory();

            if (listOfParameterOfQuery != null) {
                for (int i = 1; i <= listOfParameterOfQuery.size(); i++) {
                    if (listOfParameterOfQuery.get(i - 1) instanceof String) {
                        preparedStatement.setString(i, (String) listOfParameterOfQuery.get(i - 1));
                    } else if (listOfParameterOfQuery.get(i - 1) instanceof Integer) {
                        preparedStatement.setInt(i, (Integer) listOfParameterOfQuery.get(i - 1));
                    }
                }
            }

            connection.setAutoCommit(false);


            boolean hasResult = preparedStatement.execute();
            while (hasResult || (preparedStatement.getUpdateCount() != -1)) {
                if (hasResult) {
                    CachedRowSet rowset = factory.createCachedRowSet();
                    rowset.populate(preparedStatement.getResultSet());
                    list.add(rowset);
                } else {

                }
                hasResult = preparedStatement.getMoreResults();
            }

            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
//                    System.err.print("Transaction is being rolled back");
                    e.printStackTrace();
                    connection.rollback();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            return null;
        }

        return list;
    }

    public  void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
