package com.qamar.composeecommercestore.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qamar.composeecommercestore.R
import com.qamar.composeecommercestore.data.Product
import com.qamar.composeecommercestore.util.Resource
import com.qamar.composeecommercestore.util.rowModifier
import com.qamar.composeecommercestore.util.theme.ComposeEcommerceStoreTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit,
) {
    var selectedPosition by remember { mutableStateOf(0) }
    LaunchedEffect(Unit, block = {
        viewModel.getHome()
    })
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 24.dp)
        ) {
            Row(rowModifier(), horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(painter = painterResource(id = R.drawable.navicon), contentDescription = "")
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = "")
            }
            Text(text = "Categories", style = MaterialTheme.typography.h1)
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                itemsIndexed(items = viewModel.categoryList) {
                    index , item  ->
                        CategoryItem(
                            isSelected = selectedPosition == index,
                            categoryName = item.name ?: ""
                        ) {
                            selectedPosition = index
                    }
                }
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