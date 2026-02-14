package com.example.outfitly.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outfitly.domain.model.Weather
import com.example.outfitly.domain.model.WeatherCondition
import com.example.outfitly.ui.theme.*

@Composable
fun WeatherCard(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    val gradientColors = getWeatherGradient(weather.condition)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(colors = gradientColors)
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "📍 ${weather.cityName}",
                            style = MaterialTheme.typography.titleMedium,
                            color = OnPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = getWeatherEmoji(weather.condition),
                            fontSize = 48.sp
                        )
                    }
                    
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${weather.temperature.toInt()}°",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnPrimary
                        )
                        Text(
                            text = "Feels ${weather.feelsLike.toInt()}°",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnPrimary.copy(alpha = 0.8f)
                        )
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    WeatherInfoChip(
                        icon = "💨",
                        value = "${weather.windSpeed.toInt()} km/h"
                    )
                    WeatherInfoChip(
                        icon = "💧",
                        value = "${weather.humidity}%"
                    )
                    WeatherInfoChip(
                        icon = getConditionIcon(weather.condition),
                        value = weather.condition.name.lowercase().replaceFirstChar { it.uppercase() }
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherInfoChip(
    icon: String,
    value: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = OnPrimary.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                color = OnPrimary
            )
        }
    }
}

private fun getWeatherGradient(condition: WeatherCondition): List<androidx.compose.ui.graphics.Color> {
    return when (condition) {
        WeatherCondition.CLEAR -> listOf(SunnyYellow, WarmOrange)
        WeatherCondition.CLOUDY -> listOf(CloudyGray, CloudyGray.copy(alpha = 0.7f))
        WeatherCondition.RAIN -> listOf(RainyBlue, RainyBlue.copy(alpha = 0.7f))
        WeatherCondition.SNOW -> listOf(FreezingBlue, ColdBlue)
        WeatherCondition.THUNDERSTORM -> listOf(CloudyGray.copy(alpha = 0.8f), RainyBlue)
        WeatherCondition.MIST -> listOf(CloudyGray.copy(alpha = 0.6f), CloudyGray)
        WeatherCondition.UNKNOWN -> listOf(Primary, PrimaryVariant)
    }
}

private fun getWeatherEmoji(condition: WeatherCondition): String {
    return when (condition) {
        WeatherCondition.CLEAR -> "☀️"
        WeatherCondition.CLOUDY -> "☁️"
        WeatherCondition.RAIN -> "🌧️"
        WeatherCondition.SNOW -> "❄️"
        WeatherCondition.THUNDERSTORM -> "⛈️"
        WeatherCondition.MIST -> "🌫️"
        WeatherCondition.UNKNOWN -> "🌤️"
    }
}

private fun getConditionIcon(condition: WeatherCondition): String {
    return when (condition) {
        WeatherCondition.CLEAR -> "☀️"
        WeatherCondition.CLOUDY -> "☁️"
        WeatherCondition.RAIN -> "🌧️"
        WeatherCondition.SNOW -> "❄️"
        WeatherCondition.THUNDERSTORM -> "⛈️"
        WeatherCondition.MIST -> "🌫️"
        WeatherCondition.UNKNOWN -> "🌤️"
    }
}
