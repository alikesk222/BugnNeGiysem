package com.example.outfitly.domain.model

data class HourlyForecast(
    val hour: Int,
    val temperature: Double,
    val feelsLike: Double,
    val condition: WeatherCondition,
    val windSpeed: Double,
    val rainProbability: Int,
    val timestamp: Long
)

data class DailyForecast(
    val morning: HourlyForecast?,
    val noon: HourlyForecast?,
    val evening: HourlyForecast?,
    val night: HourlyForecast?
) {
    fun getTimeSlots(): List<TimeSlotForecast> {
        return listOfNotNull(
            morning?.let { TimeSlotForecast(TimeSlot.MORNING, it) },
            noon?.let { TimeSlotForecast(TimeSlot.NOON, it) },
            evening?.let { TimeSlotForecast(TimeSlot.EVENING, it) },
            night?.let { TimeSlotForecast(TimeSlot.NIGHT, it) }
        )
    }
}

data class TimeSlotForecast(
    val slot: TimeSlot,
    val forecast: HourlyForecast
)

enum class TimeSlot(val displayName: String, val emoji: String, val startHour: Int, val endHour: Int) {
    MORNING("Sabah", "🌅", 6, 11),
    NOON("Öğlen", "☀️", 11, 17),
    EVENING("Akşam", "🌆", 17, 21),
    NIGHT("Gece", "🌙", 21, 6)
}
