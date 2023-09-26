package com.example.mvvmnews.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmnews.model.Article
import java.util.concurrent.Flow

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertOrUpdate(article: Article):Long

    @Query("select * from article_table")
    fun getArticles(): LiveData<List<Article>>

    @Delete
     fun deleteArticle(article: Article)
}