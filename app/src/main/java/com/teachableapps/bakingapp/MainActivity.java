package com.teachableapps.bakingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.teachableapps.bakingapp.models.Recipe;
import com.teachableapps.bakingapp.utilities.JsonUtils;
import com.teachableapps.bakingapp.utilities.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.ListItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

//    private ArrayList<Recipe> recipeList;
    List<Recipe> mRecipeList = new ArrayList<>();
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

        // Query Recipe URL
        new RecipeQueryTask().execute();
    }

    @Override
    public void OnListItemClick(Recipe recipe) {

    }


    // AsyncTask to perform query
    public class RecipeQueryTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {
            if (searchResults != null && !searchResults.equals("")) {
                mRecipeList = JsonUtils.parseRecipesJson(searchResults);
                mRecipeListAdapter.setRecipeListData(mRecipeList);

                //int numRecipes = recipeList.size();
                for (Recipe r : mRecipeList) {
                    Log.d(TAG,String.valueOf(r.getId()) + ": " + r.getName());
                }

                Log.d(TAG, "Done. " );
            }
        }
    }

}
