package com.example.mvvmnews.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import java.io.Serializable

@Entity("article_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val author: String? = null,
    val content: String? = null,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String? = null
): Serializable