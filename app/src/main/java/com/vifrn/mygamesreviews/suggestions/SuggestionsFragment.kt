package com.vifrn.mygamesreviews.suggestions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vifrn.mygamesreviews.R
import com.vifrn.mygamesreviews.databinding.FragmentSuggestionsBinding

class SuggestionsFragment : Fragment() {

    lateinit var binding : FragmentSuggestionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_suggestions, container, false)
        return binding.root
    }
}