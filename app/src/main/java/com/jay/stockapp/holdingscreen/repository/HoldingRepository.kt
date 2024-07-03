package com.jay.stockapp.holdingscreen.repository

import com.jay.stockapp.core.result.ResultState
import com.jay.stockapp.network.model.HoldingResponseModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Holdings
 */
interface HoldingRepository {

    /**
     * Fetches the list of holdings from the API wrapped in [HoldingResponseModel]
     */
    suspend fun fetchHoldingResponse(): Flow<ResultState<HoldingResponseModel>>
}