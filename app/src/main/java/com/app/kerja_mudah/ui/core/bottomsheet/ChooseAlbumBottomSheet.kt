package com.app.kerja_mudah.ui.core.bottomsheet

import com.app.kerja_mudah.base.BaseBottomSheet
import com.app.kerja_mudah.data.model.media.AlbumModel
import com.app.kerja_mudah.databinding.BottomsheetChooseAlbumBinding
import com.app.kerja_mudah.ui.core.adapter.AlbumAdapter

class ChooseAlbumBottomSheet:BaseBottomSheet<BottomsheetChooseAlbumBinding>(BottomsheetChooseAlbumBinding::inflate) {
    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    companion object{
        const val LIST_ALBUM = "LIST_ALBUM"
    }

    private var albums:ArrayList<AlbumModel> = arrayListOf()

    override fun inject() {

    }

    override fun setup() {
        initAdapter()
        initData()
    }

    private lateinit var adapter:AlbumAdapter
    private fun initAdapter() {
        adapter = AlbumAdapter(albums)
        adapter.setCallBack(object : AlbumAdapter.CallBack{
            override fun onClicked(album: AlbumModel?) {
                callBack.onClicked(album)
            }
        })
        binding?.rvAlbum?.adapter = adapter
    }


    private fun initData() {
        albums.clear()
        albums.addAll(arguments?.getParcelableArrayList<AlbumModel>(LIST_ALBUM) ?: arrayListOf())
        adapter.notifyDataSetChanged()
    }

    interface CallBack{
        fun onClicked(album: AlbumModel?)
    }
}