package com.esteban.lopez.pruebatodoservy.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esteban.lopez.pruebatodoservy.R

@Composable
fun PageTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    withLogo:Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(withLogo)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(44.dp).padding(end = 6.dp)
            )
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
}