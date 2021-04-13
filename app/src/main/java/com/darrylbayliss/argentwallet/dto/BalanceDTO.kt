package com.darrylbayliss.argentwallet.dto

import com.google.gson.annotations.SerializedName

data class BalanceDTO(
    @SerializedName("status") val status: Int,
    @SerializedName("result") val result: Double
)
