package com.darrylbayliss.argentwallet.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.darrylbayliss.argentwallet.R

class ERC20TokensActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.erc20_tokens_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ERC20TokensFragment.newInstance())
                .commitNow()
        }
    }
}