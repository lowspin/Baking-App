package com.teachableapps.bakingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teachableapps.bakingapp.models.Ingredient;
import com.teachableapps.bakingapp.models.Recipe;

import java.util.List;

public class RecipeDetailsFragment extends Fragment {
    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();

    private Recipe mRecipe;
    private static TextView tvIngredients;

    public RecipeDetailsFragment() {
    }

    @SuppressLint("ValidFragment")
    public RecipeDetailsFragment(Recipe recipe) {
        mRecipe = recipe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipedetail, container, false);
        tvIngredients = rootView.findViewById(R.id.tv_ingredients);

        // set ingredient list
        setIngredientList();

        return rootView;
    }

    public void setIngredientList() {
        List<Ingredient> ingredientList = mRecipe.getIngredients();
        tvIngredients.setText("");
        for (int i=0; i<ingredientList.size(); i++) {
            tvIngredients.append( " • " +
                    ingredientList.get(i).getQuantity() + " " +
                    ingredientList.get(i).getMeasure() + " " +
                    ingredientList.get(i).getIngredient() + "\n");
        }
    }

}
