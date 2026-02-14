package com.example.outfitly.domain.engine

import com.example.outfitly.domain.model.*
import javax.inject.Inject

class RiskAlertEngine @Inject constructor() {
    
    fun analyzeRisks(
        currentWeather: Weather,
        forecast: DailyForecast?
    ): List<RiskAlert> {
        val alerts = mutableListOf<RiskAlert>()
        
        // Ani sıcaklık düşüşü kontrolü
        forecast?.let { daily ->
            val temperatures = daily.getTimeSlots().map { it.forecast.temperature }
            if (temperatures.isNotEmpty()) {
                val maxTemp = temperatures.maxOrNull() ?: 0.0
                val minTemp = temperatures.minOrNull() ?: 0.0
                
                if (maxTemp - minTemp >= 10) {
                    alerts.add(
                        RiskAlert(
                            type = RiskType.TEMPERATURE_DROP,
                            severity = RiskSeverity.HIGH,
                            message = "Gün içinde ${(maxTemp - minTemp).toInt()}°C sıcaklık farkı var",
                            recommendation = "Kat kat giyinmeyi düşün"
                        )
                    )
                }
            }
            
            // Yağmur ihtimali kontrolü
            val maxRainProb = daily.getTimeSlots()
                .maxOfOrNull { it.forecast.rainProbability } ?: 0
            
            if (maxRainProb >= 50) {
                alerts.add(
                    RiskAlert(
                        type = RiskType.RAIN_CHANCE,
                        severity = if (maxRainProb >= 70) RiskSeverity.HIGH else RiskSeverity.MEDIUM,
                        message = "%$maxRainProb yağmur ihtimali",
                        recommendation = "Şemsiye almayı unutma"
                    )
                )
            }
        }
        
        // Yüksek rüzgar kontrolü
        if (currentWeather.windSpeed > 40) {
            alerts.add(
                RiskAlert(
                    type = RiskType.HIGH_WIND,
                    severity = RiskSeverity.HIGH,
                    message = "Çok yüksek rüzgar: ${currentWeather.windSpeed.toInt()} km/h",
                    recommendation = "Rüzgarlık veya kalın mont önerilir"
                )
            )
        } else if (currentWeather.windSpeed > 25) {
            alerts.add(
                RiskAlert(
                    type = RiskType.HIGH_WIND,
                    severity = RiskSeverity.MEDIUM,
                    message = "Yüksek rüzgar: ${currentWeather.windSpeed.toInt()} km/h",
                    recommendation = "Rüzgarlık düşünebilirsin"
                )
            )
        }
        
        // Aşırı soğuk
        if (currentWeather.temperature < -10) {
            alerts.add(
                RiskAlert(
                    type = RiskType.EXTREME_COLD,
                    severity = RiskSeverity.HIGH,
                    message = "Aşırı soğuk: ${currentWeather.temperature.toInt()}°C",
                    recommendation = "Dışarı çıkmadan önce iyi giyinin"
                )
            )
        }
        
        // Aşırı sıcak
        if (currentWeather.temperature > 35) {
            alerts.add(
                RiskAlert(
                    type = RiskType.EXTREME_HEAT,
                    severity = RiskSeverity.HIGH,
                    message = "Aşırı sıcak: ${currentWeather.temperature.toInt()}°C",
                    recommendation = "Bol su için, açık renkler tercih edin"
                )
            )
        }
        
        return alerts.sortedByDescending { it.severity.ordinal }
    }
}
