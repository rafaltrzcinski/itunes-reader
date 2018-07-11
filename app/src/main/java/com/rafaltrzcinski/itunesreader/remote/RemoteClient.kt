package com.rafaltrzcinski.itunesreader.remote

import com.rafaltrzcinski.itunesreader.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RemoteClient {

    private const val BASE_URL = "https://itunes.apple.com/"

    fun getRemoteService(): RemoteService {


        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(logging)
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(RemoteService::class.java)
    }
}