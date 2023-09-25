package ru.netology.readjson

import com.google.gson.annotations.SerializedName

data class Pins (
    @SerializedName("id") val id : Int,
    @SerializedName("service") val service : String,
    @SerializedName("coordinates") val coordinates : Coordinates
)
data class JsonKotlin (
    @SerializedName("services") val services : List<String>,
    @SerializedName("pins") val pins : List<Pins>
)
data class Coordinates (
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double
)