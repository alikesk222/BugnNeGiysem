package com.example.outfitly.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.Balance
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.outfitly.domain.model.ThermalProfile
import com.example.outfitly.ui.theme.LocalAppColors

@Composable
fun ThermalProfileSelector(
    selectedProfile: ThermalProfile,
    onProfileSelected: (ThermalProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.card)
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
                    Icons.Rounded.Thermostat,
                    contentDescription = null,
                    tint = colors.onBackground,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Termal Profil",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Kişisel sıcaklık hassasiyetinizi seçin",
                style = MaterialTheme.typography.bodySmall,
                color = colors.subtext
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThermalProfile.entries.forEach { profile ->
                    ThermalProfileButton(
                        profile = profile,
                        isSelected = selectedProfile == profile,
                        onClick = { onProfileSelected(profile) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ThermalProfileButton(
    profile: ThermalProfile,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon: ImageVector = when (profile) {
        ThermalProfile.PIGEON -> Icons.Rounded.AcUnit
        ThermalProfile.NORMAL -> Icons.Rounded.Balance
        ThermalProfile.WARM_BLOODED -> Icons.Rounded.WbSunny
    }
    
    val offsetText = when {
        profile.offset < 0 -> "${profile.offset}°"
        profile.offset > 0 -> "+${profile.offset}°"
        else -> "0°"
    }
    
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary 
               else MaterialTheme.colorScheme.surface,
        tonalElevation = if (isSelected) 0.dp else 1.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = profile.displayName,
                tint = if (isSelected) MaterialTheme.colorScheme.onPrimary
                       else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = profile.displayName,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                       else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = offsetText,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                       else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}
