package com.app.kerja_mudah.ui.core

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.media.session.PlaybackState
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.extension.toDurationFormatter
import com.app.kerja_mudah.databinding.ActivitySingleVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.material.slider.Slider
import java.io.File

class SingleVideoPlayerActivity : BaseActivity<ActivitySingleVideoPlayerBinding>(ActivitySingleVideoPlayerBinding::inflate) {
    companion object{
        val TAG = SingleVideoPlayerActivity::class.java.simpleName
        const val VIDEO_URL = "VIDEO_URL"
        const val IS_FILE = "IS_LOCAL"
        const val IS_INTERNET = "IS_INTERNET"
        const val IS_ASSET = "IS_ASSET"
    }

    private lateinit var videoUrl:String
    private var isFile:Boolean = false
    private var isInternet:Boolean = false
    private var isAsset:Boolean = false

    private lateinit var exoPlayer: ExoPlayer

    private var isOverlayShowing: Boolean = true
    private var isPause:Boolean = false

    private var ORIENTATION_TYPE = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun initSetup() {
        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer.playWhenReady = true
        binding?.exoPlayer?.player = exoPlayer
        binding?.exoPlayer?.useController = false

        initData()

        if (!isFile && !isAsset && !isInternet){
            Toast.makeText(this, "video unknown resource", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        if (videoUrl.isEmpty()){
            Toast.makeText(this, "video url is required", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        registerListener()
        setVideo()
        initAction()
    }

    private fun initData() {
        videoUrl = intent.getStringExtra(VIDEO_URL) ?: ""
        isFile = intent.getBooleanExtra(IS_FILE, false)
        isInternet = intent.getBooleanExtra(IS_INTERNET, false)
        isAsset = intent.getBooleanExtra(IS_ASSET, false)
    }

    private fun initAction() {
        binding?.slider?.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {}

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                val newPosition = (slider.value * duration).toLong()
                exoPlayer.seekTo(newPosition)
            }
        })

        binding?.mainLayout?.setOnClickListener {
            if (isOverlayShowing){
                handlerController.removeCallbacks(hideAction)
                isOverlayShowing = false
                binding?.clOverlay?.visibility = View.INVISIBLE
            }else{
                isOverlayShowing = true
                binding?.clOverlay?.visibility = View.VISIBLE
                handlerController.postDelayed(hideAction, 5000)
                if (binding?.exoPlayer?.player?.isPlaying == true){
                    binding?.ivPause?.visibility = View.VISIBLE
                    binding?.ivPlay?.visibility = View.INVISIBLE
                }else{
                    binding?.ivPause?.visibility = View.INVISIBLE
                    binding?.ivPlay?.visibility = View.VISIBLE
                }
            }
        }

        binding?.ivPlay?.setOnClickListener {
            if (exoPlayer.playbackState == Player.STATE_ENDED){
                binding?.exoPlayer?.player?.play()
            }else if (exoPlayer.playbackState == Player.STATE_READY){
                isPause = false
                binding?.exoPlayer?.player?.play()
            }
            refreshController()
        }

        binding?.ivPause?.setOnClickListener {
            if(exoPlayer.playbackState == Player.STATE_READY){
                exoPlayer.pause()
                isPause = true
                refreshController()
            }
        }

        binding?.ivFullScreen?.setOnClickListener {
            rotateScreen()
        }
    }

    private fun rotateScreen(){
        if (ORIENTATION_TYPE == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            ORIENTATION_TYPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            requestedOrientation = ORIENTATION_TYPE
        }else if (ORIENTATION_TYPE == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            ORIENTATION_TYPE = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            requestedOrientation = ORIENTATION_TYPE
        }
    }

    private var handlerPosition: Handler = Handler()
    private var handlerController:Handler = Handler()
    private var runnable = object : Runnable {
        override fun run() {
            updateProgress()
            handlerPosition.postDelayed(this, 1000)
        }
    }

    private fun setVideo() {
        try {
            if (isFile){
                setVideoFromLocal()
            }else if (isInternet){
                setVideoFromInternet()
            } else if (isAsset){

            }
        }catch (e:Exception){
            Log.e(TAG, "initVideo: ${e.message}")
        }
    }

    private fun setVideoFromInternet(){
        try {
            Log.d(TAG, "setVideoFromInternet: $videoUrl")
            val videoUri:Uri = Uri.parse(videoUrl)
            val dataSourceFactory = DefaultHttpDataSource.Factory()
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                MediaItem.fromUri(videoUri))
            exoPlayer.setMediaSource(mediaSource)
            exoPlayer.prepare()
            handlerPosition.postDelayed(runnable, 1000)
        }catch (e:Exception){
            Log.e(TAG, "setVideoFromInternet: ${e.message}")
            throw e
        }
    }

    private fun setVideoFromLocal(){
        try {
            Log.d(TAG, "setVideoFromLocal: $videoUrl")
            val file = File(videoUrl)
            if (!file.exists()){
                Toast.makeText(this, "File is not exist", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            val videoUri:Uri = Uri.fromFile(file)
            val dataSourceFactory = FileDataSource.Factory()
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                MediaItem.fromUri(videoUri))
            exoPlayer.setMediaSource(mediaSource)
            exoPlayer.prepare()
            handlerPosition.postDelayed(runnable, 1000)
        }catch (e:Exception){
            Log.e(TAG, "setVideoLocal: ${e.message}")
            throw e;
        }
    }

    private fun registerListener(){
        binding?.exoPlayer?.player?.addListener(playerListener)
    }

    private var playerListener = object : Player.Listener{
        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {}

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying && isOverlayShowing){
                isOverlayShowing = false
                handlerController.postDelayed(hideAction, 5000)
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            Log.e(TAG, "onPlayerError: ${error.message}")
            super.onPlayerError(error)
        }
    }

    override fun onPause() {
        super.onPause()
//        if (exoPlayer.isPlaying){
//            exoPlayer.pause()
//        }
    }

    override fun onResume() {
        super.onResume()
//        if (exoPlayer == null){
//            setVideo()
//        }else{
//            exoPlayer.play()
//        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        handlerPosition.removeCallbacks(runnable)
        handlerController.removeCallbacks(hideAction)
        exoPlayer.stop()
        exoPlayer.release()
        super.onDestroy()
    }

    override fun inject() {}

    private var position:Long = 0L
    private var duration:Long = 0L

    private fun updateProgress(){
        position = binding?.exoPlayer?.player?.currentPosition ?: 0L
        duration = binding?.exoPlayer?.player?.duration ?: 0L

        if (position > duration){
            Log.e(TAG, "updateProgress: position > duration")
            binding?.slider?.value = duration.toFloat()/duration.toFloat()
        }

        if (position <= duration){
            binding?.slider?.value = position.toFloat()/duration.toFloat()
            binding?.tvPosition?.text = getString(R.string.position_per_duration, position.toDurationFormatter(), duration.toDurationFormatter())
        }

        refreshController()
    }

    private fun refreshController(){
        Log.d(TAG, "playbackState: ${exoPlayer.playbackState}")
        if (exoPlayer.playbackState == Player.STATE_BUFFERING || exoPlayer.playbackState == Player.STATE_IDLE){
            binding?.pbLoading?.visibility = View.VISIBLE
            binding?.ivPause?.visibility = View.GONE
            binding?.ivPlay?.visibility = View.GONE
        }else{
            binding?.pbLoading?.visibility = View.GONE
        }

        if (exoPlayer.playbackState == Player.STATE_READY){
            if (isPause){
                binding?.ivPause?.visibility = View.INVISIBLE
                binding?.ivPlay?.visibility = View.VISIBLE
            }else{
                binding?.ivPause?.visibility = View.VISIBLE
                binding?.ivPlay?.visibility = View.INVISIBLE
            }
        }else if (exoPlayer.playbackState == Player.STATE_ENDED){
            binding?.ivPause?.visibility = View.INVISIBLE
            binding?.ivPlay?.visibility = View.VISIBLE
        }
    }

    private var hideAction = Runnable {
        isOverlayShowing = false
        binding?.clOverlay?.visibility = View.INVISIBLE
    }

}