package com.example.mvvmnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvmnews.R
import com.example.mvvmnews.Repository.NewsRepository
import com.example.mvvmnews.databinding.ActivityNewsBinding
import com.example.mvvmnews.db.ArticleDatabase

class NewsActivity : AppCompatActivity() {
    lateinit var binding:ActivityNewsBinding
    lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsViewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        newsViewModel = ViewModelProvider(this,newsViewModelProviderFactory).get(NewsViewModel::class.java)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)


    }
}