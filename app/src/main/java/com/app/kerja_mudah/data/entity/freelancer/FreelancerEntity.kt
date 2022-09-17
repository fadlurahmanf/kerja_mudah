package com.app.kerja_mudah.data.entity.freelancer

import androidx.annotation.Nullable
import com.app.kerja_mudah.base.network.AbstractNetwork
import com.app.kerja_mudah.data.api.freelancer.FreelancerApi
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class FreelancerEntity @Inject constructor(
    var authRepository: AuthRepository
) : AbstractNetwork<FreelancerApi>() {
    override fun getApi(): Class<FreelancerApi> {
        return FreelancerApi::class.java
    }

    fun getAllFreelancer() = networkService(30).getAllFreelancer()

    fun getAllPagingFreelancer(page:Int? = null) = networkService(30).getAllPagingFreelancer(page)

    fun getDetailFreelancer(id:Int) = networkService(30).getDetailFreelancer(id)

    fun getAllReviewByFreelancerId(id: Int) = networkService(30).getAllReviewByFreelancerId(id)

    fun registerAsFreelancer(bearerToken:String, part: List<MultipartBody.Part>) = networkService(180).registerAsFreelancer(bearerToken, part)

    fun getListReelByFreelancer() = networkService(30).getListReelsByFreelancer(authRepository.accessToken?:"")
}