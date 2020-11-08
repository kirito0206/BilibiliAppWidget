package com.example.bilibiliappwidget.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


fun Any.debug(msg: String) {
    Log.d(javaClass.simpleName, msg)
}

fun Any.toast(context: Context, msg: String) {
    Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
}

//获取网络图片
@Throws(IOException::class)
fun Any.addPhoto(context: Context,path: String,fileName : String){
    var dir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
        Environment.getExternalStorageDirectory().absolutePath;
    else
        Environment.getRootDirectory().absolutePath;
    debug("url：$path")
    val url = URL(path)
    var mBitmap : Bitmap? = null
    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
    conn.connectTimeout = 8000
    conn.requestMethod = "GET"
    if (conn.responseCode === 200) {
        val inputStream: InputStream = conn.inputStream
        mBitmap = BitmapFactory.decodeStream(inputStream)
    }
    if (mBitmap == null)
        return
    val file = File("$dir/bilibiliWidgt")
    file.mkdirs()
    val fileImage = File(file,"$fileName.jpg")
    if (!fileImage.exists())
        fileImage.createNewFile()
    else{
        debug("路径：${fileImage.absolutePath}")
        return
    }

    val out = FileOutputStream(fileImage)
    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    out.flush()
    out.close()
    //保存图片后发送广播通知更新数据库
    //val uri: Uri = Uri.fromFile(file)
    //(context as Activity).sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
}
