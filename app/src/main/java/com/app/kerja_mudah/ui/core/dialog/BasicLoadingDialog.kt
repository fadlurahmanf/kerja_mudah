package com.app.kerja_mudah.ui.core.dialog

import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.base.BaseDialog
import com.app.kerja_mudah.databinding.DialogBasicLoadingBinding
import com.app.kerja_mudah.databinding.DialogLoadingBinding
import com.app.kerja_mudah.di.component.CoreComponent

class BasicLoadingDialog:BaseDialog<DialogBasicLoadingBinding>(DialogBasicLoadingBinding::inflate) {

    companion object{
        const val CANCELABLE = "CANCELABLE"
    }

    private var cancelableDialog:Boolean = false

    override fun inject() {

    }

    override fun setup() {
        cancelableDialog = arguments?.getBoolean(CANCELABLE) ?: false
        dialog?.setCancelable(cancelableDialog)
        dialog?.setCanceledOnTouchOutside(cancelableDialog)
    }
}