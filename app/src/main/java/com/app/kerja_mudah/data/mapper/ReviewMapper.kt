package com.app.kerja_mudah.data.mapper

import com.app.kerja_mudah.data.model.review.ReviewDetailModel
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerReviewResponse
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse

class ReviewMapper {
    companion object{
        fun toReviewModel(detail:FreelancerResponse.Review.Detail?):ReviewDetailModel{
            if (detail == null){
                return ReviewDetailModel()
            }
            return ReviewDetailModel(
                id = detail.id,
                star = detail.star,
                comment = detail.comment,
                from = detail.from,
                commentPhoto = detail.commentPhoto,
                freelancerName = detail.freelancer?.freelancerName,
                freelancerComment = detail.freelancerComment,
                freelancerId = detail.freelancer?.id,
                freelancerPhoto = detail.freelancer?.photo,
                createdAt = detail.createdAt,
                updatedAt = detail.updatedAt
            )
        }

        fun toReviewModel(detail:FreelancerReviewResponse.Detail):ReviewDetailModel{
            if (detail == null){
                return ReviewDetailModel()
            }
            return ReviewDetailModel(
                id = detail.id,
                star = detail.star,
                comment = detail.comment,
                from = detail.from,
                commentPhoto = detail.commentPhoto,
                freelancerName = detail.freelancer?.freelancerName,
                freelancerComment = detail.freelancerComment,
                freelancerId = detail.freelancer?.id,
                freelancerPhoto = detail.freelancer?.photo,
                createdAt = detail.createdAt,
                updatedAt = detail.updatedAt
            )
        }
    }
}