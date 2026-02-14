package com.example.outfitly.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.outfitly.data.local.entity.OutfitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {
    
    @Query("SELECT * FROM outfits")
    fun getAllOutfits(): Flow<List<OutfitEntity>>
    
    @Query("SELECT * FROM outfits WHERE gender = :gender OR gender = 'UNISEX'")
    fun getOutfitsByGender(gender: String): Flow<List<OutfitEntity>>
    
    @Query("""
        SELECT * FROM outfits 
        WHERE (gender = :gender OR gender = 'UNISEX')
        AND minTemp <= :temp 
        AND maxTemp >= :temp
    """)
    fun getOutfitsForTemperature(gender: String, temp: Int): Flow<List<OutfitEntity>>
    
    @Query("""
        SELECT * FROM outfits 
        WHERE (gender = :gender OR gender = 'UNISEX')
        AND minTemp <= :temp 
        AND maxTemp >= :temp
        AND (:isRaining = 0 OR rainCompatible = 1)
        AND (:isWindy = 0 OR windCompatible = 1)
    """)
    fun getOutfitsForWeather(
        gender: String, 
        temp: Int, 
        isRaining: Boolean, 
        isWindy: Boolean
    ): Flow<List<OutfitEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOutfits(outfits: List<OutfitEntity>)
    
    @Query("SELECT COUNT(*) FROM outfits")
    suspend fun getOutfitCount(): Int
}
