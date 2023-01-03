//Задание
//        1)	Сделать CRUD приложение
//        2)	Использовать JDBC
//        3)	Использовать Servlet API
//        4)	В приложении должны быть реализованы связанные сущности (OneToMany, ManyToMany)
//        5)	Приложение должно работать по принципу REST
//        6)	Запрещено использовать Spring, Hibernate
//        7)	Приложение должно соответствовать принципам SOLID и ООП
//        8)	БД на выбор PostgreSQL или MySQL
//        9)	БД можно установить локально или на docker
//        10)	Должны быть реализованы Unit тесты
//        11)	Тестирование DAO делать с помощью H2 или Test containers
//        12)	Приложение не должно быть слишком простымЗадание
//        1)	Сделать CRUD приложение
//        2)	Использовать JDBC
//        3)	Использовать Servlet API
//        4)	В приложении должны быть реализованы связанные сущности (OneToMany, ManyToMany)
//        5)	Приложение должно работать по принципу REST
//        6)	Запрещено использовать Spring, Hibernate
//        7)	Приложение должно соответствовать принципам SOLID и ООП
//        8)	БД на выбор PostgreSQL или MySQL
//        9)	БД можно установить локально или на docker
//        10)	Должны быть реализованы Unit тесты
//        11)	Тестирование DAO делать с помощью H2 или Test containers
//        12)	Приложение не должно быть слишком простым



package com.Controllers.Servlets;

import com.Controllers.Servlets.PersonsServices.ServiceDeletePersons;
import com.Controllers.Servlets.PersonsServices.ServiceGetPersonByID;
import com.Controllers.Servlets.PersonsServices.ServiceGetPersons;
import com.Controllers.Servlets.PersonsServices.ServiceInsertPersons;
import com.Models.DataBase.ConnectionForDatabase;
import com.Models.Entities.Person;

import javax.json.*;
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


@WebServlet(name = "Persons", value = "/persons")
public class ServletPersons extends HttpServlet {

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
            out.print(new ServiceGetPersons(connection).execute());
        } else {
            out.print(new ServiceGetPersonByID(connection).execute(parameters.get(ID)));
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

        ServiceInsertPersons serviceObject = new ServiceInsertPersons(connection);
        ArrayList<Integer> listOfId = serviceObject.execute(createPersonsFromJson(body));
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

        ServiceDeletePersons serviceObject = new ServiceDeletePersons(connection);
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

    private ArrayList<Person> createPersonsFromJson(String result) {

        final JsonParser parser = Json.createParser(new StringReader(result));
        parser.next();
        JsonArray jsonQueryBody = parser.getArray();

        ArrayList<Person> personsList = new ArrayList<>();
        for(JsonValue value: jsonQueryBody) {
            personsList.add(Person.fromJson(value.asJsonObject()));
        }

        return personsList;
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



