package com.haystack.photosearch.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.haystack.photosearch.R
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity(), SearchView.OnQueryTextListener {
    private val viewModel: PhotosViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBar = findViewById<SearchView>(R.id.leftSearch)
        searchBar.setOnQueryTextListener(this)

        val searchDescription = findViewById<TextView>(R.id.search_description)
        /*

        val onQueryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchDescription.text = "Search Results for $p0"
                lifecycleScope.launch {
                    p0?.let { viewModel.fetchImages(it) }
                }
                return false
            }
        }

        searchBar.setOnQueryTextListener(onQueryTextListener)

         */

    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        val searchDescription = findViewById<TextView>(R.id.search_description)

        searchDescription.text = "Search Results for $p0"
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        lifecycleScope.launch {
            Log.d("SEARCH-VIEW-MODIFIED", "Searching ... $p0")
            p0?.let { viewModel.fetchImages(it) }
        }
        return false
    }

    // Set up search box

}
