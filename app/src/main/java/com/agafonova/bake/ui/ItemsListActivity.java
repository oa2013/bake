package com.agafonova.bake.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.FrameLayout;

import com.agafonova.bake.R;
import com.agafonova.bake.db.Recipe;

/*
* Author: Olga Agafonova
* Updated: July 21, 2018
* Title: Baking app (Android Nanodegree Project 3)
*
* Description: this app shows baking recipes with steps to complete them (images and videos included)
* */

public class ItemsListActivity extends AppCompatActivity implements ItemsListFragment.OnItemSelectedListener {

    private boolean mTwoPane = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setIcon(R.drawable.cupcake);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }

		determineLayout();
	}

	private void determineLayout() {

		FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.detailContainer);
		if (fragmentItemDetail != null) {
			mTwoPane = true;
			ItemsListFragment fragmentItemsList =
					(ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.items, menu);
		return true;
	}

	@Override
	public void onItemSelected(Recipe recipe) {
		if (mTwoPane) {
			ItemDetailFragment fragmentItem = ItemDetailFragment.newInstance(recipe);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.detailContainer, fragmentItem);
			ft.commit();
		} else {
			Intent i = new Intent(this, ItemDetailActivity.class);
			i.putExtra("recipe", recipe);
			startActivity(i);
		}
	}

}
