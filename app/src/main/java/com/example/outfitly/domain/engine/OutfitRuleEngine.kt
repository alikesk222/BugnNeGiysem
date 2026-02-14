package com.example.outfitly.domain.engine

import com.example.outfitly.domain.model.Outfit
import com.example.outfitly.domain.model.Weather
import com.example.outfitly.utils.Constants
import javax.inject.Inject

class OutfitRuleEngine @Inject constructor() {
    
    fun selectBestOutfit(outfits: List<Outfit>, weather: Weather): Outfit? {
        if (outfits.isEmpty()) return null
        
        val scoredOutfits = outfits.map { outfit ->
            outfit to calculateScore(outfit, weather)
        }.sortedByDescending { it.second }
        
        return scoredOutfits.firstOrNull()?.first
    }
    
    fun getRecommendationTips(weather: Weather): List<String> {
        val tips = mutableListOf<String>()
        
        // Temperature-based tips
        when {
            weather.temperature < 0 -> {
                tips.add("Layer up! It's freezing outside.")
                tips.add("Don't forget your gloves and beanie.")
            }
            weather.temperature < 10 -> {
                tips.add("A warm coat is essential today.")
            }
            weather.temperature > 30 -> {
                tips.add("Stay hydrated in this heat!")
                tips.add("Light colors will keep you cooler.")
            }
        }
        
        // Feels like vs actual temperature
        if (weather.feelsLike < weather.temperature - 3) {
            tips.add("It feels colder than it looks - add an extra layer.")
        }
        
        // Rain tips
        if (weather.isRaining) {
            tips.add("Grab waterproof shoes and an umbrella!")
        }
        
        // Wind tips
        if (weather.windSpeed > Constants.WIND_THRESHOLD_KMH) {
            tips.add("It's windy - consider a windbreaker.")
        }
        
        return tips
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
