package com.app.kerja_mudah.ui.core

import android.content.Intent
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityCameraBinding
import com.otaliastudios.cameraview.*
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.controls.Flash
import com.otaliastudios.cameraview.controls.Mode
import java.io.File
import kotlin.random.Random

class CameraActivity : BaseActivity<ActivityCameraBinding>(ActivityCameraBinding::inflate) {

    companion object{
        const val IS_TAKING_PHOTO = "IS_TAKING_PHOTO"
        const val CAMERA_FACING = "CAMERA_FACING"

        const val PATH_RESULT = "PATH_RESULT"
    }

    private var isTakingPhoto : Boolean = false
    private var cameraFacing : Facing = Facing.BACK

    private var pathResult:String ?= null

    override fun initSetup() {
        initData()
        initCamera()
        initAction()
    }

    private fun turnOffFlash(){
        binding?.cameraView?.flash = Flash.OFF
        binding?.ivFlash?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
    }

    private fun turnOnFlash(){
        binding?.cameraView?.flash = Flash.ON
        binding?.ivFlash?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))
    }

    private fun initAction() {
        binding?.ivReverse?.setOnClickListener {
            binding?.cameraView?.facing = if (cameraFacing == Facing.BACK) {
                cameraFacing = Facing.FRONT
                Facing.FRONT
            } else {
                cameraFacing = Facing.BACK
                Facing.BACK
            }
        }
        binding?.tvPhoto?.setOnClickListener {
            setPhoto()
        }

        binding?.ivTakePhoto?.setOnClickListener {
            binding?.cameraView?.takePicture()
        }

        binding?.tvVideo?.setOnClickListener {
            setVideo()
        }

        binding?.ivFlash?.setOnClickListener {
            if (binding?.cameraView?.flash == Flash.ON){
                turnOffFlash()
            }else{
                turnOnFlash()
            }
        }
    }

    private fun setPhoto(){
        binding?.tvPhoto?.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)))
        binding?.tvVideo?.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        binding?.cameraView?.mode = Mode.PICTURE
        isTakingPhoto = true

        binding?.ivTakePhoto?.visibility = View.VISIBLE
        binding?.ivTakeVideo?.visibility = View.GONE
    }

    private fun setVideo(){
        binding?.tvPhoto?.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        binding?.tvVideo?.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)))
        binding?.cameraView?.mode = Mode.VIDEO
        isTakingPhoto = false

        binding?.ivTakePhoto?.visibility = View.GONE
        binding?.ivTakeVideo?.visibility = View.VISIBLE
    }

    private fun initCamera() {
        turnOffFlash()
        binding?.cameraView?.setLifecycleOwner(this)
        binding?.cameraView?.open()
        binding?.cameraView?.addCameraListener(object : CameraListener(){
            override fun onCameraOpened(options: CameraOptions) {
                Log.d("Camera", "Camera Opened")
            }


            override fun onPictureTaken(result: PictureResult) {
                Log.d("Camera", "On Picture Taken")
                val file = File(cacheDir, "/bead_${Random.nextInt(Int.MAX_VALUE)}.jpg")
                result.toFile(file, FileCallback {
                    if (it != null){
                        pathResult = it.path
                        val intent = Intent(this@CameraActivity, PreviewMultipleImageActivity::class.java)
                        intent.putStringArrayListExtra(PreviewMultipleImageActivity.LIST_MEDIA, arrayListOf(it.path))
                        previewImageLauncher.launch(intent)
                    }
                })
            }

            override fun onVideoTaken(result: VideoResult) {
                Log.d("Camera", "On Video Taken")
                super.onVideoTaken(result)
            }
        })
    }

    private val previewImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_CANCELED){
            Log.d("Preview Image Launcher", ": RESULT_CANCELED")
        }else if (it.resultCode == RESULT_OK){
            Log.d("Preview Image Launcher", ": RESULT_OK")
            intent.putExtra(PATH_RESULT, pathResult)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun initData(){
        isTakingPhoto = intent.getBooleanExtra(IS_TAKING_PHOTO, true)
    }

    override fun onPause() {
        super.onPause()
        binding?.cameraView?.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.cameraView?.destroy()
    }

    override fun inject() {

    }
}