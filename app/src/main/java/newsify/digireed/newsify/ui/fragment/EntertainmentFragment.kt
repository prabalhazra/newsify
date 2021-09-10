package com.github.prabalhazra.newsify.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.prabalhazra.newsify.adapter.NewsAdapter
import com.github.prabalhazra.newsify.databinding.FragmentEntertainmentBinding
import com.github.prabalhazra.newsify.ui.MainActivity
import com.github.prabalhazra.newsify.util.Resource
import com.github.prabalhazra.newsify.viewModel.NewsViewModel

class EntertainmentFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    private var _binding: FragmentEntertainmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntertainmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        viewModel.getEntertainmentNews("in", "entertainment", 100)
        setupRecyclerView()

        viewModel.entertainmentNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { news ->
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(
                            requireContext(),
                            "An Error occurred : $message",
                            Toast.LENGTH_SHORT
                        ).show()
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
        binding.recycleEntertainment.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recycleEntertainment.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(requireContext())
        binding.recycleEntertainment.adapter = newsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}