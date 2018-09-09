package com.agafonova.bake;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.agafonova.bake.db.Ingredient;
import com.agafonova.bake.db.Recipe;
import com.agafonova.bake.ui.MainActivity;
import com.agafonova.bake.ui.RecipeDetailActivity;
import com.google.gson.Gson;
import java.util.List;

/**
 * Updated by Olga Agafonova on 8/27/18.
 *
 * This class is based on the tutorial here:
 * https://github.com/tashariko/widget_sample/
 */

public class BakeAppWidgetProvider extends AppWidgetProvider {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String PREF_NAME="BakingApp";
    private static final String RECIPE = "";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(LOG_TAG, "onUpdate");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId){
        Log.d(LOG_TAG, "updateAppWidget");

        Recipe recipe = getRecipeFromSharedPrefs(context);

        if(recipe != null) {

            try {

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake_appwidget);
                Intent mainActivityIntent = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainActivityIntent, 0);
                views.setOnClickPendingIntent(R.id.iv_widget_cupcake, pendingIntent);

                Intent serviceIntent = new Intent(context, WidgetService.class);
                context.startService(serviceIntent);

                serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                views.setRemoteAdapter(R.id.lv_widget_listview, serviceIntent);

                appWidgetManager.updateAppWidget(appWidgetId, views);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_widget_listview);

            }
            catch(Exception e) {
                Log.d(LOG_TAG, e.toString());
            }
        }
    }

    public static Recipe getRecipeFromSharedPrefs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String recipeString = sharedPreferences.getString(RECIPE, "");
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(recipeString, Recipe.class);
        return recipe;
    }
}
