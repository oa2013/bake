package com.agafonova.bake.ui;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.agafonova.bake.R;
import com.agafonova.bake.db.Recipe;

public class ItemDetailActivity extends FragmentActivity {
	ItemDetailFragment fragmentItemDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
		if (savedInstanceState == null) {
			fragmentItemDetail = ItemDetailFragment.newInstance(recipe);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.detailContainer, fragmentItemDetail);
			ft.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.item_detail, menu);
		return true;
	}

}
