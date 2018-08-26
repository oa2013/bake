package com.agafonova.bake.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.agafonova.bake.R;
import com.agafonova.bake.db.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/*
* Uses https://codelabs.developers.google.com/codelabs/exoplayer-intro/#0 as a reference
* */
public class RecipeStepDetailFragment extends Fragment  {

    private static final String LOG_TAG = RecipeStepDetailFragment.class.getSimpleName();

    @BindView(R.id.buttonNext)
    Button mNextButton;

    @BindView(R.id.buttonPrevious)
    Button mPreviousButton;

    @BindView(R.id.exoPlayerView)
    PlayerView mVideoPlayer;

    @BindView(R.id.tvStepDescription)
    TextView mStepDescription;

    private int mStepListIndex;
    private ArrayList<Step> mStepList;

    private ExoPlayer mExoPlayer;
    boolean mPlayerStatus = true;
    long mPlayerPosition = 0;

    private static final String PLAYER_POSITION = "PLAYER_POSITION";
    private static final String PLAYER_STATUS = "PLAYER_STATUS";

    public RecipeStepDetailFragment() {

    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container,
				false);
        ButterKnife.bind(this,view);

        if(savedInstanceState != null) {
            mStepList = savedInstanceState.getParcelableArrayList("mStepList");
            mStepListIndex = savedInstanceState.getInt("mStepListIndex");
        }

        if(mStepList != null && mStepList.get(mStepListIndex) != null) {

            initializePlayer();

            updateFragmentData();

            mPreviousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mStepListIndex > 0) {
                        resetPlayer();
                        updatePosition(mStepListIndex - 1);
                    }
                }
            });


            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mStepListIndex < mStepList.size() - 1) {
                        resetPlayer();
                        updatePosition(mStepListIndex + 1);
                    }
                }
            });

        }

		return view;
	}

    private void initializePlayer() {
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mVideoPlayer.setPlayer(mExoPlayer);
    }

    public void setData(ArrayList<Step> stepList, int position) {
		mStepList = stepList;
		mStepListIndex = position;
	}

	public void updatePosition(int position) {

	    mStepListIndex = position;
		mPreviousButton.setEnabled(true);
		mNextButton.setEnabled(true);

		if(mStepListIndex == 0) {
			mPreviousButton.setEnabled(false);
		} else if (mStepListIndex == mStepList.size() - 1) {
			mNextButton.setEnabled(false);
		}

        updateFragmentData();
	}

	private void updateFragmentData() {

        mStepDescription.setText(mStepList.get(mStepListIndex).getmDescription());

        if(!mStepList.get(mStepListIndex).getmVideoUrl().equals("")) {
            Uri uri = Uri.parse(mStepList.get(mStepListIndex).getmVideoUrl());
            MediaSource mediaSource = buildMediaSource(uri);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(mPlayerStatus);
            mExoPlayer.seekTo(mPlayerPosition);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-bakeapp")).
                createMediaSource(uri);
    }

    public void resetPlayer() {
        mExoPlayer.stop();
        mPlayerPosition = 0;
        mPlayerStatus = true;
    }

    public void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION, mExoPlayer.getCurrentPosition());
        outState.putBoolean(PLAYER_STATUS, mExoPlayer.getPlayWhenReady());
        outState.putParcelableArrayList("mStepList", mStepList);
        outState.putInt("mStepListIndex", mStepListIndex);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong(PLAYER_POSITION);
            mPlayerStatus = savedInstanceState.getBoolean(PLAYER_STATUS);
            mStepList = savedInstanceState.getParcelableArrayList("mStepList");
            mStepListIndex = savedInstanceState.getInt("mStepListIndex");
            updateFragmentData();
        }
    }
}
