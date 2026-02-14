package com.example.outfitly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.outfitly.data.local.dao.OutfitDao
import com.example.outfitly.data.local.dao.WardrobeDao
import com.example.outfitly.data.local.entity.OutfitEntity
import com.example.outfitly.data.local.entity.WardrobeItemEntity

@Database(
    entities = [OutfitEntity::class, WardrobeItemEntity::class],
    version = 2,
    exportSchema = false
)
abstract class OutfitDatabase : RoomDatabase() {
    abstract fun outfitDao(): OutfitDao
    abstract fun wardrobeDao(): WardrobeDao
}
