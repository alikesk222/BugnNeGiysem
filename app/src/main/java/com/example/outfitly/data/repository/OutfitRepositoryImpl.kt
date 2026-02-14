package com.example.outfitly.data.repository

import com.example.outfitly.data.local.dao.OutfitDao
import com.example.outfitly.data.local.entity.OutfitEntity
import com.example.outfitly.domain.model.Gender
import com.example.outfitly.domain.model.Outfit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OutfitRepositoryImpl @Inject constructor(
    private val outfitDao: OutfitDao
) : OutfitRepository {
    
    override fun getOutfitsForWeather(
        gender: Gender,
        temperature: Int,
        isRaining: Boolean,
        isWindy: Boolean
    ): Flow<List<Outfit>> {
        return outfitDao.getOutfitsForWeather(
            gender = gender.name,
            temp = temperature,
            isRaining = isRaining,
            isWindy = isWindy
        ).map { entities ->
            entities.map { it.toOutfit() }
        }
    }
    
    override fun getAllOutfits(): Flow<List<Outfit>> {
        return outfitDao.getAllOutfits().map { entities ->
            entities.map { it.toOutfit() }
        }
    }
    
    override suspend fun insertOutfits(outfits: List<OutfitEntity>) {
        outfitDao.insertOutfits(outfits)
    }
    
    override suspend fun getOutfitCount(): Int {
        return outfitDao.getOutfitCount()
    }
    
    private fun OutfitEntity.toOutfit(): Outfit {
        return Outfit(
            id = id,
            title = title,
            description = description,
            items = items.split(",").map { it.trim() },
            gender = Gender.valueOf(gender),
            minTemp = minTemp,
            maxTemp = maxTemp,
            rainCompatible = rainCompatible,
            windCompatible = windCompatible
        )
    }
}

interface OutfitRepository {
    fun getOutfitsForWeather(
        gender: Gender,
        temperature: Int,
        isRaining: Boolean,
        isWindy: Boolean
    ): Flow<List<Outfit>>
    
    fun getAllOutfits(): Flow<List<Outfit>>
    suspend fun insertOutfits(outfits: List<OutfitEntity>)
    suspend fun getOutfitCount(): Int
}
