package com.app.kerja_mudah.data.repository.freelancer

import android.content.Context
import com.app.kerja_mudah.base.BasePreference
import com.app.kerja_mudah.core.constant.ParamsKeySP
import com.app.kerja_mudah.data.model.freelancer.FreelancerCategoryModel
import com.app.kerja_mudah.data.response.core.AdsResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FreelancerRepository @Inject constructor(
    context: Context
) : BasePreference(context){

    var listFreelancer:ArrayList<FreelancerResponse> ?= null

    var listAds:ArrayList<AdsResponse> ?= null

    //for register freelancer flow
    var freelancerCategory: FreelancerCategoryModel ?= null
        get() {
            field = getData(ParamsKeySP.REGISTER_FREELANCER_CATEGORY, FreelancerCategoryModel::class.java)
            return field
        }
        set(value) {
            field = if (value != null) {
                saveData(ParamsKeySP.REGISTER_FREELANCER_CATEGORY, value)
                value
            } else {
                clearData(ParamsKeySP.REGISTER_FREELANCER_CATEGORY)
                null
            }
        }

    var freelancerName:String ?= null
        get() {
            field = getString(ParamsKeySP.REGISTER_FREELANCER_NAME)
            return field
        }
        set(value) {
            field = if (value != null){
                saveString(ParamsKeySP.REGISTER_FREELANCER_NAME, value)
                value
            }else{
                clearData(ParamsKeySP.REGISTER_FREELANCER_NAME)
                null
            }
        }

    var freelancerDescription:String ?= null
        get() {
            field = getString(ParamsKeySP.REGISTER_FREELANCER_DESCRIPTION)
            return field
        }
        set(value) {
            field = if (value != null){
                saveString(ParamsKeySP.REGISTER_FREELANCER_DESCRIPTION, value)
                value
            }else{
                clearData(ParamsKeySP.REGISTER_FREELANCER_DESCRIPTION)
                null
            }
        }

    var highlightPhotoFreelancer:ArrayList<String> ?= null
        get() {
            field = getListString(ParamsKeySP.REGISTER_FREELANCER_HIGHLIGHT_PHOTO)
            return field
        }
        set(value) {
            field = if (value != null){
                saveListData(ParamsKeySP.REGISTER_FREELANCER_HIGHLIGHT_PHOTO, value)
                value
            }else{
                clearData(ParamsKeySP.REGISTER_FREELANCER_HIGHLIGHT_PHOTO)
                null
            }
        }

    var highlightVideoFreelancer:ArrayList<String> ?= null
        get() {
            field = getListString(ParamsKeySP.REGISTER_FREELANCER_HIGHLIGHT_VIDEO)
            return field
        }
        set(value) {
            field = if (value != null){
                saveListData(ParamsKeySP.REGISTER_FREELANCER_HIGHLIGHT_VIDEO, value)
                value
            }else{
                clearData(ParamsKeySP.REGISTER_FREELANCER_HIGHLIGHT_VIDEO)
                null
            }
        }

    var freelancerIdCard:String ? = null
        get() {
            field = getString(ParamsKeySP.REGISTER_FREELANCER_ID_CARD)
            return field
        }
        set(value) {
            field = if (value != null){
                saveString(ParamsKeySP.REGISTER_FREELANCER_ID_CARD, value)
                value
            }else{
                clearData(ParamsKeySP.REGISTER_FREELANCER_ID_CARD)
                null
            }
        }

    var freelancerSelfieIdCard:String ? = null
        get() {
            field = getString(ParamsKeySP.REGISTER_FREELANCER_SELFIE_ID_CARD)
            return field
        }
        set(value) {
            field = if (value != null){
                saveString(ParamsKeySP.REGISTER_FREELANCER_SELFIE_ID_CARD, value)
                value
            }else{
                clearData(ParamsKeySP.REGISTER_FREELANCER_SELFIE_ID_CARD)
                null
            }
        }
}