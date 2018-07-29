package com.teachableapps.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.teachableapps.bakingapp.models.Ingredient;
import com.teachableapps.bakingapp.models.Recipe;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener {
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    public static final String STEP_DETAIL_KEY = "stepdetailkey";

    private boolean mTwoPane = true;
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

                if(findViewById(R.id.div_twopanes)!=null) {
                    mTwoPane = true;
                    Log.d(TAG,"[[[ TWO PANES ]]]");

                    RecipeDetailsFragment recipeFragment = new RecipeDetailsFragment(mRecipe);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.recipedetails_container,recipeFragment).commit();


                    StepDetailsFragment stepFragment = new StepDetailsFragment(mRecipe.getSteps().get(0));
                    fragmentManager.beginTransaction().add(R.id.stepdetails_container,stepFragment).commit();

                } else {
                    mTwoPane = false;
                    Log.d(TAG,"--- Single Pane ---");
                    RecipeDetailsFragment recipeFragment = new RecipeDetailsFragment(mRecipe);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.recipedetails_container,recipeFragment).commit();

                }
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

    // Define the behavior for onStepSelected
    @Override
    public void onStepSelected(int stepId) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + stepId, Toast.LENGTH_SHORT).show();

        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepDetailsFragment stepFragment = new StepDetailsFragment(mRecipe.getSteps().get(stepId));
            fragmentManager.beginTransaction().replace(R.id.stepdetails_container, stepFragment).commit();
        } else {
            Intent stepDetailIntent = new Intent(this, StepDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(STEP_DETAIL_KEY, mRecipe.getSteps().get(stepId));
            stepDetailIntent.putExtras(bundle);
            startActivity(stepDetailIntent);
        }
    }

//    private void setTitle(String title) {
//        ((TextView)findViewById(R.id.tv_detail_text)).setText(title);
//    }
}
