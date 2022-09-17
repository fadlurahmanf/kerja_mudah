package com.app.kerja_mudah.ui.payment

import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.core.extension.toRupiahFormat
import com.app.kerja_mudah.data.response.payment.PaymentMethodResponse
import com.app.kerja_mudah.data.response.service.ServicePromoCodeResponse
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse
import com.app.kerja_mudah.databinding.ActivityCheckoutBinding
import com.app.kerja_mudah.di.component.PaymentComponent
import com.app.kerja_mudah.ui.payment.viewmodel.CheckoutViewModel
import com.bumptech.glide.Glide
import javax.inject.Inject

class CheckoutActivity : BaseActivity<ActivityCheckoutBinding>(ActivityCheckoutBinding::inflate) {

    companion object{
        const val SERVICE = "SERVICE"
    }

    private var detailServiceDetail:ServiceDetailResponse ?= null
    private var paymentMethod:PaymentMethodResponse ?= null

    private var servicePrice:Double ?= null
    private var finalPrice:Double ?= null
    private var promoCodeResponse:ServicePromoCodeResponse ?= null

    @Inject
    lateinit var viewModel:CheckoutViewModel

    override fun initSetup() {
        detailServiceDetail = intent.getParcelableExtra(SERVICE)

        if (detailServiceDetail == null){
            showMissingParameterDialog()
            return
        }

        servicePrice = detailServiceDetail?.price?.toDouble()?:0.0
        finalPrice = servicePrice

        if (servicePrice == null){
            showMissingParameterDialog()
            return
        }

        binding?.tvServicePrice?.text = servicePrice?.toRupiahFormat()?:""
        binding?.tvFinalPrice?.text = servicePrice?.toRupiahFormat()?:""
        refreshServiceView()
        initAction()
        initObserver()
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.applyPromoState == BaseState.LOADING){
                showBasicLoadingDialog()
            }else if (it.applyPromoState == BaseState.SUCCESS){
                promoCodeResponse = it.resultApplyPromo
                refreshPromoResult()
                dismissBasicLoadingDialog()
            }else if (it.applyPromoState == BaseState.FAILED){
                refreshPromoResult()
                dismissBasicLoadingDialog()
                showSnackBar(message = it.errorApplyPromo)
            }
        }
    }

    private fun refreshPromoResult(){
        if (promoCodeResponse == null){
            binding?.llApplyPromoCode?.visibility = View.VISIBLE
            binding?.llResultPromo?.visibility = View.GONE
            binding?.llFinalDiscountPromo?.visibility = View.GONE
        }else if (promoCodeResponse != null){
            val spannable1 = SpannableString("Yeay! Promo Code ")
            spannable1.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.grey)), 0, spannable1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            val spannable2 = SpannableString(promoCodeResponse?.code?:"")
            spannable2.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.black)), 0, spannable2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable2.setSpan(StyleSpan(Typeface.BOLD), 0, spannable2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            val spannable3 = SpannableString(" successfully applied!")
            spannable3.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.grey)), 0, spannable3.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            val spannableStringBuilder = buildSpannedString {
                this.append(spannable1)
                this.append(spannable2)
                this.append(spannable3)
            }
            binding?.tvFinalPromo?.text = promoCodeResponse?.discountPrice?.toRupiahFormat()?:""
            binding?.llFinalDiscountPromo?.visibility = View.VISIBLE
            binding?.llApplyPromoCode?.visibility = View.GONE
            binding?.llResultPromo?.visibility = View.VISIBLE
            binding?.tvResultPromo?.text = spannableStringBuilder
            finalPrice = promoCodeResponse?.finalPrice
            binding?.tvFinalPrice?.text = finalPrice?.toRupiahFormat()?:""
        }
    }

    private fun showMissingParameterDialog(){
        showOkDialog("Oops..", "Missing variable. Please try again later!"){
            dismissOkDialog()
            finish()
        }
    }

    private fun refreshServiceView() {
        binding?.tvServiceTitle?.text = detailServiceDetail?.title?:""
        binding?.tvFreelancerName?.text = detailServiceDetail?.freelancer?.freelancerName?:""
        binding?.tvServiceDescription?.text = detailServiceDetail?.definition?:""
        Glide.with(binding!!.ivServiceHighlight).load(detailServiceDetail?.highlightPhoto?.firstOrNull()?:"")
            .placeholder(R.drawable.light_grey_solid)
            .error(R.drawable.placeholder_broken_image)
            .into(binding!!.ivServiceHighlight)
    }


    private fun initAction() {
        binding?.btnPay?.setOnClickListener {
            if(paymentMethod == null){
                binding?.tvErrorChoosePaymentMethod?.visibility = View.VISIBLE
                return@setOnClickListener
            }
            binding?.tvErrorChoosePaymentMethod?.visibility = View.GONE
            val intent = Intent(this, PaymentLoadingActivity::class.java)
            intent.apply {
                putExtra(PaymentLoadingActivity.SERVICE, detailServiceDetail)
                if (promoCodeResponse != null){
                    putExtra(PaymentLoadingActivity.PROMO_CODE, promoCodeResponse)
                }
                putExtra(PaymentLoadingActivity.PAYMENT_METHOD, paymentMethod)
            }
            startActivity(intent)
        }
        binding?.llSelectPaymentMethod?.setOnClickListener {
            val intent = Intent(this, SelectPaymentMethodActivity::class.java)
            selectPaymentMethodLauncher.launch(intent)
        }
        binding?.btnApplyPromo?.setOnClickListener {
            if (binding?.etPromoCode?.text?.toString()?.trim()?.isEmpty() == true || finalPrice == null){
                return@setOnClickListener
            }
            promoCodeResponse = null
            refreshPromoResult()
            viewModel.applyPromo(binding?.etPromoCode?.text?.toString()?:"", finalPrice?:0.0)
        }
    }

    private val selectPaymentMethodLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            paymentMethod = it.data?.getParcelableExtra<PaymentMethodResponse>(SelectPaymentMethodActivity.PAYMENT_METHOD)
            refreshPaymentMethod()
        }
    }

    private fun refreshPaymentMethod(){
        if (paymentMethod != null){
            binding?.tvPaymentMethodName?.text = paymentMethod?.name?:""
            Glide.with(binding!!.ivPaymentMethodLogo).load(paymentMethod?.logo?:"")
                .placeholder(R.drawable.light_grey_solid)
                .error(R.drawable.placeholder_broken_image)
                .into(binding!!.ivPaymentMethodLogo)
            binding?.ivPaymentMethodLogo?.visibility = View.VISIBLE
            binding?.tvErrorChoosePaymentMethod?.visibility = View.GONE
        }else{
            binding?.tvPaymentMethodName?.text = "Pilih Metode Pembayaran"
            binding?.ivPaymentMethodLogo?.visibility = View.GONE
        }
    }



    private lateinit var component:PaymentComponent
    override fun inject() {
        component = appComponent.paymentComponent().create()
        component.inject(this)
    }

}