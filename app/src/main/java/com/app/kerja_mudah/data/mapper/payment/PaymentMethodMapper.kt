package com.app.kerja_mudah.data.mapper.payment

import android.content.Context
import com.app.kerja_mudah.data.model.payment.PaymentMethodModel
import com.app.kerja_mudah.data.response.payment.PaymentMethodResponse
import java.lang.Exception
import javax.inject.Inject

class PaymentMethodMapper @Inject constructor(
    var context: Context
) {
    fun mapFromListResponseToListModel(list: ArrayList<PaymentMethodResponse>):ArrayList<PaymentMethodModel>{
        try {
            val map: HashMap<String, Boolean> = hashMapOf()
            val newList:ArrayList<PaymentMethodModel> = arrayListOf()
            list.sortedBy {
                it.type
            }.forEach { response ->
                if (!map.containsKey(response.type)){
                    map[response.type?:""] = true
                    newList.add(PaymentMethodModel(
                        paymentMethodName = if (response.type == "e-wallet"){
                            "E-Wallet"
                        } else if (response.type == "bank-transfer"){
                            "Bank Transfer"
                        } else if (response.type == "outlet"){
                            "Outlet"
                        } else {
                            "Lainnya"
                        },
                    ))
                }
                newList.add(mapFromResponseToModel(response))
            }
            return newList
        }catch (e:Exception){
            return arrayListOf()
        }
    }

    fun mapFromResponseToModel(response: PaymentMethodResponse):PaymentMethodModel{
        return PaymentMethodModel(
            paymentMethodName = null,
            method = response
        )
    }
}