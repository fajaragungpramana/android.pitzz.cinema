package com.implizstudio.android.app.pitzz.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.implizstudio.android.app.pitzz.R

class FavoriteMovieWidget : AppWidgetProvider() {

    companion object {

        const val EXTRA_ITEM = "com.implizstudio.android.app.pitzz.EXTRA_ITEM"

        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager?, appWidgetId: Int) {

            val intent = Intent(context, FavoriteMovieService::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = toUri(Intent.URI_INTENT_SCHEME).toUri()
            }

            val views = RemoteViews(context.packageName, R.layout.widget_movie_favorite).apply {
                setRemoteAdapter(R.id.sv_favorite, intent)
                setEmptyView(R.id.sv_favorite, R.id.empty_view)
            }

            appWidgetManager?.updateAppWidget(appWidgetId, views)
        }

    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (i in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, i)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {

            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widget = ComponentName(context, FavoriteMovieWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(widget)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.sv_favorite)

        }

    }

}