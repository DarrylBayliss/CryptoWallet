package com.darrylbayliss.argentwallet.dto

import com.google.gson.annotations.SerializedName

data class TokenDTO(
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String
)
