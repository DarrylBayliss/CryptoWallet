package com.darrylbayliss.argentwallet.models

/**
 * This class defines the balance for a particular ERC-20 token associated with the users wallet.
 * @param tokenSymbol: The symbol associated with the token..
 * @param symbol: The value stored by the token.
 */
data class TokenBalance(
    val tokenSymbol: String,
    val value: Long
)
