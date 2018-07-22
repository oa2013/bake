package com.agafonova.bake.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import com.agafonova.bake.R;
import com.agafonova.bake.adapters.RecipeAdapter;
import com.agafonova.bake.db.Recipe;
import com.agafonova.bake.utils.BakeAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ResourceClickListener, Callback<List<Recipe>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    private boolean mIsTablet = false;
    private RecipeAdapter mAdapter;

    @BindView(R.id.rv_recipes)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setIcon(R.drawable.cupcake);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }

        try {

            determineDevice();

            if(mIsTablet) {
                GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3, GridLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);

            }
            else  {
                GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
            }

            mAdapter = new RecipeAdapter(this);
            mRecyclerView.setAdapter(mAdapter);

            startRetrofitService();

        }
        catch(Exception e) {
            Log.d(LOG_TAG, e.toString());
        }

    }

    private void startRetrofitService() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).build();

        BakeAPI bakeAPI = retrofit.create(BakeAPI.class);
        Call<List<Recipe>> call = bakeAPI.loadRecipes();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

        if(response.isSuccessful()) {

            List<Recipe> recipeList = response.body();

            if(recipeList != null) {
                mAdapter.setData(recipeList);
                mAdapter.notifyDataSetChanged();
            }

        } else {
            Log.d(LOG_TAG,response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable error) {
        error.printStackTrace();
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
        }
        else if (smallestWidth > 600) {
            mIsTablet = true;
        }
        else  {
            mIsTablet = false;
        }
    }

    @Override
    public void onRecipeClick(String data) {

    }
}
