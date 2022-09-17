package com.app.kerja_mudah.base

import androidx.viewbinding.ViewBinding
import com.app.kerja_mudah.ui.core.callback.PrepareCameraCallBack

abstract class BaseCamera<VB:ViewBinding>(
    var inflateActivity: InflateActivity<VB>
):BaseActivity<VB>(inflateActivity) {

    private lateinit var prepareCameraCallBack: PrepareCameraCallBack

    override fun initSetup() {
        prepareCameraCallBack.initCamera()
    }

    override fun inject() {

    }

    fun setPrepareCameraCallBack(callBack: PrepareCameraCallBack){
        this.prepareCameraCallBack = callBack
    }
}