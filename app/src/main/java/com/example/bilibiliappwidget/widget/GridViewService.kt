package com.example.bilibiliappwidget.widget

import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.bilibiliappwidget.R
import com.example.bilibiliappwidget.Room.AppDataBase
import com.example.bilibiliappwidget.data.Season
import com.example.bilibiliappwidget.utils.debug
import java.io.FileInputStream


class GridViewService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory? {
        return GridViewRemoteViewsFactory(this.applicationContext, intent)
    }

    private class GridViewRemoteViewsFactory(context: Context, intent: Intent?) :
        RemoteViewsFactory {
        private val mContext: Context = context
        private var weekday = ArrayList<String>()
        private var animeList = mutableListOf<Season>()
        private var viewList = arrayListOf<RemoteViews>()
        override fun onCreate() {
            initData()
        }

         fun initData(){
             weekday = arrayListOf("一", "二", "三", "四", "五", "六", "日")
             animeList = AppDataBase.instance.getAnimeDao().getAllAnimes()
        }

        override fun onDataSetChanged() {
        }
        override fun onDestroy() {
            animeList.clear()
            weekday.clear()
        }

        override fun getCount(): Int {
            return animeList.size+7
        }

        override fun getViewAt(position: Int): RemoteViews? {
            if (viewList.size > position && viewList[position] != null){
                return viewList[position]
            }
            val views =
                RemoteViews(mContext.packageName, R.layout.item_grid_layout)
            if (position < 7){
                views.setTextViewText(R.id.tv_title, weekday[position])
                views.setViewVisibility(R.id.iv_pic,View.GONE)
                views.setViewVisibility(R.id.tv_time,View.GONE)
            }else {
                if (animeList[position-7].title.isNullOrEmpty() || animeList[position-7].square_cover.isNullOrEmpty()){
                    views.setViewVisibility(R.id.ll_item,View.GONE)
                    viewList.add(views)
                    return views
                }
                views.setTextViewText(R.id.tv_title, animeList[position-7].title)
                views.setTextViewText(R.id.tv_time,animeList[position-7].pub_time)
                var dir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
                    Environment.getExternalStorageDirectory().absolutePath;
                else
                    Environment.getRootDirectory().absolutePath
                val fs = FileInputStream("$dir/bilibiliWidgt/${animeList[position-7].title}.jpg")
                var bitmap = BitmapFactory.decodeStream(fs)
                bitmap = Bitmap.createBitmap(bitmap)
                views.setImageViewBitmap(R.id.iv_pic,bitmap)
            }
            viewList.add(views)
            return views
        }

        /* 在更新界面的时候如果耗时就会显示 正在加载... 的默认字样，但是你可以更改这个界面
  * 如果返回null 显示默认界面
  * 否则 加载自定义的，返回RemoteViews
  */
        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean {
            return false
        }
    }


}