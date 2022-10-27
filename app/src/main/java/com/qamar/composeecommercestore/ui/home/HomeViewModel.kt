package com.qamar.composeecommercestore.ui.home

import android.util.Log
import androidx.compose.runtime.Immutable
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
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    val uiState = combine(
        fetchCategories(), fetchProducts()
    ) { categories, products ->
        when (categories) {
            is Result.Success -> CategoriesUiState.Success(categories.data)
            is Result.Loading -> CategoriesUiState.Loading
            is Result.Error -> CategoriesUiState.Error
        }
        when (products) {
            is Result.Success -> ProductsUiState.Success(products.data)
            is Result.Loading -> ProductsUiState.Loading
            is Result.Error -> ProductsUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = HomeUiState(CategoriesUiState.Loading, ProductsUiState.Loading)
    )


    fun fetchCategories(): Flow<Result<List<Category>>> {
        Log.e("QMR","heyFromCategory")
        var flow: Flow<Result<List<Category>>>? = null
        viewModelScope.launch {
            flow = categoryRepository.getCategoriesStream().asResult()
        }
        return flow!!
    }

    fun fetchProducts(): Flow<Result<List<Product>>> {
        Log.e("QMR","heyFromProducts")

        var flow: Flow<Result<List<Product>>>? = null
        viewModelScope.launch {
            flow = productRepository.getProductByCategoryId(3)
        }
        return flow!!
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
