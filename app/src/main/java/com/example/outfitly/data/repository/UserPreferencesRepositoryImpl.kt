package com.example.outfitly.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.outfitly.domain.model.Gender
import com.example.outfitly.domain.model.ThermalProfile
import com.example.outfitly.domain.model.UserPreferences
import com.example.outfitly.utils.Constants.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {
    
    private object Keys {
        val GENDER = stringPreferencesKey(PreferencesKeys.GENDER)
        val LAST_CITY = stringPreferencesKey(PreferencesKeys.LAST_CITY)
        val IS_PREMIUM = booleanPreferencesKey(PreferencesKeys.IS_PREMIUM)
        val THERMAL_PROFILE = stringPreferencesKey("thermal_profile")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val DARK_MODE = stringPreferencesKey("dark_mode")
    }
    
    override val userPreferences: Flow<UserPreferences> = dataStore.data.map { preferences ->
        UserPreferences(
            gender = preferences[Keys.GENDER]?.let { Gender.valueOf(it) } ?: Gender.UNISEX,
            lastCity = preferences[Keys.LAST_CITY],
            isPremium = preferences[Keys.IS_PREMIUM] ?: false,
            thermalProfile = preferences[Keys.THERMAL_PROFILE]?.let { 
                ThermalProfile.valueOf(it) 
            } ?: ThermalProfile.NORMAL,
            notificationsEnabled = preferences[Keys.NOTIFICATIONS_ENABLED] ?: true,
            darkMode = preferences[Keys.DARK_MODE] ?: "system"
        )
    }
    
    override suspend fun updateGender(gender: Gender) {
        dataStore.edit { preferences ->
            preferences[Keys.GENDER] = gender.name
        }
    }
    
    override suspend fun updateLastCity(city: String) {
        dataStore.edit { preferences ->
            preferences[Keys.LAST_CITY] = city
        }
    }
    
    override suspend fun updateThermalProfile(profile: ThermalProfile) {
        dataStore.edit { preferences ->
            preferences[Keys.THERMAL_PROFILE] = profile.name
        }
    }
    
    override suspend fun updateNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.NOTIFICATIONS_ENABLED] = enabled
        }
    }
    
    override suspend fun updateDarkMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[Keys.DARK_MODE] = mode
        }
    }
}

interface UserPreferencesRepository {
    val userPreferences: Flow<UserPreferences>
    suspend fun updateGender(gender: Gender)
    suspend fun updateLastCity(city: String)
    suspend fun updateThermalProfile(profile: ThermalProfile)
    suspend fun updateNotificationsEnabled(enabled: Boolean)
    suspend fun updateDarkMode(mode: String)
}
