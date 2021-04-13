package com.darrylbayliss.argentwallet.dto

import com.google.gson.annotations.SerializedName

data class TopTokensDTO(
    @SerializedName("tokens") val tokens: List<TokenDTO>
)