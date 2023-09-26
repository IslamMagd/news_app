package com.example.mvvmnews.remote

import com.example.mvvmnews.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitBuilder {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val serviceApiInstance by lazy { retrofit.create(SerivceApi::class.java) }

    }

}