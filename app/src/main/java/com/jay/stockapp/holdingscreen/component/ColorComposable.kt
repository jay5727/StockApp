package com.jay.stockapp.holdingscreen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jay.stockapp.ui.theme.GreenC
import com.jay.stockapp.ui.theme.RedC

@Composable
fun ColorComposable(value: Double): Color {
    return if (value > 0) GreenC else RedC
}