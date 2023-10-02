package com.farhan.moviepocket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailsEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("title")
    val title: String? = null,
    @ColumnInfo("rating")
    val rating: Double? = null,
    @ColumnInfo("backdropPath")
    val backdropPath: String? = null,
    @ColumnInfo("posterPath")
    val posterPath: String? = null,
    @ColumnInfo("tagline")
    val tagline: String? = null,
    @ColumnInfo("runtime")
    val runtime: String? = null,
    @ColumnInfo("releaseDate")
    val releaseDate: String? = null,
    @ColumnInfo("description")
    val description: String? = null,
    @ColumnInfo("status")
    val status: String? = null,
    @ColumnInfo("genres")
    val genres: String? = null
)