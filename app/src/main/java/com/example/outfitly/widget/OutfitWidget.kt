package com.example.outfitly.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.outfitly.R

class OutfitWidget : AppWidgetProvider() {
    
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
    
    override fun onEnabled(context: Context) {
        // Widget ilk kez eklendiğinde
    }
    
    override fun onDisabled(context: Context) {
        // Son widget kaldırıldığında
    }
    
    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_outfit)
            
            // Varsayılan değerler
            views.setTextViewText(R.id.widget_temperature, "--°")
            views.setTextViewText(R.id.widget_city, "Konum bekleniyor...")
            views.setTextViewText(R.id.widget_outfit_suggestion, "Kombin önerisi için uygulamayı açın")
            
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
