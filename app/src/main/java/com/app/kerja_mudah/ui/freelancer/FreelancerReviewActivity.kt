package com.app.kerja_mudah.ui.freelancer

import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.mapper.ReviewMapper
import com.app.kerja_mudah.data.model.review.ReviewDetailModel
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse
import com.app.kerja_mudah.databinding.ActivityFreelancerReviewBinding
import com.app.kerja_mudah.di.component.FreelancerComponent
import com.app.kerja_mudah.ui.freelancer.adapter.FreelancerReviewAdapter
import com.app.kerja_mudah.ui.freelancer.viewmodel.FreelancerReviewViewModel
import javax.inject.Inject

class FreelancerReviewActivity : BaseActivity<ActivityFreelancerReviewBinding>(ActivityFreelancerReviewBinding::inflate) {

    companion object{
        const val FREELANCER = "FREELANCER"
        const val SERVICE = "SERVICE"
    }

    private var freelancer:FreelancerResponse ?= null
    private var service:ServiceDetailResponse ?= null
    override fun initSetup() {
        freelancer = intent.getParcelableExtra(FREELANCER)
        service = intent.getParcelableExtra(SERVICE)
        initAdapter()
        initAction()
        initObserver()

        if (freelancer?.id == null && service?.id == null){
            showSnackBar("Parameter is missing. Try again later!")
            return
        }

        if (freelancer?.id != null){
            viewModel.getAllReviewByFreelancerId(freelancer?.id?:-1)
        }

        if (service?.id != null){
            viewModel.getAllReviewByServiceId(service?.id?:-1)
        }
    }

    private fun initAction() {
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }

        binding?.tvFilterAll?.setOnClickListener {
            refreshFilter()
            binding?.tvFilterAll?.background = ContextCompat.getDrawable(this, R.drawable.green_corner_35)
            binding?.tvFilterAll?.setTextColor(ContextCompat.getColor(this, R.color.white))
            list.clear()
            adapter.notifyDataSetChanged()
        }

        binding?.llFilter5?.setOnClickListener {
            refreshFilter()
            binding?.llFilter5?.background = ContextCompat.getDrawable(this, R.drawable.green_corner_35)
            binding?.ivStar5?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding?.tvStar5?.setTextColor(ContextCompat.getColor(this, R.color.white))
            list.clear()
            adapter.notifyDataSetChanged()
        }

        binding?.llFilter4?.setOnClickListener {
            refreshFilter()
            binding?.llFilter4?.background = ContextCompat.getDrawable(this, R.drawable.green_corner_35)
            binding?.ivStar4?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding?.tvStar4?.setTextColor(ContextCompat.getColor(this, R.color.white))
            list.clear()
            adapter.notifyDataSetChanged()
        }

        binding?.llFilter3?.setOnClickListener {
            refreshFilter()
            binding?.llFilter3?.background = ContextCompat.getDrawable(this, R.drawable.green_corner_35)
            binding?.ivStar3?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding?.tvStar3?.setTextColor(ContextCompat.getColor(this, R.color.white))
            list.clear()
            adapter.notifyDataSetChanged()
        }

        binding?.llFilter2?.setOnClickListener {
            refreshFilter()
            binding?.llFilter2?.background = ContextCompat.getDrawable(this, R.drawable.green_corner_35)
            binding?.ivStar2?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding?.tvStar2?.setTextColor(ContextCompat.getColor(this, R.color.white))
            list.clear()
            adapter.notifyDataSetChanged()
        }

        binding?.llFilter1?.setOnClickListener {
            refreshFilter()
            binding?.llFilter1?.background = ContextCompat.getDrawable(this, R.drawable.green_corner_35)
            binding?.ivStar1?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding?.tvStar1?.setTextColor(ContextCompat.getColor(this, R.color.white))
            list.clear()
            adapter.notifyDataSetChanged()
        }
    }

    private fun refreshFilter(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding?.tvFilterAll?.background = ContextCompat.getDrawable(this, R.drawable.white_corner_35_green_border)
            binding?.tvFilterAll?.setTextColor(ContextCompat.getColor(this, R.color.green))

            binding?.llFilter5?.background = ContextCompat.getDrawable(this, R.drawable.white_corner_35_green_border)
            binding?.ivStar5?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
            binding?.tvStar5?.setTextColor(ContextCompat.getColor(this, R.color.green))

            binding?.llFilter4?.background = ContextCompat.getDrawable(this, R.drawable.white_corner_35_green_border)
            binding?.ivStar4?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
            binding?.tvStar4?.setTextColor(ContextCompat.getColor(this, R.color.green))

            binding?.llFilter3?.background = ContextCompat.getDrawable(this, R.drawable.white_corner_35_green_border)
            binding?.ivStar3?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
            binding?.tvStar3?.setTextColor(ContextCompat.getColor(this, R.color.green))

            binding?.llFilter2?.background = ContextCompat.getDrawable(this, R.drawable.white_corner_35_green_border)
            binding?.ivStar2?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
            binding?.tvStar2?.setTextColor(ContextCompat.getColor(this, R.color.green))

            binding?.llFilter1?.background = ContextCompat.getDrawable(this, R.drawable.white_corner_35_green_border)
            binding?.ivStar1?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
            binding?.tvStar1?.setTextColor(ContextCompat.getColor(this, R.color.green))
        }
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.state == BaseState.LOADING){
                binding?.progress?.visibility = View.VISIBLE
                binding?.rvReview?.visibility = View.GONE
            }else if (it.state == BaseState.SUCCESS){
                binding?.progress?.visibility = View.GONE
                binding?.rvReview?.visibility = View.VISIBLE
                list.clear()
                list.addAll(ArrayList(
                    it.data?.data?.map { detail ->
                        ReviewMapper.toReviewModel(detail)
                    }?.toList() ?: listOf()
                ))
                adapter.setList(list)
            }

            if (it.state == BaseState.FAILED){
                showSnackBar(it.error)
            }
        }
    }

    @Inject
    lateinit var viewModel:FreelancerReviewViewModel

    private lateinit var adapter: FreelancerReviewAdapter
    private var list:ArrayList<ReviewDetailModel> = arrayListOf()
    private fun initAdapter() {
        adapter = FreelancerReviewAdapter()
        adapter.setList(list)
        binding?.rvReview?.adapter = adapter
    }


    private lateinit var component: FreelancerComponent
    override fun inject() {
        component = appComponent.freelancerComponent().create()
        component.inject(this)
    }

}