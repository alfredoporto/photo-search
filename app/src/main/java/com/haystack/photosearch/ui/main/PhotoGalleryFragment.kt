package com.haystack.photosearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
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

        val searchView = binding.root.findViewById<SearchView>(R.id.leftSearch)
        val searchText = binding.root.findViewById<TextView>(R.id.search_query)
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG,"onQueryTextSubmit: $query")
                photoGalleryViewModel.setQuery(query ?: "")
                searchText.text = "Search Results for $query"
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
                photoGalleryViewModel.galleryItems.collect { items ->
                    Log.d(TAG, "Response received: $items")
                    binding.photoGrid.adapter = PhotoListAdapter(items)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


