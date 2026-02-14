package com.example.outfitly.data.repository

import com.example.outfitly.data.local.dao.WardrobeDao
import com.example.outfitly.data.local.entity.WardrobeItemEntity
import com.example.outfitly.domain.model.ClothingCategory
import com.example.outfitly.domain.model.Season
import com.example.outfitly.domain.model.WardrobeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WardrobeRepositoryImpl @Inject constructor(
    private val wardrobeDao: WardrobeDao
) : WardrobeRepository {
    
    override fun getAllItems(): Flow<List<WardrobeItem>> {
        return wardrobeDao.getAllItems().map { entities ->
            entities.map { it.toWardrobeItem() }
        }
    }
    
    override fun getItemsByCategory(category: ClothingCategory): Flow<List<WardrobeItem>> {
        return wardrobeDao.getItemsByCategory(category.name).map { entities ->
            entities.map { it.toWardrobeItem() }
        }
    }
    
    override fun getItemsForWeather(season: Season, temperature: Int): Flow<List<WardrobeItem>> {
        return wardrobeDao.getItemsForWeather(season.name, temperature).map { entities ->
            entities.map { it.toWardrobeItem() }
        }
    }
    
    override suspend fun addItem(item: WardrobeItem): Long {
        return wardrobeDao.insertItem(item.toEntity())
    }
    
    override suspend fun updateItem(item: WardrobeItem) {
        wardrobeDao.updateItem(item.toEntity())
    }
    
    override suspend fun deleteItem(id: Int) {
        wardrobeDao.deleteItemById(id)
    }
    
    override suspend fun getItemCount(): Int {
        return wardrobeDao.getItemCount()
    }
    
    private fun WardrobeItemEntity.toWardrobeItem(): WardrobeItem {
        return WardrobeItem(
            id = id,
            name = name,
            category = ClothingCategory.valueOf(category),
            season = Season.valueOf(season),
            color = color,
            imageUri = imageUri,
            minTemp = minTemp,
            maxTemp = maxTemp
        )
    }
    
    private fun WardrobeItem.toEntity(): WardrobeItemEntity {
        return WardrobeItemEntity(
            id = id,
            name = name,
            category = category.name,
            season = season.name,
            color = color,
            imageUri = imageUri,
            minTemp = minTemp,
            maxTemp = maxTemp
        )
    }
}

interface WardrobeRepository {
    fun getAllItems(): Flow<List<WardrobeItem>>
    fun getItemsByCategory(category: ClothingCategory): Flow<List<WardrobeItem>>
    fun getItemsForWeather(season: Season, temperature: Int): Flow<List<WardrobeItem>>
    suspend fun addItem(item: WardrobeItem): Long
    suspend fun updateItem(item: WardrobeItem)
    suspend fun deleteItem(id: Int)
    suspend fun getItemCount(): Int
}
