package com.github.prabalhazra.newsify.repository

import com.github.prabalhazra.newsify.util.NewsService

class NewsRepository {

    suspend fun getHomeNews(countryCode: String, pageSize: Int) =
        NewsService.newsAPI.getHeadlines(countryCode, pageSize)

    suspend fun getHealthNews(countryCode: String, category: String, pageSize: Int) =
        NewsService.newsAPI.getCategoriesNews(countryCode, category, pageSize)

    suspend fun getBusinessNews(countryCode: String, category: String, pageSize: Int) =
        NewsService.newsAPI.getCategoriesNews(countryCode, category, pageSize)

    suspend fun getSportsNews(countryCode: String, category: String, pageSize: Int) =
        NewsService.newsAPI.getCategoriesNews(countryCode, category, pageSize)

    suspend fun getEntertainmentNews(countryCode: String, category: String, pageSize: Int) =
        NewsService.newsAPI.getCategoriesNews(countryCode, category, pageSize)

    suspend fun getTechnologyNews(countryCode: String, category: String, pageSize: Int) =
        NewsService.newsAPI.getCategoriesNews(countryCode, category, pageSize)

    suspend fun getSearchNews(searchQuery: String) = NewsService.newsAPI.searchForNews(searchQuery)
}