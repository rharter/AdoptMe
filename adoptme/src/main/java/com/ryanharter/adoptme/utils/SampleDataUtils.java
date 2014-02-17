package com.ryanharter.adoptme.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.ryanharter.adoptme.R;
import com.ryanharter.adoptme.model.Pet;
import com.ryanharter.adoptme.model.Pet.PetDeserializer;

import android.content.Context;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by rharter on 2/17/14.
 */
public class SampleDataUtils {

    /**
     * Retrieves a sample list of pets from bundled json data.
     */
    public static List<Pet> getSamplePets(Context context) {
        List<Pet> pets;

        InputStream is = context.getResources().openRawResource(R.raw.sample_pets);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Pet.class, new PetDeserializer())
                .create();

        Type petsType = new TypeToken<List<Pet>>(){}.getType();
        pets = gson.fromJson(new InputStreamReader(is), petsType);

        return pets;
    }

}
