package com.haystack.photosearch.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.haystack.photosearch.R
import com.haystack.photosearch.data.server.WebClient
import com.haystack.photosearch.data.server.model.toDomain
import com.haystack.photosearch.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBar = findViewById<SearchView>(R.id.leftSearch)
    }
}