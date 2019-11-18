package com.implizstudio.android.app.pitzz.extension

import android.content.Context
import android.view.View
import com.implizstudio.android.app.pitzz.data.local.SQLiteDatabase

fun View.invisible() { visibility = View.INVISIBLE }
fun View.gone() { visibility = View.GONE }
fun View.visible() { visibility = View.VISIBLE }

val Context.db: SQLiteDatabase?
    get() = SQLiteDatabase.getInstance(applicationContext)