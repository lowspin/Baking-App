package com.teachableapps.bakingapp.utilities;

import android.util.Log;

import com.teachableapps.bakingapp.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static ArrayList<Recipe> parseRecipesJson(String json) {
        try {
            Recipe recipe;
            JSONArray resultsArray = new JSONArray(json);

            ArrayList<Recipe> recipeList = new ArrayList<>();

            for (int i = 0; i < resultsArray.length(); i++) {

                String thisitem = resultsArray.optString(i, "");
                JSONObject recipeJson = new JSONObject(thisitem);

                recipe = new Recipe(
                        recipeJson.optString("id","Not Available"),
                        recipeJson.optString("name","Not Available"),
                        recipeJson.optString("servings","Not Available"),
                        recipeJson.optString("image","Not Available")
                );

                recipeList.add(recipe);
            }

            return recipeList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
