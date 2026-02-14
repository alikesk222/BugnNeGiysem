package com.example.outfitly.domain.model

data class RiskAlert(
    val type: RiskType,
    val severity: RiskSeverity,
    val message: String,
    val recommendation: String
)

enum class RiskType {
    TEMPERATURE_DROP,
    HIGH_WIND,
    RAIN_CHANCE,
    EXTREME_COLD,
    EXTREME_HEAT
}

enum class RiskSeverity {
    LOW,
    MEDIUM,
    HIGH
}
