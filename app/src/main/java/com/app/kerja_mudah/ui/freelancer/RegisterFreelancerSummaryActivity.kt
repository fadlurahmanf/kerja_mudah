package com.app.kerja_mudah.ui.freelancer

import android.content.Intent
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.constant.EditEvent
import com.app.kerja_mudah.core.utilities.RxBus
import com.app.kerja_mudah.data.repository.freelancer.FreelancerRepository
import com.app.kerja_mudah.databinding.ActivityRegisterFreelancerSummaryBinding
import com.app.kerja_mudah.di.component.FreelancerComponent
import com.app.kerja_mudah.ui.core.SingleVideoPlayerActivity
import com.app.kerja_mudah.ui.core.adapter.ImageAdapter
import com.app.kerja_mudah.ui.core.adapter.VideoAdapter
import com.bumptech.glide.Glide
import javax.inject.Inject

class RegisterFreelancerSummaryActivity : BaseActivity<ActivityRegisterFreelancerSummaryBinding>(ActivityRegisterFreelancerSummaryBinding::inflate) {

    private lateinit var imageAdapter: ImageAdapter
    private lateinit var videoAdapter: VideoAdapter

    override fun initSetup() {
        initData()
        initAdapter()
        initBus()
        initAction()
    }

    private fun initAction() {
        binding?.tvEditDetail?.setOnClickListener {
            val intent = Intent(this, FreelancerDetailFormActivity::class.java)
            intent.putExtra(FreelancerDetailFormActivity.IS_EDIT, true)
            startActivity(intent)
        }

        binding?.btnNext?.setOnClickListener {
            val intent = Intent(this, RegisterFreelancerLoadingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initBus() {
        RxBus.listen(EditEvent::class.java).subscribe {
            initData()
        }
    }

    private fun initData(){
        binding?.tvCategory?.text = repository.freelancerCategory?.name ?: ""
        binding?.tvFreelancerName?.text = repository.freelancerName ?: ""
        binding?.tvFreelancerDescription?.text = repository.freelancerDescription ?: ""

        Glide.with(binding!!.ivIdCard)
            .load(repository.freelancerIdCard)
            .into(binding!!.ivIdCard)

        Glide.with(binding!!.ivSelfieIdCard)
            .load(repository.freelancerSelfieIdCard)
            .into(binding!!.ivSelfieIdCard)
    }

    private fun initAdapter(){
        imageAdapter = ImageAdapter(repository.highlightPhotoFreelancer?: arrayListOf())
        imageAdapter.enableRemoved(false)
        imageAdapter.setCallBack(object : ImageAdapter.CallBack{
            override fun onClicked(image: String) {}
        })
        binding?.rvPhoto?.adapter = imageAdapter

        videoAdapter = VideoAdapter(repository.highlightVideoFreelancer?: arrayListOf())
        videoAdapter.setCallBack(object : VideoAdapter.CallBack{
            override fun onClicked(video: String) {
                val intent = Intent(this@RegisterFreelancerSummaryActivity, SingleVideoPlayerActivity::class.java)
                intent.apply {
                    putExtra(SingleVideoPlayerActivity.VIDEO_URL, video)
                    putExtra(SingleVideoPlayerActivity.IS_FILE, true)
                }
                startActivity(intent)
            }
        })
        binding?.rvVideo?.adapter = videoAdapter
    }

    @Inject
    lateinit var repository: FreelancerRepository

    private lateinit var component: FreelancerComponent
    override fun inject() {
        component = appComponent.freelancerComponent().create()
        component.inject(this)
    }

}