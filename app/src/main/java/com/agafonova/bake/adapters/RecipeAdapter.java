package com.agafonova.bake.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.agafonova.bake.R;
import com.agafonova.bake.db.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * ReyclerView adapter for recipes
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>{

    private static final String NO_RECIPES = "No recipes available.";
    private List<Recipe> mRecipeList;
    private RecipeAdapter.ResourceClickListener mOnClickListener;
    private Context mContext;

    public RecipeAdapter(Context iContext, RecipeAdapter.ResourceClickListener listener) {
        mOnClickListener = listener;
        mContext = iContext;
    }

    public interface ResourceClickListener {
        void onRecipeClick(Recipe selectedRecipe);
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

        Recipe someRecipe = mRecipeList.get(position);

        try {
            if(mRecipeList.size()>0) {
                holder.textViewRecipeName.setText(someRecipe.getmName());

                if(!someRecipe.getmImage().equals("")) {
                    Picasso.with(holder.itemView.getContext()).load(someRecipe.getmImage()).placeholder(R.drawable.cupcake)
                            .error(R.drawable.cupcake).into(holder.imageViewRecipeImage);

                }

                holder.textViewIngredientNumber.setText(Integer.toString(someRecipe.getmIngredients().size()));
                holder.textViewStepNumber.setText(Integer.toString(someRecipe.getmSteps().size()));
                holder.textViewServingNumber.setText(someRecipe.getmServings());

            }
            else {
                holder.textViewRecipeName.setText(NO_RECIPES);
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

        private ImageView imageViewRecipeImage;
        private TextView textViewRecipeName;
        private TextView textViewIngredientNumber;
        private TextView textViewServingNumber;
        private TextView textViewStepNumber;


        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewRecipeImage = itemView.findViewById(R.id.ivRecipeImage);
            textViewRecipeName = itemView.findViewById(R.id.tvRecipeName);
            textViewIngredientNumber = itemView.findViewById(R.id.tvRecipeIngredientNumber);
            textViewServingNumber = itemView.findViewById(R.id.tvRecipeServingNumber);
            textViewStepNumber = itemView.findViewById(R.id.tvRecipeStepNumber);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Recipe selectedRecipe = mRecipeList.get(clickedPosition);
            mOnClickListener.onRecipeClick(selectedRecipe);
        }
    }

}
