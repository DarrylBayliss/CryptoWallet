package com.darrylbayliss.argentwallet.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darrylbayliss.argentwallet.models.Token
import com.darrylbayliss.argentwallet.models.TokenBalance
import com.darrylbayliss.argentwallet.repos.EthereumRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class ERC20TokensViewModel(private val ethereumRepo: EthereumRepo) : ViewModel() {

    val tokenBalance: MutableLiveData<TokenBalance> by lazy {
        MutableLiveData()
    }

    val tokenBalanceError: MutableLiveData<Throwable> by lazy {
        MutableLiveData()
    }

    val possibleTokens: MutableLiveData<List<Token>> by lazy {
        MutableLiveData()
    }

    fun searchForToken(tokenSymbol: String) {

        ethereumRepo.getTokenBalanceForWallet(tokenSymbol)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { error ->
                this.tokenBalanceError.value = error
            }
            .onErrorComplete()
            .map { tokenBalance ->
                this.tokenBalance.value = tokenBalance
            }
            .subscribe()
    }

    fun requestPossibleTokens() {

        ethereumRepo.getTopTokens(TOP_TOKENS_LIMIT_PARAMETER)
            .observeOn(AndroidSchedulers.mainThread())
            .map { filteredTokensList ->
                possibleTokens.value = filteredTokensList
            }
            .subscribe()

    }

    companion object {
        const val TOP_TOKENS_LIMIT_PARAMETER = 1
    }
}