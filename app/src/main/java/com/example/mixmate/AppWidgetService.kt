package com.example.mixmate

import android.content.Intent
import android.widget.RemoteViewsService

class AppWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return WidgetDataProvider(this, p0)
    }
}