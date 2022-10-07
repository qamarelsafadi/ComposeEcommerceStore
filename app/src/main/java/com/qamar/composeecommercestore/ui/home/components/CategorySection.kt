package com.qamar.composeecommercestore.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.qamar.composeecommercestore.R
import com.qamar.composeecommercestore.ui.home.CategoriesUiState


@Composable
fun CategoryList(
    uiState: CategoriesUiState,
    selectedPosition: Int
) {
    var selectedPosition1 = selectedPosition
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(top = 22.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        when (uiState) {
            is CategoriesUiState.Success -> {
                itemsIndexed(items = uiState.categories) { index, item ->
                    CategoryItem(
                        isSelected = selectedPosition1 == index,
                        categoryName = item.name ?: ""
                    ) {
                        selectedPosition1 = index
                    }
                }
            }
            CategoriesUiState.Error -> {

            }
            CategoriesUiState.Loading -> {

            }
        }

    }
}

@Composable
fun CategoryItem(
    isSelected: Boolean? = false,
    categoryName: String,
    selected: () -> Unit
) {
    Box(Modifier.wrapContentHeight()) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .padding(end = 28.dp)
                .clickable {
                    selected()
                },
            text = categoryName,
            color = if (isSelected == false) colorResource(id = R.color.unselectedColor) else colorResource(
                id = R.color.selectedColor
            ),
            style = if (isSelected == true) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
        )
    }
}
