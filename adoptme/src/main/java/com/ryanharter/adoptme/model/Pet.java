package com.ryanharter.adoptme.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import android.util.Log;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a Pet on PetFinder as returned by their API.
 *
 * Created by rharter on 2/17/14.
 */
public class Pet {

    /**
     * The id for this pet.
     */
    Long id;

    /**
     * The type of animal for this pet.
     */
    String animal;

    /**
     * The name of the pet.
     */
    String name;

    /**
     * The breed of the pet.
     */
    String breed;

    /**
     * The id of the pet in the shelter records.
     */
    String shelterPetId;

    /**
     * Additional features of the pet.
     */
    List<String> options;

    /**
     * The status of the pet.  This will only be A (Adoptable)
     * for publicly listed pets.
     */
    String status;

    /**
     * The contact info of the shelter.
     */
    Contact contactInfo;

    /**
     * The description of the pet.
     */
    String description;

    /**
     * The sex of the pet.
     */
    String sex;

    /**
     * The age of the pet.
     */
    String age;

    /**
     * The size of the pet.
     */
    String size;

    /**
     * Whether this pet is a mixed breed.
     */
    boolean mix;

    /**
     * The id of the shelter hosting this pet.
     */
    String shelterId;

    /**
     * The last time this pet's information was updated.
     */
    Date lastUpdate;

    /**
     * The photos for this pet.
     */
    List<Photo> photos;

    public static class PetDeserializer implements JsonDeserializer<Pet> {

        private static final String TAG = PetDeserializer.class.getSimpleName();

        @Override
        public Pet deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            Pet p = new Pet();
            JsonObject json = jsonElement.getAsJsonObject();

            p.id = Long.parseLong(getString(json, "id"));
            p.animal = getString(json, "animal");
            p.name = getString(json, "name");
            p.breed = getString(json.getAsJsonObject("breeds"), "breed");
            p.shelterPetId = getString(json, "shelterPetId");
            p.status = getString(json, "status");
            p.contactInfo = Contact.getContact(json.getAsJsonObject("contact"));
            p.description = getString(json, "description");
            p.sex = getString(json, "sex");
            p.age = getString(json, "age");
            p.size = getString(json, "size");
            p.mix = getString(json, "mix").equals("yes");
            p.shelterId = getString(json, "shelterId");

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                p.lastUpdate = sdf.parse(getString(json, "lastUpdate"));
            } catch (ParseException e) {
                Log.e(TAG, "Failed to deserialize lastUpdate.", e);
            }

            JsonArray photos = json
                    .getAsJsonObject("media")
                    .getAsJsonObject("photos")
                    .getAsJsonArray("photo");
            Type photosType = new TypeToken<List<Photo>>(){}.getType();
            p.photos = jsonDeserializationContext.deserialize(photos, photosType);

            p.options = new ArrayList<>();
            JsonArray options = json.getAsJsonObject("options").getAsJsonArray("option");
            for (int i = 0; i < options.size(); i++) {
                JsonObject option = options.get(i).getAsJsonObject();
                p.options.add(option.getAsJsonPrimitive("$t").getAsString());
            }

            return p;
        }

        private String getString(JsonObject obj, String name) {
            if (obj.has(name) && obj.getAsJsonObject(name).has("$t"))
                return obj.getAsJsonObject(name).getAsJsonPrimitive("$t").getAsString();
            else
                return null;
        }
    }
}
