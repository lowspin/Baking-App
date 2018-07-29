package com.teachableapps.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.teachableapps.bakingapp.models.Ingredient;
import com.teachableapps.bakingapp.models.Recipe;
import com.teachableapps.bakingapp.models.Step;

import java.util.List;

public class RecipeDetailsFragment extends Fragment implements StepListAdapter.StepItemClickListener {
    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();

    private Recipe mRecipe;
    private static TextView tvIngredients;
    private RecyclerView mStepListRecyclerView;
    private StepListAdapter mStepListAdapter;

    // Define a new interface that triggers a callback in the host activity
    OnStepClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onStepSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    public RecipeDetailsFragment() {}

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

        // set steps list
        mStepListRecyclerView = rootView.findViewById(R.id.rv_steps);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mStepListRecyclerView.setLayoutManager(layoutManager);
        mStepListRecyclerView.setHasFixedSize(false);
        List<Step> mStepList = mRecipe.getSteps();
        mStepListAdapter = new StepListAdapter(mStepList, getActivity(), this);
        mStepListRecyclerView.setAdapter(mStepListAdapter);

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

    @Override
    public void OnListItemClick(Step step) {
        // Trigger the callback method and pass in the id that was clicked
        mCallback.onStepSelected(step.getId());
    }

}
