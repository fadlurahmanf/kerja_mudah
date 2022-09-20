package com.app.kerja_mudah.ui.core

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityCropImageBinding
import com.isseiaoki.simplecropview.CropImageView
import com.isseiaoki.simplecropview.callback.CropCallback
import java.io.File
import java.io.FileOutputStream

class CropImageActivity : BaseActivity<ActivityCropImageBinding>(ActivityCropImageBinding::inflate) {
    private val TAG = CropImageActivity::class.java.simpleName

    companion object{
        const val PATH = "PATH"
        const val IS_LOCAL = "IS_LOCAL"

        const val PATH_RESULT = "PATH_RESULT"
    }

    private var path = ""
    private var isLocal = true

    private var file:File ?= null
    private var uri:Uri ?= null

    override fun initSetup() {
        initData()
        initCropView()
        initAction()
    }

    private fun initAction() {
        binding?.btnRetook?.setOnClickListener {
            onBackPressed()
        }
        binding?.btnCrop?.setOnClickListener {
            cropImage()
        }
    }

    private fun initCropView() {
        binding?.civ?.setImageURI(uri)
        binding?.civ?.setCropMode(CropImageView.CropMode.RATIO_16_9)
    }

    private fun initData() {
        path = intent.getStringExtra(PATH) ?: ""
        if (path.isEmpty()){
            showToast(this, "File is not exist", Toast.LENGTH_SHORT)
            finish()
            return
        }

        if (isLocal){
            file = File(path)
            uri = Uri.fromFile(file)
        }

        if (uri == null){
            showToast(this, "File is not exist", Toast.LENGTH_SHORT)
            finish()
            return
        }
    }

    override fun inject() {

    }

    private fun cropImage(){
        showBasicLoadingDialog()
        binding?.civ!!.crop(uri).execute(object : CropCallback{
            override fun onError(e: Throwable?) {
                Log.e(TAG, "cropImage: onError -> ${e?.message}")
                dismissBasicLoadingDialog()
                showToast(this@CropImageActivity, "Failed to crop image", Toast.LENGTH_SHORT)
            }

            override fun onSuccess(cropped: Bitmap?) {
                dismissBasicLoadingDialog()
                if(cropped == null){
                    showToast(this@CropImageActivity, "Failed to crop image", Toast.LENGTH_SHORT)
                    finish()
                    return
                }
                val file = File(cacheDir, "/kerja_mudah_idcard_cropped_${System.currentTimeMillis()}.png")
                val fileOutputStream = FileOutputStream(file)
                cropped.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
                intent.putExtra(PATH_RESULT, file.path)
                setResult(RESULT_OK, intent)
                finish()
            }

        })
    }

}