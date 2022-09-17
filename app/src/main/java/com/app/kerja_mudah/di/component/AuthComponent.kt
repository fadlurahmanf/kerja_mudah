package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.auth.LoginActivity
import com.app.kerja_mudah.ui.auth.RegisterActivity
import com.app.kerja_mudah.ui.auth.RegistrationSuccessActivity
import dagger.Subcomponent

@Subcomponent
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():AuthComponent
    }

    fun inject(loginActivity: LoginActivity)
    fun inject(activity:RegisterActivity)
    fun inject(activity:RegistrationSuccessActivity)
}