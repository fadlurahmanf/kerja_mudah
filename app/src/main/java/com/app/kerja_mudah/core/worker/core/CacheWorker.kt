package com.app.kerja_mudah.core.worker.core

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.core.extension.toCacheKeyFromPublicVideo
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.channels.Channels


class CacheWorker(val context: Context, params:WorkerParameters):CoroutineWorker(context, params) {
    companion object{
        val TAG = CacheWorker::class.java.simpleName
        const val DATA_LIST = "DATA_LIST"
    }

    private var list:Array<String> = arrayOf()
    private val simpleCache = BaseApp.simpleCache

    override suspend fun doWork(): Result {
        coroutineScope {
            delay(2000)
            list = inputData.getStringArray(DATA_LIST) ?: arrayOf()
            for (i in 0 until list.size-1){
                download(list[i])
            }
        }
        return Result.success()
    }

    private fun download(url:String){
        try {
            val cacheKey = url.toCacheKeyFromPublicVideo() ?: return
            val file = File(context.cacheDir, cacheKey)
            if (file.exists()){
//                Log.d(TAG, "file exist ${file.path}")
//                val isSuccessDelete = file.delete()
//                Log.d(TAG, "success deleted file $isSuccessDelete")
//                return
            }
//            val url = URL(url)
//            url.openStream().use { inputStream ->
//                Channels.newChannel(inputStream).use { rbc ->
//                    FileOutputStream(file).channel.transferFrom(rbc, 0, Long.MAX_VALUE)
//                }
//            }
//            val req = DownloadManager.Request(Uri.parse(url))
//            val dm = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
//            req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, cacheKey)
//            dm.enqueue(req)
            Log.d(TAG, "downloading video $url")
        }catch (e:Exception){
            Log.e(TAG, "downloading error: ${e.message}")
        }
    }

}