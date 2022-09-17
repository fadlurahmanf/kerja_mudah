package com.app.kerja_mudah.ui.core

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.utilities.GalleryUtilities
import com.app.kerja_mudah.data.model.media.AlbumModel
import com.app.kerja_mudah.data.model.MediaModel
import com.app.kerja_mudah.databinding.ActivityGalleryBinding
import com.app.kerja_mudah.di.component.CoreComponent
import com.app.kerja_mudah.ui.core.adapter.MediaAdapter
import com.app.kerja_mudah.ui.core.bottomsheet.ChooseAlbumBottomSheet

class GalleryActivity : BaseActivity<ActivityGalleryBinding>(ActivityGalleryBinding::inflate) {

    companion object{
        const val IS_MULTIPLE_SELECT = "IS_MULTIPLE_SELECT"
        const val JUST_VIEW = "JUST_VIEW"
        const val MAXIMUM_SELECT = "MAXIMUM_SELECT"
        const val TOOK_PHOTO = "TOOK_PHOTO"
        const val TOOK_VIDEO = "TOOK_VIDEO"
        const val USE_CAMERA = "USE_CAMERA"

        const val ACTION = "ACTION"
        const val PATH_RESULT = "PATH_RESULT"
    }

    private var album: AlbumModel?= null
    private var medias:ArrayList<MediaModel> = arrayListOf()
    private var mediaSelected:ArrayList<MediaModel> = arrayListOf()
    private var maximumSelect:Int = 10
    private var tookPhoto:Boolean = true
    private var tookVideo:Boolean = true
    private var useCamera:Boolean = true

    private var isMultipleSelect:Boolean = false

    override fun initSetup() {
        isMultipleSelect = intent.getBooleanExtra(IS_MULTIPLE_SELECT, false)
        maximumSelect = intent.getIntExtra(MAXIMUM_SELECT, 10)
        tookPhoto = intent.getBooleanExtra(TOOK_PHOTO, true)
        tookVideo = intent.getBooleanExtra(TOOK_VIDEO, true)
        useCamera = intent.getBooleanExtra(USE_CAMERA, true)

        initAdapter()
        initData()
        initView()
        initAction()
    }

    private var bottomSheet:ChooseAlbumBottomSheet ?= null
    private fun initAction() {
        binding?.llAppbar?.setOnClickListener {
            val bundle = Bundle()
            bottomSheet = ChooseAlbumBottomSheet()
            bottomSheet?.setCallBack(object : ChooseAlbumBottomSheet.CallBack{
                override fun onClicked(album: AlbumModel?) {
                    if (album != null){
                        changeAlbum(album)
                    }
                    bottomSheet?.dismiss()
                }
            })
            bundle.putParcelableArrayList(ChooseAlbumBottomSheet.LIST_ALBUM, albums)
            bottomSheet?.arguments = bundle
            bottomSheet?.show(supportFragmentManager, ChooseAlbumBottomSheet::class.java.simpleName)
        }

        binding?.ivCamera?.setOnClickListener {
            intent.putExtra(ACTION, "camera")
            setResult(RESULT_OK, intent)
            finish()
        }

        binding?.btnDone?.setOnClickListener {
            if (mediaSelected.isNotEmpty()){
                val listSelected : ArrayList<String> = ArrayList(mediaSelected.map { it.path?:"" })
                intent.putExtra(ACTION, "gallery")
                intent.putStringArrayListExtra(PATH_RESULT, listSelected)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun changeAlbum(album: AlbumModel) {
        this.album = album
        this.medias.clear()
        this.medias.addAll(album.medias?: arrayListOf())
        mediaAdapter.notifyDataSetChanged()
        binding?.tvAlbumName?.text = this.album?.bucketName
    }

    private fun initView() {
        binding?.tvAlbumName?.text = album?.bucketName?:""

        binding?.ivCamera?.visibility = if (useCamera) View.VISIBLE else View.GONE
    }

    private lateinit var mediaAdapter:MediaAdapter
    private fun initAdapter() {
        mediaAdapter = MediaAdapter(medias, multipleSelect = isMultipleSelect, selected = mediaSelected)
        mediaAdapter.setCallBack(object : MediaAdapter.CallBack{
            override fun onClicked(media: MediaModel?) {
                if (media?.id != null){
                    if (isMultipleSelect){
                        if (mediaSelected.contains(media)){
                            mediaSelected.remove(media)
                        }else{
                            if (mediaSelected.size <= maximumSelect - 1){
                                mediaSelected.add(media)
                            }else{
                                showToast(this@GalleryActivity, "Maximum select 10 photo/video", Toast.LENGTH_SHORT)
                            }
                        }
                        if (mediaSelected.isEmpty()){
                            binding?.btnDone?.visibility = View.GONE
                        }else{
                            binding?.btnDone?.visibility = View.VISIBLE
                        }
                        mediaAdapter.notifyDataSetChanged()
                    }else{

                    }
                }
            }
        })
        binding?.rvMedia?.layoutManager = GridLayoutManager(this, 4)
        binding?.rvMedia?.adapter = mediaAdapter
    }

    private var albums:ArrayList<AlbumModel> = arrayListOf()
    private fun initData() {
        val galleryUtilities = GalleryUtilities(this)
        albums = galleryUtilities.queryAll(
            tookPhoto = tookPhoto,
            tookVideo = tookVideo
        ) ?: arrayListOf()
        album = albums.firstOrNull()
        medias.clear()
        medias.addAll(albums.firstOrNull()?.medias?: arrayListOf())
        mediaAdapter.notifyDataSetChanged()
    }

    private lateinit var component:CoreComponent
    override fun inject() {
        component = appComponent.coreComponent().create()
        component.inject(this)
    }
}