package com.haystack.photosearch.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.haystack.photosearch.R
import com.haystack.photosearch.databinding.FragmentPhotoGalleryBinding
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {

    private val photoGalleryViewModel: PhotoGalleryViewModel by viewModels()
    private val photoListAdapter = PhotoListAdapter()
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        binding.photoGrid.layoutManager = GridLayoutManager(context, 3)
        binding.photoGrid.adapter = photoListAdapter

        val searchView = binding.root.findViewById<SearchView>(R.id.leftSearch)
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchQuery = query.orEmpty()
                Log.d(TAG,"onQueryTextSubmit: $searchQuery")
                photoGalleryViewModel.setQuery(searchQuery)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG,"onQueryTextChange: $newText")
                return false
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoGalleryViewModel.uiState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: PhotoGalleryUiState) {
        binding.loadingIndicator.isVisible = state is PhotoGalleryUiState.Loading
        when (state) {
            PhotoGalleryUiState.Loading -> {
                binding.searchQuery.text = getString(R.string.loading_photos)
            }
            is PhotoGalleryUiState.Content -> {
                photoListAdapter.submitPhotos(state.photos)
                binding.searchQuery.text = state.query?.let {
                    getString(R.string.search_results_for_query, it)
                } ?: getString(R.string.search_results_for_trending)
            }
            is PhotoGalleryUiState.Empty -> {
                photoListAdapter.submitPhotos(emptyList())
                binding.searchQuery.text = state.query?.let {
                    getString(R.string.no_results_for_query, it)
                } ?: getString(R.string.search_results_for_trending)
            }
            is PhotoGalleryUiState.Error -> {
                photoListAdapter.submitPhotos(emptyList())
                binding.searchQuery.text = state.message
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

