package com.darrylbayliss.argentwallet.presentation

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.BaseColumns
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AutoCompleteTextView
import android.widget.CursorAdapter
import androidx.appcompat.widget.SearchView.*
import androidx.cursoradapter.widget.SimpleCursorAdapter
import com.darrylbayliss.argentwallet.R
import com.darrylbayliss.argentwallet.databinding.Erc20TokensFragmentBinding
import com.darrylbayliss.argentwallet.extensions.hideKeyboard
import com.darrylbayliss.argentwallet.presentation.viewmodels.EC20TokensViewModelFactory
import com.darrylbayliss.argentwallet.presentation.viewmodels.ERC20TokensViewModel

class ERC20TokensFragment : Fragment() {

    companion object {
        fun newInstance() = ERC20TokensFragment()
    }

    private lateinit var viewModel: ERC20TokensViewModel

    private lateinit var binding: Erc20TokensFragmentBinding

    private lateinit var cursorAdapter: SimpleCursorAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.queryHint = getString(R.string.erc20_tokens_search_view_hint)
        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1

        searchView.setOnQueryTextListener(object : OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                hideKeyboard()
                query?.let {
                    viewModel.searchForToken(it)
                }

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {

                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    viewModel.possibleTokens.value?.forEachIndexed { index, suggestion ->
                        if (suggestion.symbol.contains(query, true)) {
                            cursor.addRow(arrayOf(index, suggestion.symbol))
                        }
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true
            }
        })

        searchView.setOnSuggestionListener(object : OnSuggestionListener {

            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)
                viewModel.searchForToken(selection)
                return true
            }
        })

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)

        cursorAdapter = SimpleCursorAdapter(
            requireContext(),
            R.layout.token_suggestion_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        searchView.suggestionsAdapter = cursorAdapter

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        binding = Erc20TokensFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = EC20TokensViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(ERC20TokensViewModel::class.java)

        viewModel.tokenBalance.observe(this, { tokenBalance ->

            binding.tokenBalanceTextView.text = getString(
                R.string.token_balance_string_format,
                tokenBalance.tokenSymbol,
                tokenBalance.value,
                tokenBalance.tokenSymbol
            )
        })

        viewModel.tokenBalanceError.observe(this, { error ->
            binding.tokenBalanceTextView.text = getString(R.string.no_token_found)
        })

        viewModel.requestPossibleTokens()
    }
}