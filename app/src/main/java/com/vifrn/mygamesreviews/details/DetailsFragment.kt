package com.vifrn.mygamesreviews.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vifrn.mygamesreviews.R

class DetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(requireContext(), args.game.name, Toast.LENGTH_LONG).show()

        return inflater.inflate(R.layout.fragment_details, container, false)
    }
}