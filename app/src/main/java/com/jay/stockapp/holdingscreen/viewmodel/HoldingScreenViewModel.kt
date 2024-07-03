package com.jay.stockapp.holdingscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.stockapp.core.result.ResultState
import com.jay.stockapp.di.dispatchers.Dispatchers
import com.jay.stockapp.holdingscreen.Error
import com.jay.stockapp.holdingscreen.HoldingScreenUiState
import com.jay.stockapp.holdingscreen.ImmutableList
import com.jay.stockapp.holdingscreen.InvestmentInfo
import com.jay.stockapp.holdingscreen.repository.HoldingRepository
import com.jay.stockapp.network.model.UserHolding
import com.jay.stockapp.util.HoldingMapper
import com.jay.stockapp.util.orZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HoldingScreenViewModel @Inject constructor(
    @Dispatchers.IODispatcher private val ioDispatcher: CoroutineContext,
    private val holdingRepository: HoldingRepository
) : ViewModel() {

    /**
     * UI state
     */
    private val _uiState =
        MutableStateFlow(HoldingScreenUiState())
    val uiState: StateFlow<HoldingScreenUiState> = _uiState


    init {
        _uiState.update {
            it.copy(title = "Holdings")
        }
        fetchHoldingResponse()
    }

    /**
     * Fetches the list of holdings from the API
     */
    fun fetchHoldingResponse() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update {
                it.copy(loading = true)
            }
            holdingRepository.fetchHoldingResponse().collect { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        val list = resultState.data.data?.userHolding
                        list?.let { resList ->
                            _uiState.update {
                                it.copy(
                                    loading = false,
                                    holdingList = ImmutableList(resList),
                                    bottomInfo = getInvestmentInfo(resList)
                                )
                            }
                        }
                    }

                    is ResultState.Error -> {
                        _uiState.update {
                            it.copy(
                                loading = false,
                                error = Error(
                                    resultState.exception.message.orEmpty()
                                )
                            )
                        }
                    }

                    is ResultState.Loading -> {
                        _uiState.update {
                            it.copy(loading = it.loading)
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the investment info details for the holdings
     */
    private fun getInvestmentInfo(holdingList: List<UserHolding>): InvestmentInfo {
        val mapper = HoldingMapper()

        return InvestmentInfo().apply {
            val currentValue = holdingList.let(mapper::getCurrentValue)
            val totalInvestment = holdingList.let(mapper::getTotalInvestment)

            this.currentValue = currentValue
            this.totalInvestment = totalInvestment
            this.todaysPNL = holdingList.let(mapper::getDayPnL)
            this.totalPNL = currentValue.orZero() - totalInvestment.orZero()
            this.percentageChange = holdingList.let(mapper::getPercentageChange)
        }
    }

}