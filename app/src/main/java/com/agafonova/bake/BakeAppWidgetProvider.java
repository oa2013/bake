package com.agafonova.bake;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
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

import java.util.List;

/**
 * Created by Olga Agafonova on 8/26/18.
 *
 * This class is based on the tutorial here:
 * https://github.com/tashariko/widget_sample/
 */

public class BakeAppWidgetProvider extends AppWidgetProvider {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(LOG_TAG, "onUpdate");

        for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetIds, null);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d(LOG_TAG, "onDeleted");
    }
    @Override
    public void onEnabled(Context context) {
        Log.d(LOG_TAG, "onEnabled");
    }
    @Override
    public void onDisabled(Context context) {
        Log.d(LOG_TAG, "onDisabled");
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds, Recipe recipe) {
        Log.d(LOG_TAG, "updateAppWidget");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake_appwidget);

        if(recipe != null) {

            try {

                //I know that this is the right way to display the list
                //of ingredients (inside of a list view) but there is something wrong with my WidgetService
                //and the widget says "Problem Loading Widget" instead of showing anything.

                //My debugger fails to debug that class at all.

                //Intent serviceIntent = new Intent(context, WidgetService.class);
                //views.setRemoteAdapter(R.id.lv_appwidget_recipeList, serviceIntent);

                views.setTextViewText(R.id.tv_widget_test, buildIngredientList(recipe.getmIngredients()));

                Intent appIntent = new Intent(context, RecipeDetailActivity.class);
                appIntent.putExtra("recipeDetails", recipe);

                PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, appIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                views.setOnClickPendingIntent(R.id.tv_widget_test, configPendingIntent);
            }
            catch(Exception e) {
                Log.d(LOG_TAG, e.toString());
            }
        }
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    public static String buildIngredientList(List<Ingredient> list) {
        StringBuilder builder = new StringBuilder();

        for(Ingredient ingredient : list) {
            builder.append(ingredient.getmIngredient());
        }
        return builder.toString();
    }
}
