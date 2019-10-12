package com.dicoding.racode.jakartasweatherprediction.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class WeatherResponse (
    @SerializedName("cod")
    @Expose
    var cod: String? = null,
    @SerializedName("message")
    @Expose
    var message: Int? = null,
    @SerializedName("cnt")
    @Expose
    var cnt: Int? = null,
    @SerializedName("list")
    @Expose
    var list: List<Listt>? = null,
    @SerializedName("city")
    @Expose
    var city: City? = null
    )

data class Listt (
    @SerializedName("dt")
    @Expose
    var dt: Long,
    @SerializedName("main")
    @Expose
    var main: Main? = null,
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>,
    @SerializedName("dt_txt")
    @Expose
    var dtTxt: String? = null
)

data class City (

    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null,
    @SerializedName("country")
    @Expose
    var country: String? = null,
    @SerializedName("timezone")
    @Expose
    var timezone: Int? = null,
    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null,
    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null

)

class Coord {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null
    @SerializedName("lon")
    @Expose
    var lon: Double? = null
}

data class Main (
    @SerializedName("temp")
    @Expose
    var temp: Double,
    @SerializedName("temp_min")
    @Expose
    var tempMin: Double? = null,
    @SerializedName("temp_max")
    @Expose
    var tempMax: Double? = null,
    @SerializedName("pressure")
    @Expose
    var pressure: Int? = null,
    @SerializedName("sea_level")
    @Expose
    var seaLevel: Int? = null,
    @SerializedName("grnd_level")
    @Expose
    var grndLevel: Double? = null,
    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null,
    @SerializedName("temp_kf")
    @Expose
    var tempKf: Double? = null
)

class Weather {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("main")
    @Expose
    var main: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("icon")
    @Expose
    var icon: String? = null

}