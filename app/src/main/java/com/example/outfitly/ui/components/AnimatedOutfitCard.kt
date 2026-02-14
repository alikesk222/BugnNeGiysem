package com.example.outfitly.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outfitly.domain.model.Outfit
import com.example.outfitly.ui.theme.*

@Composable
fun AnimatedOutfitCard(
    outfit: Outfit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(outfit) {
        visible = true
    }
    
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "alpha"
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Primary.copy(alpha = 0.2f),
                spotColor = Primary.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.05f),
                            CardBackground
                        )
                    )
                )
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
                            text = "Bugünün Önerisi",
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
                    
                    AnimatedOutfitBadge(outfit)
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = outfit.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurface
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Parçalar",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = OnBackground
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(outfit.items) { item ->
                        AnimatedItemChip(item = item)
                    }
                }
                
                if (outfit.rainCompatible || outfit.windCompatible) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (outfit.rainCompatible) {
                            FeatureTag(text = "🌧️ Yağmura Hazır")
                        }
                        if (outfit.windCompatible) {
                            FeatureTag(text = "💨 Rüzgara Karşı")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimatedOutfitBadge(outfit: Outfit) {
    val infiniteTransition = rememberInfiniteTransition(label = "badge")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "badgeScale"
    )
    
    val emoji = when (outfit.gender.name) {
        "MALE" -> "👔"
        "FEMALE" -> "👗"
        else -> "👕"
    }
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Primary.copy(alpha = 0.1f),
        modifier = Modifier.scale(scale)
    ) {
        Text(
            text = emoji,
            fontSize = 36.sp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun AnimatedItemChip(item: String) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(item) {
        visible = true
    }
    
    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else 20.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "offsetY"
    )
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Background,
        modifier = Modifier.offset(y = offsetY)
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
        lowerItem.contains("coat") || lowerItem.contains("mont") || lowerItem.contains("jacket") || lowerItem.contains("ceket") -> "🧥"
        lowerItem.contains("sweater") || lowerItem.contains("kazak") || lowerItem.contains("hoodie") -> "🧶"
        lowerItem.contains("shirt") || lowerItem.contains("tee") || lowerItem.contains("top") || lowerItem.contains("tişört") -> "👕"
        lowerItem.contains("jean") || lowerItem.contains("pants") || lowerItem.contains("pantolon") || lowerItem.contains("chino") -> "👖"
        lowerItem.contains("shorts") || lowerItem.contains("şort") -> "🩳"
        lowerItem.contains("dress") || lowerItem.contains("elbise") || lowerItem.contains("skirt") || lowerItem.contains("etek") -> "👗"
        lowerItem.contains("boot") || lowerItem.contains("bot") -> "🥾"
        lowerItem.contains("sneaker") || lowerItem.contains("shoe") || lowerItem.contains("ayakkabı") -> "👟"
        lowerItem.contains("sandal") || lowerItem.contains("flip") || lowerItem.contains("slide") || lowerItem.contains("terlik") -> "🩴"
        lowerItem.contains("hat") || lowerItem.contains("şapka") || lowerItem.contains("cap") || lowerItem.contains("beanie") || lowerItem.contains("bere") -> "🧢"
        lowerItem.contains("glove") || lowerItem.contains("eldiven") -> "🧤"
        lowerItem.contains("scarf") || lowerItem.contains("atkı") -> "🧣"
        lowerItem.contains("sunglasses") || lowerItem.contains("gözlük") -> "🕶️"
        lowerItem.contains("bag") || lowerItem.contains("çanta") -> "👜"
        else -> "👔"
    }
}
