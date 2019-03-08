package com.galpotechsolutions.sandwich_club_starter_code_master3.utils;

import android.util.Log;

import com.galpotechsolutions.sandwich_club_starter_code_master3.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String tag = JsonUtils.class.getSimpleName();

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSOKNOWNAS = "alsoKnownAs";
    private static final String DESCRIPTION = "description";
    private static final String INGREDIENTS = "ingredients";
    private static final String PLACEOFORIGIN = "placeOfOrigin";
    private static final String IMAGE_SRC = "image";



    public static Sandwich parseSandwichJson(String json) {
        Log.v(tag, "json = " + json);

        Sandwich sandwich = new Sandwich();
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(json);

            // Extract the JSONObject associated with the key called "NAME"
            JSONObject name = baseJsonResponse.optJSONObject(NAME);
            // Extract the value for the key called mainName;
            String mainName = name.optString(MAIN_NAME);
            sandwich.setMainName(mainName);

            // Extract the value for the key called description;
            String description = baseJsonResponse.optString(DESCRIPTION);
            sandwich.setDescription(description);

            // Extract the value for the key called placeOfOrigin;
            String placeOfOrigin = baseJsonResponse.optString(PLACEOFORIGIN);
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            // Extract the value for the key called image;
            String imageSource = baseJsonResponse.optString(IMAGE_SRC);
            sandwich.setImage(imageSource);

            // Extract the JSONArray associated with the ket called "alsoKnownAs"
            // which represents a list of results (or news).
            JSONArray alsoKnownArray = name.optJSONArray(ALSOKNOWNAS);

            List<String> alsoKnownAs = new ArrayList<>();
            for (int i=0; i< alsoKnownArray.length(); i++){
                alsoKnownAs.add(alsoKnownArray.getString(i));
            }

            sandwich.setAlsoKnownAs(alsoKnownAs);

            // Extract the JSONArray associated with the ket called "ingredients"
            // which represents a list of results (or news).
            JSONArray ingredientsArray = baseJsonResponse.optJSONArray(INGREDIENTS);

            List<String> ingredientsList = new ArrayList<>();
            for (int i=0; i< ingredientsArray.length(); i++){
                ingredientsList.add(ingredientsArray.getString(i));
            }

            sandwich.setIngredients(ingredientsList);

        } catch (JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the sandwich JSON results", e);
        }

        return sandwich;
    }
}
