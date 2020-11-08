package com.example.bilibiliappwidget.Room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bilibiliappwidget.MyApplication
import com.example.bilibiliappwidget.data.Season

@Database(entities = [Season::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getAnimeDao(): AnimeDao

    companion object {

        val instance = Single.sin

    }

    private object Single {

        val sin :AppDataBase= Room.databaseBuilder(
            MyApplication.instance(),
            AppDataBase::class.java,
            "Anime.db"
        )
            .allowMainThreadQueries()
            .build()
    }

}
