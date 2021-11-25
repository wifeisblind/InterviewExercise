package com.gene.interviewexercise.networkservice

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceProvider {

    private var instance: NetworkService? = null

    fun provideNetworkService(): NetworkService {
        val temp = instance
        return if (temp == null) {
            Retrofit.Builder()
                .client(OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }).build())
                .baseUrl("https://private-58ab56-mocks3.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkService::class.java).also {
                    instance = it
                }
        } else {
            temp
        }
    }
}