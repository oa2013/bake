package com.agafonova.bake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Olga Agafonova on 8/26/18.
 *
 *  This class is based on the tutorial here:
 * https://github.com/tashariko/widget_sample/
 */

public class WidgetService extends RemoteViewsService  {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakeRemoteViewsFactory(this.getApplicationContext());
    }

    //This class doesn't get hit by the debugger :(
    private class BakeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private static final String INGREDIENTS = "";
        Context mContext;
        Set<String> mIngredients;
        ArrayList<String> mIngredientArray;

        public BakeRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
            mIngredients = new HashSet<String>();
            mIngredientArray = new ArrayList<String>();
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
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int position) {

            //Pass data back to list view
            RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(),R.layout.bake_appwidget);
            views.setTextViewText(R.id.tv_list_item_ingredient, mIngredientArray.get(position));

            return views;
        }

        @Override
        public void onCreate() {

            //Retrieve ingredients
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
            sharedPref.getStringSet(INGREDIENTS, mIngredients);

            if(mIngredients != null) {

                for (Iterator<String> iterator = mIngredients.iterator(); iterator.hasNext(); ) {
                    mIngredientArray.add(iterator.next());
                }
            }
        }

        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onDestroy() {}

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    };
}
