package com.app.kerja_mudah.data.mapper.faq

import com.app.kerja_mudah.data.model.core.FaqModel
import com.app.kerja_mudah.data.response.core.FaqResponse

class FaqMapper {
    fun fromListResponseToListModel(list:ArrayList<FaqResponse>):ArrayList<FaqModel>{
        val map:HashMap<String, Boolean> = hashMapOf()
        val newList:ArrayList<FaqModel> = arrayListOf()
        list.sortedBy { it.category }.forEach {
            if (map[it.category?:""] == true){
                newList.add(FaqModel(faqResponse = it))
            }else{
                if (it.category != ""){
                    map[it.category?:""] = true
                }
                val category = FaqModel(
                    faqCategory = if (it.category?.lowercase() == "general"){
                        "General"
                    } else if (it.category?.lowercase() == "payment"){
                        "Payment"
                    } else if (it.category?.lowercase() == "security"){
                        "Security"
                    } else if(it.category?.lowercase() == "service"){
                        "Service"
                    } else {
                        null
                    }
                )
                if (category.faqCategory != null){
                    newList.add(category)
                    newList.add(FaqModel(faqResponse = it))
                }
            }
        }
        newList.add(
            FaqModel(
                faqCategory = "Others"
            )
        )
        list.forEach {
            if (it.category?.lowercase() == ""){
                newList.add(FaqModel(faqResponse = it))
            }
        }
        return newList
    }
}