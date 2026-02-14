package com.example.outfitly.domain.model

enum class ThermalProfile(val offset: Int, val displayName: String) {
    PIGEON(-3, "Çabuk Üşürüm"),
    NORMAL(0, "Normal"),
    WARM_BLOODED(3, "Çabuk Terlerim")
}
