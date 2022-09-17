package com.app.kerja_mudah.ui.core

import android.content.Intent
import android.content.res.ColorStateList
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseCamera
import com.app.kerja_mudah.databinding.ActivityCameraIdCardBinding
import com.app.kerja_mudah.ui.CropImageActivity
import com.app.kerja_mudah.ui.core.callback.PrepareCameraCallBack
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.controls.Flash
import java.io.File


class CameraIdCardActivity : BaseCamera<ActivityCameraIdCardBinding>(ActivityCameraIdCardBinding::inflate),
    PrepareCameraCallBack {

    private val TAG = CameraIdCardActivity::class.java.simpleName

    companion object{
        const val PATH_RESULT = "PATH_RESULT"

        const val TYPE = "TYPE"
        const val SELFIE_ID_CARD = 0
        const val ID_CARD = 1
    }

    private var facing:Facing = Facing.FRONT
    private var type:Int = ID_CARD


    private var flash : Flash = Flash.OFF

    override fun initSetup() {
        setPrepareCameraCallBack(this)
        super.initSetup()
        initData()
        initAction()
    }

    private fun initData() {
        type = intent.getIntExtra(TYPE, ID_CARD)
        facing = if (type == SELFIE_ID_CARD){
            Facing.FRONT
        }else{
            Facing.BACK
        }
    }

    private fun initAction() {
        binding?.ivTakePhoto?.setOnClickListener {
            binding?.cameraView?.takePicture()
        }

        binding?.ivFlash?.setOnClickListener {
            if (flash == Flash.OFF){
                turnOnFlash()
            }else{
                turnOffFlash()
            }
        }
    }

    override fun initCamera() {
        turnOffFlash()
        binding?.cameraView?.setLifecycleOwner(this)
        binding?.cameraView?.open()
        binding?.cameraView?.addCameraListener(cameraListener)
    }

    private fun turnOffFlash(){
        flash = Flash.OFF
        binding?.cameraView?.flash = Flash.OFF
        binding?.ivFlash?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
    }

    private fun turnOnFlash(){
        flash = Flash.ON
        binding?.cameraView?.flash = Flash.ON
        binding?.ivFlash?.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))
    }

    private val cameraListener = object : CameraListener(){
        override fun onCameraOpened(options: CameraOptions) {
            super.onCameraOpened(options)
            Log.d(TAG, "onCameraOpened")
            binding?.cameraView?.facing = facing
        }

        override fun onCameraClosed() {
            turnOffFlash()
            super.onCameraClosed()
        }

        override fun onPictureTaken(result: PictureResult) {
            super.onPictureTaken(result)
            turnOffFlash()
            Log.d(TAG, "onPictureTaken")
            val file = File(cacheDir, "/kerjamudah_idcard_${System.currentTimeMillis()}.png")
            result.toFile(file){
                if (it != null){
                    if (type == ID_CARD){
                        val intent = Intent(this@CameraIdCardActivity, CropImageActivity::class.java)
                        intent.apply {
                            putExtra(CropImageActivity.PATH, file.path)
                            putExtra(CropImageActivity.IS_LOCAL, true)
                        }
                        cropActivityLauncher.launch(intent)
                    }else{
                        intent.putExtra(CameraIdCardActivity.PATH_RESULT, it.path)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }else{
                    showToast(this@CameraIdCardActivity, "File is not exist", Toast.LENGTH_LONG)
                }
            }
        }
    }

    private val cropActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            if (it.data?.getStringExtra(CropImageActivity.PATH_RESULT) != null){
                intent.putExtra(CameraIdCardActivity.PATH_RESULT, it.data?.getStringExtra(CropImageActivity.PATH_RESULT))
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.cameraView?.close()
        binding?.cameraView?.destroy()
    }
}