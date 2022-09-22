package com.app.kerja_mudah.ui.freelancer

import android.content.Intent
import android.os.Handler
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.mapper.ReviewMapper
import com.app.kerja_mudah.data.model.review.ReviewDetailModel
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse
import com.app.kerja_mudah.databinding.ActivityDetailFreelancerBinding
import com.app.kerja_mudah.di.component.FreelancerComponent
import com.app.kerja_mudah.ui.core.PreviewMultipleImageActivity
import com.app.kerja_mudah.ui.core.SingleVideoPlayerActivity
import com.app.kerja_mudah.ui.core.adapter.VPImageAdapter
import com.app.kerja_mudah.ui.freelancer.adapter.FreelancerReviewAdapter
import com.app.kerja_mudah.ui.core.adapter.VideoAdapter
import com.app.kerja_mudah.ui.freelancer.viewmodel.FreelancerViewModel
import com.app.kerja_mudah.ui.service.DetailServiceActivity
import com.app.kerja_mudah.ui.service.adapter.ServiceAdapter
import com.bumptech.glide.Glide
import javax.inject.Inject

class DetailFreelancerActivity : BaseActivity<ActivityDetailFreelancerBinding>(ActivityDetailFreelancerBinding::inflate){

    companion object{
        const val FREELANCER = "FREELANCER"
    }

    @Inject
    lateinit var viewModel: FreelancerViewModel

    private var mCurrentIndexImage:Int = 0
    private var mTotalImage:Int = 0

    private var freelancer:FreelancerResponse?=null
    override fun initSetup() {
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        freelancer = intent.getParcelableExtra(FREELANCER)
        mTotalImage = freelancer?.highlightPhoto?.size?:0

        initView()
        initAction()
        initAdapter()

        handler.postDelayed(sliderCallback, 4000)

        if (freelancer?.id == null){
            showSnackBar("Freelancer ID is required")
            return
        }

        viewModel.getDetailFreelancer(freelancer?.id!!)
        initObserver()
    }

    private fun initAction() {
        binding?.toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.tvSeeAll?.setOnClickListener {
            val intent = Intent(this, FreelancerReviewActivity::class.java)
            intent.putExtra(FreelancerReviewActivity.FREELANCER, freelancer)
            startActivity(intent)
        }
    }

    private fun initObserver() {
        viewModel.detailFreelancerState.observe(this){
            if (it.state == BaseState.SUCCESS){
                freelancer = it.data
                listReview.clear()
                listReview.addAll(ArrayList(
                    it.data?.review?.data?.map { detail ->
                        ReviewMapper.toReviewModel(detail)
                    }?.toList()?: listOf()
                ))
                reviewAdapter.notifyDataSetChanged()

                listServiceDetail.clear()
                listServiceDetail.addAll(it.data?.service?.data?: arrayListOf())
                serviceAdapter.notifyDataSetChanged()

                listImage.clear()
                listImage.addAll(it.data?.highlightPhoto?: arrayListOf())
                photoAdapterVP.setList(listImage)

                listVideo.clear()
                listVideo.addAll(it.data?.highlightVideo?: arrayListOf())
                videoAdapter.notifyDataSetChanged()

                binding?.llShimmerServices?.visibility = View.GONE
                initView()
            }

            if (it.state == BaseState.FAILED){
                showSnackBar(it.error)
            }
        }
    }

    private fun initView() {
        binding?.tvToolbarTitle?.text = freelancer?.freelancerName?:""
        binding?.tvFreelancerName?.text = freelancer?.freelancerName?:""

        Glide.with(binding?.ivProfilePicture!!)
            .load(freelancer?.profile?.photo)
            .centerCrop()
            .error(R.drawable.placeholder_person)
            .into(binding?.ivProfilePicture!!)

        binding?.tvHighlightTextFreelancer?.text = freelancer?.highlightText?:""
        binding?.tvTotalService?.text = getString(R.string.total_service, freelancer?.service?.totalService?:0)

        refreshReview()
        refreshServiceFreelancer()
        refreshHighlightVideo()
    }

    private fun refreshHighlightVideo(){
        if (listVideo.isEmpty()){
            binding?.llHighlightVideo?.visibility = View.GONE
        }else{
            binding?.llHighlightVideo?.visibility = View.VISIBLE
        }
    }

    private fun refreshServiceFreelancer(){
        if (listServiceDetail.isEmpty()){
            binding?.llServices?.visibility = View.GONE
        }else{
            binding?.llServices?.visibility = View.VISIBLE
        }
    }

    private fun refreshReview(){
        binding?.kmReview?.setProgress(freelancer?.review?.oneStar?:0, freelancer?.review?.twoStar?:0,
        freelancer?.review?.threeStar?:0, freelancer?.review?.fourStar?:0, freelancer?.review?.fiveStar?:0)
        if (listReview.isEmpty()){
            binding?.llReview?.visibility = View.GONE
        }else{
            binding?.llReview?.visibility = View.VISIBLE
        }
    }

    private lateinit var reviewAdapter: FreelancerReviewAdapter
    private var listReview:ArrayList<ReviewDetailModel> = arrayListOf()
    private lateinit var serviceAdapter: ServiceAdapter
    private var listServiceDetail:ArrayList<ServiceDetailResponse> = arrayListOf()
    private lateinit var videoAdapter: VideoAdapter
    private var listVideo:ArrayList<String> = arrayListOf()
    private lateinit var photoAdapterVP:VPImageAdapter
    private var listImage:ArrayList<String> = arrayListOf()
    private fun initAdapter(){
        reviewAdapter = FreelancerReviewAdapter()
        reviewAdapter.setList(listReview)
        binding?.rvReview?.adapter = reviewAdapter

        serviceAdapter = ServiceAdapter(listServiceDetail)
        serviceAdapter.setCallBack(object : ServiceAdapter.CallBack{
            override fun onServiceClicked(serviceDetail: ServiceDetailResponse) {
                val intent = Intent(this@DetailFreelancerActivity, DetailServiceActivity::class.java)
                intent.putExtra(DetailServiceActivity.DETAIL_SERVICE, serviceDetail)
                startActivity(intent)
            }
        })
        binding?.rvService?.adapter = serviceAdapter

        listVideo.clear()
        listVideo.addAll(freelancer?.highlightVideo?: arrayListOf())
        videoAdapter = VideoAdapter(listVideo)
        videoAdapter.setCallBack(object : VideoAdapter.CallBack{
            override fun onClicked(video: String) {
                val intent = Intent(this@DetailFreelancerActivity, SingleVideoPlayerActivity::class.java)
                intent.putExtra(SingleVideoPlayerActivity.VIDEO_URL, video)
                intent.putExtra(SingleVideoPlayerActivity.IS_INTERNET, true)
                startActivity(intent)
            }
        })
        binding?.rvHighlightVideo?.adapter = videoAdapter

        listImage.clear()
        listImage.addAll(freelancer?.highlightPhoto?: arrayListOf())
        photoAdapterVP = VPImageAdapter()
        photoAdapterVP.setList(listImage)
        photoAdapterVP.setCallBack(imageAdapterCallback)
        binding?.vpFreelancerPhoto?.adapter = photoAdapterVP
        binding?.vpFreelancerPhoto?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mCurrentIndexImage = position
                binding?.tvIndexPhoto?.text = getString(R.string.index_per_total_index, position+1, listImage.size)
            }
        })
    }

    private lateinit var component:FreelancerComponent
    override fun inject() {
        component = appComponent.freelancerComponent().create()
        component.inject(this)
    }

    private val handler = Handler()

    private var sliderCallback = object : Runnable{
        override fun run() {
            if (mCurrentIndexImage+1 >= mTotalImage){
                mCurrentIndexImage = 0
            }else{
                mCurrentIndexImage++
            }
            binding?.vpFreelancerPhoto?.currentItem = mCurrentIndexImage
            handler.postDelayed(this, 4000)
        }
    }

    private val imageAdapterCallback = object : VPImageAdapter.CallBack{
        override fun onClicked(image: String) {
            val intent = Intent(this@DetailFreelancerActivity, PreviewMultipleImageActivity::class.java)
            intent.apply {
                putExtra(PreviewMultipleImageActivity.HIDE_CONFIRM_BUTTON, true)
                putExtra(PreviewMultipleImageActivity.LIST_MEDIA, listImage)
            }
            startActivity(intent)
        }
    }

}