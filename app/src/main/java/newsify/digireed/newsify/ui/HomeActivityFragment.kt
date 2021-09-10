package com.github.prabalhazra.newsify.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.prabalhazra.newsify.adapter.NewsAdapter
import com.github.prabalhazra.newsify.databinding.FragmentHomeActivityBinding
import com.github.prabalhazra.newsify.util.Resource
import com.github.prabalhazra.newsify.viewModel.NewsViewModel

class HomeActivityFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter

    private var _binding: FragmentHomeActivityBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        viewModel.getHomeNews("in", 100)

        binding.swipeRefresh.setOnRefreshListener {
            onRefresh()
        }
        onRefresh()
    }
    private fun onRefresh() {
        setupRecyclerView()

        viewModel.homeNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { news ->
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    showAnimation()
                    response.message?.let { message ->
                        Toast.makeText(
                            activity,
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
        binding.swipeRefresh.isRefreshing = false
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleHome.visibility = View.VISIBLE
        binding.animationView.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.swipeRefresh.isRefreshing = true
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleHome.visibility = View.INVISIBLE
        binding.animationView.visibility = View.INVISIBLE
    }

    private fun showAnimation() {
        binding.swipeRefresh.isRefreshing = false
        binding.animationView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleHome.visibility = View.INVISIBLE
    }
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(requireContext())
        binding.recycleHome.adapter = newsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}