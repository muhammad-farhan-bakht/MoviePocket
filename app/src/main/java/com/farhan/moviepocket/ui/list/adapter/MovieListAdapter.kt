package com.farhan.moviepocket.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.farhan.moviepocket.R
import com.farhan.moviepocket.app.Constants.TMDB_BACKDROP_PATH_PREFIX
import com.farhan.moviepocket.data.model.Movie
import com.farhan.moviepocket.databinding.ItemViewMovieListBinding
import jp.wasabeef.glide.transformations.BlurTransformation
import timber.log.Timber

class MovieListAdapter(private val onItemClick: (Long) -> Unit) :
    ListAdapter<Movie, MovieListAdapter.MovieListViewHolder>(DIFF_CALLBACK),
    Filterable {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var filteredList: ArrayList<Movie> = ArrayList()
    private var backUpList: ArrayList<Movie> = ArrayList()

    fun setFilterList(list: List<Movie>) {
        filteredList.clear()
        filteredList.addAll(list)
        backUpList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }

    class MovieListViewHolder private constructor(private val binding: ItemViewMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie, onItemClick: (Long) -> Unit) {
            binding.apply {
                root.setOnClickListener {
                    onItemClick(item.id)
                }

                val bannerPath = "$TMDB_BACKDROP_PATH_PREFIX${item.backdropPath}"

                tvTitle.text = item.title
                val rating = item.rating.div(2).toString()
                val ratingText = "$rating/5"
                rbRating.rating = rating.toFloat()
                tvRatingText.text = ratingText

                Glide.with(imgBanner)
                    .load(bannerPath)
                    .placeholder(R.drawable.no_thumb)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(100)))
                    .into(imgBanner)

                Glide.with(imgMainBanner)
                    .load(bannerPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.no_thumb)
                    .into(imgMainBanner)
            }
        }

        companion object {
            fun create(parent: ViewGroup): MovieListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewMovieListBinding.inflate(layoutInflater, parent, false)
                return MovieListViewHolder(binding)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val queryString = constraint?.toString() ?: ""

                val filteredDataList = if (queryString.isEmpty()) {
                    arrayListOf()
                } else {
                    backUpList.filter { item ->
                        item.title.contains(queryString, ignoreCase = true)
                    }
                }

                Timber.d("performFiltering text ->  $queryString , filteredDataList $filteredDataList")

                filterResults.values = filteredDataList
                filterResults.count = filteredDataList.size

                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence,
                results: FilterResults
            ) {
                filteredList = if (results.values == null) {
                    arrayListOf()
                } else {
                    @Suppress("UNCHECKED_CAST")
                    results.values as ArrayList<Movie>
                }

                if (filteredList.isEmpty()) {
                    submitList(null)
                    filteredList = backUpList
                    submitList(filteredList)
                } else {
                    submitList(filteredList)
                }
            }
        }
    }
}