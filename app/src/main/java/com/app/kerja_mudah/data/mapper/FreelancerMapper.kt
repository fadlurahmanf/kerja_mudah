package com.app.kerja_mudah.data.mapper

import android.content.Context
import com.app.kerja_mudah.data.model.auth.ProfileModel
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.model.service.ServiceModel
import com.app.kerja_mudah.data.response.auth.ProfileResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse
import javax.inject.Inject


class FreelancerMapper @Inject constructor(
    var context: Context
) {
    fun fromResponseToModel(response: FreelancerResponse):FreelancerModel{
        return FreelancerModel(
            id = response.id,
            freelancerName = response.freelancerName,
            profile = ProfileModel(
                id = response.profile?.id,
                name = null,
                email = response.profile?.email,
                isUser = null,
                isFreelancer = null,
                photo = response.profile?.photo,
            ),
            review = FreelancerModel.Review(
                totalReview = response.review?.totalReview,
                totalStar = response.review?.totalStar,
                data = response.review?.data?.map { detail ->
                    FreelancerModel.Review.Detail(
                        id = detail.id,
                        star = detail.star,
                        comment = detail.comment,
                        commentPhoto = detail.commentPhoto,
                        from = ProfileModel(
                            id = detail.from?.id,
                            name = null,
                            email = detail.from?.email,
                        ),
                        createdAt = detail.createdAt,
                    )
                }
            ),
            highlightText = response.highlightText,
            highlightPhoto = response.highlightPhoto,
            highlightVideo = response.highlightVideo,
            service = FreelancerModel.Service(
                totalService = response.service?.totalService,
                data = ArrayList(response.service?.data?.map { service ->
                    ServiceModel(
                        id = service.id,
                        highlightPhoto = service.highlightPhoto,
                        title = service.title,
                        definition = service.definition,
                        createdAt = service.createdAt
                    )
                }?: arrayListOf())
            ),
            createdAt = response.createdAt,
            updatedAt = response.updatedAt,
        )
    }

    fun fromModelToResponse(model: FreelancerModel):FreelancerResponse{
        return FreelancerResponse(
            id = model.id,
            freelancerName = model.freelancerName,
            profile = ProfileResponse(
                id = model.profile?.id,
                email = model.profile?.email,
                photo = model.profile?.photo,
            ),
            review = FreelancerResponse.Review(
                totalReview = model.review?.totalReview,
                totalStar = model.review?.totalStar,
                data = model.review?.data?.map { detail ->
                    FreelancerResponse.Review.Detail(
                        id = detail.id,
                        star = detail.star,
                        comment = detail.comment,
                        commentPhoto = detail.commentPhoto,
                        from = ProfileResponse(
                            id = detail.from?.id,
                            email = detail.from?.email,
                        ),
                        createdAt = detail.createdAt,
                    )
                }
            ),
            highlightText = model.highlightText,
            highlightPhoto = model.highlightPhoto,
            highlightVideo = model.highlightVideo,
            service = FreelancerResponse.Service(
                totalService = model.service?.totalService,
                data = ArrayList(model.service?.data?.map { service ->
                    ServiceDetailResponse(
                        id = service.id,
                        highlightPhoto = service.highlightPhoto,
                        title = service.title,
                        definition = service.definition,
                        createdAt = service.createdAt
                    )
                }?: arrayListOf())
            ),
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
        )
    }
}