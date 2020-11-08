package com.example.bilibiliappwidget.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class TimeLineResponse(
    val code: Int,
    val message: String,
    val result: List<Result>
)

data class Result(
    val date: String,
    val date_ts: Int,
    val day_of_week: Int,
    val is_today: Int,
    val seasons: List<Season>
)

@Entity(tableName = "Anime")
data class Season(
    @PrimaryKey(autoGenerate = true)
    var animeId : Int?,
    @ColumnInfo(name = "s_index")
    val pub_index: String?,
    @ColumnInfo(name = "s_time")
    val pub_time: String?,
    @ColumnInfo(name = "s_cover")
    val square_cover: String?,
    @ColumnInfo(name = "s_title")
    val title: String?,
    @ColumnInfo(name = "s_url")
    val url: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }
}