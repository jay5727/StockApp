package com.jay.stockapp.holdingscreen

import androidx.compose.runtime.Immutable
import com.jay.stockapp.network.model.UserHolding

data class HoldingScreenUiState(
    val title: String = "",
    val loading: Boolean = false,
    val error: Error? = null,
    val holdingList: ImmutableList<UserHolding> = ImmutableList(emptyList()),
    val bottomInfo: InvestmentInfo? = null
)

data class InvestmentInfo(
    var currentValue: Double = 0.0,
    var totalInvestment: Double = 0.0,
    var todaysPNL: Double = 0.0,
    var totalPNL: Double = 0.0,
    var percentageChange: Double = 0.0,
)

@Immutable
data class ImmutableList<T>(
    val items: List<T>
)

data class Error(val message: String)
