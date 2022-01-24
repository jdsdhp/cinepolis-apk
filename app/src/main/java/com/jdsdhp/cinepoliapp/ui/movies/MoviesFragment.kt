package com.jdsdhp.cinepoliapp.ui.movies

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.databinding.FragmentMoviesBinding
import com.jdsdhp.cinepoliapp.ui.main.MainFragment
import com.jdsdhp.cinepoliapp.ui.main.MainFragmentDirections
import com.jdsdhp.cinepoliapp.ui.util.RecyclerDecoration
import com.jdsdhp.cinepoliapp.ui.util.getDimension
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private var _binding: FragmentMoviesBinding? = null

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        initUI()
        subscribeUI()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeUI() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when {
                    uiState.isLoading -> binding?.swipeLayout?.isRefreshing = true
                    !uiState.errorMessage.isNullOrBlank() ->
                        binding?.swipeLayout?.isRefreshing = false
                    uiState.isLoadSuccess -> binding?.swipeLayout?.isRefreshing = false
                }
            }
        }

        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
        lifecycleScope.launch {
            viewModel.paginateMovies().collectLatest {
                (binding?.recyclerView?.adapter as? MoviesAdapter)?.submitData(it)
            }
        }
    }

    private fun initUI() {
        binding?.run {
            swipeLayout.setOnRefreshListener { viewModel.fetchMovies() }
            recyclerView.run {
                setHasFixedSize(true)
                val columns =
                    if (resources.configuration.orientation == ORIENTATION_PORTRAIT) 3 else 5

                layoutManager = GridLayoutManager(context, columns)
                addItemDecoration(
                    RecyclerDecoration(
                        margin = requireContext().getDimension(R.dimen.dim_margin_small),
                        columns = columns,
                    )
                )
                adapter = MoviesAdapter {
                    val fragment = requireParentFragment().requireParentFragment()
                    if (fragment is MainFragment)
                        fragment.findNavController().navigate(
                            MainFragmentDirections.mainFragmentToMovieDetailFragment(it)
                        )
                }
            }
        }
    }

}