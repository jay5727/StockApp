package com.jay.stockapp.network.model

import com.google.gson.annotations.SerializedName

/**
 * Response model for Holding
 */
data class HoldingResponseModel(
    @SerializedName("data") val data: Data? = Data()
)

data class Data(
    @SerializedName("userHolding") val userHolding: List<UserHolding> = listOf()
)

data class UserHolding(
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("quantity") val quantity: Int? = null,
    @SerializedName("ltp") val ltp: Double? = null,
    @SerializedName("avgPrice") val avgPrice: Double? = null,
    @SerializedName("close") val close: Double? = null
)