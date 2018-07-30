package com.teachableapps.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
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

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(RecipeDetailActivity.DETAIL_RECIPE_KEY);
            mStepId = savedInstanceState.getInt(RecipeDetailActivity.STEP_ID_KEY);
            Log.d(TAG,"Rotate : " + mRecipe.getName());
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mRecipe = extras.getParcelable(RecipeDetailActivity.DETAIL_RECIPE_KEY);
                mStepId = extras.getInt(RecipeDetailActivity.STEP_ID_KEY);
            }
        }
        if(mRecipe!=null) {
            mSteps = mRecipe.getSteps();
            setTitle(mRecipe.getName());

            StepDetailsFragment stepFragment = new StepDetailsFragment(mSteps.get(mStepId));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.stepdetails_container, stepFragment).commit();

            if(findViewById(R.id.tv_step_description)==null) {
                goFullscreen();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeDetailActivity.DETAIL_RECIPE_KEY,mRecipe);
        outState.putInt(RecipeDetailActivity.STEP_ID_KEY,mStepId);
    }

    public void goFullscreen() {
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onNavigate(int newId) {
        int numSteps = mRecipe.getSteps().size();

        if (newId<0) {
            Toast.makeText(this, "Already at the First Step", Toast.LENGTH_SHORT).show();
        } else if (newId>=numSteps) {
            Toast.makeText(this, "Already at the Last Step", Toast.LENGTH_SHORT).show();
        } else {
            mStepId = newId;
            StepDetailsFragment stepFragment = new StepDetailsFragment(mSteps.get(newId));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.stepdetails_container, stepFragment).commit();
        }
    }
}
