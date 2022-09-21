package com.vifrn.mygamesreviews.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vifrn.mygamesreviews.R
import com.vifrn.mygamesreviews.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {

    lateinit var binding : FragmentReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)

        val args = ReviewFragmentArgs.fromBundle(requireArguments())
        binding.game = args.game

        return binding.root
    }
}