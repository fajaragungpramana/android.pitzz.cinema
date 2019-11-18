package com.implizstudio.android.app.pitzz.ui.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.extension.db
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteMovieRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val listFavoriteMovie = mutableListOf<BaseEntity.Movie>()

    override fun onCreate() { Binder.restoreCallingIdentity(Binder.clearCallingIdentity()) }

    override fun onDataSetChanged() {

        GlobalScope.launch {

            val movieResults = context.db?.getMovieDao()?.getFavoriteMovies()
            if (movieResults != null) {
                listFavoriteMovie.apply {
                    clear()
                    addAll(movieResults)
                }
            }

        }

    }

    override fun onDestroy() {}

    override fun getCount() = listFavoriteMovie.size

    override fun getViewAt(position: Int): RemoteViews {

        val remoteViews = RemoteViews(context.packageName, R.layout.widget_movie_item)
        val posterBitmap = Picasso.get()
            .load("${Constant.BaseUrl.TMDb.IMAGE}${Constant.Size.POSTER}${listFavoriteMovie[position].poster}")
            .placeholder(R.drawable.ic_no_image)
            .get()

        remoteViews.setImageViewBitmap(R.id.iv_poster, posterBitmap)

        val extras = bundleOf(FavoriteMovieWidget.EXTRA_ITEM to position)
        val fillIntent = Intent().putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.iv_poster, fillIntent)

        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount() = 1

    override fun getItemId(position: Int) = position.toLong()

    override fun hasStableIds() = false

}