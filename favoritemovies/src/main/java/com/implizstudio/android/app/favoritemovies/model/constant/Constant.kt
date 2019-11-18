package com.implizstudio.android.app.favoritemovies.model.constant

import android.net.Uri

object Constant {

    private const val TABLE_MOVIE_FAVORITE = "pitzz_movie_favorites"

    private const val AUTHORITY = "com.implizstudio.android.app.pitzz.provider"
    val CONTENT_URI: Uri = Uri.Builder().scheme("content")
        .authority(AUTHORITY)
        .appendPath(TABLE_MOVIE_FAVORITE)
        .build()

    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/"
    const val SIZE_POSTER = "w500"

    const val SPAN_MOVIE = 3

}