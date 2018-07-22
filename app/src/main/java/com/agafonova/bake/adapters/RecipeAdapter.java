package com.agafonova.bake.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.agafonova.bake.R;
import com.agafonova.bake.db.Recipe;
import java.util.List;

/**
 * ReyclerView adapter for recipes
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>{

    private static final String NO_RECIPES = "No recipes available.";
    private List<Recipe> mRecipeList;
    final private RecipeAdapter.ResourceClickListener mOnClickListener;

    public RecipeAdapter(RecipeAdapter.ResourceClickListener listener) {
        mOnClickListener = listener;
    }

    public interface ResourceClickListener {
        void onRecipeClick(String data);
    }

    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recipe_item, parent, false);
        RecipeAdapter.RecipeAdapterViewHolder viewHolder = new RecipeAdapter.RecipeAdapterViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeAdapterViewHolder holder, int position) {

        Recipe oneRecipe = mRecipeList.get(position);

        try {
            if(mRecipeList.size()>0) {
                holder.textViewRecipe.setText(oneRecipe.getmName());
            }
            else {
                holder.textViewRecipe.setText(NO_RECIPES);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(mRecipeList == null) {
            return 0;
        }
        return mRecipeList.size();
    }

    public void setData(List<Recipe> results) {
        mRecipeList = results;
        notifyDataSetChanged();
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewRecipe;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            textViewRecipe = itemView.findViewById(R.id.tvRecipe);
            textViewRecipe.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            String recipeID = mRecipeList.get(clickedPosition).getmId();
            mOnClickListener.onRecipeClick(recipeID);
        }
    }

}
