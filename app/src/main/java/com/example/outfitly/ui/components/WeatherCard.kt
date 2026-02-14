package com.example.outfitly.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
            .height(200.dp),
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Rounded.LocationOn,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = weather.cityName,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = getConditionTextTr(weather.condition),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                    
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${weather.temperature.toInt()}°",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Hissedilen ${weather.feelsLike.toInt()}°",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WeatherInfoChip(
                        icon = Icons.Rounded.Air,
                        value = "${weather.windSpeed.toInt()} km/s",
                        modifier = Modifier.weight(1f)
                    )
                    WeatherInfoChip(
                        icon = Icons.Rounded.WaterDrop,
                        value = "%${weather.humidity}",
                        modifier = Modifier.weight(1f)
                    )
                    WeatherInfoChip(
                        icon = Icons.Rounded.Thermostat,
                        value = getConditionShortTr(weather.condition),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherInfoChip(
    icon: ImageVector,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(alpha = 0.2f),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }
    }
}

private fun getWeatherGradient(condition: WeatherCondition): List<Color> {
    return when (condition) {
        WeatherCondition.CLEAR -> listOf(Color(0xFFF59E0B), Color(0xFFEF4444))
        WeatherCondition.CLOUDY -> listOf(Color(0xFF64748B), Color(0xFF475569))
        WeatherCondition.RAIN -> listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))
        WeatherCondition.SNOW -> listOf(Color(0xFF06B6D4), Color(0xFF0284C7))
        WeatherCondition.THUNDERSTORM -> listOf(Color(0xFF475569), Color(0xFF1E293B))
        WeatherCondition.MIST -> listOf(Color(0xFF94A3B8), Color(0xFF64748B))
        WeatherCondition.UNKNOWN -> listOf(Primary, PrimaryVariant)
    }
}

private fun getConditionTextTr(condition: WeatherCondition): String {
    return when (condition) {
        WeatherCondition.CLEAR -> "Açık ve Güneşli"
        WeatherCondition.CLOUDY -> "Bulutlu"
        WeatherCondition.RAIN -> "Yağmurlu"
        WeatherCondition.SNOW -> "Karlı"
        WeatherCondition.THUNDERSTORM -> "Gök Gürültülü Fırtına"
        WeatherCondition.MIST -> "Sisli"
        WeatherCondition.UNKNOWN -> "Belirsiz"
    }
}

private fun getConditionShortTr(condition: WeatherCondition): String {
    return when (condition) {
        WeatherCondition.CLEAR -> "Açık"
        WeatherCondition.CLOUDY -> "Bulutlu"
        WeatherCondition.RAIN -> "Yağmur"
        WeatherCondition.SNOW -> "Kar"
        WeatherCondition.THUNDERSTORM -> "Fırtına"
        WeatherCondition.MIST -> "Sis"
        WeatherCondition.UNKNOWN -> "—"
    }
}
