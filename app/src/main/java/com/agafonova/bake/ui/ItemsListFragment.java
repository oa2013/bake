package com.agafonova.bake.ui;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.agafonova.bake.R;
import com.agafonova.bake.db.Recipe;

public class ItemsListFragment extends Fragment {
	private ArrayAdapter<Recipe> adapterItems;
	private ListView lvItems;
	private OnItemSelectedListener listener;

	public interface OnItemSelectedListener {
		public void onItemSelected(Recipe i);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnItemSelectedListener) {
			listener = (OnItemSelectedListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implement ItemsListFragment.OnItemSelectedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ArrayList<Recipe> recipes = Recipe.getFakeItems();
		adapterItems = new ArrayAdapter<Recipe>(getActivity(),
				android.R.layout.simple_list_item_activated_1, recipes);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_items_list, container,
				false);

		lvItems = (ListView) view.findViewById(R.id.lvItems);
		lvItems.setAdapter(adapterItems);
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View item, int position,
					long rowId) {
				Recipe i = adapterItems.getItem(position);
				listener.onItemSelected(i);
			}
		});
		return view;
	}

}
