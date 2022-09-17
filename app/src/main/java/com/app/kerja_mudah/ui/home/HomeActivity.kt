package com.app.kerja_mudah.ui.home

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.extension.setTransparentStatusBar
import com.app.kerja_mudah.core.receiver.CoreReceiver
import com.app.kerja_mudah.databinding.ActivityHomeBinding
import com.app.kerja_mudah.di.component.HomeComponent
import com.app.kerja_mudah.ui.home.viewmodel.HomeViewModel
import com.app.kerja_mudah.ui.home.widget.tab.HomeTabFragment
import com.app.kerja_mudah.ui.home.widget.tab.MyProfileTabFragment
import com.app.kerja_mudah.ui.home.widget.tab.SearchingTabFragment
import com.tooltip.Tooltip
import javax.inject.Inject

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    companion object{
        const val FRAGMENT = "FRAGMENT"
    }

    private var index:Int = 0

    override fun initSetup() {
        initData()
        initToken()
        initFragment()
        initAction()
        setTransparentStatusBar()
//        showToolTip()
    }

    private fun showToolTip(){
        val tooltip = Tooltip.Builder(binding!!.ivMyProfile)
            .setText(R.string.dummy_text_5_word)
            .setGravity(Gravity.TOP)
            .setMargin(10f)
            .setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .setCancelable(false)
            .setPadding(20)
            .setOnClickListener {
                it.dismiss()
            }
            .build()

        Handler().postDelayed({
            tooltip.show()
        }, 5000)
    }

    private fun initData() {
        index = intent.getIntExtra(FRAGMENT, 0)
        if (index > 2){
            index = 0
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initToken() {
        viewModel.updateToken()
    }

    private fun initAction() {
        binding?.ivHome?.setOnClickListener {
            if (index != 0){
                replaceFragment(0)
                setStatusBarTextColor(false)
            }
        }

        binding?.ivSearch?.setOnClickListener {
            if (index != 1){
                replaceFragment(1)
            }
        }

        binding?.ivMyProfile?.setOnClickListener {
            if (index != 2){
                replaceFragment(2)
                setStatusBarTextColor(true)
            }
        }
    }

    @Inject
    lateinit var viewModel:HomeViewModel

    private fun initFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl, HomeTabFragment()).commit()
        binding?.ivHome?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
        replaceFragment(index)
    }

    private fun replaceFragment(index:Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(index){
            0 -> {
                this.index = index
                transaction.replace(R.id.fl, HomeTabFragment()).commit()
                refreshIcon()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding?.ivHome?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
                }
            }
            1 -> {
                this.index = index
                transaction.replace(R.id.fl, SearchingTabFragment()).commit()
                refreshIcon()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding?.ivSearch?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
                }
            }
            2 -> {
                this.index = index
                transaction.replace(R.id.fl, MyProfileTabFragment()).commit()
                refreshIcon()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding?.ivMyProfile?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
                }
            }
        }
    }

    private fun refreshIcon(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding?.ivHome?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            binding?.ivSearch?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            binding?.ivMyProfile?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
        }
    }

    private lateinit var homeComponent:HomeComponent
    override fun inject() {
        homeComponent = appComponent.homeComponent().create()
        homeComponent.inject(this)
    }
}