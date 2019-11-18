package com.implizstudio.android.app.pitzz.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.receiver.NotificationReceiver
import com.implizstudio.android.app.pitzz.ui.splash.SplashActivity
import java.util.*

class NotificationUtil(private val context: Context) {

    fun setRepeatingNotification(type: String, repeatingId: Int) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java).apply { putExtra(Constant.Key.TYPE, type) }

        val pendingIntent = PendingIntent.getBroadcast(context, repeatingId, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, setRepeatingTimeNotification(type).timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

    }

    fun showNotification(channelId: String, channelName: String, title: String?, message: String?, notificationId: Int) {

        val intent = Intent(context, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(sound)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationBuilder.setChannelId(channelId)

            notificationManager?.createNotificationChannel(channel)

        }

        val notification = notificationBuilder.build()

        notificationManager?.notify(notificationId, notification)

    }

    fun cancelNotification(type: String) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, if (type == Constant.Key.TYPE_RELEASE) Constant.Tag.ID_RELEASE else Constant.Tag.ID_DAILY, intent, 0)
        pendingIntent.cancel()

        alarmManager?.cancel(pendingIntent)

    }

    private fun setRepeatingTimeNotification(type: String) = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, if (type == Constant.Key.TYPE_RELEASE) 8 else 7)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

}