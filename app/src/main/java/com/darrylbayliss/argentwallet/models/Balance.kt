package com.darrylbayliss.argentwallet.models

/**
 * This class defines the balance stored within an ethereum wallet.
 * @param ethereumBalance: The amount of ethereum within the wallet.
 * @param fiatBalance: The real world currency equivalent of the ethereumBalance. The currency is dependent on the context the class is used in.
 */
data class Balance(val ethereumBalance: Double, val fiatBalance: Double)