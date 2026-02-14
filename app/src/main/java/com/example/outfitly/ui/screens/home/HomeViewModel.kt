package com.example.outfitly.ui.screens.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outfitly.data.local.SeedData
import com.example.outfitly.data.repository.OutfitRepository
import com.example.outfitly.data.repository.UserPreferencesRepository
import com.example.outfitly.data.repository.WeatherRepository
import com.example.outfitly.domain.engine.OutfitRuleEngine
import com.example.outfitly.domain.engine.RiskAlertEngine
import com.example.outfitly.domain.model.*
import com.example.outfitly.domain.usecase.GetOutfitRecommendationUseCase
import com.example.outfitly.utils.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val outfitRepository: OutfitRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val getOutfitRecommendationUseCase: GetOutfitRecommendationUseCase,
    private val ruleEngine: OutfitRuleEngine,
    private val riskAlertEngine: RiskAlertEngine,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        seedDatabaseIfNeeded()
        observeUserPreferences()
    }
    
    private fun seedDatabaseIfNeeded() {
        viewModelScope.launch {
            val count = outfitRepository.getOutfitCount()
            if (count == 0) {
                outfitRepository.insertOutfits(SeedData.outfits)
            }
        }
    }
    
    private fun observeUserPreferences() {
        viewModelScope.launch {
            userPreferencesRepository.userPreferences.collect { prefs ->
                _uiState.update { 
                    it.copy(
                        selectedGender = prefs.gender,
                        thermalProfile = prefs.thermalProfile
                    ) 
                }
                
                // Re-fetch outfit if weather is available
                _uiState.value.weather?.let { weather ->
                    fetchOutfitRecommendation(weather, prefs.gender, prefs.thermalProfile)
                    analyzeRisks(weather)
                }
            }
        }
    }
    
    private fun analyzeRisks(weather: Weather) {
        val alerts = riskAlertEngine.analyzeRisks(weather, null)
        _uiState.update { it.copy(riskAlerts = alerts) }
    }
    
    @SuppressLint("MissingPermission")
    fun fetchWeatherByLocation() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                fetchWeatherByCoordinates(location.latitude, location.longitude)
            } else {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = "Konum alınamadı. Lütfen tekrar deneyin veya şehir adı girin."
                    ) 
                }
            }
        }.addOnFailureListener { e ->
            _uiState.update { 
                it.copy(isLoading = false, error = e.message ?: "Konum alınamadı") 
            }
        }
    }
    
    fun fetchWeatherByCity(city: String) {
        viewModelScope.launch {
            weatherRepository.getWeatherByCity(city).collect { result ->
                handleWeatherResult(result)
            }
        }
    }
    
    private fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            weatherRepository.getWeatherByCoordinates(lat, lon).collect { result ->
                handleWeatherResult(result)
            }
        }
    }
    
    private fun handleWeatherResult(result: Resource<Weather>) {
        when (result) {
            is Resource.Loading -> {
                _uiState.update { it.copy(isLoading = true, error = null) }
            }
            is Resource.Success -> {
                result.data?.let { weather ->
                    _uiState.update { it.copy(weather = weather, isLoading = false, isOffline = false) }
                    fetchOutfitRecommendation(
                        weather, 
                        _uiState.value.selectedGender,
                        _uiState.value.thermalProfile
                    )
                    analyzeRisks(weather)
                    
                    viewModelScope.launch {
                        userPreferencesRepository.updateLastCity(weather.cityName)
                    }
                }
            }
            is Resource.Error -> {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = result.message,
                        isOffline = true
                    ) 
                }
            }
        }
    }
    
    private fun fetchOutfitRecommendation(
        weather: Weather, 
        gender: Gender,
        thermalProfile: ThermalProfile
    ) {
        viewModelScope.launch {
            getOutfitRecommendationUseCase(weather, gender).collect { recommendation ->
                val tips = ruleEngine.getRecommendationTips(weather, thermalProfile)
                _uiState.update { 
                    it.copy(
                        recommendedOutfit = recommendation.outfit,
                        alternativeOutfits = recommendation.alternativeOutfits,
                        tips = tips
                    ) 
                }
            }
        }
    }
    
    fun updateGender(gender: Gender) {
        viewModelScope.launch {
            userPreferencesRepository.updateGender(gender)
        }
    }
    
    fun refresh() {
        _uiState.value.weather?.let { weather ->
            fetchWeatherByCity(weather.cityName)
        } ?: fetchWeatherByLocation()
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class HomeUiState(
    val weather: Weather? = null,
    val recommendedOutfit: Outfit? = null,
    val alternativeOutfits: List<Outfit> = emptyList(),
    val tips: List<String> = emptyList(),
    val riskAlerts: List<RiskAlert> = emptyList(),
    val selectedGender: Gender = Gender.UNISEX,
    val thermalProfile: ThermalProfile = ThermalProfile.NORMAL,
    val isLoading: Boolean = false,
    val isOffline: Boolean = false,
    val error: String? = null
)
