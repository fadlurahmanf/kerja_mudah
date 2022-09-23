package com.app.kerja_mudah.ui.service

import android.content.Intent
import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.core.extension.toRupiahFormat
import com.app.kerja_mudah.data.mapper.ReviewMapper
import com.app.kerja_mudah.data.model.review.ReviewDetailModel
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse
import com.app.kerja_mudah.databinding.ActivityDetailServiceBinding
import com.app.kerja_mudah.di.component.ServiceComponent
import com.app.kerja_mudah.ui.chat.ChatRoomActivity
import com.app.kerja_mudah.ui.core.PreviewMultipleImageActivity
import com.app.kerja_mudah.ui.core.adapter.VPImageAdapter
import com.app.kerja_mudah.ui.freelancer.FreelancerReviewActivity
import com.app.kerja_mudah.ui.freelancer.adapter.FreelancerReviewAdapter
import com.app.kerja_mudah.ui.payment.CheckoutActivity
import com.app.kerja_mudah.ui.service.viewmodel.DetailServiceViewModel
import com.bumptech.glide.Glide
import javax.inject.Inject

class DetailServiceActivity : BaseActivity<ActivityDetailServiceBinding>(ActivityDetailServiceBinding::inflate) {
    private lateinit var component:ServiceComponent

    companion object{
        const val DETAIL_SERVICE = "DETAIL_SERVICE"
    }

    private var detailModel:ServiceDetailResponse ?= null

    @Inject
    lateinit var viewModel:DetailServiceViewModel

    override fun initSetup() {
        supportActionBar?.hide()
        initData()
        initView()
        initObserver()
        initAdapter()
        initAction()
    }

    private fun initAction() {
        binding?.ivChat?.setOnClickListener {
            if (detailModel?.freelancer?.id != null){
                val intent = Intent(this, ChatRoomActivity::class.java)
                intent.putExtra(ChatRoomActivity.TO, detailModel?.freelancer?.id)
                startActivity(intent)
            }else{
                showSnackBar(message = "Oops, ID is not found")
            }
        }

        binding?.btnOrder?.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra(CheckoutActivity.SERVICE, detailModel)
            startActivity(intent)
        }

        binding?.tvSeeAllReview?.setOnClickListener {
            val intent = Intent(this, FreelancerReviewActivity::class.java)
            intent.putExtra(FreelancerReviewActivity.SERVICE, detailModel)
            startActivity(intent)
        }
    }

    private lateinit var vpImageAdapter: VPImageAdapter
    private var listImage:ArrayList<String> = arrayListOf()

    private fun initAdapter() {
        vpImageAdapter = VPImageAdapter()
        vpImageAdapter.setCallBack(object : VPImageAdapter.CallBack{
            override fun onClicked(image: String) {
                val intent = Intent(this@DetailServiceActivity, PreviewMultipleImageActivity::class.java)
                intent.apply {
                    putExtra(PreviewMultipleImageActivity.LIST_MEDIA, listImage)
                    putExtra(PreviewMultipleImageActivity.HIDE_CONFIRM_BUTTON, true)
                }
                startActivity(intent)
            }
        })
        vpImageAdapter.setList(listImage)
        binding?.vpServicePhoto?.adapter = vpImageAdapter
        binding?.vpServicePhoto?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding?.tvIndexPhoto?.text = getString(R.string.index_per_total_index, position+1, listImage.size)
            }
        })
    }

    private fun initData() {
        detailModel = intent?.getParcelableExtra(DETAIL_SERVICE)
        if (detailModel?.id != null){
            viewModel.getDetailService(detailModel?.id!!)
        }else{
            showOkDialog(
                title = "Oops..",
                content = "ID shouldn't be empty",
                cancelable = false
            )
        }
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.getDetailState == BaseState.SUCCESS){
                dismissBasicLoadingDialog()
                detailModel = it.serviceDetailData
                binding?.pb?.visibility = View.GONE
                binding?.clMain?.visibility = View.VISIBLE
                binding?.llBottom?.visibility = View.VISIBLE

                initView()
                listImage.clear()
                listImage.addAll(it.serviceDetailData?.highlightPhoto?: arrayListOf())
                vpImageAdapter.setList(listImage)

            }else if (it.getDetailState == BaseState.LOADING){
                showBasicLoadingDialog()
            }else if (it.getDetailState == BaseState.FAILED){
                dismissBasicLoadingDialog()
            }

            if (it.getDetailState == BaseState.FAILED){
                showOkDialog(
                    title = "Oops..",
                    content = it.errorGetDetail,
                    cancelable = false
                ){
                    dismissOkDialog()
                    finish()
                }
            }

            if (it.orderState == BaseState.LOADING){
                showLoadingDialog(cancelable = true)
            }else if (it.orderState == BaseState.SUCCESS){
                dismissLoadingDialog()
                showOkDialog(
                    title = "Order successfully",
                    content = "Check status of your order in your order menu"
                )
            }else if (it.orderState == BaseState.FAILED){
                dismissLoadingDialog()
                showOkDialog(
                    title = "Order failed",
                    content = it.errorOrder
                )
            }
        }
    }

    override fun inject() {
        component = appComponent.serviceComponent().create()
        component.inject(this)
    }

    private fun initView(){
        binding?.tvServicePrice?.text = "${detailModel?.price?.toDouble()?.toRupiahFormat()}"
        binding?.tvIndexPhoto?.text = getString(R.string.index_per_total_index, 1, listImage.size)
        binding?.tvServiceTitle?.text = detailModel?.title?:""
        binding?.tvFreelancerName?.text = detailModel?.freelancer?.freelancerName?:""
        binding?.tvEmail?.text = detailModel?.freelancer?.email?:""

        Glide.with(binding!!.ivProfilePicture)
            .load(detailModel?.freelancer?.profile?.photo)
            .centerCrop()
            .placeholder(ContextCompat.getDrawable(this, R.drawable.placeholder_person))
            .into(binding!!.ivProfilePicture)

        binding?.tvServiceDefinition?.text = detailModel?.definition?:""
        if ((detailModel?.definition?.length?:0) > 150){
            val builder = SpannableStringBuilder()
            val span1 = SpannableString(detailModel?.definition?.substring(0, 100))
            val blackForegroundSpan : ForegroundColorSpan = ForegroundColorSpan(Color.BLACK)
            val greenForegroundSpan : ForegroundColorSpan = ForegroundColorSpan(Color.parseColor("#FF019267"))
            span1.setSpan(blackForegroundSpan, 0, span1.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            val span2 = SpannableString("..more")
            span2.setSpan(greenForegroundSpan, 0, span2.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            val moreClickableSpan = object : ClickableSpan(){
                override fun onClick(widget: View) {
                    binding?.tvServiceDefinition?.text = detailModel?.definition
                }
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }
            span2.setSpan(moreClickableSpan, 0, span2.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.append(span1)
                .append(span2)
            binding?.tvServiceDefinition?.movementMethod = LinkMovementMethod.getInstance()
            binding?.tvServiceDefinition?.highlightColor = Color.TRANSPARENT
            binding?.tvServiceDefinition?.text = builder
        }
        refreshReview()
    }

    private fun refreshReview(){
        binding?.kmReview?.setProgress(detailModel?.review?.oneStar?:0, detailModel?.review?.twoStar?:0,
        detailModel?.review?.threeStar?:0, detailModel?.review?.fourStar?:0, detailModel?.review?.fiveStar?:0)
    }

}