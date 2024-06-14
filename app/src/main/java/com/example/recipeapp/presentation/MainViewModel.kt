package com.example.recipeapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.model.categories.ApiCategory
import com.example.recipeapp.domain.recipeService
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel: ViewModel() {

    private val _categoryState = mutableStateOf(RecipeState())
    val categoryState: State<RecipeState> = _categoryState

    init {
        fetchCategories()
    }
    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    list = response.categories,
                    error = null
                )
            } catch(e: Exception) {
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = "Error fetching categories: ${e.message}"
                )
            }
        }
    }
    data class RecipeState(
        val loading:Boolean = true,
        val list: List<ApiCategory> = emptyList(),
        val error: String? = null,
    )
}