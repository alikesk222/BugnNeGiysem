package com.example.outfitly.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outfitly.domain.model.Gender
import com.example.outfitly.ui.theme.*

@Composable
fun GenderSelector(
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GenderOption.entries.forEach { option ->
                GenderButton(
                    option = option,
                    isSelected = selectedGender == option.gender,
                    onClick = { onGenderSelected(option.gender) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun GenderButton(
    option: GenderOption,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) Primary else Surface
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = option.emoji,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = option.label,
                style = MaterialTheme.typography.labelMedium,
                color = if (isSelected) OnPrimary else OnSurface
            )
        }
    }
}

private enum class GenderOption(
    val gender: Gender,
    val emoji: String,
    val label: String
) {
    MALE(Gender.MALE, "👔", "Male"),
    FEMALE(Gender.FEMALE, "👗", "Female"),
    UNISEX(Gender.UNISEX, "👕", "All")
}
