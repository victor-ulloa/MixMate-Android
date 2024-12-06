package com.example.mixmate

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.mixmate.data.Cocktail
import com.example.mixmate.data.Constants
import com.example.mixmate.repository.Supabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class StackRemoteViewsFactory(val context: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    private val LOG_TAG = "Widget test log"
    private lateinit var recipes: List<Cocktail>
    private val appWidgetId: Int = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
    )

    override fun onCreate() {
        Log.d(LOG_TAG, "StackRemoteViewsFactory on create")
        run {
            getRecipesOfTheDay()
        }

    }

    override fun onDataSetChanged() {
        Log.d(LOG_TAG, "StackRemoteViewsFactory on data set changed")
        getRecipesOfTheDay()
    }

    private fun getRecipesOfTheDay()  {
        runBlocking {
            recipes = withContext(Dispatchers.Default){Supabase.getRecipesOfTheDay()}
            Log.d(LOG_TAG, "StackRemoteViewsFactory retrieved ${recipes.count()} recipes of the day")
        }
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int {
        return recipes.size
    }

    override fun getViewAt(p0: Int): RemoteViews {
        val recipe = recipes[p0]
        val views = RemoteViews(context.packageName, R.layout.widget_item)
        try {
            views.setTextViewText(R.id.RODWidgetTextView, recipe.name)
            var image: Bitmap?
            runBlocking {
                val `in` = java.net.URL(recipe.imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
                views.setImageViewBitmap(R.id.RODWidgetImageView,image)

//                val fillInIntent = Intent()
//                    .apply {
//                    Bundle().also { extras ->
//                        extras.putInt(Constants.RECIPE_ID, recipe.id)
//                        putExtras(extras)
//                    }
//                }
//                Log.d(LOG_TAG,"putting info in the intent's extra")
//                fillInIntent.putExtra(Constants.NAME,recipe.name)
//                fillInIntent.putExtra(Constants.RECIPE_ID, recipe.id)
//                // Make it possible to distinguish the individual on-click
//                // action of a given item
//                views.setOnClickFillInIntent(R.id.widget_item, fillInIntent)
            }
        }
        catch (e: Exception) {
            views.setTextViewText(R.id.RODWidgetTextView, "pulling recipes of the day failed")
        }

        return views
        //return RemoteViews(context.packageName, R.layout.recipe_of_the_day_widget)
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}