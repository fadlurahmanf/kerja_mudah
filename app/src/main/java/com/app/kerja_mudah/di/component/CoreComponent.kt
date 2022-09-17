package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.core.fcm.FcmService
import com.app.kerja_mudah.ui.SplashActivity
import com.app.kerja_mudah.ui.core.BaseEmptyActivity
import com.app.kerja_mudah.ui.core.GalleryActivity
import com.app.kerja_mudah.ui.core.LegalActivity
import com.app.kerja_mudah.ui.core.dialog.LoadingDialog
import dagger.Subcomponent

@Subcomponent
interface CoreComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():CoreComponent
    }

    fun inject(service:FcmService)

    fun inject(activity:SplashActivity)
    fun inject(activity:GalleryActivity)
    fun inject(activity:BaseEmptyActivity)
    fun inject(activity:LegalActivity)
    fun inject(dialog:LoadingDialog)
}