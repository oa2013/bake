package com.agafonova.bake.adapters;

import android.content.Context;;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.agafonova.bake.R;
import com.agafonova.bake.db.Ingredient;

import java.util.List;

/**
 * Created by Olga Agafonova on 8/8/18.
 * ReyclerView adapter for recipe ingredients
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder>{

    private static final String NO_INGREDIENTS = "No ingredients available.";
    private List<Ingredient> mIngredientList;
    private Context mContext;

    public IngredientAdapter(Context iContext) {
        mContext = iContext;
    }

    public interface ResourceClickListener {
        void onIngredientClick(int position);
    }

    @Override
    public IngredientAdapter.IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.ingredient_item, parent, false);
        IngredientAdapter.IngredientAdapterViewHolder viewHolder = new IngredientAdapter.IngredientAdapterViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientAdapterViewHolder holder, int position) {

        Ingredient oneIngredient = mIngredientList.get(position);

        try {
            if(mIngredientList.size()>0) {
                holder.textViewIngredientName.setText(oneIngredient.getmIngredient());
                holder.textViewIngredientQuantity.setText(oneIngredient.getmQuantity());
                holder.textViewIngredientMeasure.setText(oneIngredient.getmMeasure());
            }
            else {
                holder.textViewIngredientName.setText(NO_INGREDIENTS);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(mIngredientList == null) {
            return 0;
        }
        return mIngredientList.size();
    }

    public void setData(List<Ingredient> results) {
        mIngredientList = results;
        notifyDataSetChanged();
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewIngredientName;
        private TextView textViewIngredientQuantity;
        private TextView textViewIngredientMeasure;

        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);

            textViewIngredientName = itemView.findViewById(R.id.tvIgredientName);
            textViewIngredientQuantity = itemView.findViewById(R.id.tvIgredientQuantity);
            textViewIngredientMeasure = itemView.findViewById(R.id.tvIgredientMeasure);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
        }
    }

}
