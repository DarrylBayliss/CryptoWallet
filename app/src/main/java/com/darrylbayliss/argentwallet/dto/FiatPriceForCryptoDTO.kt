package com.darrylbayliss.argentwallet.dto

import com.google.gson.annotations.SerializedName

data class FiatPriceForCryptoDTO(@SerializedName("ethereum") val ethereumDTO: EthereumValueDTO)

data class EthereumValueDTO(@SerializedName("usd") val usDollar: Double)