package com.example.outfitly.domain.engine

import com.example.outfitly.domain.model.Outfit
import com.example.outfitly.domain.model.ThermalProfile
import com.example.outfitly.domain.model.Weather
import com.example.outfitly.utils.Constants
import javax.inject.Inject

class OutfitRuleEngine @Inject constructor() {
    
    fun selectBestOutfit(
        outfits: List<Outfit>, 
        weather: Weather,
        thermalProfile: ThermalProfile = ThermalProfile.NORMAL
    ): Outfit? {
        if (outfits.isEmpty()) return null
        
        val adjustedWeather = weather.copy(
            temperature = weather.temperature + thermalProfile.offset
        )
        
        val scoredOutfits = outfits.map { outfit ->
            outfit to calculateScore(outfit, adjustedWeather)
        }.sortedByDescending { it.second }
        
        return scoredOutfits.firstOrNull()?.first
    }
    
    fun getRecommendationTips(
        weather: Weather,
        thermalProfile: ThermalProfile = ThermalProfile.NORMAL
    ): List<String> {
        val tips = mutableListOf<String>()
        val adjustedTemp = weather.temperature + thermalProfile.offset
        
        // Sıcaklık bazlı ipuçları
        when {
            adjustedTemp < 0 -> {
                tips.add("Çok soğuk! Sıkı giyinin.")
                tips.add("Eldiven ve bere almayı unutmayın.")
            }
            adjustedTemp < 10 -> {
                tips.add("Bugün kalın bir mont şart.")
            }
            adjustedTemp > 30 -> {
                tips.add("Bol su için, sıcak!")
                tips.add("Açık renkler sizi daha serin tutar.")
            }
        }
        
        // Hissedilen vs gerçek sıcaklık
        if (weather.feelsLike < weather.temperature - 3) {
            tips.add("Hava göründüğünden soğuk - fazladan bir kat ekleyin.")
        }
        
        // Yağmur ipuçları
        if (weather.isRaining) {
            tips.add("Su geçirmez ayakkabı ve şemsiye alın!")
        }
        
        // Rüzgar ipuçları
        if (weather.windSpeed > Constants.WIND_THRESHOLD_KMH) {
            tips.add("Rüzgar var - rüzgarlık düşünün.")
        }
        
        return tips
    }
    
    fun getAdjustedTemperature(temp: Double, thermalProfile: ThermalProfile): Double {
        return temp + thermalProfile.offset
    }
    
    private fun calculateScore(outfit: Outfit, weather: Weather): Int {
        var score = 100
        
        // Base temperature match
        val temp = weather.temperature.toInt()
        if (temp < outfit.minTemp) {
            score -= (outfit.minTemp - temp) * 5
        }
        if (temp > outfit.maxTemp) {
            score -= (temp - outfit.maxTemp) * 5
        }
        
        // Rain compatibility
        if (weather.isRaining && !outfit.rainCompatible) {
            score -= 30
        }
        if (weather.isRaining && outfit.rainCompatible) {
            score += 20
        }
        
        // Wind compatibility
        val isWindy = weather.windSpeed > Constants.WIND_THRESHOLD_KMH
        if (isWindy && !outfit.windCompatible) {
            score -= 20
        }
        if (isWindy && outfit.windCompatible) {
            score += 15
        }
        
        // Feels like adjustment
        if (weather.feelsLike < weather.temperature - 3) {
            // Prefer warmer outfits
            if (outfit.minTemp < temp) {
                score += 10
            }
        }
        
        return score
    }
    
    fun getTemperatureCategory(temp: Double): TemperatureCategory {
        return when {
            temp < 0 -> TemperatureCategory.FREEZING
            temp < 10 -> TemperatureCategory.COLD
            temp < 18 -> TemperatureCategory.COOL
            temp < 25 -> TemperatureCategory.WARM
            else -> TemperatureCategory.HOT
        }
    }
}

enum class TemperatureCategory {
    FREEZING,
    COLD,
    COOL,
    WARM,
    HOT
}
