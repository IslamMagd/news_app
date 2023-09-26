package com.example.mvvmnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mvvmnews.R
import com.example.mvvmnews.databinding.FragmentArticleBinding
import com.example.mvvmnews.databinding.FragmentBreakingNewsBinding
import com.example.mvvmnews.ui.NewsActivity
import com.example.mvvmnews.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern


class ArticleFragment : Fragment() {
    lateinit var newsViewModel:NewsViewModel
    lateinit var binding: FragmentArticleBinding
    private val args: ArticleFragmentArgs by navArgs<ArticleFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article
        val urlOfArticle = article.url
        if(isYouTubeVideo(urlOfArticle))
        {
            binding.webView.settings.javaScriptEnabled = true // Enable JavaScript in WebView
            val html = "<html><body style='margin:0;padding:0;'><iframe width=\"100%\" height=\"100%\" src=\"$urlOfArticle\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
            // Load the HTML string into the WebView
            binding.webView.loadData(html, "text/html", "utf-8")
        }
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        binding.fab.setOnClickListener {
            newsViewModel.saveArticle(article)
            Snackbar.make(it,"article saved successfully",Snackbar.LENGTH_LONG).show()

        }
    }
    private fun isYouTubeVideo(url: String): Boolean {
        val pattern = Pattern.compile("^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.*")
        val matcher = pattern.matcher(url)
        return matcher.matches()
    }





    }
