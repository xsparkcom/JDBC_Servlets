package com.Models.Entities;

import javax.json.*;
import java.util.HashSet;
import java.util.Set;

public class Address implements Entity {

    String address;
    Integer id;
    Set<Person> persons = new HashSet<>();

    public Address(Integer id, String address) {
        this.address = address;
        this.id  = id;
    }

    public Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public JsonObject toJson() {

        JsonObject json = Json.createObjectBuilder()
                .add("id", this.id)
                .add("address", this.address).build();
        return json;
    }


    public static Address fromJson(JsonObject jsonObject) {

        Integer id = null;
        try {
            id = jsonObject.getInt("id");
        } catch (NullPointerException e) {

        }

        return new Address(id, jsonObject.getString("address"));
    }
}
