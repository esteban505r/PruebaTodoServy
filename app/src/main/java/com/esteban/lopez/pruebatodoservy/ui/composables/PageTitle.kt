package com.esteban.lopez.pruebatodoservy.ui.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun PageTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            letterSpacing = 0.15.sp
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}