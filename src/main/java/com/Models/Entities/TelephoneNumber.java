package com.Models.Entities;

import javax.json.Json;
import javax.json.JsonObject;

public class TelephoneNumber implements Entity {

    String telephoneNumber ;
    Person person;
    Integer id;

    public TelephoneNumber(Integer id, String telephoneNumber, Person person) {
        this.telephoneNumber = telephoneNumber;
        this.person = person;
        this.id = id;
    }

    public TelephoneNumber(String telephoneNumber, Person person) {
        this.telephoneNumber = telephoneNumber;
        this.person = person;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public JsonObject toJson() {

        JsonObject json = Json.createObjectBuilder()
                .add("id", this.id)
                .add("telephone", this.telephoneNumber).build();
        return json;
    }


    public static TelephoneNumber fromJson(JsonObject jsonObject, Person person) {

        Integer id = null;
        Person tempPerson = person;

        try{
            if (person == null) {
                tempPerson = new Person(jsonObject.getInt("Person_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            id = jsonObject.getInt("id");
        } catch (NullPointerException e) {

        }

        return new TelephoneNumber(id, jsonObject.getString("telephone"), tempPerson);
    }


}
