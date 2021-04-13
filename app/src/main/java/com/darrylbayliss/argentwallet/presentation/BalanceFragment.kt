package com.darrylbayliss.argentwallet.presentation

import android.content.Intent
import android.icu.number.NumberFormatter
import android.icu.text.NumberFormat
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrylbayliss.argentwallet.R
import com.darrylbayliss.argentwallet.databinding.BalanceFragmentBinding
import com.darrylbayliss.argentwallet.presentation.viewmodels.BalanceViewModel
import com.darrylbayliss.argentwallet.presentation.viewmodels.BalanceViewModelFactory
import java.util.*

class BalanceFragment : Fragment() {

    companion object {
        fun newInstance() = BalanceFragment()
    }

    private lateinit var viewModel: BalanceViewModel

    private lateinit var binding: BalanceFragmentBinding

    private lateinit var dollarFormatter: NumberFormat

    private lateinit var ethereumFormatter: NumberFormat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dollarFormatter = NumberFormat.getCurrencyInstance(Locale.US)
        dollarFormatter.maximumFractionDigits = 2
        dollarFormatter.maximumIntegerDigits = 8

        ethereumFormatter = NumberFormat.getInstance()
        ethereumFormatter.maximumFractionDigits = 2
        ethereumFormatter.maximumIntegerDigits = 8

        binding = BalanceFragmentBinding.inflate(layoutInflater)

        binding.erc20TokensScreenButton.setOnClickListener {
            val intent = Intent(requireActivity(), ERC20TokensActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = BalanceViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(BalanceViewModel::class.java)

        viewModel.balance.observe(this, { balance ->
            binding.ethereumBalanceTextview.text = getString(R.string.ethereum_balance_value_format, dollarFormatter.format(balance.fiatBalance), ethereumFormatter.format(balance.ethereumBalance))
        })

        viewModel.walletAddress.observe(this, { walletAddress ->
            binding.ethereumAddressTextView.text = walletAddress
        })

        viewModel.initialiseScreen()
    }
}