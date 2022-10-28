package com.qamar.composeecommercestore.ui.home

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qamar.composeecommercestore.data.category.model.Category
import com.qamar.composeecommercestore.data.category.source.CategoryRepository
import com.qamar.composeecommercestore.data.product.ProductRepository
import com.qamar.composeecommercestore.data.product.model.Product
import com.qamar.composeecommercestore.util.Extensions.WhileUiSubscribed
import com.qamar.composeecommercestore.util.Result
import com.qamar.composeecommercestore.util.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val categories: CategoriesUiState,
    val products: ProductsUiState,
    val isLoading: Boolean = false,
)

@Immutable
sealed interface CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState
    object Error : CategoriesUiState
    object Loading : CategoriesUiState
}

sealed interface ProductsUiState {
    data class Success(val products: List<Product>) : ProductsUiState
    object Error : ProductsUiState
    object Loading : ProductsUiState
}

/**
 * ViewModel for the category in home screen.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    var selectedId = 0
    private val _isLoading = MutableStateFlow(false)
    private val _getProductByCategory: StateFlow<Result<List<Product>>> =
        flow { emit(productRepository.getProductByCategoryId(selectedId)) }.stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = Result.Loading
        )

    private val getProductByCategory: MutableStateFlow<Result<List<Product>>> =
        MutableStateFlow(_getProductByCategory.value)
    val uiState = combine(
        categoryRepository.getCategory(),
        getProductByCategory, _isLoading
    ) { categories, products, loading ->
        HomeUiState(
            when (categories) {
                is Result.Success -> CategoriesUiState.Success(categories.data)
                is Result.Loading -> CategoriesUiState.Loading
                is Result.Error -> CategoriesUiState.Error
            },
            when (products) {
                is Result.Success -> ProductsUiState.Success(products.data)
                is Result.Loading -> ProductsUiState.Loading
                is Result.Error -> ProductsUiState.Error
            },
            isLoading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = HomeUiState(CategoriesUiState.Loading, ProductsUiState.Loading)
    )

    fun getCategories() {
        _isLoading.value = true
        viewModelScope.launch {
            categoryRepository.getCategoriesStream()
            _isLoading.value = false
        }
    }

    fun getProducts() {
        _isLoading.value = true
        viewModelScope.launch {
            getProductByCategory.value = productRepository.getProductByCategoryId(selectedId)
            _isLoading.value = false
        }
    }
}
