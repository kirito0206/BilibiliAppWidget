package com.example.bilibiliappwidget.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bilibiliappwidget.data.Season

@Dao
interface AnimeDao:BaseDao<Season> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element:Season)

    @Query("select * from Anime order by animeId")
    fun getAllAnimes():MutableList<Season>

    @Query("select * from Anime where animeId = :animeId")
    fun getAnime(animeId:Int):Season

    @Query("select * from Anime order by animeId desc ")
    fun getAllByDateDesc():MutableList<Season>

    @Query("delete from Anime")
    fun deleteAll()

}
