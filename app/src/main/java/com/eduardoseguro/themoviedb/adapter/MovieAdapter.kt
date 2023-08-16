package com.eduardoseguro.themoviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eduardoseguro.themoviedb.ConstantsAPI
import com.eduardoseguro.themoviedb.databinding.RowMovieBinding
import com.eduardoseguro.themoviedb.model.Movie

class MovieAdapter(
    private val baseItemList: List<Movie>,
    private val context: Context,
    private val openMovie: (Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return baseItemList.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = baseItemList[position]
        holder.bindView(item, context, openMovie)
    }

    class MovieHolder(binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        private val movieTitle = binding.textMovieTitle
        private val moviePoster = binding.imageMoviePoster

        fun bindView(
            item: Movie,
            context: Context,
            openMovie: (Int) -> Unit
        ) {

            movieTitle.text = item.title

            Glide.with(context)
                .load(ConstantsAPI.IMAGES_POSTER_URL + item.posterPath)
                .into(moviePoster)

            moviePoster.clipToOutline = true

            itemView.setOnClickListener {
                openMovie(item.id)
            }
        }
    }
}