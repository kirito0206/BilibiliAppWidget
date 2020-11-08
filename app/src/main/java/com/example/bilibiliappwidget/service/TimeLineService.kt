package com.example.bilibiliappwidget.service

import com.example.bilibiliappwidget.data.TimeLineResponse
import retrofit2.http.GET

interface TimeLineService {

    @GET("web_api/timeline_global")
    suspend fun getTimeLine() : TimeLineResponse
}