package com.app.kerja_mudah.ui.landing_page.widget

import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseDialog
import com.app.kerja_mudah.databinding.DialogChooseLanguageBinding

class ChooseLanguageDialog : BaseDialog<DialogChooseLanguageBinding>(DialogChooseLanguageBinding::inflate) {
    override fun inject() {}

    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    override fun setup() {
        dialog?.setCancelable(false)

        binding?.ivEn?.setOnClickListener {
            this.callBack.onClicked("en")
        }
        binding?.tvEn?.setOnClickListener {
            this.callBack.onClicked("en")
        }

        binding?.ivId?.setOnClickListener {
            this.callBack.onClicked("id")
        }

        binding?.tvId?.setOnClickListener {
            this.callBack.onClicked("id")
        }
    }

    interface CallBack{
        fun onClicked(id:String)
    }

    override fun getTheme(): Int {
        return R.style.WhiteRoundedDialog
    }
}