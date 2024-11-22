package com.example.mixmate

import android.app.PendingIntent
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
        appWidgetIds.forEach { appWidgetId ->
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                /* requestCode = */  0,
                /* intent = */ Intent(context, MainActivity::class.java),
                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Get the layout for the widget and attach an onClick listener to
            // the button.
            val views: RemoteViews = RemoteViews(
                context.packageName,
                R.layout.recipe_of_the_day_widget
            ).apply {
                setOnClickPendingIntent(R.id.RODFrameLayout, pendingIntent)
            }

            views.setImageViewResource(R.id.RODWidgetImageView, R.drawable.preview_image)

            // Tell the AppWidgetManager to perform an update on the current
            // widget.
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onEnabled(context: Context) {
        Log.d(LOG_TAG, "RecipeOfTheDayWidget on enabled")
    }

    override fun onDisabled(context: Context) {
        Log.d(LOG_TAG, "RecipeOfTheDayWidget on disabled")
    }
}