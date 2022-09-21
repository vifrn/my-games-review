package com.vifrn.mygamesreviews.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.vifrn.mygamesreviews.R
import com.vifrn.mygamesreviews.databinding.FragmentDetailsBinding
import com.vifrn.mygamesreviews.model.Game

class DetailsFragment : Fragment() {

    private lateinit var binding : FragmentDetailsBinding
    private lateinit var game : Game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        val args = DetailsFragmentArgs.fromBundle(requireArguments())

        game = args.game
        binding.game = game

        binding.addReview.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToReviewFragment(game))
        }

        return binding.root
    }
}