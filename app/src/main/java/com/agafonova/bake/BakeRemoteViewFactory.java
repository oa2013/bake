package com.agafonova.bake;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.agafonova.bake.db.Ingredient;
import com.agafonova.bake.db.Recipe;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Olga Agafonova on 8/27/18.
 */

public class BakeRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        private static final String PREF_NAME="BakingApp";
        private static final String RECIPE = "";

        Context mContext;
        ArrayList<Ingredient> mIngredientArray;
        private int widgetId;

        public BakeRemoteViewFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
            mIngredientArray = new ArrayList<Ingredient>();
            widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public int getCount() {
            if(mIngredientArray != null) {
                return mIngredientArray.size();
            }
            else {
                return 0;
            }
        }

        @Override
        public int getViewTypeCount() {
            if(mIngredientArray != null) {
                return mIngredientArray.size();
            }
            else {
                return 0;
            }
        }

        //This isn't getting called :(
        @Override
        public RemoteViews getViewAt(int position) {

            //Pass data back to list view
            RemoteViews views = new RemoteViews(mContext.getPackageName(),R.layout.bake_appwidget);

            views.setTextViewText(R.id.tv_widget_ingredient_item, mIngredientArray.get(position).getmIngredient());
            views.setTextViewText(R.id.tv_widget_ingredient_measurement, mIngredientArray.get(position).getmMeasure());
            views.setTextViewText(R.id.tv_widget_ingredient_quantity, mIngredientArray.get(position).getmQuantity());

            return views;
        }

        @Override
        public void onCreate() {
            getData();
        }

        public void getData() {

            mIngredientArray.clear();

            SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String recipeString = sharedPreferences.getString(RECIPE, "");
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(recipeString, Recipe.class);

            mIngredientArray.addAll(recipe.getmIngredients());
        }

        @Override
        public void onDataSetChanged() {
            getData();
        }

        @Override
        public void onDestroy() {
            mIngredientArray.clear();
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

}
