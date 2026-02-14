package com.example.outfitly.domain.model

data class WardrobeItem(
    val id: Int = 0,
    val name: String,
    val category: ClothingCategory,
    val season: Season,
    val color: String? = null,
    val imageUri: String? = null,
    val minTemp: Int? = null,
    val maxTemp: Int? = null
)

enum class ClothingCategory(val displayName: String, val emoji: String) {
    TOPS("Üstler", "👕"),
    BOTTOMS("Altlar", "👖"),
    OUTERWEAR("Dış Giyim", "🧥"),
    SHOES("Ayakkabılar", "👟"),
    ACCESSORIES("Aksesuarlar", "🧣")
}

enum class Season(val displayName: String) {
    SPRING("İlkbahar"),
    SUMMER("Yaz"),
    FALL("Sonbahar"),
    WINTER("Kış"),
    ALL("Tüm Mevsimler")
}
