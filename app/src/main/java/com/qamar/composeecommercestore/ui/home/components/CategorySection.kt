package com.qamar.composeecommercestore.ui.home.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.qamar.composeecommercestore.R
import com.qamar.composeecommercestore.data.category.model.Category
import com.qamar.composeecommercestore.ui.home.CategoriesUiState


@Composable
fun CategoryList(
    uiState: CategoriesUiState,
    selectedPosition: Int,
    selected: (Category) -> Unit
) {
    var selectedPosition1 by remember { mutableStateOf(selectedPosition) }

    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var list = listOf<Category>()
        when (uiState) {
            is CategoriesUiState.Success -> {
                itemsIndexed(items = uiState.categories) { index, item ->
                    CategoryItem(
                        isSelected = selectedPosition1 == index,
                        category = item,
                    ) {
                        selectedPosition1 = index
                        selected(item)
                    }
                }
                list = uiState.categories
            }
            CategoriesUiState.Error -> {

            }
            CategoriesUiState.Loading -> {
            }
        }
        if (selectedPosition1 == 0 && list.isNotEmpty())
            selected(list.first())
    }


}

@Composable
fun CategoryItem(
    isSelected: Boolean? = false,
    category: Category,
    selected: (Category) -> Unit,
) {
    Box(Modifier.wrapContentHeight()) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .padding(end = 28.dp)
                .clickable {
                    selected(category)
                },
            text = category.name ?: "",
            color = if (isSelected == false) colorResource(id = R.color.unselectedColor) else colorResource(
                id = R.color.selectedColor
            ),
            style = if (isSelected == true) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
        )
    }
}
