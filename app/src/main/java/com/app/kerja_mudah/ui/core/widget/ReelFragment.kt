package com.app.kerja_mudah.ui.core.widget

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseFragment
import com.app.kerja_mudah.core.extension.toCacheKeyFromPublicVideo
import com.app.kerja_mudah.data.response.freelancer.FreelancerReelResponse
import com.app.kerja_mudah.databinding.FragmentReelBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

class ReelFragment : BaseFragment<FragmentReelBinding>(FragmentReelBinding::inflate) {

    private var reel:FreelancerReelResponse ?= null


    companion object{
        val TAG = ReelFragment::class.java.simpleName
        const val REEL = "REEL"
        @JvmStatic
        fun newInstance(reel:FreelancerReelResponse):ReelFragment{
            return ReelFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(REEL, reel)
                }
            }
        }
    }

    private lateinit var exoPlayer: ExoPlayer

    override fun setup(savedInstanceState: Bundle?) {
        exoPlayer = ExoPlayer.Builder(this.requireContext()).build()
        exoPlayer.playWhenReady = true
        initData()
        initView()

        if ((reel?.url?:"").isEmpty()){
            return
        }

        binding?.exoPlayer?.player = exoPlayer
        binding?.exoPlayer?.useController = false
        prepareMedia()
    }

    private fun prepareMedia() {

        val cacheKey = (reel?.url?:"").toCacheKeyFromPublicVideo()

        val uri:Uri = Uri.parse(reel?.url?:"")
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
        val defaultDataSourceFactory = DefaultDataSource.Factory(requireContext(), httpDataSourceFactory)
        val cacheFactory = CacheDataSource.Factory()
        if (BaseApp.simpleCache != null){
            cacheFactory.setCache(BaseApp.simpleCache!!)
        }
        cacheFactory.setUpstreamDataSourceFactory(defaultDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        val dataSource = ProgressiveMediaSource.Factory(cacheFactory).createMediaSource(
            MediaItem.Builder().setUri(uri).build()
        )


        exoPlayer.setMediaSource(dataSource)
        exoPlayer.prepare()
    }

    override fun onResume() {
        super.onResume()
        restartVideo()
    }

    private fun restartVideo() {
        exoPlayer.seekTo(0)
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        pauseVideo()
    }

    private fun pauseVideo(){
        exoPlayer.pause()
    }

    private fun initData() {
        reel = arguments?.getParcelable(REEL)
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.stop()
        exoPlayer.release()
    }

    private fun initView() {
        binding?.tvDescription?.text = reel?.description?:""
        binding?.tvFreelancerName?.text = reel?.freelancer?.freelancerName?:""
    }

    override fun inject() {

    }
}