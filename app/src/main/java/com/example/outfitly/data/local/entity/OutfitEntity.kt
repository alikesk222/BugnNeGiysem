package com.example.outfitly.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "outfits")
data class OutfitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val items: String,
    val gender: String,
    val minTemp: Int,
    val maxTemp: Int,
    val rainCompatible: Boolean,
    val windCompatible: Boolean
)
