package com.app.kerja_mudah.data.response.weather

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "data", strict = false)
data class ProvinceWeatherResponse @JvmOverloads constructor(
    @field:Element(name = "forecast")
    var forecast:Forecast? = null
)

@Root(name = "forecast", strict = false)
data class Forecast @JvmOverloads constructor(
    @field:Attribute(name = "domain")
    @param:Attribute(name = "domain")
    var domain: String? = null,

    @field:Element(name = "issue")
    var issue:Issue? = null
)

@Root(name = "issue", strict = false)
data class Issue @JvmOverloads constructor(
    @field:Element(name = "timestamp")
    var timestamp:String? = null
)
