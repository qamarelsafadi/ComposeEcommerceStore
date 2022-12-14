package com.qamar.composeecommercestore.ui.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue
import com.qamar.composeecommercestore.R
import com.qamar.composeecommercestore.data.product.model.Product
import com.qamar.composeecommercestore.ui.home.ProductsUiState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselView(products: ProductsUiState, loading: Boolean) {

    val gradient = Brush.verticalGradient(
        1000f to colorResource(id = R.color.gradinet3),
        1000f to colorResource(id = R.color.gradinet)
    )
    val gradient2 = Brush.verticalGradient(
        1000f to colorResource(id = R.color.gradinet4),
        1000f to colorResource(id = R.color.gradinet2),
    )
    val pagerState = rememberPagerState(0)
    when (products) {
        is ProductsUiState.Error -> {
        }
        is ProductsUiState.Loading -> {
        }
        is ProductsUiState.Success -> {
            HorizontalPager(
                count = products.products.size,
                // the more you increae the end padding the more
                // content of your other page will show
                // Add 32.dp horizontal padding to 'center' the pages
                contentPadding = PaddingValues(end = 65.dp, start = 18.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState
            ) { page ->
                Card(
                    Modifier
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                            // We animate the scaleX + scaleY, between 85% and 100%
                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                            // We animate the alpha, between 50% and 100%
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                        // you can control your card height for here if you decrease the ratio it will
                        //get more height else will be shorter
                        .aspectRatio(0.72f),
                    backgroundColor = Color.Transparent,
                    shape = RoundedCornerShape(15.dp),
                    elevation = 10.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(if (page % 2 == 0) gradient else gradient2)
                    ) {
                        ProductItem(page = page, products.products[page])
                    }
                }
            }
        }
    }

}

@Composable
fun ProductItem(page: Int, product: Product) {
    Column(
        modifier = Modifier
            .padding(end = 11.dp, start = 11.dp, top = 15.dp)
            .fillMaxWidth(),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${product.category?.name}",
                style = MaterialTheme.typography.h2,
                color = colorResource(
                    id = R.color.text_alpha
                )
            )
            Image(painter = painterResource(id = R.drawable.favicon), contentDescription = "")

        }
        Text(
            text = "${product.title}",
            style = MaterialTheme.typography.h3, color = Color.White,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(44.dp))
        AsyncImage(
            model = product.images?.first(),
            contentDescription = "",
            Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 184.dp, height = 222.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column() {
                repeat(3) {
                    Card(
                        elevation = 3.dp,
                        backgroundColor =
                        if (page % 2 == 0)
                            if (it == 0) Color.Black else if (it == 1) colorResource(id = R.color.red_dark) else Color.White
                        else if (it == 0) colorResource(id = R.color.brown) else if (it == 1) colorResource(
                            id = R.color.gray_light
                        ) else colorResource(id = R.color.brown_light),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .padding(top = 3.dp)
                            .size(20.dp, 20.dp)
                    ) {
                        AsyncImage(
                            model = product.images?.get(it) ?: "",
                            modifier = Modifier
                                .padding(top = 3.dp)
                                .size(20.dp, 20.dp),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    }
                }
            }

            Card(
                shape = RoundedCornerShape(6.dp), elevation = 0.dp,
                modifier = Modifier
                    .defaultMinSize(minWidth = 73.dp, minHeight = 29.dp)
                    .align(Alignment.Bottom),
                backgroundColor = colorResource(id = R.color.white_alpha)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "${product.price} USD",
                        style = MaterialTheme.typography.h4,
                        color = Color.White
                    )
                }
            }
        }

    }
}


