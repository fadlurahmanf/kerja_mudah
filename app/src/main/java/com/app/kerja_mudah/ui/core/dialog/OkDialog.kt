package com.app.kerja_mudah.ui.core.dialog

import android.view.View
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseDialog
import com.app.kerja_mudah.databinding.DialogOkBinding

class OkDialog:BaseDialog<DialogOkBinding>(DialogOkBinding::inflate) {

    companion object{
        const val TITLE = "TITLE"
        const val CONTENT = "CONTENT"
        const val BUTTON_TEXT = "BUTTON_TEXT"
        const val CANCELABLE = "CANCELABLE"
    }

    override fun inject() {}

    override fun setup() {
        initData()
    }

    private var okButtonListener: () -> Unit = {
        dismiss()
    }

    private var title:String ?= null
    private var content:String ?= null
    private var btnText:String ?= null
    private var cancelableDialog:Boolean = true

    private fun initData() {
        title = arguments?.getString(TITLE)
        content = arguments?.getString(CONTENT)
        btnText = arguments?.getString(BUTTON_TEXT)
        cancelableDialog = arguments?.getBoolean(CANCELABLE) ?: true
        initView()
        initAction()
    }

    private fun initAction() {
        binding?.btnOk?.setOnClickListener {
            okButtonListener()
        }
    }

    override fun getTheme(): Int {
        return R.style.WhiteRoundedDialog
    }

    fun setListener(listener:() -> Unit){
        this.okButtonListener = listener
    }



    private fun initView(){
        if (title != null){
            binding?.tvTitle?.visibility = View.VISIBLE
            binding?.tvTitle?.text = title
        }else{
            binding?.tvTitle?.visibility = View.GONE
        }

        if (content != null){
            binding?.tvContent?.visibility = View.VISIBLE
            binding?.tvContent?.text = content
        }else{
            binding?.tvContent?.visibility = View.GONE
        }

        binding?.btnOk?.text = btnText ?: "OK"
        dialog?.setCancelable(cancelableDialog)
        dialog?.setCanceledOnTouchOutside(cancelableDialog)
    }
}