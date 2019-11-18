package com.implizstudio.android.app.pitzz.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService

class FavoriteMovieService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        FavoriteMovieRemoteViewsFactory(this.applicationContext)

}