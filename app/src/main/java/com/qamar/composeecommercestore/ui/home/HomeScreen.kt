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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.qamar.composeecommercestore.R
import com.qamar.composeecommercestore.data.Product
import com.qamar.composeecommercestore.ui.home.components.CarouselView
import com.qamar.composeecommercestore.ui.home.components.CategoryList
import com.qamar.composeecommercestore.ui.home.components.TrendingList
import com.qamar.composeecommercestore.ui.home.components.TrendingSection
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
                .padding( top = 14.dp)
        ) {
            Row(rowModifier(), horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(painter = painterResource(id = R.drawable.navicon), contentDescription = "")
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = "")
            }
            Text(
                modifier = Modifier.padding(start = 18.dp),
                text = stringResource(R.string.categories),
                style = MaterialTheme.typography.h1)
            CategoryList(uiState.categories, selectedPosition)
            CarouselView()
            Text(modifier = Modifier.padding(top = 18.dp, start = 18.dp),
                text = stringResource(R.string.trending_items), style = MaterialTheme.typography.h1)
            TrendingList()

        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeEcommerceStoreTheme {
        HomeScreen() {}
    }
}