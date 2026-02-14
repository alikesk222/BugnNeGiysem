package com.example.outfitly.domain.model

data class Weather(
    val temperature: Double,
    val feelsLike: Double,
    val condition: WeatherCondition,
    val windSpeed: Double,
    val humidity: Int,
    val cityName: String,
    val isRaining: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

enum class WeatherCondition {
    CLEAR,
    CLOUDY,
    RAIN,
    SNOW,
    THUNDERSTORM,
    MIST,
    UNKNOWN
}
