package com.jay.stockapp.holdingscreen.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun MultiColorText(vararg textWithColors: Pair<String, Color>) {
    Text(buildAnnotatedString {
        textWithColors.forEach { (text, color) ->
            withStyle(style = SpanStyle(color = color)) {
                append(text)
                append(" ")
            }
        }
    })
}