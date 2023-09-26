package com.example.mvvmnews.Repository

import com.example.mvvmnews.db.ArticleDatabase
import com.example.mvvmnews.model.Article
import com.example.mvvmnews.model.NewsResponse
import com.example.mvvmnews.remote.RetrofitBuilder
import retrofit2.Response

class NewsRepository(val db:ArticleDatabase) {
    suspend fun getBreakingNews(counteryCode:String,pageNumber:Int) = RetrofitBuilder.serviceApiInstance.getBreakingNews(counteryCode,pageNumber)
    suspend fun searchNews(searchQuery:String,pageNumber: Int) = RetrofitBuilder.serviceApiInstance.searchNews(searchQuery,pageNumber)
     fun insertOrUpdate(article: Article) = db.getArticleDAO().insertOrUpdate(article)
     fun getSavedNews() = db.getArticleDAO().getArticles()
     fun deleteArticle(article: Article) = db.getArticleDAO().deleteArticle(article)


}