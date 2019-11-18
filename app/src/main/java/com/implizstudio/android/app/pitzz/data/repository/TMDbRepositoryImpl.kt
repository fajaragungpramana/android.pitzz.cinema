package com.implizstudio.android.app.pitzz.data.repository

import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.remote.ApiDao
import com.implizstudio.android.app.pitzz.data.remote.ApiResponse
import com.implizstudio.android.app.pitzz.data.response.TMDbResponse

class TMDbRepositoryImpl(private val tmDbDao: ApiDao.TMDb) : TMDbRepository {

    override suspend fun getTelevisions(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Television> =
        try {

            val response = tmDbDao.televisionsAsync(map).await()
            if (response.isSuccessful) ApiResponse.OnSuccess(response.body()) else ApiResponse.OnFailure(response.code())

        } catch (e: Throwable) { ApiResponse.OnError(e) }

    override suspend fun getDetailTelevision(id: Int?, language: String?): ApiResponse<BaseEntity.DetailMovie> =
        try {

            val response = tmDbDao.detailTelevisionAsync(id, language).await()
            if (response.isSuccessful) ApiResponse.OnSuccess(response.body()) else ApiResponse.OnFailure(response.code())

        } catch (e: Throwable) { ApiResponse.OnError(e) }

    override suspend fun getMovies(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Movie> =
        try {

            val response = tmDbDao.moviesAsync(map).await()
            if (response.isSuccessful) ApiResponse.OnSuccess(response.body()) else ApiResponse.OnFailure(response.code())

        } catch (e: Throwable) { ApiResponse.OnError(e) }

    override suspend fun getDetailMovie(id: Int?, language: String?): ApiResponse<BaseEntity.DetailMovie> =
        try {

            val response = tmDbDao.detailMovieAsync(id, language).await()
            if (response.isSuccessful) ApiResponse.OnSuccess(response.body()) else ApiResponse.OnFailure(response.code())

        } catch (e: Throwable) { ApiResponse.OnError(e) }

    override suspend fun searchMovies(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Movie> =
        try {

            val response = tmDbDao.searchMoviesAsync(map).await()
            if (response.isSuccessful) ApiResponse.OnSuccess(response.body()) else ApiResponse.OnFailure(response.code())

        } catch (e: Throwable) { ApiResponse.OnError(e) }

    override suspend fun searchTelevisions(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Television> =
        try {

            val response = tmDbDao.searchTelevisionsAsync(map).await()
            if (response.isSuccessful) ApiResponse.OnSuccess(response.body()) else ApiResponse.OnFailure(response.code())

        } catch (e: Throwable) { ApiResponse.OnError(e) }

    override suspend fun getReleasedMovies(map: MutableMap<String, Any?>): ApiResponse<TMDbResponse.Movie> =
        try {

            val response = tmDbDao.releasedMoviesAsync(map).await()
            if (response.isSuccessful) ApiResponse.OnSuccess(response.body()) else ApiResponse.OnFailure(response.code())

        } catch (e: Throwable) { ApiResponse.OnError(e) }

}