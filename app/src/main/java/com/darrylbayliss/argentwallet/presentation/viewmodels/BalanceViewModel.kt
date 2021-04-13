package com.darrylbayliss.argentwallet.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darrylbayliss.argentwallet.models.Balance
import com.darrylbayliss.argentwallet.repos.EthereumRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class BalanceViewModel(private val ethereumRepo: EthereumRepo) : ViewModel() {

    val walletAddress: MutableLiveData<String> by lazy {
        MutableLiveData("Fetching Address")
    }

    val balance: MutableLiveData<Balance> by lazy {
        MutableLiveData(Balance(0.0, 0.0))
    }

    fun initialiseScreen() {
        requestWalletAddress()
        requestWalletBalance()
    }

    fun requestWalletBalance() {
        ethereumRepo.getEthereumBalanceForWallet()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { balance ->
                this.balance.value = balance
            }
    }

    fun requestWalletAddress() {
        this.walletAddress.value = ethereumRepo.getWalletAddress();
    }
}