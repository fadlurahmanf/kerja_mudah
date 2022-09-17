package com.app.kerja_mudah.ui.payment

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.model.payment.PaymentMethodModel
import com.app.kerja_mudah.data.response.payment.PaymentMethodResponse
import com.app.kerja_mudah.databinding.ActivitySelectPaymentMethodBinding
import com.app.kerja_mudah.di.component.PaymentComponent
import com.app.kerja_mudah.ui.core.WebViewActivity
import com.app.kerja_mudah.ui.payment.adapter.PaymentMethodAdapter
import com.app.kerja_mudah.ui.payment.viewmodel.PaymentViewModel
import javax.inject.Inject

class SelectPaymentMethodActivity : BaseActivity<ActivitySelectPaymentMethodBinding>(ActivitySelectPaymentMethodBinding::inflate) {
    companion object{
        const val PAYMENT_METHOD = "PAYMENT_METHOD"
    }

    override fun initSetup() {
        initAdapter()
        initAction()
        initObserver()
        setInfoText()
        viewModel.getAllPaymentMethod()
    }

    private fun setInfoText() {
        val spannable1 = SpannableString("Click ")
        spannable1.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.black)), 0, spannable1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val spannable2 = SpannableString("Info ")
        spannable2.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.black)), 0, spannable2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable2.setSpan(StyleSpan(Typeface.BOLD), 0, spannable2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val spannable3 = SpannableString("to check how to pay from each payment method.")
        spannable3.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.black)), 0, spannable3.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val spannableStringBuilder = buildSpannedString {
            this.append(spannable1)
            this.append(spannable2)
            this.append(spannable3)
        }
        binding?.tvInfo?.text = spannableStringBuilder
    }

    private lateinit var adapter:PaymentMethodAdapter
    private var list:ArrayList<PaymentMethodModel> = arrayListOf()
    private fun initAdapter() {
        adapter = PaymentMethodAdapter()
        adapter.setList(list)
        adapter.setCallBack(paymentMethodCallBack)
        binding?.rv?.adapter = adapter
    }

    private val paymentMethodCallBack = object : PaymentMethodAdapter.CallBack{
        override fun onPaymentClicked(method: PaymentMethodResponse) {
            adapter.selectMethod(method)
        }

        override fun onInfoClicked(method: PaymentMethodResponse) {
            val intent = Intent(this@SelectPaymentMethodActivity, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.URL, method.howToPayUrl)
            intent.putExtra(WebViewActivity.TITLE, "Tata cara pembayaran")
            startActivity(intent)
        }
    }



    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.getAllPaymentMethodState == BaseState.SUCCESS){
                binding?.llLoading?.visibility = View.GONE
                binding?.llError?.visibility = View.GONE
                binding?.llSuccess?.visibility = View.VISIBLE
                adapter.setList(it.listPaymentMethod)
            }else if (it.getAllPaymentMethodState == BaseState.FAILED){
                binding?.llLoading?.visibility = View.GONE
                binding?.llError?.visibility = View.VISIBLE
                binding?.llSuccess?.visibility = View.GONE
            }else if (it.getAllPaymentMethodState == BaseState.LOADING){
                binding?.llLoading?.visibility = View.VISIBLE
                binding?.llError?.visibility = View.GONE
                binding?.llSuccess?.visibility = View.GONE
            }
        }
    }

    private fun initAction() {
        binding?.tvError?.setOnClickListener {
            viewModel.getAllPaymentMethod()
        }
        binding?.btnChoose?.setOnClickListener {
            intent.putExtra(PAYMENT_METHOD, adapter.selectedMethod)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    @Inject
    lateinit var viewModel:PaymentViewModel

    private lateinit var component: PaymentComponent
    override fun inject() {
        component = appComponent.paymentComponent().create()
        component.inject(this)
    }

}