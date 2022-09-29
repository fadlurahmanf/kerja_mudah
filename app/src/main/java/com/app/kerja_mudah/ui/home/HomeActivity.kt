package com.app.kerja_mudah.ui.home

import android.os.Handler
import android.widget.Toast
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.extension.setTransparentStatusBar
import com.app.kerja_mudah.databinding.ActivityHomeBinding
import com.app.kerja_mudah.di.component.HomeComponent
import com.app.kerja_mudah.ui.home.viewmodel.HomeViewModel
import com.app.kerja_mudah.ui.home.widget.tab.HomeTabFragment
import com.app.kerja_mudah.ui.home.widget.tab.MyProfileTabFragment
import com.app.kerja_mudah.ui.home.widget.tab.SearchingTabFragment
import nl.joery.animatedbottombar.AnimatedBottomBar
import java.util.*
import java.util.concurrent.TimeUnit
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
    }

    private fun showToolTip(){
//        val tooltip = Tooltip.Builder(binding!!.ivMyProfile)
//            .setText(R.string.dummy_text_5_word)
//            .setGravity(Gravity.TOP)
//            .setMargin(10f)
//            .setBackgroundColor(ContextCompat.getColor(this, R.color.green))
//            .setTextColor(ContextCompat.getColor(this, R.color.white))
//            .setCancelable(false)
//            .setPadding(20)
//            .setOnClickListener {
//                it.dismiss()
//            }
//            .build()
//
//        Handler().postDelayed({
//            tooltip.show()
//        }, 5000)
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
        binding?.bottomNav?.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                if (lastIndex != newIndex){
                    index = newIndex
                    replaceFragment(newIndex)
                }
            }
        })
    }

    @Inject
    lateinit var viewModel:HomeViewModel

    private fun initFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl, HomeTabFragment()).commit()
        replaceFragment(index)
        binding?.bottomNav?.selectTabAt(0, animate = false)
    }

    private fun replaceFragment(index:Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(index){
            0 -> {
                this.index = index
                transaction.replace(R.id.fl, HomeTabFragment()).commit()
            }
            1 -> {
                this.index = index
                transaction.replace(R.id.fl, SearchingTabFragment()).commit()
            }
            2 -> {
                this.index = index
                transaction.replace(R.id.fl, MyProfileTabFragment()).commit()
            }
        }
    }

    private var dateOnTapBack:Date ?= null
    override fun onBackPressed() {
        if (dateOnTapBack == null){
            dateOnTapBack = Calendar.getInstance().time
            showToast(this,"Tap again to exit", Toast.LENGTH_SHORT)
            handler.postDelayed(backRunnable, 2000)
        }else{
            val dateCompare = Calendar.getInstance().time
            val diff = dateCompare.time - dateOnTapBack!!.time
            val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diff)
            if (diffInSeconds <= 2){
                super.onBackPressed()
            }
        }
    }

    private var handler = Handler()
    private var backRunnable = object : Runnable{
        override fun run() {
            dateOnTapBack = null
            handler.removeCallbacks(this)
        }
    }

    private lateinit var homeComponent:HomeComponent
    override fun inject() {
        homeComponent = appComponent.homeComponent().create()
        homeComponent.inject(this)
    }
}