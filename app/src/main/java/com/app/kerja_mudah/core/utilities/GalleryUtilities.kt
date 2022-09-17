package com.app.kerja_mudah.core.utilities

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.app.kerja_mudah.data.model.media.AlbumModel
import com.app.kerja_mudah.data.model.MediaModel
import com.app.kerja_mudah.data.model.MediaModelType
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@Suppress("DEPRECATION")
class GalleryUtilities(var context: Context) {
    fun queryAll(tookPhoto:Boolean = true, tookVideo:Boolean = true) : ArrayList<AlbumModel>? {
        try {
            val videos:ArrayList<MediaModel> = queryVideo() ?: arrayListOf<MediaModel>()
            val photos:ArrayList<MediaModel> = queryPhotos() ?: arrayListOf<MediaModel>()

            val all:ArrayList<MediaModel> = arrayListOf()
            if (tookVideo){
                all.addAll(videos)
            }
            if (tookPhoto){
                all.addAll(photos)
            }

            val sortedList:ArrayList<MediaModel> = arrayListOf()
            all.sortedByDescending { Date(it.dateAdded?.toLong()?:0) }.forEach {
                sortedList.add(it)
            }

            val mapAlbum :HashMap<Long, AlbumModel> = hashMapOf<Long, AlbumModel>()

            all.forEachIndexed { index, it ->
                if (it.bucketId != null){
                    if (mapAlbum[it.bucketId!!] != null){
                        val albumModel = mapAlbum[it.bucketId!!]!!
                        albumModel.medias?.add(MediaModel(
                                id = it.id, path = it.path, bucketName = it.bucketName, bucketId = it.bucketId,
                                type = if (it.path?.contains(".mp4", ignoreCase = true) == true){
                                    MediaModelType.VIDEO
                                } else if (it.path?.contains(".jpg", ignoreCase = true) == true || it.path?.contains(".png", ignoreCase = true) == true){
                                    MediaModelType.PHOTO
                                }else {
                                    MediaModelType.UNKNOWN
                                }
                        ))
                        mapAlbum[it.bucketId!!] = albumModel
                    }else if (mapAlbum[it.bucketId!!] == null){
                        mapAlbum[it.bucketId!!] = AlbumModel(
                            bucketId = it.bucketId,
                            bucketName = it.bucketName,
                            medias = arrayListOf(
                                MediaModel(
                                    id = it.id, path = it.path, bucketName = it.bucketName, bucketId = it.bucketId,
                                    type = if (it.path?.contains(".mp4", ignoreCase = true) == true){
                                        MediaModelType.VIDEO
                                    } else if (it.path?.contains(".jpg", ignoreCase = true) == true || it.path?.contains(".png", ignoreCase = true) == true){
                                        MediaModelType.PHOTO
                                    }else {
                                        MediaModelType.UNKNOWN
                                    }
                                )
                            )
                        )
                    }
                }
            }
            val albums:ArrayList<AlbumModel> = arrayListOf()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mapAlbum.forEach { (_, u) ->
                    albums.add(u)
                }
            }
            return albums
        } catch (e:Exception) {
            Log.e("Query all", "${e.message}")
            return null
        }
    }


    fun queryVideo(): ArrayList<MediaModel>? {
        try {
            val videoProjection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.DATE_ADDED,
            )

            val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

            val cursor = context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                videoProjection,
                null,
                null,
                sortOrder
            )

            val idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val dataColumn = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val bucketNameColumn = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
            val bucketIdColumn = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID)
            val dateAddedColumn = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)

            var list:ArrayList<MediaModel> = arrayListOf()

            while (cursor?.moveToNext() == true){
                val id = idColumn?.let { cursor.getLong(it) }
                val path = dataColumn?.let { cursor.getString(it) }
                val bucket = bucketNameColumn?.let { cursor.getString(it) }
                val bucketId = bucketIdColumn?.let { cursor.getLong(it) }
                val date = dateAddedColumn?.let { cursor.getString(it) }
                val contentUri = id?.let { ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, it) }

                list.add(
                    MediaModel(
                        id = id,
                        path = path,
                        bucketName = bucket,
                        bucketId = bucketId,
                        dateAdded = date,
                        type = if (path?.contains(".mp4", ignoreCase = true) == true){
                            MediaModelType.VIDEO
                        } else if (path?.contains(".jpg", ignoreCase = true) == true || path?.contains(".png", ignoreCase = true) == true){
                            MediaModelType.PHOTO
                        }else {
                            MediaModelType.UNKNOWN
                        }
                    )
                )
            }
            return list
            Log.d("Query Video", "Success")
        }catch (e:Exception){
            Log.e("Query Video", "${e.message}")
            return null
        }
    }

    fun queryPhotos(): ArrayList<MediaModel>? {
        try {
            val imageProjection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DATE_ADDED,
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imageProjection,
                null,
                null,
                sortOrder
            )


            val idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dataColumn = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val bucketNameColumn = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val buckedIdColumn = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
            val dateAddedColumn = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

            var list:ArrayList<MediaModel> = arrayListOf()

            while (cursor?.moveToNext() == true){
                val id = idColumn?.let { cursor.getLong(it) }
                val path = dataColumn?.let { cursor.getString(it) }
                val bucket = bucketNameColumn?.let { cursor.getString(it) }
                val bucketId = buckedIdColumn?.let { cursor.getLong(it) }
                val date = dateAddedColumn?.let { cursor.getString(it) }
                val contentUri = id?.let { ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, it) }

                list.add(
                    MediaModel(
                        id = id,
                        path = path,
                        bucketName = bucket,
                        bucketId = bucketId,
                        dateAdded = date,
                        type = if (path?.contains(".mp4", ignoreCase = true) == true){
                            MediaModelType.VIDEO
                        } else if (path?.contains(".jpg", ignoreCase = true) == true || path?.contains(".png", ignoreCase = true) == true){
                            MediaModelType.PHOTO
                        }else {
                            MediaModelType.UNKNOWN
                        }
                    )
                )
            }
            return list
            Log.d("Query Image", "Success")
        }catch (e:Exception){
            Log.e("Query Image", "${e.message}")
            return null
        }
    }
}