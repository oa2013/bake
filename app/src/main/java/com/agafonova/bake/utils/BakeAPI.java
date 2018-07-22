package com.agafonova.bake.utils;

import com.agafonova.bake.db.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Defines the GET method
 * Tutorial: http://www.vogella.com/tutorials/Retrofit/article.html
 */

public interface BakeAPI {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> loadRecipes();

}
