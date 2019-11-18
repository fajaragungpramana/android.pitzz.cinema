package com.implizstudio.android.app.pitzz.data.response

import com.google.gson.annotations.SerializedName
import com.implizstudio.android.app.pitzz.data.model.BaseEntity

sealed class TMDbResponse {
    data class Movie ( @SerializedName("results") val movieResults: List<BaseEntity.Movie> ): TMDbResponse()
    data class Television ( @SerializedName("results") val televisionResults: List<BaseEntity.Television> ): TMDbResponse()
}