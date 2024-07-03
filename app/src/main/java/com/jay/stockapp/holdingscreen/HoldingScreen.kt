@file:OptIn(ExperimentalMaterial3Api::class)

package com.jay.stockapp.holdingscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jay.stockapp.holdingscreen.component.LoadingIndicator
import com.jay.stockapp.holdingscreen.component.NoDataScreen
import com.jay.stockapp.holdingscreen.component.ProfitLossBottomSheetInfoScreen
import com.jay.stockapp.holdingscreen.component.StockItem
import com.jay.stockapp.holdingscreen.repository.HoldingRepositoryImpl
import com.jay.stockapp.holdingscreen.viewmodel.HoldingScreenViewModel
import com.jay.stockapp.network.HoldingService
import com.jay.stockapp.network.model.HoldingResponseModel
import com.jay.stockapp.network.model.UserHolding
import com.jay.stockapp.ui.theme.LightGrey
import com.jay.stockapp.ui.theme.PlasMid
import com.jay.stockapp.util.HoldingMapper
import com.jay.stockapp.util.StringConstants.CurrentValue
import com.jay.stockapp.util.StringConstants.PNL
import com.jay.stockapp.util.StringConstants.TodaysPL
import com.jay.stockapp.util.StringConstants.TotalInvestment
import com.jay.stockapp.util.formatAmount
import com.jay.stockapp.util.getFormattedString
import com.jay.stockapp.util.getRoundUpto2Decimals
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoldingScreen(
    modifier: Modifier = Modifier,
    screenViewModel: HoldingScreenViewModel
) {

    val state by screenViewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = state.title, color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = PlasMid)
            )
        },
        bottomBar = {
            HoldingBottomBar(state)
        },
        content = { paddingValues ->
            HoldingContent(
                state = state,
                paddingValues = paddingValues,
                onRefresh = { screenViewModel.fetchHoldingResponse() }
            )
        })
}

@Composable
fun HoldingBottomBar(state: HoldingScreenUiState) {
    if (state.holdingList.items.isNotEmpty()) {
        state.bottomInfo?.let {
            ProfitLossBottomSheetInfoScreen(
                modifier = Modifier.background(color = LightGrey),
                totalPNL = it.totalPNL,
                todaysPNL = it.todaysPNL,
                percentageChange = it.percentageChange,
                pair = Pair(PNL, it.totalPNL.getFormattedString()),
                list = listOf(
                    Pair(CurrentValue, it.currentValue.getFormattedString()),
                    Pair(TotalInvestment, it.totalInvestment.getFormattedString()),
                    Pair(TodaysPL, it.todaysPNL.getFormattedString()),
                )
            )

        }
    }
}

@Composable
fun HoldingContent(
    state: HoldingScreenUiState,
    paddingValues: PaddingValues,
    onRefresh: () -> Unit
) {
    when {
        state.loading -> LoadingIndicator(paddingValues)
        state.holdingList.items.isNotEmpty() -> HoldingList(state.holdingList.items, paddingValues)
        else -> NoDataScreen(paddingValues, onRefresh)
    }
}


@Composable
fun HoldingList(items: List<UserHolding>, paddingValues: PaddingValues) {
    val holdingMapper = remember { HoldingMapper() }
    if (items.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            itemsIndexed(items) { index, item ->
                StockItem(
                    modifier = Modifier.padding(16.dp),
                    holding = item,
                    individualStockPnl = holdingMapper.getIndividualStockPNL(item),
                    shouldHideSpacer = index == items.lastIndex,
                    formattedLTP = item.ltp.toString().formatAmount(),
                    formattedPNL = holdingMapper.getIndividualStockPNL(item).getRoundUpto2Decimals()
                        .getFormattedString()

                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewUsersScreen() {
    HoldingScreen(
        modifier = Modifier.fillMaxSize(),
        screenViewModel = HoldingScreenViewModel(
            Dispatchers.IO,
            HoldingRepositoryImpl(object : HoldingService {
                override suspend fun fetchHoldingResponse() = HoldingResponseModel()
            })
        )
    )
}