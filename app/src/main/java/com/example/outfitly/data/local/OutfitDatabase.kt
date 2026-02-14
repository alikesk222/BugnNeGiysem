package com.example.outfitly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.outfitly.data.local.dao.OutfitDao
import com.example.outfitly.data.local.entity.OutfitEntity

@Database(
    entities = [OutfitEntity::class],
    version = 1,
    exportSchema = false
)
abstract class OutfitDatabase : RoomDatabase() {
    abstract fun outfitDao(): OutfitDao
}
