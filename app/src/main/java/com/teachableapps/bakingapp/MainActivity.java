package com.teachableapps.bakingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.teachableapps.bakingapp.models.Recipe;
import com.teachableapps.bakingapp.utilities.ApiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.ListItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

//    private ArrayList<Recipe> mRecipeList;

//    private ArrayList<Recipe> recipeList;
    private List<Recipe> mRecipeList = new ArrayList<>();
    private RecyclerView mRecipeListRecyclerView;
    private RecipeListAdapter mRecipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Clear recipeList
//        if (mRecipeList != null) {
//            mRecipeList.clear();
//        } else {
//            mRecipeList = new ArrayList<Recipe>();
//        }
        mRecipeList.clear();

        // RecyclerView
        mRecipeListRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecipeListRecyclerView.setLayoutManager(layoutManager);
        mRecipeListRecyclerView.setHasFixedSize(false);
        mRecipeListAdapter = new RecipeListAdapter(mRecipeList, this, this);
        mRecipeListRecyclerView.setAdapter(mRecipeListAdapter);

        // Query Recipe API
        loadRecipes();
    }

    @Override
    public void OnListItemClick(Recipe recipe) {

    }

    public void loadRecipes() {

        // Create a call object
        Call<ArrayList<Recipe>> call = ApiUtils.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {

            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                // Success
                mRecipeList = response.body();
                mRecipeListAdapter.setRecipeListData(mRecipeList);

                //int numRecipes = recipeList.size();
                for (Recipe r : mRecipeList) {
                    Log.d(TAG,String.valueOf(r.getId()) + ": " + r.getName());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

                // Failed
                t.printStackTrace();
                Log.d(TAG,"Failed");
                showEmptyView();
            }
        });
    }

    // Show this view when list is empty
    private void showEmptyView() {
        Toast.makeText(this, "EMPTY List", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"EMPTY");
    }

}
