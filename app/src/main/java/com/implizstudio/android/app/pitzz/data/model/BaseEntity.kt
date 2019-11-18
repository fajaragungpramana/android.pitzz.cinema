package com.implizstudio.android.app.pitzz.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.implizstudio.android.app.pitzz.data.model.constant.Constant

sealed class BaseEntity {

    @Entity(tableName = Constant.Table.MOVIE_FAVORITE)
    data class Movie (
        @ColumnInfo(name = "row_id") @PrimaryKey(autoGenerate = true) val rowId: Int?,
        @ColumnInfo(name = "id") @SerializedName("id") val id: Int?,
        @ColumnInfo(name = "poster") @SerializedName("poster_path") val poster: String?,
        @ColumnInfo(name = "title") @SerializedName("original_title") val title: String?
    ) : BaseEntity()

    @Entity(tableName = Constant.Table.TELEVISION_FAVORITE)
    data class Television (
        @ColumnInfo(name = "row_id") @PrimaryKey(autoGenerate = true) val rowId: Int?,
        @ColumnInfo(name = "id") @SerializedName("id") val id: Int?,
        @ColumnInfo(name = "poster") @SerializedName("poster_path") val poster: String?,
        @ColumnInfo(name = "name") @SerializedName("name") val name: String?
    ) : BaseEntity()

    data class DetailMovie (
        @SerializedName("id") val id: Int?,
        @SerializedName("poster_path") val poster: String?,
        @SerializedName("backdrop_path") val backdrop: String?,
        @SerializedName("original_title") val title: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("genres") val genres: List<Genre>?,
        @SerializedName("overview") val overview: String?,
        @SerializedName("release_date") val releaseDate: String?,
        @SerializedName("vote_average") val voteAverage: Float?,
        @SerializedName("runtime") val runtime: Int?,
        @SerializedName("vote_count") val voteCount: Int?,
        @SerializedName("production_companies") val productions: List<Production>?
    )

    data class Genre (@SerializedName("name") val name: String?)

    data class Production (
        @SerializedName("logo_path") val logo: String?,
        @SerializedName("name") val name: String?
    ) : BaseEntity()

}