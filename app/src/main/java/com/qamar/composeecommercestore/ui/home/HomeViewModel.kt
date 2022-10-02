package com.qamar.composeecommercestore.ui.home

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.data.category.source.CategoryRepository
import com.qamar.composeecommercestore.util.Result
import com.qamar.composeecommercestore.util.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val categories: CategoriesUiState
)

@Immutable
sealed interface CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState
    object Error : CategoriesUiState
    object Loading : CategoriesUiState
}

sealed interface ProductsUiState {
    data class Success(val categories: List<Category>) : ProductsUiState
    object Error : ProductsUiState
    object Loading : ProductsUiState
}

/**
 * ViewModel for the category in home screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(CategoriesUiState.Loading))
    val uiState = _uiState.asStateFlow()

    fun fetchHome() {
        viewModelScope.launch {
            Log.e("QMR","HEY FROM VIEWMODEL")
            categoryRepository.getCategoriesStream().asResult()
                .collect { result ->
                    val categoriesUiState = when (result) {
                        is Result.Success -> CategoriesUiState.Success(result.data)
                        is Result.Loading -> CategoriesUiState.Loading
                        is Result.Error -> CategoriesUiState.Error
                    }
                    _uiState.value = HomeUiState(categoriesUiState)
                }
        }
    }
    // if more than one in same viewModel we can use combine else
//    val uiState: StateFlow<HomeUiState> = categories.collect {
//        val topRated: CategoriesUiState = when (it) {
//            is Result.Success -> CategoriesUiState.Success(it.data)
//            is Result.Loading -> CategoriesUiState.Loading
//            is Result.Error -> CategoriesUiState.Error
//        }
//
//        HomeUiState(
//            topRated,
//            isRefreshing.value,
//            isError.value
//        )
//    }
//        .stateIn(
//            scope = viewModelScope,
//            started = WhileUiSubscribed,
//            initialValue = HomeUiState(
//                CategoriesUiState.Loading,
//                isRefreshing = false,
//                isError = false
//            )
//        )
}
