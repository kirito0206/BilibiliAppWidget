package com.example.bilibiliappwidget

import com.example.bilibiliappwidget.service.TimeLineService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    const val USER_URL = "https://bangumi.bilibili.com/"

    val timeLineService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(USER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            return@lazy retrofit.create(TimeLineService::class.java)
    }
}