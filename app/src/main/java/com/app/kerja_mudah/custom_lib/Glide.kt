package com.app.kerja_mudah.custom_lib

import com.app.kerja_mudah.core.extension.toCacheKeyFromPublicVideo
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.Headers
import java.net.URL


 /**
 * GOALS === > TO CACHE IMAGE NETWORK FROM DYNAMIC URL
 * */
class CustomGlideCacheKey : GlideUrl {
    constructor(url: String?) : super(url) {}
    constructor(url: String?, headers: Headers?) : super(url, headers) {}
    constructor(url: URL?) : super(url) {}
    constructor(url: URL?, headers: Headers?) : super(url, headers) {}

     override fun getCacheKey(): String {
         val url = toStringUrl()
         val urlFromExtension = getCacheKeyFromExtension(url) ?: return url
         val urlFromCache = getCacheKeyFromPath(urlFromExtension) ?: return url

         return urlFromCache

//         return if (url.contains(".jpg?") && url.contains("/chat/", ignoreCase = true)) {
//            val splitUrl = url.split(".jpg?").first().split("/chat/").last()
//            splitUrl
//        } else if (url.contains(".jpeg?") && url.contains("/chat/", ignoreCase = true)){
//            val splitUrl = url.split(".jpeg?").first().split("/chat/").last()
//            splitUrl
//        } else {
//            url
//        }
    }

     private fun getCacheKeyFromExtension(url: String):String?{
         return if (url.contains(".jpg?", ignoreCase = true)){
             url.split(".jpg?").first()
         } else if(url.contains(".jpeg?", ignoreCase = true)){
             url.split(".jpeg?").first()
         } else {
             null
         }
     }

     private fun getCacheKeyFromPath(url: String):String?{
         return if (url.contains("/chat/", ignoreCase = true)){
             url.split("/chat/").last()
         }else if(url.contains("/id_card/", ignoreCase = true)){
             url.split("/id_card/").last()
         }else{
             null
         }
     }
}

class CustomGlidePublicUrl(var url: String?) : GlideUrl(url) {
    override fun getCacheKey(): String {
        val result = url?.toCacheKeyFromPublicVideo() ?: super.getCacheKey()
        return result
    }
}