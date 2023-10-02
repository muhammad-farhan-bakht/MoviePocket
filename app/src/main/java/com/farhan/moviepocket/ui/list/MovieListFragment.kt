package com.farhan.moviepocket.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.farhan.moviepocket.R
import com.farhan.moviepocket.data.model.Movie
import com.farhan.moviepocket.databinding.FragmentMovieListBinding
import com.farhan.moviepocket.ui.list.adapter.MovieListAdapter
import com.farhan.moviepocket.ui.list.state.MovieListViewState
import com.farhan.moviepocket.ui.list.viewmodel.MovieListViewModel
import com.farhan.moviepocket.utils.getErrorMessage
import com.farhan.moviepocket.utils.gone
import com.farhan.moviepocket.utils.hideKeyboard
import com.farhan.moviepocket.utils.toast
import com.farhan.moviepocket.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener{

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var searchView: SearchView
    private val listAdapter by lazy {
        MovieListAdapter {
            navigate(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setUpToolBarMenu()
        setUpObservers()
        setUpListAdapter()
    }

    private fun setUpToolBarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    when (viewState) {
                        is MovieListViewState.Error -> {
                            binding.progress.gone()
                            requireContext().toast(requireContext().getErrorMessage(viewState.message))
                            showEmptyListError()
                        }

                        MovieListViewState.Loading -> {
                            binding.progress.visible()
                        }

                        is MovieListViewState.Success -> {
                            binding.progress.gone()
                            submitList(viewState.movies)
                            hideEmptyListError()
                        }
                    }
                }
            }
        }
    }

    private fun setUpListAdapter() {
        binding.rvMovieList.apply {
            adapter = listAdapter
        }
    }

    private fun showEmptyListError() {
        if (listAdapter.currentList.isEmpty()) {
            binding.tvEmptyListError.visible()
        }
    }

    private fun hideEmptyListError() {
        if (listAdapter.currentList.isNotEmpty()) {
            binding.tvEmptyListError.gone()
        }
    }

    private fun submitList(movies: List<Movie>) {
        listAdapter.setFilterList(movies)
        listAdapter.submitList(movies)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun navigate(movieId: Long) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.movie_list_toolbar_menus, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this@MovieListFragment)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_search -> {
                true
            }
            else -> false
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.hideKeyboard()
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            listAdapter.filter.filter(query)
        }
        return true
    }
}