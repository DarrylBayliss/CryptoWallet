package com.darrylbayliss.argentwallet.repos

import com.darrylbayliss.argentwallet.models.Balance
import com.darrylbayliss.argentwallet.models.Token
import com.darrylbayliss.argentwallet.models.TokenBalance
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

/**
 * This interface defines the contract for a repo providing a Ethereum Wallet address
 * and associated methods to get ERC-20 Token associated with the wallet.
 */
interface EthereumRepo {

    /**
     * Provides the users wallet address.
     * @return The users wallet address (example: 0x0e3a2a1f2146d86a604adc220b4967a898d7fe07)
     */
    fun getWalletAddress(): String

    /**
     * Provides the amount of the ethereum and the equivalent fiat value the user has in their wallet.
     * @return The users ethereum Balance (example: 200 ETH, 4003)
     */
    fun getEthereumBalanceForWallet() : Single<Balance>

    /**
     * Provides the token balance owned by the wallet for a particular ERC-20 Token
     * @return The token balance, containing the token symbol and amount owned by the wallet.
     */
    fun getTokenBalanceForWallet(tokenSymbol: String) : Single<TokenBalance>

    /**
     * Provides the top tokens to the app. Useful for filtering tokens or providing search suggestions.
     * @param limit: The number of top tokens to return.
     * @return A list of tokens. Each contains the token name and symbol.
     */
    fun getTopTokens(limit: Int) : Single<List<Token>>
}