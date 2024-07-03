package com.jay.stockapp.network

import com.jay.stockapp.network.model.HoldingResponseModel
import retrofit2.http.GET

/**
 * API Service for Holdings
 */
interface HoldingService {
    @GET("v1/d4e21780-27b4-41d3-b6bb-11036eb83a54")
    suspend fun fetchHoldingResponse(): HoldingResponseModel
}
