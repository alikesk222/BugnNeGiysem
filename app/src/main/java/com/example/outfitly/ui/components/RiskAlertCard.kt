package com.example.outfitly.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.TipsAndUpdates
import androidx.compose.material.icons.rounded.Umbrella
import androidx.compose.material.icons.rounded.Warning
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
            containerColor = MaterialTheme.colorScheme.errorContainer
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
                Icon(
                    Icons.Rounded.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Dikkat!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
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
        RiskSeverity.HIGH -> MaterialTheme.colorScheme.error
        RiskSeverity.MEDIUM -> WarmOrange
        RiskSeverity.LOW -> SunnyYellow
    }
    
    val icon = when (alert.type) {
        RiskType.TEMPERATURE_DROP -> Icons.Rounded.Thermostat
        RiskType.HIGH_WIND -> Icons.Rounded.Air
        RiskType.RAIN_CHANCE -> Icons.Rounded.Umbrella
        RiskType.EXTREME_COLD -> Icons.Rounded.AcUnit
        RiskType.EXTREME_HEAT -> Icons.Rounded.LocalFireDepartment
    }
    
    val colors = LocalAppColors.current
    
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
            Icon(
                icon,
                contentDescription = null,
                tint = severityColor,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = alert.message,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = colors.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Rounded.TipsAndUpdates,
                        contentDescription = null,
                        tint = colors.subtext,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = alert.recommendation,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.subtext
                    )
                }
            }
        }
    }
}
