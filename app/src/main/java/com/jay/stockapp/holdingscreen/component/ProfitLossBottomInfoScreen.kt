package com.jay.stockapp.holdingscreen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfitLossBottomSheetInfoScreen(
    modifier: Modifier = Modifier,
    totalPNL: Double,
    todaysPNL: Double,
    percentageChange: Double = 0.0,
    pair: Pair<String, String>,
    list: List<Pair<String, String>>,

    ) {
    var showInfoSheet by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            AnimatedVisibility(
                visible = showInfoSheet,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)) + expandVertically(),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)) + shrinkVertically()
            ) {
                Column {
                    list.forEachIndexed { index, it ->
                        InvestmentDetails(
                            modifier = Modifier.padding(8.dp),
                            pair = it,
                            shouldSetPnlColor = index == list.lastIndex,
                            value = todaysPNL
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                }
            }

            //Profit & Loss
            InvestmentDetails(
                modifier = Modifier.padding(8.dp),
                pair = pair,
                shouldShowIcon = true,
                shouldSetPnlColor = true,
                value = totalPNL,
                percentageChange = percentageChange
            ) {
                showInfoSheet = !showInfoSheet
            }
        }
    }
}

@Preview
@Composable
private fun ProfitLossBottomSheetInfoScreenPreview() {
    ProfitLossBottomSheetInfoScreen(
        modifier = Modifier.background(color = Color.LightGray),
        totalPNL = 100101.0,
        todaysPNL = 22332.0,
        percentageChange = 0.0,
        Pair("Profit & Loss*", "₹ 5727"),
        listOf(
            Pair("Current Value*", "123"),
            Pair("Total Investment*", "3224"),
            Pair("Today's Profit & Loss*", "232")
        )
    )
}