package com.example.outfitly.domain.model

data class UserPreferences(
    val gender: Gender = Gender.UNISEX,
    val lastCity: String? = null,
    val isPremium: Boolean = false
)
