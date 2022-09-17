package com.app.kerja_mudah.ui

import android.content.Intent
import android.util.Log
import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.databinding.ActivitySplashBinding
import com.app.kerja_mudah.di.component.CoreComponent
import com.app.kerja_mudah.ui.core.BaseEmptyActivity
import com.app.kerja_mudah.ui.example.activity.ExampleActivity
import com.app.kerja_mudah.ui.home.HomeActivity
import com.app.kerja_mudah.ui.landing_page.LandingPageActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule


class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    companion object{
        val TAG = SplashActivity::class.java.simpleName
    }

    lateinit var component:CoreComponent
    
    @Inject
    lateinit var authRepository: AuthRepository


    override fun initSetup() {
        Timer().schedule(1000){
            if (authRepository.isLoggedIn){
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
                handleDeepLink()
            }else{
                val intent = Intent(this@SplashActivity, LandingPageActivity::class.java)
                startActivity(intent)
                finish()
                handleDeepLink()
            }
        }
//        val options = PusherOptions()
//        options.setCluster("ap1");
//
//        val pusher = Pusher("5a83c2c40bcef92021ee", options)
//
//        pusher.connect(object : ConnectionEventListener {
//            override fun onConnectionStateChange(change: ConnectionStateChange) {
//                Log.i("Pusher", "State changed from ${change.previousState} to ${change.currentState}")
//            }
//
//            override fun onError(
//                message: String,
//                code: String,
//                e: Exception
//            ) {
//                Log.i("Pusher", "There was a problem connecting! code ($code), message ($message), exception($e)")
//            }
//        }, ConnectionState.ALL)
//
//        val channel = pusher.subscribe("status-liked")
//        channel.bind("status-liked-event") { event ->
//            Log.i("Pusher","Received event with data: $event")
//        }
    }

    private fun handleDeepLink() {
        val intentData = intent.data
        Log.d("handleDeepLink", "${intentData?.path}")
        if(intentData?.path?.contains("/confirmation-registration/", ignoreCase = true) == true){
            val tokenUser = intentData.lastPathSegment
            val token = intentData.getQueryParameter("token")
            val intent = Intent(this, BaseEmptyActivity::class.java)
            intent.putExtra(BaseEmptyActivity.TYPE, "CONFIRMATION-REGISTRATION")
            val objects = JSONObject()
            objects.put("token", token)
            objects.put("token_user", tokenUser)
            intent.putExtra(BaseEmptyActivity.BUNDLE, Gson().toJson(objects))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun inject() {
        component = (applicationContext as BaseApp).applicationComponent.coreComponent().create()
        component.inject(this)
    }
}