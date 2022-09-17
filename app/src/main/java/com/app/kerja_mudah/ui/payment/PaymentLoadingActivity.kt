package com.app.kerja_mudah.ui.payment

import android.content.Intent
import android.os.Handler
import android.view.View
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.payment.PaymentMethodResponse
import com.app.kerja_mudah.data.response.service.ServicePromoCodeResponse
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse
import com.app.kerja_mudah.databinding.ActivityPaymentLoadingBinding
import com.app.kerja_mudah.di.component.PaymentComponent
import com.app.kerja_mudah.ui.home.HomeActivity
import com.app.kerja_mudah.ui.payment.viewmodel.CheckoutViewModel
import javax.inject.Inject

class PaymentLoadingActivity : BaseActivity<ActivityPaymentLoadingBinding>(ActivityPaymentLoadingBinding::inflate) {
    companion object{
        const val SERVICE = "SERVICE"
        const val PROMO_CODE = "PROMO_CODE"
        const val PAYMENT_METHOD = "PAYMENT_METHOD"
    }

    private var detailServiceDetail:ServiceDetailResponse ?= null
    private var promoCode:ServicePromoCodeResponse ?= null
    private var paymentMethod:PaymentMethodResponse ?= null

    override fun initSetup() {
        detailServiceDetail = intent.getParcelableExtra(SERVICE)
        promoCode = intent.getParcelableExtra(PROMO_CODE)
        paymentMethod = intent.getParcelableExtra(PAYMENT_METHOD)

        if (detailServiceDetail == null){
            showMissingVariableDialog()
            return
        }

        binding?.btnCheckPaymentStatus?.setContentButtonPadding(50, 50, 50, 50)

        if (detailServiceDetail?.freelancer?.id == null && detailServiceDetail?.id == null){
            showMissingVariableDialog()
            return
        }

        if (paymentMethod?.id == null){
            showMissingVariableDialog()
            return
        }

        initAction()
        initObserver()
        viewModel.orderService(detailServiceDetail?.freelancer?.id?:-1, detailServiceDetail?.id?:-1, promoCode, paymentMethod = paymentMethod!!)
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.orderServiceState == BaseState.SUCCESS){
                Handler().postDelayed({
                    binding?.llLoading?.visibility = View.GONE
                    binding?.llSuccess?.visibility = View.VISIBLE
                    binding?.llFailed?.visibility = View.GONE
                }, 1500)
            }else if (it.orderServiceState == BaseState.FAILED){
                Handler().postDelayed({
                    binding?.llLoading?.visibility = View.GONE
                    binding?.llSuccess?.visibility = View.GONE
                    binding?.llFailed?.visibility = View.VISIBLE
                    binding?.tvError?.text = it.errorOrderService?:""
                }, 1500)
            }else if (it.orderServiceState == BaseState.LOADING){
                binding?.llLoading?.visibility = View.VISIBLE
                binding?.llSuccess?.visibility = View.GONE
                binding?.llFailed?.visibility = View.GONE
            }
        }
    }


    private fun initAction() {
        binding?.btnCheckPaymentStatus?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding?.btnBackToHome?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun showMissingVariableDialog(){
        showOkDialog("Oops..", "Missing variable. Please try again later!"){
            dismissOkDialog()
            finish()
        }
    }

    @Inject
    lateinit var viewModel:CheckoutViewModel

    private lateinit var component: PaymentComponent
    override fun inject() {
        component = appComponent.paymentComponent().create()
        component.inject(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}