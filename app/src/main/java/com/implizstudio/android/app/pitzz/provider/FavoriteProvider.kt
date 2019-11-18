package com.implizstudio.android.app.pitzz.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.extension.db

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.implizstudio.android.app.pitzz.provider"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        private const val CODE_FAVORITE_DIR = 1
        private const val CODE_FAVORITE_ITEM = 2

        init {
            uriMatcher.addURI(AUTHORITY, Constant.Table.MOVIE_FAVORITE, CODE_FAVORITE_DIR)
            uriMatcher.addURI(AUTHORITY, "${Constant.Table.MOVIE_FAVORITE}/#", CODE_FAVORITE_ITEM)
        }
    }

    override fun onCreate() = true

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {

        val cursor = when (uriMatcher.match(uri)) {

            CODE_FAVORITE_DIR -> context?.db?.getMovieDao()?.selectAll()

            CODE_FAVORITE_ITEM -> context?.db?.getMovieDao()?.selectById(ContentUris.parseId(uri).toInt())

            else -> null

        }

        cursor?.setNotificationUri(context?.contentResolver, uri)

        return cursor
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?) = 0

}