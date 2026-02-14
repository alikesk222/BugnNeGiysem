package com.example.outfitly.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wardrobe_items")
data class WardrobeItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val season: String,
    val color: String? = null,
    val imageUri: String? = null,
    val minTemp: Int? = null,
    val maxTemp: Int? = null,
    val createdAt: Long = System.currentTimeMillis()
)
