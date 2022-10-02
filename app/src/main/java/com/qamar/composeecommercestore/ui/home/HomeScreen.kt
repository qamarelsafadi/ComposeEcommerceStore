package com.qamar.composeecommercestore.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.qamar.composeecommercestore.R
import com.qamar.composeecommercestore.data.Product
import com.qamar.composeecommercestore.ui.home.components.CarouselView
import com.qamar.composeecommercestore.util.rowModifier
import com.qamar.composeecommercestore.util.theme.ComposeEcommerceStoreTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit,
) {
    val selectedPosition by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        viewModel.fetchHome()
    }
    val uiState: HomeUiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 18.dp, top = 24.dp, bottom = 24.dp)
        ) {
            Row(rowModifier(), horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(painter = painterResource(id = R.drawable.navicon), contentDescription = "")
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = "")
            }
            Text(text = "Categories", style = MaterialTheme.typography.h1)
            CategoryList(uiState.categories, selectedPosition)

            CarouselView()
        }

    }
}

@Composable
private fun CategoryList(
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeEcommerceStoreTheme {
        HomeScreen() {}
    }
}