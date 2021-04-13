package com.darrylbayliss.argentwallet.di

import com.darrylbayliss.argentwallet.api.CoinGeckoApi
import com.darrylbayliss.argentwallet.api.EthPlorerApi
import com.darrylbayliss.argentwallet.api.EtherScanApi
import com.darrylbayliss.argentwallet.repos.EthereumRepo
import com.darrylbayliss.argentwallet.repos.EthereumRepoImpl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DependencyInjector {

    companion object {

        private var ethereumRepo: EthereumRepo? = null

        var geckoApi: CoinGeckoApi

        var ethPlorerApi: EthPlorerApi

        var etherScanApi: EtherScanApi

        init {

            val rxJava3Adapter = RxJava3CallAdapterFactory.create()
            val gsonConverterFactory = GsonConverterFactory.create()

            val geckoApiRetrofit = Retrofit.Builder()
                .baseUrl("https://api.coingecko.com/api/v3/")
                .addCallAdapterFactory(rxJava3Adapter)
                .addConverterFactory(gsonConverterFactory)
                .build()

            val ethPlorerApiRetrofit = Retrofit.Builder()
                .baseUrl("https://api.ethplorer.io/")
                .addCallAdapterFactory(rxJava3Adapter)
                .addConverterFactory(gsonConverterFactory)
                .build()

            val etherScanApiRetrofit = Retrofit.Builder()
                .baseUrl("https://api.etherscan.io/")
                .addCallAdapterFactory(rxJava3Adapter)
                .addConverterFactory(gsonConverterFactory)
                .build()

            geckoApi = geckoApiRetrofit.create(CoinGeckoApi::class.java)
            ethPlorerApi = ethPlorerApiRetrofit.create(EthPlorerApi::class.java)
            etherScanApi = etherScanApiRetrofit.create(EtherScanApi::class.java)
        }

        fun provideEthereumRepo(): EthereumRepo {

            if (ethereumRepo == null) {
                ethereumRepo = EthereumRepoImpl(geckoApi, ethPlorerApi, etherScanApi)
            }

            return ethereumRepo!!
        }
    }
}