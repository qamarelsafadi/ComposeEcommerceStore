package com.qamar.composeecommercestore.ui.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qamar.composeecommercestore.data.category.model.Category
import com.qamar.composeecommercestore.data.category.source.CategoryRepository
import com.qamar.composeecommercestore.data.product.ProductRepository
import com.qamar.composeecommercestore.data.product.model.Product
import com.qamar.composeecommercestore.util.Extensions.WhileUiSubscribed
import com.qamar.composeecommercestore.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeUiState(
    val categories: CategoriesUiState,
    val products: ProductsUiState
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
    categoryRepository: CategoryRepository,
    productRepository: ProductRepository
) : ViewModel() {

    val uiState = combine(
        categoryRepository.getCategory(), productRepository.getProductByCategoryId(3)
    ) { categories, products ->
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
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = HomeUiState(CategoriesUiState.Loading, ProductsUiState.Loading)
    )

}
