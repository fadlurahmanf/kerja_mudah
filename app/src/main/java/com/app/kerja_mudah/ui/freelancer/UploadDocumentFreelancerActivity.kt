package com.app.kerja_mudah.ui.freelancer

import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.utilities.PermissionUtilities
import com.app.kerja_mudah.data.repository.freelancer.FreelancerRepository
import com.app.kerja_mudah.databinding.ActivityUploadDocumentFreelancerBinding
import com.app.kerja_mudah.di.component.FreelancerComponent
import com.app.kerja_mudah.ui.core.CameraIdCardActivity
import com.app.kerja_mudah.ui.core.ExampleCameraActivity
import com.bumptech.glide.Glide
import java.io.File
import javax.inject.Inject

class UploadDocumentFreelancerActivity : BaseActivity<ActivityUploadDocumentFreelancerBinding>(ActivityUploadDocumentFreelancerBinding::inflate) {

    companion object{
        const val ID_CARD = 0
        const val SELFIE_WITH_ID_CARD = 1
    }

    private var REQUEST_CODE = 0

    private var idCardPath:String ?= null
    private var selfieIDCardPath:String ?= null

    override fun initSetup() {
        initData()
        refreshUI()
        initAction()
    }

    private fun initData() {
        idCardPath = repository.freelancerIdCard
        selfieIDCardPath = repository.freelancerSelfieIdCard
        refreshUI()
    }

    private fun goToExampleCameraActivity(){
        if (PermissionUtilities().checkSelfPermission(this, android.Manifest.permission.CAMERA)){
            val intent = Intent(this, ExampleCameraActivity::class.java)
            if (REQUEST_CODE == ID_CARD){
                intent.putExtra(ExampleCameraActivity.TYPE, ExampleCameraActivity.ID_CARD_TYPE)
            }else{
                intent.putExtra(ExampleCameraActivity.TYPE, ExampleCameraActivity.SELFIE_WITH_ID_CARD_TYPE)
            }
            exampleLauncher.launch(intent)
        }else{
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun initAction() {
        binding?.llIdCard?.setOnClickListener {
            REQUEST_CODE = ID_CARD
            goToExampleCameraActivity()
        }

        binding?.llSelfieIdCard?.setOnClickListener {
            REQUEST_CODE = SELFIE_WITH_ID_CARD
            goToExampleCameraActivity()
        }

        binding?.btnNext?.setOnClickListener {
            if (isButtonEnabled()){
                saveData()
                val intent = Intent(this, RegisterFreelancerSummaryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun saveData(){
        repository.freelancerIdCard = idCardPath
        repository.freelancerSelfieIdCard = selfieIDCardPath
    }

    private fun isButtonEnabled():Boolean{
        return idCardPath?.isNotEmpty() == true && selfieIDCardPath?.isNotEmpty() == true
    }

    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (!it){
            showOkDialog(
                title = "Oops..",
                content = "kerjamudah. need camera & storage permission",
            )
        }else{
            val intent = Intent(this, ExampleCameraActivity::class.java)
            if (REQUEST_CODE == ID_CARD){
                intent.putExtra(ExampleCameraActivity.TYPE, ExampleCameraActivity.ID_CARD_TYPE)
            }else{
                intent.putExtra(ExampleCameraActivity.TYPE, ExampleCameraActivity.SELFIE_WITH_ID_CARD_TYPE)
            }
            exampleLauncher.launch(intent)
        }
    }



    private val exampleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            val intent = Intent(this, CameraIdCardActivity::class.java)
            intent.putExtra(CameraIdCardActivity.TYPE, if (REQUEST_CODE == ID_CARD) CameraIdCardActivity.ID_CARD else CameraIdCardActivity.SELFIE_ID_CARD)
            cameraIdCardLauncher.launch(intent)
        }
    }

    private val cameraIdCardLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            val path = it.data?.getStringExtra(CameraIdCardActivity.PATH_RESULT)
            if (path != null){
                if (REQUEST_CODE == ID_CARD){
                    idCardPath = path
                }else if (REQUEST_CODE == SELFIE_WITH_ID_CARD){
                    selfieIDCardPath = path
                }
                refreshUI()
            }
        }
    }

    private fun refreshUI(){
        if (idCardPath?.isEmpty() == false && File(idCardPath?:"").exists()){
            binding?.ivArrowIdCard?.visibility = View.GONE
            binding?.ivCheckIdCard?.visibility = View.VISIBLE
            binding?.ivIdCard?.visibility = View.VISIBLE
            Glide.with(binding!!.ivIdCard).load(idCardPath)
                .into(binding!!.ivIdCard)
        }else{
            binding?.ivArrowIdCard?.visibility = View.VISIBLE
            binding?.ivCheckIdCard?.visibility = View.GONE
            binding?.ivIdCard?.visibility = View.GONE
        }

        if (selfieIDCardPath?.isEmpty() == false && File(selfieIDCardPath?:"").exists()){
            binding?.ivArrowSelfieIdCard?.visibility = View.GONE
            binding?.ivCheckSelfieIdCard?.visibility = View.VISIBLE
            binding?.ivSelfieIdCard?.visibility = View.VISIBLE
            Glide.with(binding!!.ivSelfieIdCard).load(selfieIDCardPath)
                .into(binding!!.ivSelfieIdCard)
        }else{
            binding?.ivArrowSelfieIdCard?.visibility = View.VISIBLE
            binding?.ivCheckSelfieIdCard?.visibility = View.GONE
            binding?.ivSelfieIdCard?.visibility = View.GONE
        }

        refreshButton(isButtonEnabled())
    }

    private fun refreshButton(enabled:Boolean){
        binding?.btnNext?.setButtonEnabled(enabled)
    }

    @Inject
    lateinit var repository: FreelancerRepository

    private lateinit var component: FreelancerComponent
    override fun inject() {
        component = appComponent.freelancerComponent().create()
        component.inject(this)
    }

}