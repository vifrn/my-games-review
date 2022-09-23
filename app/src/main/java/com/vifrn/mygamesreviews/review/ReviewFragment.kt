package com.vifrn.mygamesreviews.review

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vifrn.mygamesreviews.R
import com.vifrn.mygamesreviews.databinding.FragmentReviewBinding
import com.vifrn.mygamesreviews.review.list.GameListViewModel

class ReviewFragment : Fragment() {

    lateinit var binding : FragmentReviewBinding
    lateinit var viewModel : ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)
        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val args = ReviewFragmentArgs.fromBundle(requireArguments())
        binding.game = args.game

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addRatingButton.setOnClickListener {
            viewModel.toggleCapturingShake()

            //MotionScene OnClick tag is not working in parallel with
            //Button onClick attribute, so I had to animate manually here.
            if (binding.motion.progress == 0.0f) {
                binding.motion.transitionToEnd()
            } else {
                binding.motion.transitionToStart()
            }

        }

        viewModel.setBaseInfo(binding.game!!)
        viewModel.shouldDisplayError.observe(viewLifecycleOwner) { displayError ->
            if(displayError) {
                Snackbar.make(binding.root, R.string.error_missing_review_fields, Snackbar.LENGTH_SHORT).show()
                viewModel.errorWasDisplayed()
            }
        }

        viewModel.gameUpdated.observe(viewLifecycleOwner) { updated ->
            if(updated) {
                findNavController().popBackStack()
                viewModel.doneNavigating()
            }
        }
    }
}