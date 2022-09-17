package com.app.kerja_mudah.base

import androidx.lifecycle.ViewModel
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel:ViewModel() {
    fun addSubscription(disposable: Disposable) = CompositeDisposable().add(disposable)

    fun disposable() = CompositeDisposable()

    fun pusherOptions() = PusherOptions()
        .setCluster("ap1")

    fun pusher() = Pusher("5a83c2c40bcef92021ee", pusherOptions())
}