package com.example.outfitly.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outfitly.data.repository.UserPreferencesRepository
import com.example.outfitly.domain.model.ThermalProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    
    init {
        observePreferences()
    }
    
    private fun observePreferences() {
        viewModelScope.launch {
            userPreferencesRepository.userPreferences.collect { prefs ->
                _uiState.update {
                    it.copy(
                        thermalProfile = prefs.thermalProfile,
                        notificationsEnabled = prefs.notificationsEnabled,
                        isPremium = prefs.isPremium,
                        darkMode = prefs.darkMode
                    )
                }
            }
        }
    }
    
    fun updateThermalProfile(profile: ThermalProfile) {
        viewModelScope.launch {
            userPreferencesRepository.updateThermalProfile(profile)
        }
    }
    
    fun updateNotifications(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateNotificationsEnabled(enabled)
        }
    }
    
    fun updateDarkMode(mode: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateDarkMode(mode)
        }
    }
}

data class SettingsUiState(
    val thermalProfile: ThermalProfile = ThermalProfile.NORMAL,
    val notificationsEnabled: Boolean = true,
    val isPremium: Boolean = false,
    val darkMode: String = "system"
)
