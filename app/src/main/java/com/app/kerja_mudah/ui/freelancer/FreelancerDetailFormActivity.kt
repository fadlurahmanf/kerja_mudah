package com.app.kerja_mudah.ui.freelancer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.constant.EditEvent
import com.app.kerja_mudah.core.constant.Permission
import com.app.kerja_mudah.core.utilities.PermissionUtilities
import com.app.kerja_mudah.core.utilities.RxBus
import com.app.kerja_mudah.data.repository.freelancer.FreelancerRepository
import com.app.kerja_mudah.databinding.ActivityFreelancerDetailFormBinding
import com.app.kerja_mudah.di.component.FreelancerComponent
import com.app.kerja_mudah.ui.core.GalleryActivity
import com.app.kerja_mudah.ui.core.adapter.ImageAdapter
import com.app.kerja_mudah.ui.core.adapter.VideoAdapter
import javax.inject.Inject

class FreelancerDetailFormActivity : BaseActivity<ActivityFreelancerDetailFormBinding>(ActivityFreelancerDetailFormBinding::inflate) {
    private val TAG = FreelancerDetailFormActivity::class.java.simpleName
    companion object{
        const val REQUEST_IMAGE = 0
        const val REQUEST_VIDEO = 1
        const val IS_EDIT = "IS_EDIT"
    }
    private var photos:ArrayList<String> = arrayListOf()
    private var videos:ArrayList<String> = arrayListOf()
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var videoAdapter: VideoAdapter

    private var isEdit:Boolean = false

    @Inject
    lateinit var repository: FreelancerRepository

    private var REQUEST_CODE : Int = 0

    override fun initSetup() {
        isEdit = intent.getBooleanExtra(IS_EDIT, false)

        binding?.etFreelancerName?.addTextChangedListener(textWatcher)
        binding?.etDescription?.addTextChangedListener(textWatcher)

        initData()
        initView()
        initAction()
        initAdapter()
        refreshButton(isButtonEnabled())
        initBus()
    }

    private fun initData() {
        binding?.tvCategory?.text = repository.freelancerCategory?.name ?: ""
        binding?.etFreelancerName?.setText(repository.freelancerName ?: "")
        binding?.etDescription?.setText(repository.freelancerDescription ?: "")
        photos.clear()
        photos.addAll(repository.highlightPhotoFreelancer ?: arrayListOf())
        videos.clear()
        videos.addAll(repository.highlightVideoFreelancer?: arrayListOf())
    }

    private fun initView() {}

    private fun initBus(){
        RxBus.listen(EditEvent::class.java).subscribe {
            initData()
        }
    }

    private var textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            refreshButton(isButtonEnabled())
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            refreshButton(isButtonEnabled())
        }

        override fun afterTextChanged(s: Editable?) {
            refreshButton(isButtonEnabled())
        }

    }

    private fun initAdapter() {
        imageAdapter = ImageAdapter(photos)
        imageAdapter.enableRemoved(true)
        imageAdapter.setCallBack(object : ImageAdapter.CallBack{
            override fun onClicked(image: String) {}
            override fun onRemoved(image: String) {
                super.onRemoved(image)
                photos.remove(image)
                imageAdapter.notifyDataSetChanged()
            }
        })
        binding?.rvPhoto?.adapter = imageAdapter

        videoAdapter = VideoAdapter(videos)
        videoAdapter.setCallBack(object : VideoAdapter.CallBack{
            override fun onClicked(video: String) {}
        })
        binding?.rvVideo?.adapter = videoAdapter
    }

    private fun initAction() {
        binding?.btnAddPhoto?.ivThumbnail?.setOnClickListener {
            REQUEST_CODE = REQUEST_IMAGE
            checkPermission()
        }

        binding?.btnAddVideo?.ivThumbnail?.setOnClickListener {
            REQUEST_CODE = REQUEST_VIDEO
            checkPermission()
        }

        binding?.btnNext?.setOnClickListener {
            if (isButtonEnabled()){
                if (!isEdit){
                    saveData()
                    val intent = Intent(this, UploadDocumentFreelancerActivity::class.java)
                    startActivity(intent)
                }else{
                    saveData()
                    RxBus.publish(EditEvent())
                    onBackPressed()
                }
            }
        }
    }

    private fun saveData() {
        repository.freelancerName = binding?.etFreelancerName?.text?.toString() ?: ""
        repository.freelancerDescription = binding?.etDescription?.text?.toString() ?: ""
        repository.highlightPhotoFreelancer = photos
        repository.highlightVideoFreelancer = videos
    }

    private fun checkPermission(){
        if (PermissionUtilities().checkMultiplePermission(this, Permission.GALLERY_PERMISSIONS)){
            goToGalleryActivity()
        }else{
            permissionLauncher.launch(Permission.GALLERY_PERMISSIONS)
        }
    }

    private fun goToGalleryActivity(){
        if (REQUEST_CODE == REQUEST_IMAGE){
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra(GalleryActivity.IS_MULTIPLE_SELECT, true)
            intent.putExtra(GalleryActivity.TOOK_PHOTO, true)
            intent.putExtra(GalleryActivity.TOOK_VIDEO, false)
            intent.putExtra(GalleryActivity.USE_CAMERA, false)
            addPhotoLauncher.launch(intent)
        }else if (REQUEST_CODE == REQUEST_VIDEO){
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra(GalleryActivity.IS_MULTIPLE_SELECT, true)
            intent.putExtra(GalleryActivity.TOOK_PHOTO, false)
            intent.putExtra(GalleryActivity.TOOK_VIDEO, true)
            intent.putExtra(GalleryActivity.USE_CAMERA, false)
            addVideoLauncher.launch(intent)
        }
    }

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        if (it.containsValue(false)){
            showOkDialog(
                title = "Oops..",
                content = "kerjamudah. need your permission to access storage"
            )
        }else{
            goToGalleryActivity()
        }
    }

    private val addVideoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            if(it.data?.getStringExtra(GalleryActivity.ACTION) == "gallery" && !it.data?.getStringArrayListExtra(
                    GalleryActivity.PATH_RESULT).isNullOrEmpty()){
                val list:ArrayList<String> = it.data?.getStringArrayListExtra(GalleryActivity.PATH_RESULT) ?: arrayListOf()
                videos.addAll(list)
                videoAdapter.notifyDataSetChanged()
                refreshButton(isButtonEnabled())
            }else{
                Log.d(TAG, "addVideoLauncher: ${it.data?.getStringExtra(GalleryActivity.ACTION)} & ${it.data?.getStringArrayListExtra(
                    GalleryActivity.PATH_RESULT)?.size}")
            }
        }
    }

    private val addPhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            if(it.data?.getStringExtra(GalleryActivity.ACTION) == "gallery" && !it.data?.getStringArrayListExtra(
                    GalleryActivity.PATH_RESULT).isNullOrEmpty()){
                val list:ArrayList<String> = it.data?.getStringArrayListExtra(GalleryActivity.PATH_RESULT) ?: arrayListOf()
                photos.addAll(list)
                imageAdapter.notifyDataSetChanged()
                refreshButton(isButtonEnabled())
            }else{
                Log.d(TAG, "addPhotoLauncher: ${it.data?.getStringExtra(GalleryActivity.ACTION)} & ${it.data?.getStringArrayListExtra(
                    GalleryActivity.PATH_RESULT)?.size}")
            }
        }
    }

    private fun isButtonEnabled():Boolean{
        return (binding?.etFreelancerName?.text?.isNotEmpty() == true
                && binding?.etDescription?.text?.isNotEmpty() == true) && photos.isNotEmpty() && videos.isNotEmpty()
    }

    private fun refreshButton(value:Boolean){
        binding?.btnNext?.setButtonEnabled(value)
        binding?.btnNext?.backgroundTintList = if (value){
            AppCompatResources.getColorStateList(this, R.color.green)
        }else{
            AppCompatResources.getColorStateList(this, R.color.light_grey)
        }
    }

    private lateinit var component: FreelancerComponent
    override fun inject() {
        component = appComponent.freelancerComponent().create()
        component.inject(this)
    }

}