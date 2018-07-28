package com.teachableapps.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.teachableapps.bakingapp.models.Ingredient;
import com.teachableapps.bakingapp.models.Recipe;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipedetail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d(TAG,MainActivity.RECIPE_DETAIL_KEY);
//            mRecipe = (Recipe) extras.getParcelable(MainActivity.RECIPE_DETAIL_KEY);
            mRecipe = (Recipe) extras.getParcelable(MainActivity.RECIPE_DETAIL_KEY);

            if (mRecipe != null) {

                setTitle(mRecipe.getName());

                RecipeDetailsFragment recipeFragment = new RecipeDetailsFragment(mRecipe);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.recipedetails_container,recipeFragment).commit();


                StepDetailsFragment stepFragment = new StepDetailsFragment(mRecipe.getSteps());
                fragmentManager.beginTransaction().add(R.id.stepdetails_container,stepFragment).commit();

//                List<Ingredient> ingredientList = mRecipe.getIngredients();
//                recipeFragment.setIngredientList(ingredientList);

//                RecipeDetailFragment fragment = new RecipeDetailFragment();
//                StepAdapter stepAdapter = new StepAdapter(this, this);
//                fragment.setRecipe(mRecipe);
//                stepAdapter.setData(mRecipe.getSteps());
//                fragment.setStepAdapter(stepAdapter);
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.recipe_detail_container, fragment).commit();
            }
        }


    }

//    private void setTitle(String title) {
//        ((TextView)findViewById(R.id.tv_detail_text)).setText(title);
//    }
}
