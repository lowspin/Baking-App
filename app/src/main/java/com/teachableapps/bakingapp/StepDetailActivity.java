package com.teachableapps.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.teachableapps.bakingapp.models.Step;

public class StepDetailActivity extends AppCompatActivity {
    private static final String TAG = StepDetailActivity.class.getSimpleName();

    private Step mStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepdetail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mStep = (Step) extras.getParcelable(RecipeDetailActivity.STEP_DETAIL_KEY);

            StepDetailsFragment stepFragment = new StepDetailsFragment(mStep);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.stepdetails_container,stepFragment).commit();
        }
    }
}
