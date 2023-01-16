package com.haystack.photosearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.VerticalGridPresenter
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class MainFragment : VerticalGridSupportFragment() {

    private val viewModel: PhotosViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = 3
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArrayObjectAdapter(CardPresenter())

        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("onViewCreated", "first time")
            viewModel.selectedImages.value?.map {
                (adapter as ArrayObjectAdapter).add(it)
            }
        }

        viewModel.selectedImages.observe(viewLifecycleOwner) { photos ->
            Log.d("MainFragment-Observer", "data changed")
            photos.map { (adapter as ArrayObjectAdapter).add(it) }
        }
    }
}


