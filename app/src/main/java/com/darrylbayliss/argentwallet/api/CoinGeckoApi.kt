package com.darrylbayliss.argentwallet.api

import com.darrylbayliss.argentwallet.dto.FiatPriceForCryptoDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {

    /**
     * Call this api call to get the fiat price for a crypto currency
     * @param ids: A string to request the cryptocurrency to compare against fiat currency
     * @param vs_currencies A string to request the fiat currency to compare against cryptocurrency
     */
    @GET("simple/price")
    fun getFiatPriceForCrypto(@Query("ids") ids: String, @Query("vs_currencies") vs_currencies: String): Single<FiatPriceForCryptoDTO>
}