package com.darrylbayliss.argentwallet.api

import com.darrylbayliss.argentwallet.dto.TopTokensDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface EthPlorerApi {

    /**
     * Call this api to get the top tokens from ethplorer
     * @param limit: The amount of top tokens to return.
     * @param apiKey: Provide the ethplorer apikey here.
     */
    @GET("getTopTokens")
    fun getTopTokens(@Query("limit") limit: Int, @Query("apiKey") apiKey: String): Single<TopTokensDTO>
}