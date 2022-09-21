package com.vifrn.mygamesreviews.review.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vifrn.mygamesreviews.R
import com.vifrn.mygamesreviews.suggestions.GameClickListener
import com.vifrn.mygamesreviews.suggestions.GameListAdapter
import com.vifrn.mygamesreviews.suggestions.SuggestionsFragmentDirections
import com.vifrn.mygamesreviews.suggestions.SuggestionsViewModel

class ReviewsListFragment : Fragment() {

    lateinit var viewModel : GameListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.my_reviews_list)

        val adapter = GameListAdapter(GameClickListener { game ->
            findNavController().navigate(ReviewsListFragmentDirections.actionMyReviewsListFragmentToDetailsFragment(game))
        })
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(GameListViewModel::class.java)
        viewModel.myReviews.observe(viewLifecycleOwner) { games ->
            games?.let {
                adapter.submitList(games)
            }
        }
    }

}