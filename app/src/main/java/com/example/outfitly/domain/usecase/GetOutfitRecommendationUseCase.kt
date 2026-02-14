package com.example.outfitly.domain.usecase

import com.example.outfitly.data.repository.OutfitRepository
import com.example.outfitly.domain.engine.OutfitRuleEngine
import com.example.outfitly.domain.model.Gender
import com.example.outfitly.domain.model.Outfit
import com.example.outfitly.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOutfitRecommendationUseCase @Inject constructor(
    private val outfitRepository: OutfitRepository,
    private val ruleEngine: OutfitRuleEngine
) {
    
    operator fun invoke(
        weather: Weather,
        gender: Gender
    ): Flow<OutfitRecommendation> {
        val isWindy = weather.windSpeed > 25.0
        
        return outfitRepository.getOutfitsForWeather(
            gender = gender,
            temperature = weather.temperature.toInt(),
            isRaining = weather.isRaining,
            isWindy = isWindy
        ).map { outfits ->
            val bestOutfit = ruleEngine.selectBestOutfit(outfits, weather)
            val tips = ruleEngine.getRecommendationTips(weather)
            
            OutfitRecommendation(
                outfit = bestOutfit,
                alternativeOutfits = outfits.filter { it != bestOutfit }.take(3),
                tips = tips
            )
        }
    }
}

data class OutfitRecommendation(
    val outfit: Outfit?,
    val alternativeOutfits: List<Outfit>,
    val tips: List<String>
)
