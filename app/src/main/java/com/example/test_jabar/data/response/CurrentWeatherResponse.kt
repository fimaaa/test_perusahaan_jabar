package com.example.test_jabar.data.response

import com.example.test_jabar.data.model.*
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(

    @field:SerializedName("coord")
    val coord: Coord? = null,

    @field:SerializedName("weather")
    val listWeather: MutableList<WeatherItem>? = null,

    @field:SerializedName("base")
    val base: String? = null,

    @field:SerializedName("main")
    val main: Main? = null,

    @field:SerializedName("visibility")
    val visibility: Int? = null,

    @field:SerializedName("wind")
    val wind: Wind? = null,

    @field:SerializedName("clouds")
    val clouds: Clouds? = null,

    @field:SerializedName("dt")
    val dt: Int? = null,

    @field:SerializedName("sys")
    val sys: Sys? = null,

    @field:SerializedName("timezone")
    val timeZone: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("cod")
    val cod: Int? = null

)