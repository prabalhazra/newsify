package com.github.prabalhazra.newsify.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.prabalhazra.newsify.NewsApplication
import com.github.prabalhazra.newsify.data.News
import com.github.prabalhazra.newsify.repository.NewsRepository
import com.github.prabalhazra.newsify.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    app: Application,
    private val newsRepository: NewsRepository
    ) : AndroidViewModel(app) {

    val homeNews: MutableLiveData<Resource<News>> = MutableLiveData()
    val healthNews: MutableLiveData<Resource<News>> = MutableLiveData()
    val businessNews: MutableLiveData<Resource<News>> = MutableLiveData()
    val sportsNews: MutableLiveData<Resource<News>> = MutableLiveData()
    val entertainmentNews: MutableLiveData<Resource<News>> = MutableLiveData()
    val technologyNews: MutableLiveData<Resource<News>> = MutableLiveData()
    val searchNews: MutableLiveData<Resource<News>> = MutableLiveData()

    fun getHomeNews(countryCode: String, pageSize: Int) = viewModelScope.launch {
        safeHomeNewsCall(countryCode, pageSize)
    }

    private fun handleHomeNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getHealthNews(countryCode: String, category: String, pageSize: Int) =
        viewModelScope.launch {
            safeHealthNewsCall(countryCode, category, pageSize)
        }

    private fun handleHealthNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getBusinessNews(countryCode: String, category: String, pageSize: Int) =
        viewModelScope.launch {
            safeBusinessNewsCall(countryCode, category, pageSize)
        }

    private fun handleBusinessNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getSportsNews(countryCode: String, category: String, pageSize: Int) =
        viewModelScope.launch {
           safeSportsNewsCall(countryCode, category, pageSize)
        }

    private fun handleSportsNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getEntertainmentNews(countryCode: String, category: String, pageSize: Int) =
        viewModelScope.launch {
            safeEntertainmentNewsCall(countryCode, category, pageSize)
        }

    private fun handleEntertainmentNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getTechnologyNews(countryCode: String, category: String, pageSize: Int) =
        viewModelScope.launch {
           safeTechnologyNewsCall(countryCode, category, pageSize)
        }

    private fun handleTechnologyNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        safeSearchNews(searchQuery)
    }

    private fun handleSearchNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true

                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    private suspend fun safeHomeNewsCall(countryCode: String, pageSize: Int) {
        homeNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getHomeNews(countryCode, pageSize)
                homeNews.postValue(handleHomeNewsResponse(response))
            } else {
                homeNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> homeNews.postValue(Resource.Error("Network Error"))
                else -> homeNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeHealthNewsCall(countryCode: String, category: String, pageSize: Int) {
        healthNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getHealthNews(countryCode, category, pageSize)
                healthNews.postValue(handleHealthNewsResponse(response))
            } else {
                healthNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> healthNews.postValue(Resource.Error("Network Error"))
                else -> healthNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeBusinessNewsCall(countryCode: String, category: String, pageSize: Int) {
        businessNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getBusinessNews(countryCode, category, pageSize)
                businessNews.postValue(handleBusinessNewsResponse(response))
            } else {
                businessNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> businessNews.postValue(Resource.Error("Network Error"))
                else -> businessNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeSportsNewsCall(countryCode: String, category: String, pageSize: Int) {
        sportsNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getSportsNews(countryCode, category, pageSize)
                sportsNews.postValue(handleSportsNewsResponse(response))
            } else {
                sportsNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> sportsNews.postValue(Resource.Error("Network Error"))
                else -> sportsNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeEntertainmentNewsCall(countryCode: String, category: String, pageSize: Int) {
        entertainmentNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getEntertainmentNews(countryCode, category, pageSize)
                entertainmentNews.postValue(handleEntertainmentNewsResponse(response))
            } else {
                entertainmentNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> entertainmentNews.postValue(Resource.Error("Network Error"))
                else -> entertainmentNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeTechnologyNewsCall(countryCode: String, category: String, pageSize: Int) {
        technologyNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getTechnologyNews(countryCode, category, pageSize)
                technologyNews.postValue(handleTechnologyNewsResponse(response))
            } else {
                technologyNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> technologyNews.postValue(Resource.Error("Network Error"))
                else -> technologyNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeSearchNews(searchQuery: String) {
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getSearchNews(searchQuery)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Error"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

}