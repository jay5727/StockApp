package com.jay.stockapp.holdingscreen.repository

import com.jay.stockapp.core.performApiCall
import com.jay.stockapp.core.result.ResultState
import com.jay.stockapp.network.HoldingService
import com.jay.stockapp.network.model.HoldingResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [HoldingRepository]
 */
@Singleton
class HoldingRepositoryImpl @Inject constructor(
    private val holdingService: HoldingService
) : HoldingRepository {

    /**
     * Fetches the list of holdings from the API wrapped in [HoldingResponseModel]
     */
    override suspend fun fetchHoldingResponse(): Flow<ResultState<HoldingResponseModel>> {
        return performApiCall {
            holdingService.fetchHoldingResponse()
        }
    }
}
