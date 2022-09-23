package com.vifrn.mygamesreviews.suggestions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vifrn.mygamesreviews.R
import com.vifrn.mygamesreviews.databinding.FragmentSuggestionsBinding
import com.vifrn.mygamesreviews.network.TokenStatus

class SuggestionsFragment : Fragment() {

    lateinit var binding : FragmentSuggestionsBinding
    lateinit var viewModel : SuggestionsViewModel

    companion object {
        private const val TAG = "SuggestionsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_suggestions, container, false)
        viewModel = ViewModelProvider(this).get(SuggestionsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GameListAdapter(GameClickListener { game ->
            findNavController().navigate(SuggestionsFragmentDirections.actionSuggestionsFragmentToDetailsFragment(game))
        })
        binding.suggestionsList.adapter = adapter

        viewModel.suggestions.observe(viewLifecycleOwner) { games ->
            if(!viewModel.showSearchResults) {
                games?.let {
                    adapter.submitList(games)
                }
            }
        }

        viewModel.searchedGames.observe(viewLifecycleOwner) { games ->
            if(viewModel.showSearchResults) {
                games?.let {
                    adapter.submitList(games)
                }
            }
        }

        viewModel.newToken.observe(viewLifecycleOwner) { status ->
            when (status) {
                TokenStatus.READY -> {
                    viewModel.getSuggestions()
                    viewModel.clearTokenStatus()
                }
                TokenStatus.IDLE, TokenStatus.REQUESTING -> {
                    //Ignore
                }
                else -> {
                    Log.e(TAG, "Error when fetching a new token")
                }
            }
        }

        binding.searchButton.setOnClickListener {
            if(!viewModel.showSearchResults) {
                val inputName = binding.searchBar.text.toString()
                if(!inputName.isNullOrEmpty()) {
                    viewModel.searchGame(inputName)
                    updateSearchButton()
                }
            } else {
                viewModel.searchCancelled()
                binding.searchBar.setText("")
                adapter.submitList(viewModel.suggestions.value)
                updateSearchButton()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateSearchButton()
    }

    fun updateSearchButton () {
        if(viewModel.showSearchResults) {
            binding.searchButton.setBackgroundColor(requireContext().getColor(R.color.red_search_cancel))
            binding.searchButton.setImageResource(R.drawable.ic_close)
        } else {
            binding.searchButton.setBackgroundColor(requireContext().getColor(R.color.search_button_background_color))
            binding.searchButton.setImageResource(R.drawable.ic_search)
        }

    }
}