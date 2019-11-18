package com.implizstudio.android.app.pitzz.data.remote

import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    object TMDbApiKey : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response =
            chain.proceed(
                chain.request().newBuilder()
                    .url(
                        chain.request().url
                            .newBuilder()
                            .addQueryParameter("api_key", Constant.ApiKey.TMDb)
                            .build()
                    ).build()
            )

    }

    fun createTMDb(): ApiDao.TMDb =
        Retrofit.Builder()
            .baseUrl(Constant.BaseUrl.TMDb.MAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(TMDbApiKey)
                    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                    .build()
            )
            .build()
            .create(ApiDao.TMDb::class.java)

}