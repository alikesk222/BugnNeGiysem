package com.example.outfitly.data.local.dao

import androidx.room.*
import com.example.outfitly.data.local.entity.WardrobeItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WardrobeDao {
    
    @Query("SELECT * FROM wardrobe_items ORDER BY createdAt DESC")
    fun getAllItems(): Flow<List<WardrobeItemEntity>>
    
    @Query("SELECT * FROM wardrobe_items WHERE category = :category ORDER BY createdAt DESC")
    fun getItemsByCategory(category: String): Flow<List<WardrobeItemEntity>>
    
    @Query("SELECT * FROM wardrobe_items WHERE season = :season OR season = 'ALL' ORDER BY createdAt DESC")
    fun getItemsBySeason(season: String): Flow<List<WardrobeItemEntity>>
    
    @Query("""
        SELECT * FROM wardrobe_items 
        WHERE (season = :season OR season = 'ALL')
        AND (minTemp IS NULL OR minTemp <= :temp)
        AND (maxTemp IS NULL OR maxTemp >= :temp)
        ORDER BY createdAt DESC
    """)
    fun getItemsForWeather(season: String, temp: Int): Flow<List<WardrobeItemEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: WardrobeItemEntity): Long
    
    @Update
    suspend fun updateItem(item: WardrobeItemEntity)
    
    @Delete
    suspend fun deleteItem(item: WardrobeItemEntity)
    
    @Query("DELETE FROM wardrobe_items WHERE id = :id")
    suspend fun deleteItemById(id: Int)
    
    @Query("SELECT COUNT(*) FROM wardrobe_items")
    suspend fun getItemCount(): Int
}
