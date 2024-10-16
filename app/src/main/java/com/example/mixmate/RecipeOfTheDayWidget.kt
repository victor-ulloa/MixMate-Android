package com.example.mixmate

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews

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
    Log.d("Widget test log", "updateAppWidget function")

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.recipe_of_the_day_widget).apply {
        setRemoteAdapter(R.id.widget_view, Intent(context, AppWidgetService::class.java))
    }

    //TODO: LAUNCH MAIN ACTIVITY WHEN CLICKED

    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_view)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}