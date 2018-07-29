package com.teachableapps.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.teachableapps.bakingapp.models.Recipe;
import com.teachableapps.bakingapp.models.Step;

import java.util.List;

public class StepDetailActivity extends AppCompatActivity implements StepDetailsFragment.OnNavClickListener {
    private static final String TAG = StepDetailActivity.class.getSimpleName();

    private Recipe mRecipe;
    private List<Step> mSteps=null;
    private int mStepId=-1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepdetail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mRecipe = (Recipe) extras.getParcelable(RecipeDetailActivity.DETAIL_RECIPE_KEY);
            mSteps = mRecipe.getSteps();
            mStepId = extras.getInt(RecipeDetailActivity.STEP_ID_KEY);

            StepDetailsFragment stepFragment = new StepDetailsFragment(mSteps.get(mStepId));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.stepdetails_container,stepFragment).commit();
        }
    }

    @Override
    public void onNavigate(int newId) {
        int numSteps = mRecipe.getSteps().size();

        if (newId<0) {
            Toast.makeText(this, "Already at the First Step", Toast.LENGTH_SHORT).show();
        } else if (newId>=numSteps) {
            Toast.makeText(this, "Already at the Last Step", Toast.LENGTH_SHORT).show();
        } else {
            StepDetailsFragment stepFragment = new StepDetailsFragment(mSteps.get(newId));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.stepdetails_container, stepFragment).commit();
        }
    }
}
