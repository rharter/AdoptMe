package com.ryanharter.adoptme.sample;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by rharter on 2/17/14.
 */
public class Contact {

    public String email;
    public String phone;
    public String fax;
    public String address1;
    public String address2;
    public String city;
    public String state;
    public String zip;

    public static Contact getContact(JsonObject json) {
        Contact c = new Contact();
        if (json.has("email") && json.getAsJsonObject("email").has("$t")) {
            c.email = json.getAsJsonObject("email").getAsJsonPrimitive("$t").getAsString();
        }
        if (json.has("phone") && json.getAsJsonObject("phone").has("$t")) {
            c.phone = json.getAsJsonObject("phone").getAsJsonPrimitive("$t").getAsString();
        }
        if (json.has("fax") && json.getAsJsonObject("fax").has("$t")) {
            c.fax = json.getAsJsonObject("fax").getAsJsonPrimitive("$t").getAsString();
        }
        if (json.has("address1") && json.getAsJsonObject("address1").has("$t")) {
            c.address1 = json.getAsJsonObject("address1").getAsJsonPrimitive("$t").getAsString();
        }
        if (json.has("address2") && json.getAsJsonObject("address2").has("$t")) {
            c.address2 = json.getAsJsonObject("address2").getAsJsonPrimitive("$t").getAsString();
        }
        if (json.has("city") && json.getAsJsonObject("city").has("$t")) {
            c.city = json.getAsJsonObject("city").getAsJsonPrimitive("$t").getAsString();
        }
        if (json.has("state") && json.getAsJsonObject("state").has("$t")) {
            c.state = json.getAsJsonObject("state").getAsJsonPrimitive("$t").getAsString();
        }
        if (json.has("zip") && json.getAsJsonObject("zip").has("$t")) {
            c.zip = json.getAsJsonObject("zip").getAsJsonPrimitive("$t").getAsString();
        }
        return c;
    }

    public class ContactDeserializer implements JsonDeserializer<Contact> {

        @Override
        public Contact deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();
            return Contact.getContact(json);
        }
    }

}
