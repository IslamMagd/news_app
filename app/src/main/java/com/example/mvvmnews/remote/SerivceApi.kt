package com.example.mvvmnews.remote

import com.example.mvvmnews.model.NewsResponse
import com.example.mvvmnews.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale.IsoCountryCode

interface SerivceApi {
    @GET("v2/top-headlines")
suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey:String = API_KEY
    ):Response<NewsResponse>
@GET("v2/top-headlines")
suspend fun searchNews(
    @Query("q")
    q:String,
    @Query("page")
    pageNumber: Int = 1,
    @Query("apiKey")
    apiKey:String = API_KEY
):Response<NewsResponse>





}