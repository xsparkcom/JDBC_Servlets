package com.Models.DataBase;


import javax.management.Query;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.xml.xpath.XPathExpressionException;
import java.awt.image.AreaAveragingScaleFilter;
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

    public ArrayList<CachedRowSet> executeQuery(ArrayList<Query> queries){

        ArrayList<CachedRowSet> list = new ArrayList<CachedRowSet>();

        try {
            RowSetFactory factory = RowSetProvider.newFactory();

            connection.setAutoCommit(false);

            for (Query query : queries) {
                PreparedStatement preparedStatement = connection.prepareStatement(query.queryText);

                if (query.parameters != null) {
                    for (int i = 1; i <= query.parameters.size(); i++) {
                        if (query.parameters.get(i - 1) instanceof String) {
                            preparedStatement.setString(i, (String) query.parameters.get(i - 1));
                        } else if (query.parameters.get(i - 1) instanceof Integer) {
                            preparedStatement.setInt(i, (Integer) query.parameters.get(i - 1));
                        }
                    }
                }
                boolean hasResult = preparedStatement.execute();
                if (hasResult) {
                    CachedRowSet rowset = factory.createCachedRowSet();
                    rowset.populate(preparedStatement.getResultSet());
                    list.add(rowset);
                } else {

                }
            }

            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
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

    public ArrayList<CachedRowSet> executeQuery(String queryText, ArrayList<Object> parameters){

        ArrayList<Query> queries = new ArrayList<>();
        queries.add(new Query(queryText, parameters));
        return executeQuery(queries);
    }



    public  void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Query{

        String queryText;
        ArrayList<Object> parameters;

        public Query(String queryText, ArrayList<Object> parameters) {
            this.queryText = queryText;
            this.parameters = parameters;
        }

    }


}
