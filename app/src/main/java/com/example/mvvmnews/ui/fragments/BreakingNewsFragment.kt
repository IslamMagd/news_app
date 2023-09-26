package com.example.mvvmnews.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmnews.R
import com.example.mvvmnews.adapters.NewsAdapter
import com.example.mvvmnews.adapters.OnListItemClick
import com.example.mvvmnews.databinding.FragmentBreakingNewsBinding
import com.example.mvvmnews.model.Article
import com.example.mvvmnews.ui.NewsActivity
import com.example.mvvmnews.ui.NewsViewModel
import com.example.mvvmnews.util.Status


class BreakingNewsFragment : Fragment(),OnListItemClick{
    lateinit var newsViewModel: NewsViewModel
    lateinit var binding: FragmentBreakingNewsBinding
    lateinit var newsAdapter: NewsAdapter
    val TAG: String = "BreakingNewsFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBreakingNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewsActivity).newsViewModel
        setUpRecyclerView()
        newsViewModel.breakingNewsMutableLiveData.observe(viewLifecycleOwner, Observer {response ->
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

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView(){
        newsAdapter = NewsAdapter(this)
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        }

    override fun onItemClick(article: Article) {
        val action: NavDirections = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)


    }
}



