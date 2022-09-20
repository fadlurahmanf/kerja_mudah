package com.app.kerja_mudah.ui.core.dialog

import android.view.View
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseDialog
import com.app.kerja_mudah.databinding.DialogConfirmBinding

class ConfirmDialog:BaseDialog<DialogConfirmBinding>(DialogConfirmBinding::inflate) {
    companion object{
        const val TITLE = "TITLE"
        const val CONTENT = "CONTENT"
        const val NEGATIVE_TEXT = "NEGATIVE_TEXT"
        const val POSITIVE_TEXT = "POSITIVE_TEXT"
        const val CANCELABLE = "CANCELABLE"
    }

    private var negativeListener:() -> Unit = {
        dialog?.dismiss()
    }
    private var positiveListener:() -> Unit = {
        dialog?.dismiss()
    }

    private var title:String ?= null
    private var content:String ?= null
    private var negativeText:String = "Yes"
    private var positiveText:String = "Cancel"
    private var is_cancelable:Boolean = true

    override fun inject() {

    }

    fun setNegativeListener(function:() -> Unit){
        this.negativeListener = function
    }

    fun setPositiveListener(function: () -> Unit){
        this.positiveListener = function
    }

    override fun getTheme(): Int {
        return R.style.WhiteRoundedDialog
    }

    override fun setup() {
        title = arguments?.getString(TITLE)
        content = arguments?.getString(CONTENT)
        negativeText = arguments?.getString(NEGATIVE_TEXT) ?: "Yes"
        positiveText = arguments?.getString(POSITIVE_TEXT) ?: "Cancel"

        if (title == null){
            binding?.tvTitle?.visibility = View.GONE
        }

        if (content == null){
            binding?.tvContent?.visibility = View.GONE
        }

        binding?.tvTitle?.text = title?:""
        binding?.tvContent?.text = content?:""
        binding?.mViewNegative?.text = negativeText
        binding?.mViewPositive?.text = positiveText

        binding?.mViewNegative?.setOnClickListener {
            this.negativeListener()
        }

        binding?.mViewPositive?.setOnClickListener {
            this.positiveListener()
        }

        dialog?.setCanceledOnTouchOutside(is_cancelable)
        dialog?.setCancelable(is_cancelable)
    }
}