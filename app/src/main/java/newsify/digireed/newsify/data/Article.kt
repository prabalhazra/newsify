package com.github.prabalhazra.newsify.data

data class Article(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val source: Source
)
