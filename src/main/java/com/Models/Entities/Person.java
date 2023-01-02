package com.Models.Entities;

import javax.json.*;
import java.util.HashSet;
import java.util.Set;

public class Person implements Entity{

    Integer id;
    String name;
    Set<Address> addresses = new HashSet<>();
    Set<TelephoneNumber> telephoneNumbers = new HashSet<>();

    public Person(Integer id) {
        this.id = id;
    }

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(String name) {
       this.name = name;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }


    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<TelephoneNumber> getTelephoneNumbers() {
        return telephoneNumbers;
    }

    public void setTelephoneNumbers(Set<TelephoneNumber> telephoneNumbers) {
        this.telephoneNumbers = telephoneNumbers;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public JsonObject toJson() {

        JsonArrayBuilder jsonTelephones = Json.createArrayBuilder();

        for (TelephoneNumber telephone: this.telephoneNumbers) {
            jsonTelephones.add(telephone.toJson());
        }

        JsonArrayBuilder jsonAddresses = Json.createArrayBuilder();

        for (Address address: this.addresses) {
            jsonAddresses.add(address.toJson());
        }

        JsonObject json = Json.createObjectBuilder()
                .add("id", this.id)
                .add("name", this.name)
                .add("addresses", jsonAddresses)
                .add("telephones", jsonTelephones).build();
        return json;
    }

    public static Person fromJson(JsonObject jsonObject) {

        Integer id = null;
        try {
            id = jsonObject.getInt("id");
        } catch (NullPointerException e) {

        }
        Person person = new Person(id, jsonObject.getString("name"));

        JsonArray jsonArrayAddresses = jsonObject.getJsonArray("addresses");
        for (JsonValue jsonAddress : jsonArrayAddresses) {
            Address address = Address.fromJson(jsonAddress.asJsonObject());
            person.getAddresses().add(address);
            address.getPersons().add(person);
        }

        JsonArray jsonArrayTelephones = jsonObject.getJsonArray("telephones");
        for (JsonValue jsonTelephones : jsonArrayTelephones) {
            TelephoneNumber telephone = TelephoneNumber.fromJson(jsonTelephones.asJsonObject(), person);
            person.getTelephoneNumbers().add(telephone);
        }

        return person;
    }

}
