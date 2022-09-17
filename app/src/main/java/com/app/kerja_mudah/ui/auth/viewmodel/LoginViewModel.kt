package com.app.kerja_mudah.ui.auth.viewmodel

import android.util.Log
import android.util.Log.ASSERT
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.entity.auth.AuthEntity
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.ui.auth.state.LoginState
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    var authEntity:AuthEntity,
    var authRepository: AuthRepository
) : BaseViewModel() {
    private var loginStateData = LoginState()

    private var _loginState = MutableLiveData<LoginState>()
    val loginState:LiveData<LoginState> get() = _loginState

    fun login(email:String, password:String){
        var body = JsonObject()
        body.addProperty("email", email)
        body.addProperty("password", password)
        loginStateData.state = BaseState.LOADING
        _loginState.postValue(loginStateData)
        disposable().add(authEntity.login(body).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        loginStateData.state = BaseState.SUCCESS
                        loginStateData.data = it
                        authRepository.accessToken = it.data?.accessToken
                        authRepository.myProfile = it.data?.profile
                        _loginState.postValue(loginStateData)
                    }else{
                        authRepository.accessToken = null
                        authRepository.myProfile = null
                        loginStateData.state = BaseState.FAILED
                        loginStateData.errorMessage = it.message
                        _loginState.postValue(loginStateData)
                    }
                },
                {
                    Log.wtf("tes", "saasa")
                    loginStateData.state = BaseState.FAILED
                    loginStateData.errorMessage = it.message
                    _loginState.postValue(loginStateData)
                },
                {}
            ))
    }

    fun loginGoogle(email:String, googleId:String){
        val body = JsonObject()
        body.addProperty("email", email)
        body.addProperty("google_id", googleId)
        loginStateData.state = BaseState.LOADING
        _loginState.postValue(loginStateData)
        disposable().add(authEntity.googleLogin(body).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        loginStateData.state = BaseState.SUCCESS
                        loginStateData.data = it
                        _loginState.postValue(loginStateData)
                    }else{
                        loginStateData.state = BaseState.FAILED
                        loginStateData.errorMessage = it.message
                        _loginState.postValue(loginStateData)
                    }
                },
                {
                    loginStateData.state = BaseState.FAILED
                    loginStateData.errorMessage = it.message
                    _loginState.postValue(loginStateData)
                },
                {}
            ))
    }
}