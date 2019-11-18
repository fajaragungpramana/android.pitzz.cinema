package com.implizstudio.android.app.pitzz.data.repository

import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.remote.ApiResponse
import com.implizstudio.android.app.pitzz.data.response.TMDbResponse

interface TMDbRepository {

    suspend fun getTelevisions(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Television>
    suspend fun getDetailTelevision(id: Int?, language: String?): ApiResponse<BaseEntity.DetailMovie>
    suspend fun getMovies(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Movie>
    suspend fun getDetailMovie(id: Int?, language: String?): ApiResponse<BaseEntity.DetailMovie>
    suspend fun searchMovies(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Movie>
    suspend fun searchTelevisions(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Television>
    suspend fun getReleasedMovies(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Movie>

}