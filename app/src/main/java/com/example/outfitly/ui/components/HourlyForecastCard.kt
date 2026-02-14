package com.example.outfitly.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outfitly.domain.model.Outfit
import com.example.outfitly.domain.model.TimeSlot
import com.example.outfitly.domain.model.TimeSlotForecast
import com.example.outfitly.ui.theme.*

@Composable
fun HourlyForecastCard(
    forecasts: List<TimeSlotForecast>,
    outfitSuggestions: Map<TimeSlot, Outfit?>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "📅 Saatlik Tahmin",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnBackground
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(forecasts) { timeSlotForecast ->
                    TimeSlotCard(
                        timeSlotForecast = timeSlotForecast,
                        outfit = outfitSuggestions[timeSlotForecast.slot]
                    )
                }
            }
        }
    }
}

@Composable
private fun TimeSlotCard(
    timeSlotForecast: TimeSlotForecast,
    outfit: Outfit?
) {
    val slot = timeSlotForecast.slot
    val forecast = timeSlotForecast.forecast
    
    val gradientColors = getTemperatureGradient(forecast.temperature)
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        modifier = Modifier.width(140.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = gradientColors.map { it.copy(alpha = 0.15f) }
                    )
                )
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Zaman dilimi
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = slot.emoji,
                    fontSize = 16.sp
                )
                Text(
                    text = slot.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Sıcaklık
            Text(
                text = "${forecast.temperature.toInt()}°",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = gradientColors.first()
            )
            
            // Yağmur ihtimali
            if (forecast.rainProbability > 20) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "🌧️ %${forecast.rainProbability}",
                    style = MaterialTheme.typography.labelSmall,
                    color = RainyBlue
                )
            }
            
            // Kombin önerisi
            outfit?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Primary.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = it.items.firstOrNull() ?: "",
                        style = MaterialTheme.typography.labelSmall,
                        color = Primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

private fun getTemperatureGradient(temp: Double): List<androidx.compose.ui.graphics.Color> {
    return when {
        temp < 0 -> listOf(FreezingBlue, ColdBlue)
        temp < 10 -> listOf(ColdBlue, CoolGreen)
        temp < 18 -> listOf(CoolGreen, SunnyYellow)
        temp < 25 -> listOf(SunnyYellow, WarmOrange)
        else -> listOf(WarmOrange, HotRed)
    }
}
