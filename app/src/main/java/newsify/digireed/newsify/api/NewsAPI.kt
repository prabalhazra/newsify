package com.github.prabalhazra.newsify.api

import com.github.prabalhazra.newsify.data.News
import com.github.prabalhazra.newsify.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines?apiKey=$API_KEY")
   suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int
    ): Response<News>

    @GET("v2/top-headlines?apiKey=$API_KEY")
    suspend fun getCategoriesNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int
    ): Response<News>

    @GET("v2/everything?apiKey=$API_KEY")
    suspend fun searchForNews(
        @Query("q") searchQuery: String): Response<News>
}