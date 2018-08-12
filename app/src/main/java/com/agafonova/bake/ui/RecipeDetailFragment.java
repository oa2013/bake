package com.agafonova.bake.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.agafonova.bake.R;
import com.agafonova.bake.adapters.IngredientAdapter;
import com.agafonova.bake.adapters.RecipeStepAdapter;
import com.agafonova.bake.db.Recipe;
import com.agafonova.bake.db.Step;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements RecipeStepAdapter.ResourceClickListener {

    private static final String LOG_TAG = RecipeDetailFragment.class.getSimpleName();

    @BindView(R.id.rvFragmentIngredients)
    RecyclerView mIngredientRecyclerView;

    @BindView(R.id.rvFragmentSteps)
    RecyclerView mStepRecyclerView;

    private Recipe mSelectedRecipe;
    private IngredientAdapter mIngredientAdapter;
    private RecipeStepAdapter mStepAdapter;
    private OnRecipeStepClickListener mCallback;

    @Override
    public void onRecipeStepClick(int position) {
        mCallback.onRecipeStepClick((ArrayList<Step>)mSelectedRecipe.getmSteps(), position);
    }

    public interface OnRecipeStepClickListener {
		void onRecipeStepClick(ArrayList<Step> recipeStepList, int position);
	}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnRecipeStepClickListener) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement OnRecipeStepClickListener");
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_recipe_detail,
				container, false);
		ButterKnife.bind(this,view);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity());

        mIngredientRecyclerView.setLayoutManager(layoutManager);
        mIngredientRecyclerView.setHasFixedSize(true);
        mIngredientAdapter = new IngredientAdapter(getActivity());
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);

        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getActivity());

        mStepRecyclerView.setLayoutManager(layoutManager2);
        mStepRecyclerView.setHasFixedSize(true);
        mStepAdapter = new RecipeStepAdapter(getActivity(), this);
        mStepRecyclerView.setAdapter(mStepAdapter);

		return view;
	}

   public void setAdapterData(Recipe recipe) {
	    mSelectedRecipe = recipe;
	    mStepAdapter.setData(recipe.getmSteps());
        mIngredientAdapter.setData(recipe.getmIngredients());
   }
}
