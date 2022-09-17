package com.app.kerja_mudah.data.entity.example

import android.content.Context
import com.app.kerja_mudah.base.network.AbstractNetwork
import com.app.kerja_mudah.data.api.example.ExampleApi
import javax.inject.Inject

class ExampleEntity @Inject constructor(
    var context: Context
): AbstractNetwork<ExampleApi>() {
    override fun getApi(): Class<ExampleApi> {
        return ExampleApi::class.java
    }

    fun getTestimonial() = networkService(30).getTestimonial()

}