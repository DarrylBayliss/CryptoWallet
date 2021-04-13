package com.darrylbayliss.argentwallet.repos

import com.darrylbayliss.argentwallet.models.Balance
import com.darrylbayliss.argentwallet.models.Token
import com.darrylbayliss.argentwallet.models.TokenBalance
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class EtherumRepoTest {

    private val tokenList = listOf(
        Token("Token-1", "TOK1"),
        Token("Token-2", "TOK2"),
        Token("Token-3", "TOK3")
    )

    private val sut: EthereumRepo = mock {
        on { getWalletAddress() } doReturn "someAddress"
        on { getTopTokens(Mockito.anyInt()) } doReturn Single.just(tokenList)
        on { getTokenBalanceForWallet(Mockito.anyString()) } doReturn Single.just(
            TokenBalance(
                "TOK-1",
                50L
            )
        )
        on { getEthereumBalanceForWallet() } doReturn Single.just(Balance(50.0, 25.0))
    }

    @Test
    fun testWalletAddress() {
        val walletAddress = sut.getWalletAddress()
        Assert.assertEquals("someAddress", walletAddress)
    }

    @Test
    fun testTopTokens() {

        sut.getTopTokens(10)
            .test()
            .assertNoErrors()
            .assertValue { it.size == 3 }
    }

    @Test
    fun testTokenBalanceForWallet() {

        sut.getTokenBalanceForWallet(Mockito.anyString())
            .test()
            .assertNoErrors()
            .assertValue { it.tokenSymbol == "TOK-1" && it.value == 50L }
    }

    @Test
    fun testGetEthereumBalanceForWallet() {

        sut.getEthereumBalanceForWallet()
            .test()
            .assertNoErrors()
            .assertValue { it.ethereumBalance == 50.0 && it.fiatBalance == 25.0 }
    }
}