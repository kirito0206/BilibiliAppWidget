package com.example.bilibiliappwidget

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.bilibiliappwidget.Room.AppDataBase
import com.example.bilibiliappwidget.api.TimeLineApi
import com.example.bilibiliappwidget.data.Season
import com.example.bilibiliappwidget.utils.addPhoto
import com.example.bilibiliappwidget.utils.debug
import com.example.bilibiliappwidget.utils.rename
import com.example.bilibiliappwidget.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    var animeList = arrayListOf<Season>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(android
            .Manifest.permission.WRITE_EXTERNAL_STORAGE), 10001)

        btn_quit.setOnClickListener{
            toast(this,"快去添加窗口小部件吧！！")
            finish()
        }
        btn_quit.visibility = View.GONE
    }

    private suspend fun initData() {
        AppDataBase.instance.getAnimeDao().deleteAll()
        var flag = false
        var max = 0
        for (t in TimeLineApi.getTimeLine().result){
            max = if(max > t.seasons.size)max else t.seasons.size
        }

        for (i in 0 until  (7*max)){
            animeList.add(Season(i,"","","","",""))
        }

        var num = 0
        for (t in TimeLineApi.getTimeLine().result){
            if(t.day_of_week == 1)
                flag = true
            if(flag){
                for(index in t.seasons.indices){
                    animeList[index*7 + t.day_of_week -1] = t.seasons[index]
                    animeList[index*7 + t.day_of_week -1].animeId = index*7 + t.day_of_week -1
                    debug("番剧${t.day_of_week}:${animeList[index*7 + t.day_of_week -1].title}")
                    if (t.seasons[index].square_cover != null && t.seasons[index].title != null) {
                        var title = rename(t.seasons[index].title!!)
                        addPhoto(t.seasons[index].square_cover!!,title)
                    }
                }
                num++
            }
            if(num == 7)
                break
        }
        AppDataBase.instance.getAnimeDao().insertAll(animeList)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //是否获取到权限
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            GlobalScope.launch {
                initData()
                withContext(Dispatchers.Main){
                    btn_quit.visibility = View.VISIBLE
                }
            }
        }
    }
}