package com.app.kerja_mudah.ui.core.dialog

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseDialog
import com.app.kerja_mudah.databinding.DialogAppVersionBinding

class AppVersionDialog:BaseDialog<DialogAppVersionBinding>(DialogAppVersionBinding::inflate) {

    companion object{
        const val UPDATE = "UPDATE"
        const val FORCED = "FORCED"
        const val NEW_VERSION = "NEW_VERSION"
    }

    private var callBack:CallBack ?= null

    private var update:Boolean = false
    private var forced:Boolean = false
    private var newVersion:String ?= null

    override fun inject() {}

    override fun setup() {
        update = arguments?.getBoolean(UPDATE, false) ?: false
        forced = arguments?.getBoolean(FORCED, false) ?: false
        newVersion = arguments?.getString(NEW_VERSION)

        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        if ((update || forced) && (newVersion == null || newVersion?.isEmpty() == true)){
            dismiss()
            return
        }

        initAction()

        if (update && forced){
            showForceUpdate()
        }else if (update){
            showSoftUpdate()
        }else{
            showUpdated()
        }
    }

    private fun initAction() {
        binding?.mViewLater?.setOnClickListener {
            callBack?.onLaterClick()
        }

        binding?.mViewUpdate?.setOnClickListener {
            callBack?.onUpdateClick()
        }
    }

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    private fun showForceUpdate(){
        binding?.ilUpdate?.visibility = View.GONE
        binding?.ilAppStore?.visibility = View.VISIBLE
        binding?.ilPlayStore?.visibility = View.VISIBLE

        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        binding?.mViewLater?.visibility = View.GONE
        binding?.mViewUpdate?.visibility = View.VISIBLE

        val spannable1 = SpannableString(newVersion?:"")
        spannable1.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.grey)), 0, spannable1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable1.setSpan(StyleSpan(Typeface.BOLD), 0, spannable1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val spannable2 = SpannableString("New Version Available! Please update to enhance performance of app")
        spannable2.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)), 0, spannable2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val stringBuilder = buildSpannedString {
//            append(spannable1)
            append(spannable2)
        }

        binding?.tvVersionInfo?.text = stringBuilder
    }

    override fun getTheme(): Int {
        return R.style.WhiteRoundedDialog
    }

    private fun showSoftUpdate(){
        binding?.ilUpdate?.visibility = View.GONE
        binding?.ilAppStore?.visibility = View.VISIBLE
        binding?.ilPlayStore?.visibility = View.VISIBLE

        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        binding?.mViewLater?.visibility = View.VISIBLE
        binding?.mViewUpdate?.visibility = View.VISIBLE

        val spannable1 = SpannableString(newVersion?:"")
        spannable1.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)), 0, spannable1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable1.setSpan(StyleSpan(Typeface.BOLD), 0, spannable1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val spannable2 = SpannableString("New Version Available! Please update to enhance performance of app")
        spannable2.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)), 0, spannable2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable2.setSpan(StyleSpan(Typeface.NORMAL), 0, spannable2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val stringBuilder = buildSpannedString {
//            append(spannable1)
            append(spannable2)
        }

        binding?.tvVersionInfo?.text = stringBuilder
    }

    private fun showUpdated(){
        binding?.ilUpdate?.visibility = View.VISIBLE
        binding?.ilAppStore?.visibility = View.GONE
        binding?.ilPlayStore?.visibility = View.GONE

        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)

        binding?.mViewLater?.visibility = View.GONE
        binding?.mViewUpdate?.visibility = View.GONE

        binding?.tvVersionInfo?.text = "You already use the new version"
    }

    interface CallBack{
        fun onLaterClick(){}
        fun onUpdateClick(){}
    }
}