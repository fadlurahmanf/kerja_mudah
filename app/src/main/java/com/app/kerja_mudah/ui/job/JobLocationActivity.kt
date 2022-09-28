package com.app.kerja_mudah.ui.job

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.data.response.job.JobDetailResponse
import com.app.kerja_mudah.databinding.ActivityJobLocationBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class JobLocationActivity : BaseActivity<ActivityJobLocationBinding>(ActivityJobLocationBinding::inflate), OnMapReadyCallback {

    companion object{
        const val JOB = "JOB"
    }

    private var jobDetail:JobDetailResponse ?= null

    override fun initSetup() {
        jobDetail = intent.getParcelableExtra(JOB)
        initMapFragment()
        if (jobDetail == null){
            showOkDialog(
                title = "Oops..", content = "Job is required", cancelable = false
            )
            return
        }
    }

    private lateinit var mapFragment:SupportMapFragment
    private lateinit var mGoogleMap:GoogleMap
    private fun initMapFragment() {
        mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)
    }

    override fun inject() {

    }

    override fun onMapReady(p0: GoogleMap) {
        mGoogleMap = p0
        addMarker(jobDetail?.latitude?.toDoubleOrNull()?:0.0, jobDetail?.longitude?.toDoubleOrNull()?:0.0)
        Handler().postDelayed({
            moveCamera(jobDetail?.latitude?.toDoubleOrNull()?:0.0, jobDetail?.longitude?.toDoubleOrNull()?:0.0, 17f)
        }, 1000)
    }

    private fun addMarker(latitude:Double, longitude:Double){
        val markerOption = MarkerOptions()

        markerOption.position(LatLng(latitude, longitude))
            .title(jobDetail?.name?:"")

        val imageBitmap = BitmapFactory.decodeResource(resources, R.drawable.il_marker)
        val bitmap = Bitmap.createScaledBitmap(imageBitmap, 100, 100, false)
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)

        markerOption.icon(bitmapDescriptor)
        mGoogleMap.addMarker(markerOption)
    }

    private fun moveCamera(latitude:Double, longitude:Double, zoom:Float){
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoom))
    }

}