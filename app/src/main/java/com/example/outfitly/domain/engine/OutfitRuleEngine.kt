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
        val feelsLike = weather.feelsLike
        
        // Termal profil bilgisi
        if (thermalProfile != ThermalProfile.NORMAL) {
            val profilAdj = if (thermalProfile.offset > 0) "daha serin" else "daha sıcak"
            tips.add("Termal profilinize göre ${profilAdj} giyinmeniz öneriliyor.")
        }
        
        // Detaylı sıcaklık bazlı ipuçları
        when {
            adjustedTemp < -10 -> {
                tips.add("Aşırı soğuk! Termal iç çamaşır + kalın mont + kat kat giyinin.")
                tips.add("Eldiven, bere ve atkı şart. Kulak ve el parmakları için dikkatli olun.")
            }
            adjustedTemp < 0 -> {
                tips.add("Dondurucu soğuk! Kalın mont, kaşkol ve bere çok önemli.")
                tips.add("Eldiven almayı unutmayın, termal çorap tercih edin.")
            }
            adjustedTemp < 5 -> {
                tips.add("Oldukça soğuk. Kalın mont ve kat kat giyinmeyi tercih edin.")
                tips.add("Atkı ve bere sizi sıcak tutacaktır.")
            }
            adjustedTemp < 10 -> {
                tips.add("Serin bir gün. Kalın bir ceket veya mont önerilir.")
            }
            adjustedTemp in 10.0..15.0 -> {
                tips.add("Ilıman hava. İnce bir ceket veya hırka yeterli olabilir.")
            }
            adjustedTemp in 15.0..20.0 -> {
                tips.add("Güzel bir hava. Hafif katmanlar ile rahat giyinebilirsiniz.")
            }
            adjustedTemp in 20.0..25.0 -> {
                tips.add("Sıcak bir gün. Pamuklu ve nefes alan kumaşlar tercih edin.")
            }
            adjustedTemp in 25.0..30.0 -> {
                tips.add("Oldukça sıcak! Açık renkli, ince kıyafetler giyinin.")
                tips.add("Bol su için ve güneş kremi sürmeyi unutmayın.")
            }
            adjustedTemp in 30.0..35.0 -> {
                tips.add("Çok sıcak! Keten veya pamuklu açık renkli kıyafetler tercih edin.")
                tips.add("Şapka ve güneş gözlüğü alın, gölgede kalmaya çalışın.")
            }
            adjustedTemp > 35 -> {
                tips.add("Aşırı sıcak! Mümkün olduğunca serin kıyafetler tercih edin.")
                tips.add("Bol su için, güneşten korunun ve açık havada dikkatli olun.")
            }
        }
        
        // Hissedilen vs gerçek sıcaklık farkı
        val tempDiff = weather.temperature - feelsLike
        when {
            tempDiff > 5 -> tips.add("Hava göründüğünden çok daha soğuk hissettiriyor. Fazladan kat giyinin!")
            tempDiff > 3 -> tips.add("Hissedilen sıcaklık daha düşük - bir kat fazla giyinmeyi düşünün.")
            tempDiff < -3 -> tips.add("Hissedilen sıcaklık daha yüksek - bir kat daha az giyinebilirsiniz.")
        }
        
        // Yağmur ipuçları
        if (weather.isRaining) {
            tips.add("Yağmur var! Su geçirmez ayakkabı ve şemsiye almayı unutmayın.")
        }
        
        // Rüzgar ipuçları
        when {
            weather.windSpeed > 50 -> tips.add("Çok şiddetli rüzgar! Dışarı çıkmadan önce dikkatli olun.")
            weather.windSpeed > 30 -> tips.add("Kuvvetli rüzgar bekleniyor. Rüzgarlık veya kalın ceket tercih edin.")
            weather.windSpeed > Constants.WIND_THRESHOLD_KMH -> tips.add("Hafif rüzgar var - ince bir rüzgarlık işe yarayabilir.")
        }
        
        // Nem bazlı ipuçları
        when {
            weather.humidity > 80 && adjustedTemp > 25 -> tips.add("Yüksek nem ve sıcaklık! Nefes alan kumaşlar tercih edin, terletmeyen kıyafetler seçin.")
            weather.humidity < 30 -> tips.add("Hava çok kuru. Dudak nemlendiricisi ve cilt bakımı önerilir.")
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
