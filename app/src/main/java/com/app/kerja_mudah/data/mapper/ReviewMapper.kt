package com.app.kerja_mudah.data.mapper

import com.app.kerja_mudah.data.model.review.ReviewDetailModel
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse

class ReviewMapper {
    public fun toReviewModel(detail:ServiceDetailResponse.Review.Detail?):ReviewDetailModel{
        if (detail == null){
            return ReviewDetailModel()
        }
        return ReviewDetailModel(
            id = detail.id,
            star = detail.star,
            comment = detail.comment,
            createdAt = detail.createdAt,
            from = detail.from
        )
    }

    public fun toReviewModel(detail:FreelancerResponse.Review.Detail?):ReviewDetailModel{
        if (detail == null){
            return ReviewDetailModel()
        }
        return ReviewDetailModel(
            id = detail.id,
            star = detail.star,
            comment = detail.comment,
            createdAt = detail.createdAt,
            from = detail.from
        )
    }
}