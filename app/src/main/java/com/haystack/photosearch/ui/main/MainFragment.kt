package com.haystack.photosearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import com.haystack.photosearch.R
import com.haystack.photosearch.data.server.WebClient
import com.haystack.photosearch.data.server.model.toDomain
import kotlinx.coroutines.launch


class MainFragment : VerticalGridSupportFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = 3
        //title = "Trending no"

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArrayObjectAdapter(CardPresenter())

        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("onViewCreated", "Alfredo")
            val searchResponse = WebClient.service.fetchImages("trending")
            searchResponse.data.photos.map {
                (adapter as ArrayObjectAdapter).add(it.toDomain())
            }

        }
    }
}

