package com.eduardoseguro.themoviedb.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.eduardoseguro.themoviedb.ConstantsAPI
import com.eduardoseguro.themoviedb.api.Status
import com.eduardoseguro.themoviedb.databinding.FragmentMovieDetailsBinding
import com.eduardoseguro.themoviedb.model.MovieDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModel()
    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.getMovieDetail(args.movieId)
        binding.imageDetailsPoster.clipToOutline = true
        binding.imageDetailsBackArrow.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun setupObservers() {
        viewModel.resourceLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    //showLoading()
                }

                Status.SUCCESS -> {
                    showSuccess(it.data)
                }

                Status.ERROR -> {
                    //showError(it.message)
                }
            }
        }
    }

    private fun showSuccess(movieDetail: MovieDetail?) {
        if (movieDetail == null || movieDetail.id == 0) {

        } else {
            binding.textDetailsMovieTitle.text = movieDetail.title
            val voteAverage = (movieDetail.voteAverage * 10).toInt()
            binding.textDetailsMovieInfo.text = "${movieDetail.releaseDate} - ${movieDetail.runtime} minutes - $voteAverage%"
            binding.textDetailsOverview.text = movieDetail.overview

            Glide.with(this)
                .load(ConstantsAPI.IMAGE_BACKDROP_URL + movieDetail.backdropPath)
                .into(binding.imageDetailsBackdrop)

            Glide.with(this)
                .load(ConstantsAPI.IMAGES_POSTER_URL + movieDetail.posterPath)
                .into(binding.imageDetailsPoster)
        }
    }
}