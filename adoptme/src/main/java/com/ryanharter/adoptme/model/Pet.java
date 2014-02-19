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
    public Long id;

    /**
     * The type of animal for this pet.
     */
    public String animal;

    /**
     * The name of the pet.
     */
    public String name;

    /**
     * The breeds of the pet.
     */
    public List<String> breeds;

    /**
     * The id of the pet in the shelter records.
     */
    public String shelterPetId;

    /**
     * Additional features of the pet.
     */
    public List<String> options;

    /**
     * The status of the pet.  This will only be A (Adoptable)
     * for publicly listed pets.
     */
    public String status;

    /**
     * The contact info of the shelter.
     */
    public Contact contactInfo;

    /**
     * The description of the pet.
     */
    public String description;

    /**
     * The sex of the pet.
     */
    public String sex;

    /**
     * The age of the pet.
     */
    public String age;

    /**
     * The size of the pet.
     */
    public String size;

    /**
     * Whether this pet is a mixed breed.
     */
    public boolean mix;

    /**
     * The id of the shelter hosting this pet.
     */
    public String shelterId;

    /**
     * The last time this pet's information was updated.
     */
    public Date lastUpdate;

    /**
     * The photos for this pet.
     */
    public List<Photo> photos;

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

            // Breeds can either be an object or a list
            p.breeds = new ArrayList<>();
            JsonObject breeds = json.getAsJsonObject("breeds");
            if (breeds.get("breed").isJsonObject()) {
                p.breeds.add(getString(breeds, "breed"));
            } else {
                JsonArray breedList = breeds.getAsJsonArray("breed");
                for (int i = 0; i < breedList.size(); i++) {
                    JsonObject breed = breedList.get(i).getAsJsonObject();
                    p.breeds.add(breed.getAsJsonPrimitive("$t").getAsString());
                }
            }

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
            if (json.getAsJsonObject("options").has("option")) {
                JsonArray options = json.getAsJsonObject("options").getAsJsonArray("option");
                for (int i = 0; i < options.size(); i++) {
                    JsonObject option = options.get(i).getAsJsonObject();
                    p.options.add(option.getAsJsonPrimitive("$t").getAsString());
                }
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
