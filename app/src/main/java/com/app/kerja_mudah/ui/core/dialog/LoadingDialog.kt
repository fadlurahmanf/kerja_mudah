package com.app.kerja_mudah.ui.core.dialog

import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.base.BaseDialog
import com.app.kerja_mudah.databinding.DialogLoadingBinding
import com.app.kerja_mudah.di.component.CoreComponent

class LoadingDialog:BaseDialog<DialogLoadingBinding>(DialogLoadingBinding::inflate) {

    companion object{
        const val CANCELABLE = "CANCELABLE"
    }

    private var cancelableDialog:Boolean = false

    lateinit var component:CoreComponent

    override fun inject() {
        component = (requireActivity().applicationContext as BaseApp).applicationComponent.coreComponent().create()
        component.inject(this)
    }

    override fun setup() {
        cancelableDialog = arguments?.getBoolean(CANCELABLE) ?: false
        dialog?.setCancelable(cancelableDialog)
        dialog?.setCanceledOnTouchOutside(cancelableDialog)
    }
}