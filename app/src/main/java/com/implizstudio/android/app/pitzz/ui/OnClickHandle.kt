package com.implizstudio.android.app.pitzz.ui

import android.app.Activity
import android.content.Context

class OnClickHandle(private val context: Context) {

    fun doDestroyActivity() { (context as Activity).finish() }

}