package com.jay.stockapp.holdingscreen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoDataScreen(paddingValues: PaddingValues, onRefresh: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Center
    ) {
        Column(horizontalAlignment = CenterHorizontally) {
            Text(text = "No data available")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRefresh) {
                Text(text = "Refresh")
            }
        }
    }
}