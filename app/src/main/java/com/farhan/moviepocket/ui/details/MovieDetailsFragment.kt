package com.farhan.moviepocket.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.farhan.moviepocket.R
import com.farhan.moviepocket.app.Constants
import com.farhan.moviepocket.data.model.MovieDetails
import com.farhan.moviepocket.databinding.FragmentMovieDetailsBinding
import com.farhan.moviepocket.ui.details.state.MovieDetailsState
import com.farhan.moviepocket.ui.details.viewmodel.MovieDetailsViewModel
import com.farhan.moviepocket.utils.getErrorMessage
import com.farhan.moviepocket.utils.gone
import com.farhan.moviepocket.utils.toast
import com.farhan.moviepocket.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewModel.getMovieDetails(args.movieId)
        setUpObservers()
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    when (viewState) {
                        is MovieDetailsState.Error -> {
                            binding.progress.gone()
                            requireContext().toast(requireContext().getErrorMessage(viewState.message))
                        }

                        MovieDetailsState.Loading -> {
                            binding.progress.visible()
                        }

                        is MovieDetailsState.Success -> {
                            binding.progress.gone()
                            populateData(viewState.data)
                        }
                    }
                }
            }
        }
    }

    private fun populateData(data: MovieDetails) {
        binding.apply {

            val posterUrl = "${Constants.TMDB_POSTER_PATH_PREFIX}${data.posterPath}"
            val backDrop = "${Constants.TMDB_BACKDROP_PATH_PREFIX}${data.backdropPath}"

            Glide.with(imgBackDrop)
                .load(backDrop)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_thumb)
                .into(imgBackDrop)

            Glide.with(imgPoster)
                .load(posterUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_poster)
                .into(imgPoster)

            tvTitle.text = data.title
            tvPlot.text = data.description

            tvGenre.text = data.genres
            tvRuntime.text = data.runtime

            val rating = data.rating?.div(2)
            rbRating.rating = rating?.toFloat() ?: 0F

            val ratingText = "${rating?.toString()?.take(4) ?: "N/A"}/5"
            tvRatingText.text = ratingText
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}