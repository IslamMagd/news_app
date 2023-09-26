package com.example.mvvmnews.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmnews.Repository.NewsRepository
import com.example.mvvmnews.model.Article
import com.example.mvvmnews.model.NewsResponse
import com.example.mvvmnews.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository): ViewModel() {
    val breakingNewsMutableLiveData: MutableLiveData<Status<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1
    val searchNewsMutableLiveData: MutableLiveData<Status<NewsResponse>> = MutableLiveData()
    val searchNewsPage = 1
    init {
        getBreakingNews("us")
    }
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNewsMutableLiveData.postValue(Status.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
       breakingNewsMutableLiveData.postValue(handleBreakingNewsResponse(response))
    }
    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        searchNewsMutableLiveData.postValue(Status.Loading())
        val response = newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNewsMutableLiveData.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Status<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Status.Success(resultResponse)
            }
        }
        return Status.Error(response.message())
    }
    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Status<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Status.Success(resultResponse)
            }
        }
        return Status.Error(response.message())
    }
    fun saveArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        newsRepository.insertOrUpdate(article)
    }
    fun getSavedArticle() = newsRepository.getSavedNews()
    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        newsRepository.deleteArticle(article)
    }


    }


