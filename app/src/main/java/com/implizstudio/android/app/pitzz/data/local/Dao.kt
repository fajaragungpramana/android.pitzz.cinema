package com.implizstudio.android.app.pitzz.data.local

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant

interface Dao {

    @Dao
    interface Movie {

        @Query("SELECT * FROM ${Constant.Table.MOVIE_FAVORITE}")
        fun selectAll(): Cursor

        @Query("SELECT * FROM ${Constant.Table.MOVIE_FAVORITE} WHERE id = :id")
        fun selectById(id: Int?): Cursor

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun addFavoriteMovie(movie: BaseEntity.Movie)

        @Query("SELECT * FROM ${Constant.Table.MOVIE_FAVORITE} WHERE id = :id")
        fun onFavoriteMovie(id: Int?): BaseEntity.Movie

        @Query("DELETE FROM ${Constant.Table.MOVIE_FAVORITE} WHERE id = :id")
        fun removeFavoriteMovie(id: Int?)

        @Query("SELECT * FROM ${Constant.Table.MOVIE_FAVORITE}")
        fun getFavoriteMovies(): List<BaseEntity.Movie>

    }

    @Dao
    interface Television {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun addFavoriteTelevision(movie: BaseEntity.Television)

        @Query("SELECT * FROM ${Constant.Table.TELEVISION_FAVORITE} WHERE id = :id")
        fun onFavoriteTelevision(id: Int?): BaseEntity.Television

        @Query("DELETE FROM ${Constant.Table.TELEVISION_FAVORITE} WHERE id = :id")
        fun removeFavoriteTelevision(id: Int?)

        @Query("SELECT * FROM ${Constant.Table.TELEVISION_FAVORITE}")
        fun getFavoriteTelevisions(): List<BaseEntity.Television>

    }

}