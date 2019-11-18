package com.implizstudio.android.app.pitzz.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.data.remote.ApiResponse
import com.implizstudio.android.app.pitzz.data.remote.ApiService
import com.implizstudio.android.app.pitzz.data.repository.TMDbRepositoryImpl
import com.implizstudio.android.app.pitzz.util.NotificationUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {

            if (intent?.getStringExtra(Constant.Key.TYPE) == Constant.Key.TYPE_RELEASE) {

                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val now = format.format(Date())

                val map = mutableMapOf<String, Any?>(
                    "primary_release_date.gte" to now,
                    "primary_release_date.lte" to now
                )

                GlobalScope.launch {

                    when (val response = TMDbRepositoryImpl(ApiService.createTMDb()).getReleasedMovies(map)) {

                        is ApiResponse.OnSuccess -> {

                            if (response.body != null) {
                                
                                val listMovie = response.body.movieResults
                                
                                for (i in listMovie.indices) {

                                    NotificationUtil(context).showNotification(
                                        Constant.Notification.CHANNEL_RELEASE_REMINDER_ID,
                                        Constant.Notification.CHANNEL_RELEASE_REMINDER_NAME,
                                        context.getString(R.string.release_reminder),
                                        listMovie[i].title,
                                        Constant.Tag.ID_RELEASE
                                    )

                                }

                            }

                        }

                        is ApiResponse.OnFailure -> {}

                        is ApiResponse.OnError -> response.error.printStackTrace()

                    }

                }
                
            } else
                NotificationUtil(context).showNotification(
                    Constant.Notification.CHANNEL_DAILY_REMINDER_ID,
                    Constant.Notification.CHANNEL_DAILY_REMINDER_NAME,
                    context.getString(R.string.daily_reminder),
                    context.getString(R.string.daily_reminder_notification_message),
                    Constant.Tag.ID_DAILY
                )

        }
    }

}