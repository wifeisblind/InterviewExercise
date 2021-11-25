package com.gene.interviewexercise.networkservice

import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkService {

    @GET("/pages")
    suspend fun getWebUrls(): GetWebUrlsResult
}