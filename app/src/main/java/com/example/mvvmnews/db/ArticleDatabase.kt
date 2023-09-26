package com.example.mvvmnews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvvmnews.model.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDAO(): ArticleDAO
    companion object{
        @Volatile
        private var instance:ArticleDatabase? = null
      operator fun invoke(context: Context):ArticleDatabase{
            return instance?: synchronized(Any()){
                instance?:buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,ArticleDatabase::class.java,"article_database").build()

    }


}