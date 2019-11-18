package com.implizstudio.android.app.pitzz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.implizstudio.android.app.pitzz.data.model.BaseEntity

@Database(entities = [BaseEntity.Movie::class, BaseEntity.Television::class], version = 1)
abstract class SQLiteDatabase : RoomDatabase() {
    abstract fun getMovieDao(): Dao.Movie
    abstract fun getTelevisionDao(): Dao.Television

    companion object {
        private var instance: SQLiteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SQLiteDatabase? {
            if (instance == null) instance = Room.databaseBuilder(context.applicationContext, SQLiteDatabase::class.java, "pitzz_favorite.db").build()

            return instance
        }

    }

}