package com.eduardoseguro.themoviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eduardoseguro.themoviedb.databinding.RowCategoryBinding
import com.eduardoseguro.themoviedb.model.MovieResponse

class MovieCategoryAdapter(
    private val baseItemList: MutableList<MovieResponse>,
    private val context: Context,
    private val openMovie: (Int) -> Unit
) : RecyclerView.Adapter<MovieCategoryAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return baseItemList.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val item = baseItemList[position]
        holder.bindView(item, context, openMovie)
    }

    class CategoryHolder(binding: RowCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        private val movieCategory = binding.textMovieCategory
        private val movieList = binding.movieList

        fun bindView(
            item: MovieResponse,
            context: Context,
            openMovie: (Int) -> Unit
        ) {
            movieCategory.text = item.category

            val adapter = MovieAdapter(
                item.movies,
                context,
                openMovie
            )
            movieList.adapter = adapter
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            movieList.layoutManager = layoutManager
        }
    }
}