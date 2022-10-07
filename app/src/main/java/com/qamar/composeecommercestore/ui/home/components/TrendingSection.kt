package com.qamar.composeecommercestore.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qamar.composeecommercestore.R
import com.qamar.composeecommercestore.util.theme.ComposeEcommerceStoreTheme


@Composable
fun TrendingList() {
    LazyRow(
        Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 0.dp)
    ) {
        items(3) {
            TrendingItem(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(end = 18.dp)
                    .height(126.dp)
            )
        }
    }
}

@Composable
fun TrendingItem(
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 10.dp, vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.chair_4),
                contentDescription = ""
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 12.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Turquoise Chair",
                        style = MaterialTheme.typography.button
                    )
                    Image(
                        painter = painterResource(id = R.drawable.favicon),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = Color.Black)
                    )

                }
                Text(
                    text = "Category Name",
                    style = MaterialTheme.typography.h2,
                    color = colorResource(
                        id = R.color.text_alpha2
                    )
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "350 USD",
                        style = MaterialTheme.typography.h4,
                        color = colorResource(id = R.color.text_alpha2)
                    )
                    Row() {
                        repeat(3) {
                            Card(
                                elevation = 3.dp,
                                backgroundColor =
                                if (it == 0) colorResource(id = R.color.terq) else if (it == 1) colorResource(
                                    id = R.color.purp
                                )
                                else colorResource(id = R.color.yell),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier
                                    .padding(end = 3.dp)
                                    .size(20.dp, 20.dp)
                            ) {

                            }
                        }
                    }
                }

            }
        }
    }
}


class TrendingSection {

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ComposeEcommerceStoreTheme {
            TrendingList()
        }
    }


}

