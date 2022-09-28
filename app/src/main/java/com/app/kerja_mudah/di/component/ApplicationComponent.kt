package com.app.kerja_mudah.di.component

import android.content.Context
import com.app.kerja_mudah.BaseApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [])
interface ApplicationComponent {
    fun inject(app: BaseApp)
    fun exampleComponent():ExampleComponent.Factory
    fun coreComponent():CoreComponent.Factory
    fun landingPageComponent():LandingPageComponent.Factory
    fun authComponent():AuthComponent.Factory
    fun homeComponent():HomeComponent.Factory
    fun freelancerComponent():FreelancerComponent.Factory
    fun serviceComponent():ServiceComponent.Factory
    fun chatComponent():ChatComponent.Factory
    fun paymentComponent():PaymentComponent.Factory
    fun quranComponent():QuranComponent.Factory
    fun jobComponent():JobComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }
}