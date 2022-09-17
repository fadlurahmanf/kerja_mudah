package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.payment.CheckoutActivity
import com.app.kerja_mudah.ui.payment.PaymentLoadingActivity
import com.app.kerja_mudah.ui.payment.SelectPaymentMethodActivity
import dagger.Subcomponent

@Subcomponent
interface PaymentComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():PaymentComponent
    }

    fun inject(activity:CheckoutActivity)
    fun inject(activity:SelectPaymentMethodActivity)
    fun inject(activity:PaymentLoadingActivity)
}