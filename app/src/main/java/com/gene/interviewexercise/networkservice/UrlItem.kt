package com.gene.interviewexercise.networkservice

data class GetWebUrlsResult(
    val items: List<UrlItem>
)

data class UrlItem(val url: String)
