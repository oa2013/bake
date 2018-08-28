package com.agafonova.bake.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.agafonova.bake.BakeAppWidgetProvider;
import com.agafonova.bake.R;
import com.agafonova.bake.db.Recipe;
import com.agafonova.bake.db.Step;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnRecipeStepClickListener {

    private static final String LOG_TAG = RecipeDetailActivity.class.getSimpleName();
    private static final String PREF_NAME="BakingApp";
    private static final String RECIPE = "";

    private boolean mIsTablet = false;
    private RecipeStepDetailFragment mRecipeStepFragment;
    private ArrayList<Step> mStepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();

        loadEverything(savedInstanceState, intent);

    }

    private void loadEverything(Bundle savedInstanceState, Intent intent) {
        try {

            if (intent.hasExtra("recipeDetails")) {
                Recipe recipe = intent.getParcelableExtra("recipeDetails");

                //We will retrieve the ingredient list in WidgetService
                putRecipeIntoSharedPrefs(recipe);

                RecipeDetailFragment detailFragment = (RecipeDetailFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragment_recipe_detail);

                detailFragment.setAdapterData(recipe);

                setTitle(recipe.getmName());

                determineDevice();

                sentDataToBakeWidget(recipe);

                if (mIsTablet) {

                    mStepList = recipe.getmSteps();

                    if (savedInstanceState == null) {

                        mRecipeStepFragment = new RecipeStepDetailFragment();
                        mRecipeStepFragment.setData(mStepList, 0);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .add(R.id.recipeStepDetailContainer, mRecipeStepFragment)
                                .addToBackStack(null)
                                .commit();
                    }

                } else {
                    mIsTablet = false;
                }
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString());
        }
    }

    private void putRecipeIntoSharedPrefs(Recipe recipe) {
        Gson gson = new Gson();
        String recipeString = gson.toJson(recipe);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(RECIPE, recipeString);
        editor.commit();
    }

    @Override
    public void onRecipeStepClick(ArrayList<Step> recipeStepList, int position) {

        if (mIsTablet) {
            mRecipeStepFragment.updatePosition(position);
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putParcelableArrayListExtra("recipeStepList", recipeStepList);
            intent.putExtra("recipeStepPosition", position);
            startActivity(intent);
        }
    }

    /*
    * See https://stackoverflow.com/questions/15055458/detect-7-inch-and-10-inch-tablet-programmatically
    * */
    public void determineDevice() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;

        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);

        if (smallestWidth > 720) {
            mIsTablet = true;
        } else if (smallestWidth > 600) {
            mIsTablet = true;
        } else {
            mIsTablet = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /*
    * We will hit this method when we come
    * back from our video fragment in phone (not tablet) view
    */
    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
    }

    public void sentDataToBakeWidget(Recipe recipe) {
       AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
       int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeAppWidgetProvider.class));
       BakeAppWidgetProvider.updateAppWidget(this, appWidgetManager, ids[0]);
    }
}