package com.github.prabalhazra.newsify.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.github.prabalhazra.newsify.adapter.NewsAdapter
import com.github.prabalhazra.newsify.databinding.FragmentSearchActivityBinding
import com.github.prabalhazra.newsify.util.Resource
import com.github.prabalhazra.newsify.viewModel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivityFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter

    private var _binding: FragmentSearchActivityBinding? = null

    private val binding get() = _binding!!

    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentSearchActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        var job: Job? = null
        binding.searchNews.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(400)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getSearchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, { response ->
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
        binding.searchNews.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleSearch.visibility = View.VISIBLE
        binding.animationView.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.searchNews.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE
        binding.recycleSearch.visibility = View.INVISIBLE
        binding.animationView.visibility = View.INVISIBLE
    }

    private fun showAnimation() {
        binding.searchNews.visibility = View.INVISIBLE
        binding.animationView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
        binding.recycleSearch.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(requireContext())
        binding.recycleSearch.adapter = newsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}