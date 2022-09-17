package com.app.kerja_mudah.base

//abstract class BaseViewState<T> {
//    var data:T ?= null
//    set(value) {
//        error = null
//        state = STATE.SUCCESS
//        field = value
//    }
//
//    var error:String ?= null
//    set(value){
//        state = STATE.FAILED
//        field = value
//    }
//
//    var state:STATE ?= null
//}

data class BaseViewState<T> (
    var data:T ?= null,
    var error:String ?= null,
    var BaseState:BaseState ?= null
)

enum class BaseState{
    IDLE, LOADING, SUCCESS, FAILED
}