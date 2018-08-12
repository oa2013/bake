package com.agafonova.bake.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.agafonova.bake.R;
import com.agafonova.bake.db.Step;
import java.util.ArrayList;

/*
* Author: Olga Agafonova
* Updated: August 11, 2018
* Title: Baking app (Android Nanodegree Project 3)
*
* Houses the RecipeStepDetailFragment
*
* */

public class RecipeStepDetailActivity extends AppCompatActivity  {

    private static final String LOG_TAG = RecipeDetailActivity.class.getSimpleName();
    private RecipeStepDetailFragment mRecipeStepFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_step_detail);

        Intent intent = getIntent();

        if (intent.hasExtra("recipeStepList")) {

            ArrayList<Step> stepList = intent.getParcelableArrayListExtra("recipeStepList");

            int position = 0;

            if(savedInstanceState == null) {

                if (intent.hasExtra("recipeStepPosition")) {
                    position = intent.getIntExtra("recipeStepPosition", 0);
                }

                mRecipeStepFragment = new RecipeStepDetailFragment();
                mRecipeStepFragment.setData(stepList, position);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipeStepDetailContainer, mRecipeStepFragment)
                        .commit();
            }

        }

	}

}
