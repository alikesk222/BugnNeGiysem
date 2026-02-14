package com.example.outfitly.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outfitly.domain.model.ThermalProfile
import com.example.outfitly.ui.theme.*

@Composable
fun ThermalProfileSelector(
    selectedProfile: ThermalProfile,
    onProfileSelected: (ThermalProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "🌡️ Termal Profil",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnBackground
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Kişisel sıcaklık hassasiyetinizi seçin",
                style = MaterialTheme.typography.bodySmall,
                color = OnSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
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
    val emoji = when (profile) {
        ThermalProfile.PIGEON -> "🥶"
        ThermalProfile.NORMAL -> "😊"
        ThermalProfile.WARM_BLOODED -> "🥵"
    }
    
    val offsetText = when {
        profile.offset < 0 -> "${profile.offset}°"
        profile.offset > 0 -> "+${profile.offset}°"
        else -> ""
    }
    
    Surface(
        onClick = onClick,
        modifier = modifier.padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) Primary else Surface,
        shadowElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = emoji,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = profile.displayName,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) OnPrimary else OnSurface
            )
            if (offsetText.isNotEmpty()) {
                Text(
                    text = offsetText,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) OnPrimary.copy(alpha = 0.8f) else OnSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
