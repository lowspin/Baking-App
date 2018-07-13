package com.teachableapps.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teachableapps.bakingapp.models.Recipe;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>{
    private static final String TAG = RecipeListAdapter.class.getSimpleName();
    private List<Recipe> mRecipeList;
    private final Context mContext;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void OnListItemClick(Recipe recipe);
    }

    public RecipeListAdapter(List<Recipe> recipeList, ListItemClickListener listener, Context context) {

        mRecipeList = recipeList;

        mOnClickListener = listener;
        mContext = context;
    }
    @NonNull
    @Override
    public RecipeListAdapter.RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.RecipeListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeList == null ? 0 : mRecipeList.size();
    }

    public void setRecipeListData(List<Recipe> movieItemList) {
        mRecipeList = movieItemList;
        notifyDataSetChanged();
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView listRecipeItemView;

        public RecipeListViewHolder(View itemView) {
            super(itemView);
            listRecipeItemView = itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            Recipe recipe = mRecipeList.get(listIndex);
            listRecipeItemView = itemView.findViewById(R.id.tv_recipe_name);
            listRecipeItemView.setText(recipe.getName());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.OnListItemClick(mRecipeList.get(clickedPosition));
            Log.d(TAG,"adapter clicked: " + String.valueOf(clickedPosition));
        }
    }
}
