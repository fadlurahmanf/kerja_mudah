package com.app.kerja_mudah.ui.freelancer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.repository.freelancer.FreelancerRepository
import com.app.kerja_mudah.databinding.ActivityRegisterFreelancerLoadingBinding
import com.app.kerja_mudah.di.component.FreelancerComponent
import com.app.kerja_mudah.ui.freelancer.viewmodel.FreelancerViewModel
import com.app.kerja_mudah.ui.home.HomeActivity
import java.io.File
import javax.inject.Inject

class RegisterFreelancerLoadingActivity : BaseActivity<ActivityRegisterFreelancerLoadingBinding>(ActivityRegisterFreelancerLoadingBinding::inflate) {
    override fun initSetup() {
        handler.postDelayed(callback, 1000)
        initAction()
        viewModel.registerFreelancer(
            repository.freelancerCategory?.name?:"",
            repository.freelancerName?:"",
            repository.freelancerDescription?:"",
            ArrayList<File>(repository.highlightPhotoFreelancer?.map {
                File(it)
            }?: listOf()),
            ArrayList<File>(repository.highlightVideoFreelancer?.map {
                File(it)
            }?: listOf()),
            File(repository.freelancerIdCard?:""),
            File(repository.freelancerSelfieIdCard?:"")
        )
        initObserver()
    }

    private fun initObserver() {
        viewModel.registerFreelancerState.observe(this){
            if (it.status == BaseState.SUCCESS){
                handler.removeCallbacks(callback)
                binding?.tvLoading?.text = "Success Uploading Data"
                binding?.btnNext?.visibility = View.VISIBLE
            }else if (it.status == BaseState.FAILED){
                handler.removeCallbacks(callback)
                binding?.btnNext?.visibility = View.INVISIBLE
                showOkDialog("Oops..", it.error, buttonText = "Back", cancelable = false){
                    val intent = Intent(this, RegisterFreelancerSummaryActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            }
        }
    }

    @Inject
    lateinit var repository: FreelancerRepository

    @Inject
    lateinit var viewModel:FreelancerViewModel

    private fun initAction() {
        binding?.btnNext?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(HomeActivity.FRAGMENT, 2)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private val handler = Handler()

    private var totalDot:Int = 0
    private var callback = object : Runnable {
        override fun run() {
            if (totalDot >= 3){
                totalDot = 0
            }
            var loadingText = "uploading"
            for (i in 0..totalDot){
                loadingText += "."
            }
            binding?.tvLoading?.text = loadingText
            handler.postDelayed(this, 1000)
            totalDot++
        }
    }

    private lateinit var component: FreelancerComponent
    override fun inject() {
        component = appComponent.freelancerComponent().create()
        component.inject(this)
    }

}