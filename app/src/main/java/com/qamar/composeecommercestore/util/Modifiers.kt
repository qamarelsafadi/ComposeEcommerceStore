package com.qamar.composeecommercestore.util

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun rowModifier(): Modifier {
    return Modifier.fillMaxWidth().padding(bottom = 22.dp, end = 18.dp, start = 18.dp)

}