package com.app.kerja_mudah.data.response.weather

import org.simpleframework.xml.*

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
    var issue:Issue? = null,
    @field:ElementList(inline = true)
    var area:List<Area> ?= null
)

@Root(name = "issue", strict = false)
data class Issue @JvmOverloads constructor(
    @field:Element(name = "timestamp")
    var timestamp:String? = null
)

@Root(name = "area", strict = false)
data class Area @JvmOverloads constructor(
    @field:Attribute(name = "latitude")
    @param:Attribute(name = "latitude")
    var latitude:String? = null,

    @field:Attribute(name = "longitude")
    @param:Attribute(name = "longitude")
    var longitude:String? = null,

    @field:Attribute(name = "description")
    @param:Attribute(name = "description")
    var city:String? = null,

    @field:Attribute(name = "domain")
    @param:Attribute(name = "domain")
    var province:String? = null,

    @field:ElementList(inline = true, required = false)
    var parameter:List<Parameter>? = null
)

@Root(name = "parameter", strict = false)
data class Parameter @JvmOverloads constructor(
    @field:Attribute(name = "id")
    @param:Attribute(name = "id")
    var id:String? = null,

    @field:Attribute(name = "description")
    @param:Attribute(name = "description")
    var description:String? = null,

    @field:Attribute(name = "type")
    @param:Attribute(name = "type")
    var type:String? = null,

    @field:ElementList(inline = true)
    var timerange:List<TimeRange>? = null
)

@Root(name = "timerange", strict = false)
data class TimeRange @JvmOverloads constructor(
    @field:Attribute(name = "type")
    @param:Attribute(name = "type")
    var type:String? = null,

    @field:Attribute(name = "datetime")
    @param:Attribute(name = "datetime")
    var datetime:String? = null,

    @field:ElementList(inline = true, required = false, entry = "value")
    var value:List<Value>? = null
)

@Root(name = "value", strict = false)
data class Value @JvmOverloads constructor(
    @field:Attribute(name = "unit")
    @param:Attribute(name = "unit")
    var unit:String? = null,

    @field:Text
    @param:Text
    var value:String? = null
)
