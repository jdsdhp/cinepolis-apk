package com.jdsdhp.cinepoliapp.ui.movies.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.data.api.mappers.SizeType
import com.jdsdhp.cinepoliapp.data.database.model.ResourceType
import com.jdsdhp.cinepoliapp.data.database.model.getResourceUrl
import com.jdsdhp.cinepoliapp.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private var _binding: FragmentMovieDetailBinding? = null

    private val binding get() = _binding!!

    private lateinit var player: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
        _binding = null
    }

    private fun initUI() {
        binding.run {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            viewModel.movie.run {


                titleView.text = name
                durationView.text = length
                genreView.text = genre
                classificationView.text = rating
                synopsisView.text = synopsis

                getResourceUrl(ResourceType.TRAILER, SizeType.MEDIUM)
                    ?.let { initializePlayer(it) }

                posterView.load(
                    this.getResourceUrl(
                        ResourceType.POSTER,
                        SizeType.MEDIUM,
                    )
                ) {
                    crossfade(true)
                    placeholder(R.drawable.ic_movie_placeholder)
                }
                posterHorizontalView.load(
                    this.getResourceUrl(
                        ResourceType.POSTER_HORIZONTAL,
                        SizeType.MEDIUM,
                    )
                ) {
                    crossfade(true)
                    placeholder(R.drawable.ic_movie_placeholder)
                }
                backgroundSynopsisView.load(
                    this.getResourceUrl(
                        ResourceType.BACKGROUND_SYNOPSIS,
                        SizeType.MEDIUM,
                    )
                ) {
                    crossfade(true)
                    placeholder(R.drawable.ic_movie_placeholder)
                }
            }
        }
    }

    private fun initializePlayer(videoUri: String) {
        // Instantiate the player.
        player = ExoPlayer.Builder(requireContext()).build()
        // Attach player to the view.
        binding.playerView.player = player
        // Set the media item to be played.
        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
        player.play()
    }

}