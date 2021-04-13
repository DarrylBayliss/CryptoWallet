package com.darrylbayliss.argentwallet.dto

import com.google.gson.annotations.SerializedName

data class TokenTransferEventsDTO(@SerializedName("result") val result: List<TokenTransferEventDTO>)

data class TokenTransferEventDTO(
    @SerializedName("contractAddress") val contractAddress: String,
    @SerializedName("tokenSymbol") val tokenSymbol: String,
    @SerializedName("to") val to: String
)
