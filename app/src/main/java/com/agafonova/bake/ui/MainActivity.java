package com.agafonova.bake.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String PREF_NAME="BakingApp";
    private static final String RECIPE = "";
    private RecipeAdapter mAdapter;
    private List<Recipe> recipeList;

    @BindView(R.id.rv_recipes)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvNetworkError)
    TextView mNetworkError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mNetworkError.setVisibility(View.INVISIBLE);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setIcon(R.drawable.cupcake);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }

        try {

            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            mAdapter = new RecipeAdapter(this,this);
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

            recipeList = response.body();

            if(recipeList != null) {
                mAdapter.setData(recipeList);
                mAdapter.notifyDataSetChanged();
            }

        } else {
            Log.d(LOG_TAG,response.errorBody().toString());
            mNetworkError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable error) {
        error.printStackTrace();
    }


    @Override
    public void onRecipeClick(Recipe selectedRecipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("recipeDetails", selectedRecipe);

        //update widget
        putRecipeIntoSharedPrefs(selectedRecipe);

        startActivity(intent);
    }

    private void putRecipeIntoSharedPrefs(Recipe recipe) {
        Gson gson = new Gson();
        String recipeString = gson.toJson(recipe);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(RECIPE, recipeString);
        editor.commit();
    }

}
