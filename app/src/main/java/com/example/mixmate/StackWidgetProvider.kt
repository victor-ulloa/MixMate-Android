package com.example.mixmate

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.example.mixmate.data.Constants

const val OPEN_VIEW_ACTION = "com.example.mixmate.OPEN_VIEW_ACTION"

/**
 * Implementation of App Widget functionality.
 */
class StackWidgetProvider : AppWidgetProvider() {
    val LOG_TAG = "Widget test log"
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d(LOG_TAG, "StackWidgetProvider on update")
        appWidgetIds.forEach { appWidgetId ->

            // Set up the intent that starts the AppWidgetService, which
            // provides the views for this collection.
            val intent = Intent(context, AppWidgetService::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            }

            // Instantiate the RemoteViews object for the widget layout.
            val views: RemoteViews = RemoteViews(
                context.packageName,
                R.layout.recipe_of_the_day_widget
            ).apply {
                setRemoteAdapter(R.id.widgetStackView, intent)
                setEmptyView(R.id.widgetStackView, R.id.empty_view)
            }

            // This section makes it possible for items to have individualized
            // behavior. It does this by setting up a pending intent template.
            // Individuals items of a collection can't set up their own pending
            // intents. Instead, the collection as a whole sets up a pending
            // intent template, and the individual items set a fillInIntent
            // to create unique behavior on an item-by-item basis.
//            val clickPendingIntent: PendingIntent = Intent(
//                context,
//                StackWidgetProvider::class.java
//            ).run {
                // Set the action for the intent.
                // When the user touches a particular view, it has the effect of broadcasting OPEN_VIEW_ACTION.
                //action = OPEN_VIEW_ACTION
                //putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
//                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
//
//                PendingIntent.getBroadcast(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
//            }
//            views.setPendingIntentTemplate(R.id.widgetStackView, clickPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context, intent: Intent) {
//        val extras = intent.extras
//        val data = intent.data
//        val recipeId: Int = intent.getIntExtra(Constants.RECIPE_ID, 0)
//        Log.d(LOG_TAG, "clicked on recipe id $recipeId in the widget")

        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        Log.d(LOG_TAG, "StackWidgetProvider on enabled")
    }

    override fun onDisabled(context: Context) {
        Log.d(LOG_TAG, "StackWidgetProvider on disabled")
    }
}