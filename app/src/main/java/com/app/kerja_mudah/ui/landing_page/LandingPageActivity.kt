package com.app.kerja_mudah.ui.landing_page

import android.content.Intent
import android.content.res.Configuration
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.data.repository.AppCoreRepository
import com.app.kerja_mudah.databinding.ActivityLandingPageBinding
import com.app.kerja_mudah.di.component.LandingPageComponent
import com.app.kerja_mudah.ui.auth.LoginActivity
import com.app.kerja_mudah.ui.home.HomeActivity
import com.app.kerja_mudah.ui.landing_page.adapter.BannerAdapter
import com.app.kerja_mudah.ui.landing_page.widget.ChooseLanguageDialog
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LandingPageActivity : BaseActivity<ActivityLandingPageBinding>(ActivityLandingPageBinding::inflate) {

    override fun initSetup() {
        if (appCoreRepository.codeLanguage == null){
            initDialogLanguage()
        }
        initAdapter()
        initAction()
    }

    private lateinit var bannerAdapter: BannerAdapter
    var bannerList:ArrayList<String> = arrayListOf("sa", "asgaga", "gaga")
    private fun initAdapter() {
        bannerAdapter = BannerAdapter(bannerList)
        binding?.vpBanner?.adapter = bannerAdapter
        binding?.vpBanner?.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        binding?.vpBanner?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                refreshIndicator(position)
                super.onPageSelected(position)
            }
        })
        setupIndicator(0)
    }

    var tes = arrayOfNulls<ImageView>(bannerList.size)
    private fun setupIndicator(currentIndex: Int){
        for (i in tes.indices){
            if (i != currentIndex){
                val view = ImageView(applicationContext)
                val lp = LinearLayout.LayoutParams(30, 30)
                lp.setMargins(10, 0, 10, 0)
                view.layoutParams = lp
                view.background = ContextCompat.getDrawable(this, R.drawable.yellow_indicator_inactive)
                tes[i] = view
            }else{
                val view = ImageView(applicationContext)
                val lp = LinearLayout.LayoutParams(135, 30)
                lp.setMargins(10, 0, 10, 0)
                view.layoutParams = lp
                view.background = ContextCompat.getDrawable(this, R.drawable.yellow_indicator_active)
                tes[i] = view
            }
        }

        for (i in 0 until bannerList.size){
            binding?.llIndicator?.addView(tes[i])
        }
    }

    private fun refreshIndicator(currentIndex: Int){
        for (i in 0 until (binding?.llIndicator?.childCount?:0)){
            if (i != currentIndex){
                val view = binding?.llIndicator?.getChildAt(i) as ImageView
                val lp = LinearLayout.LayoutParams(30, 30)
                lp.setMargins(10, 0, 10, 0)
                view.layoutParams = lp
                view.background = ContextCompat.getDrawable(this, R.drawable.yellow_indicator_inactive)
            }else{
                val view = binding?.llIndicator?.getChildAt(i) as ImageView
                val lp = LinearLayout.LayoutParams(135, 30)
                lp.setMargins(10, 0, 10, 0)
                view.layoutParams = lp
                view.background = ContextCompat.getDrawable(this, R.drawable.yellow_indicator_active)
            }
        }
    }

    @Inject
    lateinit var appCoreRepository: AppCoreRepository

    private fun initAction() {
        binding?.btnLogin?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding?.btnDiscoverJob?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLocale(code:String) {
        val locale = Locale(code)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        @Suppress("DEPRECATION")
        baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
        recreate()
    }

    private  var chooseLanguageDialog: ChooseLanguageDialog ?= null
    private fun initDialogLanguage() {
        chooseLanguageDialog = ChooseLanguageDialog()
        chooseLanguageDialog?.setCallBack(object : ChooseLanguageDialog.CallBack{
            override fun onClicked(code: String) {
                appCoreRepository.codeLanguage = code
                setLocale(code)
                recreate()
                chooseLanguageDialog?.dismiss()
                chooseLanguageDialog = null
            }
        })
        chooseLanguageDialog?.show(supportFragmentManager, ChooseLanguageDialog::class.java.simpleName)
    }

    private lateinit var component: LandingPageComponent
    override fun inject() {
        component = appComponent.landingPageComponent().create()
        component.inject(this)
    }

}