package com.example.outfitly.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.outfitly.domain.model.RiskAlert
import com.example.outfitly.domain.model.RiskSeverity
import com.example.outfitly.domain.model.RiskType
import com.example.outfitly.ui.theme.*

@Composable
fun RiskAlertCard(
    alerts: List<RiskAlert>,
    modifier: Modifier = Modifier
) {
    if (alerts.isEmpty()) return
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Error.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "⚠️",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Dikkat!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Error
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            alerts.forEach { alert ->
                RiskAlertItem(alert = alert)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun RiskAlertItem(alert: RiskAlert) {
    val severityColor = when (alert.severity) {
        RiskSeverity.HIGH -> Error
        RiskSeverity.MEDIUM -> WarmOrange
        RiskSeverity.LOW -> SunnyYellow
    }
    
    val icon = when (alert.type) {
        RiskType.TEMPERATURE_DROP -> "🌡️"
        RiskType.HIGH_WIND -> "💨"
        RiskType.RAIN_CHANCE -> "🌧️"
        RiskType.EXTREME_COLD -> "❄️"
        RiskType.EXTREME_HEAT -> "🔥"
    }
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = severityColor.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = alert.message,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "💡 ${alert.recommendation}",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}
