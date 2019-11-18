package com.implizstudio.android.app.pitzz.data.remote

import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.response.TMDbResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiDao {

    interface TMDb {

        @GET("discover/tv")
        fun televisionsAsync(@QueryMap map: MutableMap<String, Any?>): Deferred<Response<TMDbResponse.Television>>

        @GET("tv/{tv_id}")
        fun detailTelevisionAsync(@Path("tv_id") id: Int?, @Query("language") language: String?): Deferred<Response<BaseEntity.DetailMovie>>

        @GET("discover/movie")
        fun moviesAsync(@QueryMap map: MutableMap<String, Any?>): Deferred<Response<TMDbResponse.Movie>>

        @GET("movie/{movie_id}")
        fun detailMovieAsync(@Path("movie_id") id: Int?, @Query("language") language: String?): Deferred<Response<BaseEntity.DetailMovie>>

        @GET("search/movie")
        fun searchMoviesAsync(@QueryMap map: MutableMap<String, Any?>): Deferred<Response<TMDbResponse.Movie>>

        @GET("search/tv")
        fun searchTelevisionsAsync(@QueryMap map: MutableMap<String, Any?>): Deferred<Response<TMDbResponse.Television>>

        @GET("discover/movie")
        fun releasedMoviesAsync(@QueryMap map: MutableMap<String, Any?>): Deferred<Response<TMDbResponse.Movie>>

    }

}