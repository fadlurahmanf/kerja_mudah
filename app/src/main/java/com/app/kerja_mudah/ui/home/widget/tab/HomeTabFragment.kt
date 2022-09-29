package com.app.kerja_mudah.ui.home.widget.tab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseFragment
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.core.extension.setLightStatusBarColor
import com.app.kerja_mudah.core.utilities.PermissionUtilities
import com.app.kerja_mudah.core.worker.core.CacheWorker
import com.app.kerja_mudah.data.repository.freelancer.FreelancerRepository
import com.app.kerja_mudah.data.response.core.AdsResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerReelResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.job.JobResponse
import com.app.kerja_mudah.databinding.FragmentHomeTabBinding
import com.app.kerja_mudah.di.component.HomeComponent
import com.app.kerja_mudah.ui.core.VPVerticalVideoActivity
import com.app.kerja_mudah.ui.ewallet.ReadNfcActivity
import com.app.kerja_mudah.ui.freelancer.DetailFreelancerActivity
import com.app.kerja_mudah.ui.freelancer.PagingFreelancerActivity
import com.app.kerja_mudah.ui.freelancer.adapter.HorizontalFreelancerReelAdapter
import com.app.kerja_mudah.ui.home.HomeActivity
import com.app.kerja_mudah.ui.home.adapter.AdsBannerAdapter
import com.app.kerja_mudah.ui.home.adapter.FreelancerAdapter
import com.app.kerja_mudah.ui.home.adapter.JobAdapter
import com.app.kerja_mudah.ui.home.viewmodel.HomeViewModel
import com.app.kerja_mudah.ui.job.JobLocationSearchingAnimationActivity
import com.app.kerja_mudah.ui.job.JobDetailActivity
import com.app.kerja_mudah.ui.quran.QuranInformationActivity
import javax.inject.Inject


class HomeTabFragment : BaseFragment<FragmentHomeTabBinding>(FragmentHomeTabBinding::inflate) {

    var bundle:Bundle ?= null
    override fun setup(savedInstanceState: Bundle?) {
        requireActivity().setLightStatusBarColor(false)
        initAction()
        initAdsBannerAdapter()
        initFreelancerAdapter()
        initFreelancerReelAdapter()
        initJobAdapter()
        setupIndicator()
        homeViewModel.getAllAdsBanner()
        homeViewModel.getAllFreelancer()
        homeViewModel.getAllReelFreelancer()
        homeViewModel.getListJob()
        initObserver()
    }

    private fun initAction(){
        binding?.tvFreelancerSeeAll?.setOnClickListener {
            val intent = Intent(this.activity, PagingFreelancerActivity::class.java)
            startActivity(intent)
        }

        binding?.swl?.setOnRefreshListener {
            binding?.swl?.isRefreshing = true
            homeViewModel.getAllAdsBanner()
            homeViewModel.getAllFreelancer()
            homeViewModel.getListJob()
        }

        binding?.mQuranView?.setOnClickListener {
            val intent = Intent(requireActivity(), QuranInformationActivity::class.java)
            startActivity(intent)
        }

        binding?.mJobNearMe?.setOnClickListener {
            val result = PermissionUtilities.checkMyGPSLocationProvider(requireContext())
            if (!result){
                (requireActivity() as HomeActivity).showConfirmDialog(
                    title = "Location Permission",
                    content = "You need to enable access location to use this feature",
                    negativeText = "Cancel",
                    positiveText = "App Setting",
                    positiveListener = {
                        (requireActivity() as HomeActivity).dismissConfirmDialog()
                        startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                )
            }else{
                val intent = Intent(requireActivity(), JobLocationSearchingAnimationActivity::class.java)
                startActivity(intent)
            }
        }

        binding?.mEwallet?.setOnClickListener {
            val granted = PermissionUtilities.checkNfcEnabled(requireContext())
            if (!granted){
                (requireActivity() as HomeActivity).showConfirmDialog(
                    title = "NFC Permission",
                    content = "You need to enable NFC to use this feature",
                    negativeText = "Cancel",
                    positiveText = "App Setting",
                    positiveListener = {
                        (requireActivity() as HomeActivity).dismissConfirmDialog()
                        startActivity(Intent(android.provider.Settings.ACTION_NFC_SETTINGS))
                    }
                )
            }else{
                val intent = Intent(requireActivity(), ReadNfcActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initObserver() {
        homeViewModel.homeState.observe(this){
            if (it.allFreelancerState == BaseState.LOADING){
                binding?.llFreelancerShimmer?.visibility = View.VISIBLE
                binding?.rvFreelancer?.visibility = View.GONE
                if (freelancerRepository.listFreelancer?.isNullOrEmpty() == false){
                    binding?.llFreelancerShimmer?.visibility = View.GONE
                    binding?.rvFreelancer?.visibility = View.VISIBLE
                    listFreelancer.clear()
                    listFreelancer.addAll(freelancerRepository.listFreelancer?: arrayListOf())
                    freelancerAdapter.notifyDataSetChanged()
                }
            }else if (it.allFreelancerState == BaseState.SUCCESS){
                binding?.swl?.isRefreshing = false
                binding?.llFreelancerShimmer?.visibility = View.GONE
                binding?.rvFreelancer?.visibility = View.VISIBLE
                listFreelancer.clear()
                listFreelancer.addAll(if ((it.dataAllFreelancer?.size?:0) > 10 ) (it.dataAllFreelancer?.take(10)?.toList()?: arrayListOf()) else (it.dataAllFreelancer?: arrayListOf()))
                freelancerAdapter.notifyDataSetChanged()
            }

            if (it.allFreelancerState == BaseState.FAILED){
                    binding?.swl?.isRefreshing = false
                (requireActivity() as HomeActivity).showSnackBar(it.errorMessageAllFreelancer)
            }

            if (it.adsBannerState == BaseState.SUCCESS && it.dataAdsBanner?.isNullOrEmpty() == false){
                binding?.swl?.isRefreshing = false
                binding?.vpBanner?.visibility = View.VISIBLE
                binding?.llIndicatorBanner?.visibility = View.VISIBLE
                listAdsBanner.clear()
                listAdsBanner.addAll(it.dataAdsBanner?: arrayListOf())
                bannerAdapter.notifyDataSetChanged()
                setupIndicator()
            }else{
                binding?.vpBanner?.visibility = View.GONE
                binding?.llIndicatorBanner?.visibility = View.GONE
                if (freelancerRepository.listAds?.isNullOrEmpty() == false){
                    binding?.vpBanner?.visibility = View.VISIBLE
                    binding?.llIndicatorBanner?.visibility = View.VISIBLE
                    listAdsBanner.clear()
                    listAdsBanner.addAll(freelancerRepository.listAds?: arrayListOf())
                    bannerAdapter.notifyDataSetChanged()
                    setupIndicator()
                }else{
                    binding?.vpBanner?.visibility = View.GONE
                    binding?.llIndicatorBanner?.visibility = View.GONE
                }
            }

            if (it.adsBannerState == BaseState.FAILED){
                binding?.swl?.isRefreshing = false
                (requireActivity() as HomeActivity).showSnackBar(it.errorMessageAdsBanner)
            }

            if (it.allJobState == BaseState.SUCCESS){
                binding?.swl?.isRefreshing = false
                binding?.rvJob?.visibility = View.VISIBLE
                binding?.llJobShimmer?.visibility = View.GONE
                listJob.clear()
                listJob.addAll(it.dataAllJob?: arrayListOf())
                jobAdapter.setList(listJob)
            }else{
                binding?.rvJob?.visibility = View.GONE
                binding?.llJobShimmer?.visibility = View.VISIBLE
                if (listJob.isNotEmpty()){
                    binding?.rvJob?.visibility = View.VISIBLE
                    binding?.llJobShimmer?.visibility = View.GONE
                }
            }

            if (it.reelsFreelancerState == BaseState.SUCCESS){
                listReelFreelancer.clear()
                listReelFreelancer.addAll(it.listReelFreelancer?: arrayListOf())
                freelancerReelAdapter.setList(listReelFreelancer)
                refreshFreelancerReelView()
                cachingReelVideos()
            }else{
                refreshFreelancerReelView()
            }

        }
    }

    private fun cachingReelVideos(){
        val inputData = Data.Builder().putStringArray(CacheWorker.DATA_LIST, listReelFreelancer.map { it.url?:"" }.toTypedArray()).build()
        val cachingWork = OneTimeWorkRequestBuilder<CacheWorker>().setInputData(inputData)
            .build()
        WorkManager.getInstance(this.requireContext())
            .enqueue(cachingWork)
    }

    private fun refreshFreelancerReelView(){
        if (listReelFreelancer.isNotEmpty()){
            binding?.llReelsFreelancer?.visibility = View.VISIBLE
        }else{
            binding?.llReelsFreelancer?.visibility = View.GONE
        }
    }

    @Inject
    lateinit var homeViewModel:HomeViewModel

    private lateinit var freelancerAdapter:FreelancerAdapter
    private var listFreelancer:ArrayList<FreelancerResponse> = arrayListOf()
    private fun initFreelancerAdapter() {
        freelancerAdapter = FreelancerAdapter(listFreelancer)
        freelancerAdapter.setCallBack(object : FreelancerAdapter.CallBack{
            override fun onFreelancerClicked(freelancer: FreelancerResponse) {
                val intent = Intent(this@HomeTabFragment.requireActivity(), DetailFreelancerActivity::class.java)
                intent.putExtra(DetailFreelancerActivity.FREELANCER, freelancer)
                startActivity(intent)
            }
        })
        binding?.rvFreelancer?.adapter = freelancerAdapter
    }

    private lateinit var freelancerReelAdapter:HorizontalFreelancerReelAdapter
    private var listReelFreelancer:ArrayList<FreelancerReelResponse> = arrayListOf()
    private fun initFreelancerReelAdapter(){
        freelancerReelAdapter = HorizontalFreelancerReelAdapter()
        freelancerReelAdapter.setList(listReelFreelancer)
        freelancerReelAdapter.setCallBack(object : HorizontalFreelancerReelAdapter.CallBack{
            override fun onClicked() {
                val intent = Intent(this@HomeTabFragment.requireActivity(), VPVerticalVideoActivity::class.java)
                intent.putParcelableArrayListExtra(VPVerticalVideoActivity.LIST_VIDEO, listReelFreelancer)
                this@HomeTabFragment.startActivity(intent)
            }
        })
        binding?.rvReels?.adapter = freelancerReelAdapter
    }

    private lateinit var bannerAdapter: AdsBannerAdapter
    private var listAdsBanner:ArrayList<AdsResponse> = arrayListOf()
    private fun initAdsBannerAdapter() {
        bannerAdapter = AdsBannerAdapter(listAdsBanner)
        bannerAdapter.setCallBack(object : AdsBannerAdapter.CallBack{
            override fun onAdsBannerClicked(adsResponse: AdsResponse) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(adsResponse.destinationUrl))
                startActivity(browserIntent)
            }
        })
        binding?.vpBanner?.adapter = bannerAdapter
        binding?.vpBanner?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                refreshIndicator(position)
            }
        })
    }

    private lateinit var jobAdapter: JobAdapter
    private var listJob:ArrayList<JobResponse> = arrayListOf()
    private fun initJobAdapter(){
        jobAdapter = JobAdapter()
        jobAdapter.setCallBack(jobCallBack)
        jobAdapter.setList(listJob)
        binding?.rvJob?.adapter = jobAdapter
    }

    private var jobCallBack = object : JobAdapter.CallBack{
        override fun onClicked(job: JobResponse) {
            val intent = Intent(this@HomeTabFragment.requireActivity(), JobDetailActivity::class.java)
            intent.putExtra(JobDetailActivity.JOB, job)
            startActivity(intent)
        }
    }

    @Inject
    lateinit var freelancerRepository: FreelancerRepository

    override fun onPause() {
        freelancerRepository.listFreelancer = listFreelancer
        freelancerRepository.listAds = listAdsBanner
        super.onPause()
    }

    override fun onStop() {
        homeViewModel.disposable().clear()
        super.onStop()
    }

    override fun onDestroy() {
        homeViewModel.disposable().dispose()
        super.onDestroy()
    }

    private fun setupIndicator(){
        binding?.llIndicatorBanner?.removeAllViews()
        if (listAdsBanner.size > 0){
            val adsList = arrayOfNulls<ImageView>(listAdsBanner.size)
            for (i in adsList.indices){
                val view = ImageView(context)
                val lp = LinearLayout.LayoutParams(25, 25)
                lp.setMargins(10, 0, 10, 0)
                view.layoutParams = lp
                view.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.yellow_indicator_inactive)
                adsList[i] = view
                binding?.llIndicatorBanner?.addView(adsList[i])
            }

            val view = binding?.llIndicatorBanner?.getChildAt(0) as ImageView
            val lp = LinearLayout.LayoutParams(60, 25)
            lp.setMargins(10, 0, 10, 0)
            view.layoutParams = lp
            view.background = ContextCompat.getDrawable(requireContext(), R.drawable.yellow_indicator_active)
        }
    }

    private fun refreshIndicator(currentIndex:Int){
        for (i in 0 until (listAdsBanner.size)){
            if (i != currentIndex){
                val view = binding?.llIndicatorBanner?.getChildAt(i) as ImageView
                val lp = LinearLayout.LayoutParams(25, 25)
                lp.setMargins(7, 0, 7, 0)
                view.layoutParams = lp
                view.background = ContextCompat.getDrawable(requireContext(), R.drawable.yellow_indicator_inactive)
            }else{
                val view = binding?.llIndicatorBanner?.getChildAt(i) as ImageView
                val lp = LinearLayout.LayoutParams(60, 25)
                lp.setMargins(7, 0, 7, 0)
                view.layoutParams = lp
                view.background = ContextCompat.getDrawable(requireContext(), R.drawable.yellow_indicator_active)
            }
        }
    }

    private lateinit var component: HomeComponent
    override fun inject() {
        component = appComponent.homeComponent().create()
        component.inject(this)
    }
}