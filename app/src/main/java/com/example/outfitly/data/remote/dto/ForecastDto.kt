package com.example.outfitly.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list") val list: List<ForecastItemDto>,
    @SerializedName("city") val city: CityDto
)

data class ForecastItemDto(
    @SerializedName("dt") val timestamp: Long,
    @SerializedName("main") val main: MainDto,
    @SerializedName("weather") val weather: List<WeatherDto>,
    @SerializedName("wind") val wind: WindDto,
    @SerializedName("pop") val rainProbability: Double,
    @SerializedName("dt_txt") val dateText: String
)

data class CityDto(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("timezone") val timezone: Int
)
