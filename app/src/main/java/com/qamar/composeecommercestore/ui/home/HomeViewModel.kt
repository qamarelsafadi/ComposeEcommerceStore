package com.qamar.composeecommercestore.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qamar.composeecommercestore.data.category.Category
import com.qamar.composeecommercestore.data.category.source.CategoryRepository
import com.qamar.composeecommercestore.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the category in home screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categoryList = mutableStateListOf<Category>()
    val categoryList: List<Category> get() = _categoryList
    private var errorMessage: String by mutableStateOf("")
    var isLoading: Boolean by mutableStateOf(true)

    fun getHome() {
        viewModelScope.launch {
            isLoading = true
            val responseResult = categoryRepository.getCategoriesStream()
            responseResult.collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let {
                            isLoading = false
                            _categoryList.clear()
                            _categoryList.addAll(it)
                        }
                    }
                    Status.ERROR -> {
                        isLoading = false
                        errorMessage = result.message ?: ""
                    }
                    else -> {}
                }
            }
        }
    }

}