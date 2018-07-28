package com.teachableapps.bakingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teachableapps.bakingapp.models.Step;

import java.util.List;

public class StepDetailsFragment extends Fragment {
    private static final String TAG = StepDetailsFragment.class.getSimpleName();

    private List<Step> mSteps;
    private TextView tv_description;
    private ImageView iv_prev;
    private ImageView iv_next;

    // Mandatory empty constructor
    public StepDetailsFragment() {
    }

    @SuppressLint("ValidFragment")
    public StepDetailsFragment(List<Step> steps) {
        mSteps = steps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_stepdetail, container, false);

        tv_description = rootView.findViewById(R.id.tv_step_description);
        iv_prev = rootView.findViewById(R.id.nav_prev);
        iv_next = rootView.findViewById(R.id.nav_next);

        if(mSteps != null) {
            tv_description.setText(mSteps.get(0).getDescription());
        }
        return rootView;

    }

}
