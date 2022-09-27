package com.app.kerja_mudah.base


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewbinding.ViewBinding
import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.di.component.ApplicationComponent
import com.app.kerja_mudah.ui.core.dialog.*
import com.google.android.material.snackbar.Snackbar

typealias InflateActivity<T> = (LayoutInflater) -> T

abstract class BaseActivity<VB:ViewBinding>(
    var inflate:InflateActivity<VB>
):AppCompatActivity() {

    lateinit var appComponent:ApplicationComponent

    private var _binding:VB ?= null
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        initAppComponent()
        inject()
        super.onCreate(savedInstanceState)
        setLayout()
        internalSetup()
        initSetup()
    }

    private fun initAppComponent() {
        appComponent = (applicationContext as BaseApp).applicationComponent
    }

    open fun internalSetup(){}

    abstract fun initSetup()

    private fun setLayout(){
        _binding = inflate.invoke(layoutInflater)
        setContentView(binding?.root)
    }

    abstract fun inject()

    private var loadingDialog:LoadingDialog ?= null
    fun showLoadingDialog(
        cancelable: Boolean = false
    ){
        dismissLoadingDialog()
        if (loadingDialog == null){
            val bundle = Bundle()
            loadingDialog = LoadingDialog()
            bundle.putBoolean(LoadingDialog.CANCELABLE, cancelable)
            loadingDialog?.arguments = bundle
            loadingDialog?.show(supportFragmentManager, LoadingDialog::class.java.simpleName)
        }
    }

    fun dismissLoadingDialog(){
        if (loadingDialog != null){
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }

    private var basicLoadingDialog:BasicLoadingDialog ?= null
    fun showBasicLoadingDialog(
        cancelable: Boolean = false
    ){
        dismissBasicLoadingDialog()
        if (basicLoadingDialog == null){
            val bundle = Bundle()
            basicLoadingDialog = BasicLoadingDialog()
            bundle.putBoolean(BasicLoadingDialog.CANCELABLE, cancelable)
            basicLoadingDialog?.arguments = bundle
            basicLoadingDialog?.show(supportFragmentManager, BasicLoadingDialog::class.java.simpleName)
        }
    }

    fun dismissBasicLoadingDialog(){
        if (basicLoadingDialog != null){
            basicLoadingDialog?.dismiss()
            basicLoadingDialog = null
        }
    }

    fun showSnackBar(message: String?, typeLong:Int?=null){
        Snackbar.make(binding!!.root, message?:"", typeLong ?: Snackbar.LENGTH_LONG).show()
    }

    private var requiredLoginDialog:RequiredLoginDialog ?= null
    fun showRequiredLoginDialog(){
        dismissRequiredLoginDialog()
        requiredLoginDialog = RequiredLoginDialog()
        requiredLoginDialog?.show(supportFragmentManager, RequiredLoginDialog::class.java.simpleName)
    }

    fun dismissRequiredLoginDialog(){
        if (requiredLoginDialog != null){
            requiredLoginDialog?.dismiss()
        }
        requiredLoginDialog = null
    }

    private var okDialog:OkDialog ?= null
    fun showOkDialog(
        title:String ?= null,
        content:String ?= null,
        buttonText:String ?= null,
        cancelable:Boolean ?= null,
        listener:() -> Unit = {
            dismissOkDialog()
        }
    ){
        dismissOkDialog()
        if (okDialog == null){
            val bundle = Bundle()
            okDialog = OkDialog()
            bundle.putString(OkDialog.TITLE, title)
            bundle.putString(OkDialog.CONTENT, content)
            bundle.putString(OkDialog.BUTTON_TEXT, buttonText)
            bundle.putBoolean(OkDialog.CANCELABLE, cancelable?:true)
            okDialog?.arguments = bundle
            okDialog?.setListener(listener)
            okDialog?.show(supportFragmentManager, OkDialog::class.java.simpleName)
        }
    }

    private var confirmDialog:ConfirmDialog ?= null
    fun showConfirmDialog(
        title:String ?= null,
        content:String ?= null,
        negativeText:String ?= null,
        positiveText:String ?= null,
        cancelable:Boolean ?= null,
        negativeListener:() -> Unit = {
            confirmDialog?.dismiss()
        },
        positiveListener:() -> Unit = {
            confirmDialog?.dismiss()
        }
    ){
        dismissConfirmDialog()
        val bundle = Bundle()
        bundle.putString(ConfirmDialog.TITLE, title)
        bundle.putString(ConfirmDialog.CONTENT, content)
        bundle.putString(ConfirmDialog.NEGATIVE_TEXT, negativeText)
        bundle.putString(ConfirmDialog.POSITIVE_TEXT, positiveText)
        bundle.putBoolean(ConfirmDialog.CANCELABLE, cancelable?:true)
        confirmDialog = ConfirmDialog()
        confirmDialog?.arguments = bundle
        confirmDialog?.setNegativeListener(negativeListener)
        confirmDialog?.setPositiveListener(positiveListener)
        confirmDialog?.show(supportFragmentManager, ConfirmDialog::class.java.simpleName)
    }

    fun dismissConfirmDialog(){
        if (confirmDialog != null){
            confirmDialog?.dismiss()
        }
        confirmDialog = null
    }

    fun dismissOkDialog(){
        if (okDialog != null){
            okDialog?.dismiss()
            okDialog = null
        }
    }

    fun setStatusBarTextColor(isLight:Boolean){
        val windowInsetController = ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetController?.isAppearanceLightStatusBars = isLight
    }

    fun showToast(context:Context ?= null,message:String, long:Int){
        val toast = Toast.makeText(context?:this, message, long)
        toast.show()
    }

    fun hideStatusBar(){
        if (Build.VERSION.SDK_INT < 16){
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }else{
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
}