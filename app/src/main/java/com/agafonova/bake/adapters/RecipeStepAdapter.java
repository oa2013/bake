package com.agafonova.bake.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.agafonova.bake.R;
import com.agafonova.bake.db.Step;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Olga Agafonova on 8/8/18.
 * ReyclerView adapter for recipe steps
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder>{

    private static final String NO_STEPS = "No recipe steps available.";
    private List<Step> mStepsList;
    private RecipeStepAdapter.ResourceClickListener mOnClickListener;
    private Context mContext;

    public RecipeStepAdapter(Context iContext, RecipeStepAdapter.ResourceClickListener listener) {
        mOnClickListener = listener;
        mContext = iContext;
    }

    public interface ResourceClickListener {
        void onRecipeStepClick(int position);
    }

    @Override
    public RecipeStepAdapter.RecipeStepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recipe_step_item, parent, false);
        RecipeStepAdapter.RecipeStepAdapterViewHolder viewHolder = new RecipeStepAdapter.RecipeStepAdapterViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecipeStepAdapter.RecipeStepAdapterViewHolder holder, int position) {

        final Step oneStep = mStepsList.get(position);

        try {
            if(mStepsList.size()>0) {
                holder.textViewRecipeStep.setText(oneStep.getmShortDescription());

                if(!oneStep.getmThumbnailUrl().equals("")) {
                    Picasso.with(mContext).load(oneStep.getmThumbnailUrl()).placeholder(R.drawable.cupcake)
                            .error(R.drawable.ic_error_24dp).into(holder.imageViewRecipeStep, new Callback() {

                        @Override
                        public void onSuccess() {
                            Log.d("RecipeStepAdapter","Picasso success: " + oneStep.getmThumbnailUrl());
                        }

                        @Override
                        public void onError() {
                            Log.d("RecipeStepAdapter","Picasso failure: " + oneStep.getmThumbnailUrl());
                        }
                    });
                }
            }
            else {
                holder.textViewRecipeStep.setText(NO_STEPS);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(mStepsList == null) {
            return 0;
        }
        return mStepsList.size();
    }

    public void setData(List<Step> results) {
        mStepsList = results;
        notifyDataSetChanged();
    }

    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewRecipeStep;
        private ImageView imageViewRecipeStep;

        public RecipeStepAdapterViewHolder(View itemView) {
            super(itemView);
            textViewRecipeStep = itemView.findViewById(R.id.tvRecipeStep);
            imageViewRecipeStep = itemView.findViewById(R.id.ivRecipeStep);
            textViewRecipeStep.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onRecipeStepClick(clickedPosition);
        }
    }

}

