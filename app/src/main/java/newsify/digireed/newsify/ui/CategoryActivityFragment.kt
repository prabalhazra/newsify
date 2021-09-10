package com.github.prabalhazra.newsify.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.github.prabalhazra.newsify.R
import com.github.prabalhazra.newsify.databinding.FragmentCategoryActivityBinding

class CategoryActivityFragment : Fragment() {

    private var _binding: FragmentCategoryActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategoryActivityBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        binding.healthCategory.setOnClickListener {
            navController.navigate(R.id.action_categoryActivityFragment_to_healthFragment)
        }

        binding.businessCategory.setOnClickListener {
            navController.navigate(R.id.action_categoryActivityFragment_to_businessFragment)
        }

        binding.entertainmentCategory.setOnClickListener {
            navController.navigate(R.id.action_categoryActivityFragment_to_entertainmentFragment)
        }

        binding.sportsCategory.setOnClickListener {
            navController.navigate(R.id.action_categoryActivityFragment_to_sportsFragment)
        }

        binding.technologyCategory.setOnClickListener {
            navController.navigate(R.id.action_categoryActivityFragment_to_technologyFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}