package com.eduardoseguro.themoviedb.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduardoseguro.themoviedb.R
import com.eduardoseguro.themoviedb.adapter.MovieCategoryAdapter
import com.eduardoseguro.themoviedb.api.Status
import com.eduardoseguro.themoviedb.databinding.FragmentMovieListBinding
import com.eduardoseguro.themoviedb.model.MovieResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieListViewModel by viewModel()
    private val navController: NavController by lazy { findNavController() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.getMovieList()
    }

    private fun setupObservers() {
        viewModel.resourceLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    showSuccess(it.data)
                }

                Status.ERROR -> {
                    showError(it.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressLoading.isVisible = true
        binding.txtMessage.isVisible = false
        binding.listCategories.isVisible = false
        binding.txtReload.isVisible = false
    }

    private fun showError(message: String?) {
        binding.progressLoading.isVisible = false
        binding.txtMessage.text = getString(R.string.error_text, message)
        binding.txtMessage.isVisible = true
        binding.txtReload.isVisible = false
        binding.listCategories.isVisible = false
    }

    private fun showEmptyState() {
        binding.progressLoading.isVisible = false
        binding.txtMessage.text = getString(R.string.empty_data)
        binding.txtMessage.isVisible = true
        binding.txtReload.isVisible = true
        binding.listCategories.isVisible = false
    }

    private fun showSuccess(movieList: MutableList<MovieResponse>?) {
        binding.progressLoading.isVisible = false
        binding.txtMessage.isVisible = false
        binding.listCategories.isVisible = true
        binding.txtReload.isVisible = false
        if (movieList.isNullOrEmpty()) {
            showEmptyState()
        } else {
            fillMovieData(movieList)
        }
    }

    private fun openMovieDetail(movieId: Int) {
        val action = MovieListFragmentDirections.movieListFragmentToMovieDetailFragment(movieId)
        navController.navigate(action)
    }

    private fun fillMovieData(movieList: MutableList<MovieResponse>) {
        val adapter = MovieCategoryAdapter(movieList, requireContext(), this@MovieListFragment::openMovieDetail)
        binding.listCategories.adapter = adapter
        binding.listCategories.layoutManager = LinearLayoutManager(requireContext())
    }
}