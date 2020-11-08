package com.example.bilibiliappwidget.api

import com.example.bilibiliappwidget.RetrofitHelper

object TimeLineApi{
    val timeLineService = RetrofitHelper.timeLineService
    suspend fun getTimeLine() = timeLineService.getTimeLine()
}