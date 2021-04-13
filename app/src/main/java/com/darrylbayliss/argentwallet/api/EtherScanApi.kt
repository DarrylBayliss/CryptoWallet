package com.darrylbayliss.argentwallet.api

import com.darrylbayliss.argentwallet.dto.BalanceDTO
import com.darrylbayliss.argentwallet.dto.TokenBalanceDTO
import com.darrylbayliss.argentwallet.dto.TokenTransferEventsDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface EtherScanApi {

    /**
     * Call this api to get the ethereum balance for a wallet.
     * @param module: The module path for the etherscan api to use.
     * @param action: The action path for the etherscan api to use.
     * @param address: The wallet address to search for.
     * @param apiKey: Provide the etherscan apiKey here.
     */
    @GET("api")
    fun getBalanceForAddress(
        @Query("module") module: String,
        @Query("action") action: String,
        @Query("address") address: String,
        @Query("apiKey") apiKey: String): Single<BalanceDTO>

    /**
     * Call this api to get ERC-20 Token transfer events for a particular wallet.
     * @param module: The module path for the etherscan api to use.
     * @param action: The action path for the etherscan api to use.
     * @param address: The wallet address to search for.
     * @param startBlock: The block on the blockchain(?) to begin the search.
     * @param endBlock: The block on the blockchain to finish the search.
     * @param apiKey: Provide the etherscan apiKey here.
     */
    @GET("api")
    fun getTokenTransferEventsForAddress(
        @Query("module") module: String,
        @Query("action") action: String,
        @Query("address") address: String,
        @Query("startBlock") startBlock: Int,
        @Query("endBlock") endBlock: Int,
        @Query("apiKey") apiKey: String): Single<TokenTransferEventsDTO>

    /**
     * Call this api to get the balance for a ERC-20 Token.
     * @param module: The module path for the etherscan api to use.
     * @param action: The action path for the etherscan api to use.
     * @param contractaddress: The ERC-20 contract address to search for.
     * @param address: The wallet address to use to search for the contract.
     * @param tag: The tag to filter the results by. Usually this is latest.
     * @param apiKey: Provide the etherscan apiKey here.
     */
    @GET("api")
    fun getTokenBalanceForAddress(
        @Query("module") module: String,
        @Query("action") action: String,
        @Query("contractaddress") contractAddress: String,
        @Query("address") address: String,
        @Query("tag") tag: String,
        @Query("apiKey") apiKey: String): Single<TokenBalanceDTO>

    companion object {
        const val MODULE_ACCOUNT = "account"
        const val ACTION_BALANCE = "balance"
        const val ACTION_TOKENTX = "tokentx"
        const val ACTION_TOKEN_BALANCE = "tokenbalance"
    }
}