package com.example.mvvmnews.adapters

import com.example.mvvmnews.model.Article

interface OnListItemClick {
    fun onItemClick(article: Article)
}