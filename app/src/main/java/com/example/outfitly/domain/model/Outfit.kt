package com.example.outfitly.domain.model

data class Outfit(
    val id: Int,
    val title: String,
    val description: String,
    val items: List<String>,
    val gender: Gender,
    val minTemp: Int,
    val maxTemp: Int,
    val rainCompatible: Boolean,
    val windCompatible: Boolean
)

enum class Gender {
    MALE,
    FEMALE,
    UNISEX
}
