package com.Controllers.Servlets;


import com.Controllers.Servlets.AddressServices.ServiceGetAddressByID;
import com.Controllers.Servlets.AddressServices.ServiceGetAddresses;
import com.Controllers.Servlets.AddressServices.ServiceInsertAddresses;
import com.Controllers.Servlets.PersonsServices.ServiceDeletePersons;
import com.Controllers.Servlets.TelephoneServices.ServiceDeleteTelephone;
import com.Controllers.Servlets.TelephoneServices.ServiceGetTelephoneByID;
import com.Controllers.Servlets.TelephoneServices.ServiceGetTelephones;
import com.Controllers.Servlets.TelephoneServices.ServiceInsertTelephone;
import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Address;
import com.Models.Entities.TelephoneNumber;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

@WebServlet(name = "Telephones", value = "/telephones")
public class ServletTelephone extends HttpServlet {

    ConnectionForDatabase connection;

    @Override
    public void init() throws ServletException {

        try {
            FileReader reader = new FileReader(getServletContext().getRealPath(ConnectionForDatabase.WEB_FILENAME));
            connection = new ConnectionForDatabase(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        };
    }

    @Override
    public void destroy() {
        connection.close();
        super.destroy();
    }

    final String ID = "id";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> parameters = request.getParameterMap();

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (parameters.get(ID) == null || parameters.get(ID).length == 0) {
            out.print(new ServiceGetTelephones(connection).execute());
        } else {
            out.print(new ServiceGetTelephoneByID(connection).execute(parameters.get(ID)));
        }
        out.flush();
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String body = inputStreamToString(request.getInputStream());

        if (body == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ServiceInsertTelephone serviceObject = new ServiceInsertTelephone(connection);
        ArrayList<Integer> listOfId = serviceObject.execute(createTelephonesFromJson(body));
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(createJsonFromId(listOfId));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String body = inputStreamToString(request.getInputStream());

        if (body == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ArrayList<Integer> listOfId = createIdFromJson(body);

        ServiceDeleteTelephone serviceObject = new ServiceDeleteTelephone(connection);
        serviceObject.execute(listOfId);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private static String inputStreamToString(InputStream inputStream) {

        try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
            return scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
        } catch (Exception e) {

        }

        return null;
    }

    private ArrayList<TelephoneNumber> createTelephonesFromJson(String result) {

        final JsonParser parser = Json.createParser(new StringReader(result));
        parser.next();
        JsonArray jsonQueryBody = parser.getArray();

        ArrayList<TelephoneNumber> telephoneList = new ArrayList<>();
        for(JsonValue value: jsonQueryBody) {
            telephoneList.add(TelephoneNumber.fromJson(value.asJsonObject(), null));
        }

        return telephoneList;
    }

    private String createJsonFromId(ArrayList<Integer> listOfId) {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Integer id: listOfId) {
            arrayBuilder.add(id);
        }

        return arrayBuilder.build().toString();

    }


    private ArrayList<Integer> createIdFromJson(String result){

        final JsonParser parser = Json.createParser(new StringReader(result));
        parser.next();
        JsonArray jsonQueryBody = parser.getArray();

        ArrayList<Integer> listOfId = new ArrayList<>();
        for(JsonValue value: jsonQueryBody) {
            listOfId.add(Integer.parseInt(value.toString()));
        }

        return listOfId;

    }



}
