package com.implizstudio.android.app.pitzz.data.model.constant

object Constant {

    object ApiKey {
        const val TMDb = "YOUR-API-KEY-FROM-The_Movie_DB_API"
    }

    object BaseUrl {

        object TMDb {
            const val MAIN = "https://api.themoviedb.org/3/"
            const val IMAGE = "https://image.tmdb.org/t/p/"
        }

    }

    object Key {
        const val LANGUAGE = "language"
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_INDONESIA = "id"
        const val MOVIE_ID = "movie_id"
        const val SEARCH = "search"
        const val TYPE = "type"
        const val TYPE_RELEASE = "release_reminder"
        const val TYPE_DAILY = "daily_reminder"
        const val SETTING_RELEASE = "release"
        const val SETTING_DAILY = "daily"
    }

    object Notification {
        const val CHANNEL_RELEASE_REMINDER_ID = "Channel_1"
        const val CHANNEL_RELEASE_REMINDER_NAME = "Release_Reminder"
        const val CHANNEL_DAILY_REMINDER_ID = "Channel_2"
        const val CHANNEL_DAILY_REMINDER_NAME = "Daily_Reminder"
    }

    object Size {
        const val LOGO = "w500"
        const val POSTER = "w500"
        const val BACKDROP = "w500"
    }

    object Table {
        const val MOVIE_FAVORITE = "pitzz_movie_favorites"
        const val TELEVISION_FAVORITE = "pitzz_television_favorites"
    }

    object Tag {
        const val LANGUAGE_PREFERENCE = "language_preference"
        const val LANGUAGE_SHEET = "language_sheet"
        const val ID_RELEASE = 100
        const val ID_DAILY = 101
        const val SETTING_PREFERENCE = "setting_preference"
    }

    object Var {
        const val TIME_SPLASH = 3000L
        const val SPAN_MOVIE = 3
        const val SPAN_PRODUCTION = 2
        const val LIGHT_STATUS_BAR = 0
        const val DARK_STATUS_BAR = 0x00002000
    }

}