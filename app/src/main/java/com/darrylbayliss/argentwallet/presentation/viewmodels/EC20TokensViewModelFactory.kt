package com.darrylbayliss.argentwallet.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darrylbayliss.argentwallet.di.DependencyInjector
import com.darrylbayliss.argentwallet.repos.EthereumRepo

class EC20TokensViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return modelClass
            .getConstructor(EthereumRepo::class.java)
            .newInstance(DependencyInjector.provideEthereumRepo())
    }
}