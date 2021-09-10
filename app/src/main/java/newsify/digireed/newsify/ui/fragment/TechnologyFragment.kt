package com.github.prabalhazra.newsify.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.prabalhazra.newsify.adapter.NewsAdapter
import com.github.prabalhazra.newsify.databinding.FragmentTechnologyBinding
import com.github.prabalhazra.newsify.ui.MainActivity
import com.github.prabalhazra.newsify.util.Resource
import com.github.prabalhazra.newsify.viewModel.NewsViewModel

class TechnologyFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    private var _binding: FragmentTechnologyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTechnologyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        viewModel.getTechnologyNews("in", "technology", 100)
        setupRecyclerView()

        viewModel.technologyNews.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { news ->
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(requireContext(), "An Error occurred : $message", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleTechnology.visibility = View.VISIBLE
    }
    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recycleTechnology.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(requireContext())
        binding.recycleTechnology.adapter = newsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}