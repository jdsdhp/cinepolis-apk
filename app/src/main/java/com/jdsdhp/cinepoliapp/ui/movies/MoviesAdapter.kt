package com.jdsdhp.cinepoliapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.data.api.mappers.SizeType
import com.jdsdhp.cinepoliapp.data.database.model.Movie
import com.jdsdhp.cinepoliapp.data.database.model.ResourceType
import com.jdsdhp.cinepoliapp.data.database.model.getResourceUrl
import com.jdsdhp.cinepoliapp.databinding.ItemMovieBinding
import com.jdsdhp.cinepoliapp.ui.util.setSafeOnClickListener

class MoviesAdapter(
    private val onClickCallback: (Movie) -> Unit
) : PagingDataAdapter<Movie, MoviesAdapter.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setSafeOnClickListener {
                getItem(bindingAdapterPosition)?.let { it1 -> onClickCallback.invoke(it1) }
            }
        }

        fun bind(movie: Movie) = binding.run {
            titleView.text = movie.name
            val imageUrl: String? = movie.getResourceUrl(ResourceType.POSTER, SizeType.MEDIUM)
            binding.imageView.run {
                load(imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_movie_placeholder)
                }
            }
        }

    }

}
