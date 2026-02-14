package com.example.outfitly.ui.screens.wardrobe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outfitly.data.repository.WardrobeRepository
import com.example.outfitly.domain.model.ClothingCategory
import com.example.outfitly.domain.model.Season
import com.example.outfitly.domain.model.WardrobeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WardrobeViewModel @Inject constructor(
    private val wardrobeRepository: WardrobeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(WardrobeUiState())
    val uiState: StateFlow<WardrobeUiState> = _uiState.asStateFlow()
    
    init {
        loadWardrobe()
    }
    
    private fun loadWardrobe() {
        viewModelScope.launch {
            wardrobeRepository.getAllItems().collect { items ->
                val grouped = items.groupBy { it.category }
                _uiState.update { 
                    it.copy(
                        items = items,
                        groupedItems = grouped,
                        isLoading = false
                    ) 
                }
            }
        }
    }
    
    fun addItem(
        name: String,
        category: ClothingCategory,
        season: Season,
        color: String? = null
    ) {
        viewModelScope.launch {
            val item = WardrobeItem(
                name = name,
                category = category,
                season = season,
                color = color
            )
            wardrobeRepository.addItem(item)
        }
    }
    
    fun deleteItem(id: Int) {
        viewModelScope.launch {
            wardrobeRepository.deleteItem(id)
        }
    }
    
    fun setSelectedCategory(category: ClothingCategory?) {
        _uiState.update { it.copy(selectedCategory = category) }
    }
    
    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }
    
    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }
}

data class WardrobeUiState(
    val items: List<WardrobeItem> = emptyList(),
    val groupedItems: Map<ClothingCategory, List<WardrobeItem>> = emptyMap(),
    val selectedCategory: ClothingCategory? = null,
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false
)
