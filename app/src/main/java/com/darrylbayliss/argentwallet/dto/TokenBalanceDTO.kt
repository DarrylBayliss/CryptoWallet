package com.darrylbayliss.argentwallet.dto

import com.google.gson.annotations.SerializedName

data class TokenBalanceDTO(@SerializedName("result") val result: Long)
