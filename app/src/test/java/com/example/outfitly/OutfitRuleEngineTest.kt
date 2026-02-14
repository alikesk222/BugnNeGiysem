package com.example.outfitly

import com.example.outfitly.domain.engine.OutfitRuleEngine
import com.example.outfitly.domain.engine.TemperatureCategory
import com.example.outfitly.domain.model.Gender
import com.example.outfitly.domain.model.Outfit
import com.example.outfitly.domain.model.Weather
import com.example.outfitly.domain.model.WeatherCondition
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class OutfitRuleEngineTest {

    private lateinit var ruleEngine: OutfitRuleEngine

    @Before
    fun setup() {
        ruleEngine = OutfitRuleEngine()
    }

    @Test
    fun `temperature category returns FREEZING for below 0`() {
        assertEquals(TemperatureCategory.FREEZING, ruleEngine.getTemperatureCategory(-5.0))
    }

    @Test
    fun `temperature category returns COLD for 0 to 10`() {
        assertEquals(TemperatureCategory.COLD, ruleEngine.getTemperatureCategory(5.0))
    }

    @Test
    fun `temperature category returns COOL for 10 to 18`() {
        assertEquals(TemperatureCategory.COOL, ruleEngine.getTemperatureCategory(15.0))
    }

    @Test
    fun `temperature category returns WARM for 18 to 25`() {
        assertEquals(TemperatureCategory.WARM, ruleEngine.getTemperatureCategory(22.0))
    }

    @Test
    fun `temperature category returns HOT for above 25`() {
        assertEquals(TemperatureCategory.HOT, ruleEngine.getTemperatureCategory(30.0))
    }

    @Test
    fun `selectBestOutfit returns null for empty list`() {
        val weather = createWeather(20.0)
        assertNull(ruleEngine.selectBestOutfit(emptyList(), weather))
    }

    @Test
    fun `getRecommendationTips includes rain tip when raining`() {
        val weather = createWeather(15.0, isRaining = true)
        val tips = ruleEngine.getRecommendationTips(weather)
        assertTrue(tips.any { it.contains("umbrella", ignoreCase = true) || it.contains("waterproof", ignoreCase = true) })
    }

    @Test
    fun `getRecommendationTips includes wind tip when windy`() {
        val weather = createWeather(15.0, windSpeed = 30.0)
        val tips = ruleEngine.getRecommendationTips(weather)
        assertTrue(tips.any { it.contains("wind", ignoreCase = true) })
    }

    private fun createWeather(
        temp: Double,
        feelsLike: Double = temp,
        isRaining: Boolean = false,
        windSpeed: Double = 10.0
    ) = Weather(
        temperature = temp,
        feelsLike = feelsLike,
        condition = if (isRaining) WeatherCondition.RAIN else WeatherCondition.CLEAR,
        windSpeed = windSpeed,
        humidity = 50,
        cityName = "Test City",
        isRaining = isRaining
    )
}
