package com.app.kerja_mudah

import android.app.Application
import com.app.kerja_mudah.di.component.ApplicationComponent
import com.app.kerja_mudah.di.component.DaggerApplicationComponent
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache


class BaseApp : Application() {
    lateinit var applicationComponent: ApplicationComponent
    companion object{
        var simpleCache:SimpleCache ?= null
    }

    override fun onCreate() {
        super.onCreate()
        initInjection()
    }

    private fun initInjection(){
        applicationComponent = DaggerApplicationComponent.factory().create(this)
        applicationComponent.inject(this)

        val leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(90 * 1024 * 1024)
        val databaseProvider: DatabaseProvider = ExoDatabaseProvider(this)

        if (simpleCache == null){
            simpleCache = SimpleCache(cacheDir, leastRecentlyUsedCacheEvictor, databaseProvider)
        }
    }
}