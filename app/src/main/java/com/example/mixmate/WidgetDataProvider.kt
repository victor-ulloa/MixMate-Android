package com.example.mixmate

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.net.toUri
import com.example.mixmate.data.Cocktail
import com.example.mixmate.repository.Supabase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class WidgetDataProvider(val context: Context, val intent: Intent?) : RemoteViewsService.RemoteViewsFactory {

    private val LOG_TAG = "Widget test log"
    var rod: Cocktail? = null

    override fun onCreate() {
        Log.d(LOG_TAG, "WidgetDataProvider on create")
        //getRecipeOfTheDay()
    }

    override fun onDataSetChanged() {
        Log.d(LOG_TAG, "WidgetDataProvider on data set changed")
        getRecipeOfTheDay()
    }

    private fun getRecipeOfTheDay()  {
        runBlocking {
            rod = withContext(Dispatchers.Default){Supabase.getRecipeOfTheDay()}
            Log.d(LOG_TAG, "WidgetDataProvider retrieved recipe of the day: ${rod?.name}")
        }
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getViewAt(p0: Int): RemoteViews {
//        val views = RemoteViews(context.packageName, R.layout.widget_item)
//        views.setImageViewResource(R.id.RODWidgetImageView,R.drawable.preview_img)
//        try {
//            views.setTextViewText(R.id.RODWidgetTextView, rod!!.name)
//            var image: Bitmap? = null
//            runBlocking {
//                val `in` = java.net.URL(rod!!.imageURL).openStream()
//                image = BitmapFactory.decodeStream(`in`)
//                views.setImageViewBitmap(R.id.RODWidgetImageView,image)
//            }
//        }
//        catch (e: Exception) {
//            views.setTextViewText(R.id.RODWidgetTextView, "pulling recipe of the day failed")
//        }

//        return views
        return RemoteViews(context.packageName, R.layout.recipe_of_the_day_widget)
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