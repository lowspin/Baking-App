package com.teachableapps.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.teachableapps.bakingapp.models.Step;

import java.util.List;

public class StepDetailsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = StepDetailsFragment.class.getSimpleName();

    private Step mStep;
    private TextView tv_description;
    private ImageButton nav_prev;
    private ImageButton nav_next;

    // Define a new interface that triggers a callback in the host activity
    StepDetailsFragment.OnNavClickListener mCallback;

    // OnNavClickListener interface, calls a method in the host activity named onNavigate
    public interface OnNavClickListener {
        void onNavigate(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (StepDetailsFragment.OnNavClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNavClickListener");
        }
    }

    // Mandatory empty constructor
    public StepDetailsFragment() {
    }

    @SuppressLint("ValidFragment")
    public StepDetailsFragment(Step step) {
        mStep = step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_stepdetail, container, false);

        tv_description = rootView.findViewById(R.id.tv_step_description);

        if(rootView.findViewById(R.id.nav_next)!=null) {
            nav_prev = rootView.findViewById(R.id.nav_prev);
            nav_next = rootView.findViewById(R.id.nav_next);

            nav_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // Trigger the callback method and pass in the previous id
                    mCallback.onNavigate(mStep.getId() - 1);
                    Log.d(TAG, "<<<<<<< Prev " + String.valueOf(mStep.getId() - 1));
                }
            });
            nav_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // Trigger the callback method and pass in the next id
                    mCallback.onNavigate(mStep.getId() + 1);
                    Log.d(TAG, "NExt >>>>>>>> " + String.valueOf(mStep.getId() + 1));
                }
            });
        }

        if(mStep != null) {
            tv_description.setText(mStep.getDescription());
        }
        return rootView;

    }

    @Override
    public void onClick(View view) {}
}
