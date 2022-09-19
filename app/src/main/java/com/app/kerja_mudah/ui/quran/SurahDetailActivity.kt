package com.app.kerja_mudah.ui.quran

import android.content.Intent
import android.os.Handler
import android.view.View
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.core.extension.toDurationFormatter
import com.app.kerja_mudah.data.response.quran.AyahResponse
import com.app.kerja_mudah.data.response.quran.SurahResponse
import com.app.kerja_mudah.databinding.ActivitySurahDetailBinding
import com.app.kerja_mudah.di.component.QuranComponent
import com.app.kerja_mudah.ui.quran.adapter.LisyAyahAdapter
import com.app.kerja_mudah.ui.quran.viewmodel.QuranViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.MimeTypes
import javax.inject.Inject

class SurahDetailActivity : BaseActivity<ActivitySurahDetailBinding>(ActivitySurahDetailBinding::inflate) {
    companion object{
        const val SURAH = "SURAH"
    }

    private var surah:SurahResponse ?= null
    private lateinit var mExoPlayer:ExoPlayer
    private var isPause:Boolean = false

    override fun initSetup() {
        mExoPlayer = ExoPlayer.Builder(this).build()
        mExoPlayer.playWhenReady = true
        surah = intent.getParcelableExtra(SURAH)
        binding?.tvSurahTitle?.text = surah?.namaLatin?:""
        if (surah?.nomor == null){
            showSnackBar("Nomor Surah is required")
            return
        }
        initAction()
        initAdapter()
        initObserver()
        viewModel.getDetailSurah(surah?.nomor?:-1)
    }

    private fun initAction() {
        binding?.ivPlayPause?.setOnClickListener {
            playAudio()
        }

        binding?.ivPlayBottom?.setOnClickListener {
            if (mExoPlayer.playbackState == Player.STATE_READY){
                if (!isPause){
                    mExoPlayer.pause()
                    isPause = true
                }else{
                    mExoPlayer.play()
                    isPause = false
                }
            }
        }

        binding?.ivInfo?.setOnClickListener {
            if (surah == null){
                return@setOnClickListener
            }
            val intent = Intent(this, TafsirSurahActivity::class.java)
            intent.putExtra(TafsirSurahActivity.SURAH, surah)
            startActivity(intent)
        }

        binding?.ivNext?.setOnClickListener {
            if ((surah?.nomor?:114) >= 114){
                showSnackBar("${surah?.namaLatin} adalah surah terakhir")
                return@setOnClickListener
            }

            mExoPlayer.stop()
            handlerPosition.removeCallbacks(positionCallback)
            binding?.llControllerAudio?.visibility = View.GONE
            viewModel.getDetailSurah((surah?.nomor?:0)+1, true)
        }

        binding?.ivPrevious?.setOnClickListener {
            if ((surah?.nomor?:1) <= 1){
                showSnackBar("${surah?.namaLatin} adalah surah pertama")
                return@setOnClickListener
            }
            mExoPlayer.stop()
            handlerPosition.removeCallbacks(positionCallback)
            binding?.llControllerAudio?.visibility = View.GONE
            viewModel.getDetailSurah((surah?.nomor?:0)-1, playAudio = true)
        }
    }

    override fun onStop() {
        handlerPosition.removeCallbacks(positionCallback)
        mExoPlayer.stop()
        mExoPlayer.release()
        super.onStop()
    }

    private fun playAudio() {
        if (surah?.audio?.isNullOrEmpty() == true){
            showSnackBar("Audio is empty")
            return
        }

        val mediaItem = MediaItem.Builder()
            .setUri(surah?.audio?:"")
            .setMimeType(MimeTypes.AUDIO_WAV)
            .build()

        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaSource = ProgressiveMediaSource.Factory(httpDataSourceFactory).createMediaSource(mediaItem)
        mExoPlayer.setMediaSource(mediaSource)
        mExoPlayer.prepare()
        mExoPlayer.addListener(audioCallback)
        handlerPosition.postDelayed(positionCallback, 1000)
    }

    private var audioCallback = object : Player.Listener{
        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState != Player.STATE_IDLE){
                binding?.llControllerAudio?.visibility = View.VISIBLE
            }else{
                binding?.llControllerAudio?.visibility = View.GONE
            }
        }
    }

    private var handlerPosition: Handler = Handler()
    private var positionCallback = object : Runnable{
        override fun run() {
            updateProgress()
            handlerPosition.postDelayed(this, 1000)
        }
    }

    private var position:Long = 0L
    private var duration:Long = 0L

    private fun updateProgress() {
        position = mExoPlayer.currentPosition
        duration = mExoPlayer.duration

        if (position > duration){
            binding?.tvPositionAudio?.text = duration.toDurationFormatter()?:"00:00"
            binding?.tvDurationAudio?.text = duration.toDurationFormatter()?:"00:00"
        }

        if (position <= duration){
            binding?.slider?.value = position.toFloat()/duration.toFloat()
            binding?.tvPositionAudio?.text = position.toDurationFormatter()?:"00:00"
            binding?.tvDurationAudio?.text = duration.toDurationFormatter()?:"00:00"
        }

        if (mExoPlayer.playbackState == Player.STATE_READY || mExoPlayer.playbackState == Player.STATE_ENDED){
            if (!isPause){
                binding?.ivPlayBottom?.setImageResource(R.drawable.ic_pause)
            }else{
                binding?.ivPlayBottom?.setImageResource(R.drawable.ic_play)
            }
        }
    }

    private lateinit var adapter:LisyAyahAdapter
    private var listAyah = arrayListOf<AyahResponse>()
    private fun initAdapter() {
        adapter = LisyAyahAdapter()
        adapter.setCallBack(callback)
        adapter.setList(listAyah)
        binding?.rv?.adapter = adapter
    }

    private var callback = object : LisyAyahAdapter.CallBack{
        override fun onSharedClicked(ayah: AyahResponse) {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "\n${surah?.namaLatin} - Ayat ke ${ayah.nomor}\n\n${ayah.ar}\n\n${ayah.idn}")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }

    private fun playAudio(url:String){
        if (url.isEmpty()){
            showSnackBar("URL is required")
            return
        }

        val dataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem = MediaItem.Builder()
            .setUri(url)
            .setMimeType(MimeTypes.AUDIO_WAV)
            .build()
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

        mExoPlayer.setMediaSource(mediaSource)
        mExoPlayer.prepare()
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.getDetailSurah == BaseState.SUCCESS){
                surah = it.detailSurah
                binding?.tvSurahTitle?.text = surah?.namaLatin?:""
                listAyah.clear()
                listAyah.addAll(it.detailSurah?.ayat?: arrayListOf())
                adapter.setList(listAyah)

                if (it.playAudio){
                    playAudio()
                }

                binding?.pb?.visibility = View.GONE
                binding?.tvError?.visibility = View.GONE
                binding?.rv?.visibility = View.VISIBLE
            }else if (it.getDetailSurah == BaseState.FAILED){
                binding?.pb?.visibility = View.GONE
                binding?.tvError?.visibility = View.VISIBLE
                binding?.rv?.visibility = View.GONE
            }else if (it.getDetailSurah == BaseState.LOADING){
                binding?.pb?.visibility = View.VISIBLE
                binding?.tvError?.visibility = View.GONE
                binding?.rv?.visibility = View.GONE
            }
        }
    }

    private lateinit var component: QuranComponent
    override fun inject() {
        component = appComponent.quranComponent().create()
        component.inject(this)
    }

    @Inject
    lateinit var viewModel:QuranViewModel
}