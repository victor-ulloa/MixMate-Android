package com.example.mixmate

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.example.mixmate.data.Cocktail

/**
 * Implementation of App Widget functionality.
 */
class RecipeOfTheDayWidget : AppWidgetProvider() {
    val LOG_TAG = "Widget test log"
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d(LOG_TAG, "RecipeOfTheDayWidget on update")
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        Log.d(LOG_TAG, "RecipeOfTheDayWidget on enabled")
    }

    override fun onDisabled(context: Context) {
        Log.d(LOG_TAG, "RecipeOfTheDayWidget on disabled")
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.recipe_of_the_day_widget)

    //TODO: LAUNCH MAIN ACTIVITY WHEN CLICKED

    views.setRemoteAdapter(R.id.RODWidgetLayout, Intent(context, AppWidgetService::class.java))

    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.RODWidgetLayout)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}