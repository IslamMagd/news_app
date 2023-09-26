package com.example.mvvmnews.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmnews.R
import com.example.mvvmnews.adapters.NewsAdapter
import com.example.mvvmnews.adapters.OnListItemClick
import com.example.mvvmnews.databinding.FragmentBreakingNewsBinding
import com.example.mvvmnews.databinding.FragmentSearchNewsBinding
import com.example.mvvmnews.model.Article
import com.example.mvvmnews.ui.NewsActivity
import com.example.mvvmnews.ui.NewsViewModel
import com.example.mvvmnews.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.mvvmnews.util.Status
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment(),OnListItemClick {
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var binding: FragmentSearchNewsBinding
    val TAG = "searchNewsFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewsActivity).newsViewModel
        setupRecyclerView()
        var job: Job? = null
        binding.etSearch.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                    newsViewModel.getSearchNews(editable.toString())
               }

                }
            }
        }

        newsViewModel.searchNewsMutableLiveData.observe(viewLifecycleOwner, Observer {response ->
            when (response){
                is Status.Success ->{
                    hideProgressBar()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }

                }
                is Status.Error ->{
                    hideProgressBar()
                    response.message?.let {message ->
                        Log.e(TAG,"An erorr is $message")
                    }
                }
                is Status.Loading ->
                {
                    showProgressBar()
                }
            }


        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(this)
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    override fun onItemClick(article: Article) {
        val action: NavDirections = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}