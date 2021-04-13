package com.darrylbayliss.argentwallet.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.darrylbayliss.argentwallet.R

class BalanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.balance_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BalanceFragment.newInstance())
                .commitNow()
        }
    }
}