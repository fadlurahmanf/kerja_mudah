package com.app.kerja_mudah.data.api.example

import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.example.TestimonialResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ExampleApi {
    @GET("api/testimonial/all")
    fun getTestimonial(): Observable<BaseResponse<List<TestimonialResponse>>>
}