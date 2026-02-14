package com.example.outfitly.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outfitly.domain.model.Outfit
import com.example.outfitly.ui.theme.*

@Composable
fun OutfitCard(
    outfit: Outfit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Today's Recommendation",
                        style = MaterialTheme.typography.labelMedium,
                        color = Primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = outfit.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = OnBackground
                    )
                }
                
                OutfitBadge(outfit)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = outfit.description,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurface
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Items",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = OnBackground
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(outfit.items) { item ->
                    OutfitItemChip(item = item)
                }
            }
            
            if (outfit.rainCompatible || outfit.windCompatible) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (outfit.rainCompatible) {
                        FeatureTag(text = "🌧️ Rain Ready")
                    }
                    if (outfit.windCompatible) {
                        FeatureTag(text = "💨 Wind Proof")
                    }
                }
            }
        }
    }
}

@Composable
private fun OutfitBadge(outfit: Outfit) {
    val emoji = when (outfit.gender.name) {
        "MALE" -> "👔"
        "FEMALE" -> "👗"
        else -> "👕"
    }
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Primary.copy(alpha = 0.1f)
    ) {
        Text(
            text = emoji,
            fontSize = 32.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun OutfitItemChip(item: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Background
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = getItemEmoji(item),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = item,
                style = MaterialTheme.typography.bodySmall,
                color = OnSurface
            )
        }
    }
}

@Composable
private fun FeatureTag(text: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Secondary.copy(alpha = 0.1f)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = Secondary,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

private fun getItemEmoji(item: String): String {
    val lowerItem = item.lowercase()
    return when {
        lowerItem.contains("coat") || lowerItem.contains("jacket") -> "🧥"
        lowerItem.contains("sweater") || lowerItem.contains("hoodie") -> "🧶"
        lowerItem.contains("shirt") || lowerItem.contains("tee") || lowerItem.contains("top") -> "👕"
        lowerItem.contains("jean") || lowerItem.contains("pants") || lowerItem.contains("chino") -> "👖"
        lowerItem.contains("shorts") -> "🩳"
        lowerItem.contains("dress") || lowerItem.contains("skirt") -> "👗"
        lowerItem.contains("boot") -> "🥾"
        lowerItem.contains("sneaker") || lowerItem.contains("shoe") -> "👟"
        lowerItem.contains("sandal") || lowerItem.contains("flip") || lowerItem.contains("slide") -> "🩴"
        lowerItem.contains("hat") || lowerItem.contains("cap") || lowerItem.contains("beanie") -> "🧢"
        lowerItem.contains("glove") -> "🧤"
        lowerItem.contains("scarf") -> "🧣"
        lowerItem.contains("sunglasses") -> "🕶️"
        lowerItem.contains("bag") -> "👜"
        lowerItem.contains("underwear") || lowerItem.contains("thermal") -> "🩲"
        else -> "👔"
    }
}
