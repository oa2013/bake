package com.agafonova.bake.ui;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agafonova.bake.R;
import com.agafonova.bake.db.Recipe;

public class ItemDetailFragment extends Fragment {
	private Recipe recipe;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		recipe = (Recipe) getArguments().getParcelable("recipe");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item_detail,
				container, false);
		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
		tvTitle.setText(recipe.getmName());
		tvBody.setText(recipe.getmServings());
		return view;
	}

    public static ItemDetailFragment newInstance(Recipe recipe) {
    	ItemDetailFragment fragmentDemo = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", recipe);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
