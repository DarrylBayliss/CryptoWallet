package com.darrylbayliss.argentwallet.repos

import com.darrylbayliss.argentwallet.api.CoinGeckoApi
import com.darrylbayliss.argentwallet.api.EthPlorerApi
import com.darrylbayliss.argentwallet.api.EtherScanApi
import com.darrylbayliss.argentwallet.dto.TokenTransferEventDTO
import com.darrylbayliss.argentwallet.models.Balance
import com.darrylbayliss.argentwallet.models.Token
import com.darrylbayliss.argentwallet.models.TokenBalance
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers

class EthereumRepoImpl(
    private val geckoApi: CoinGeckoApi,
    private val ethPlorerApi: EthPlorerApi,
    private val etherScanApi: EtherScanApi
) : EthereumRepo {

    override fun getEthereumBalanceForWallet(): Single<Balance> {

        val walletAddress = getWalletAddress()

        return Single.zip(etherScanApi.getBalanceForAddress(
            EtherScanApi.MODULE_ACCOUNT,
            EtherScanApi.ACTION_BALANCE,
            walletAddress,
            ETHER_SCAN_API_KEY
        ), geckoApi.getFiatPriceForCrypto(ETHEREUM_CRYPTO, US_DOLLAR_CURRENCY),
            BiFunction { walletBalanceResponse, fiatValueResponse ->
                val dollarValueOfWalletBalance =
                    fiatValueResponse.ethereumDTO.usDollar * walletBalanceResponse.result
                            return@BiFunction Balance(walletBalanceResponse.result, dollarValueOfWalletBalance)
            })
            .subscribeOn(Schedulers.io())
    }

    override fun getTokenBalanceForWallet(tokenSymbol: String): Single<TokenBalance> {

        return getTokenTransferEventsForForWallet(tokenSymbol)
            .doOnError { error -> throw error }
            .flatMap { tokenTransferEvent -> getTokenBalanceForWallet(tokenTransferEvent) }
            .subscribeOn(Schedulers.io())
    }

    private fun getTokenTransferEventsForForWallet(tokenSymbol: String): Single<TokenTransferEventDTO> {

        val walletAddress = getWalletAddress()

        return etherScanApi.getTokenTransferEventsForAddress(
            EtherScanApi.MODULE_ACCOUNT,
            EtherScanApi.ACTION_TOKENTX,
            walletAddress,
            DEFAULT_START_BLOCK,
            DEFAULT_END_BLOCK,
            ETHER_SCAN_API_KEY
        )
            .toObservable()
            .flatMapIterable { transferEvents -> transferEvents.result }
            .filter { transferEvent -> transferEvent.tokenSymbol == tokenSymbol }
            .firstOrError()
            .doOnError { error -> throw error }
    }

    private fun getTokenBalanceForWallet(tokenTransferEventDTO: TokenTransferEventDTO): Single<TokenBalance> {

        val walletAddress = getWalletAddress()

        return etherScanApi.getTokenBalanceForAddress(
            EtherScanApi.MODULE_ACCOUNT,
            EtherScanApi.ACTION_TOKEN_BALANCE,
            tokenTransferEventDTO.contractAddress,
            walletAddress,
            TAG_LATEST,
            ETHER_SCAN_API_KEY
        )
            .map { tokenBalanceDTO ->
                TokenBalance(
                    tokenTransferEventDTO.tokenSymbol,
                    tokenBalanceDTO.result
                )
            }
    }

    override fun getTopTokens(limit: Int): Single<List<Token>> {

        return ethPlorerApi.getTopTokens(TOP_TOKEN_LIMIT_QUERY, ETHPLORER_API_KEY)
            .toObservable()
            .concatMapIterable { topTokensDTO -> topTokensDTO.tokens }
            .map { tokenDTO -> Token(tokenDTO.name, tokenDTO.symbol) }
            .toList()
            .subscribeOn(Schedulers.io())
    }

    override fun getWalletAddress(): String {
        return WALLET_ADDRESS
    }

    companion object {
        const val WALLET_ADDRESS = "0x082d3e0f04664b65127876e9A05e2183451c792a"
        const val ETHEREUM_CRYPTO = "ethereum"
        const val US_DOLLAR_CURRENCY = "USD"
        const val TOP_TOKEN_LIMIT_QUERY = 1
        const val ETHER_SCAN_API_KEY = "E5QFXD7ZYRH7THQM5PIXB8JD4GY38SEJZ4"
        const val ETHPLORER_API_KEY = "freekey"
        const val DEFAULT_START_BLOCK = 0
        const val DEFAULT_END_BLOCK = 999999999
        const val TAG_LATEST = "latest"
    }
}