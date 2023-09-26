package com.example.mvvmnews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.mvvmnews.R
import com.example.mvvmnews.databinding.ArticleItemBinding
import com.example.mvvmnews.model.Article
import kotlin.coroutines.coroutineContext

class NewsAdapter(val onListItemClick: OnListItemClick? = null ): RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>(){
    inner class ArticleViewHolder(  val binding: ArticleItemBinding): RecyclerView.ViewHolder(binding.root)
    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
       return ArticleViewHolder( ArticleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            Glide.with(root.context).load(article.urlToImage).into(ivArticleImage)
            tvDescription.text = article.description
            tvSource.text = article.source.name
            tvTitle.text = article.title
            tvPublishedAt.text = article.publishedAt
            root.setOnClickListener {
                onListItemClick?.onItemClick(article)
            }
        }


    }





}