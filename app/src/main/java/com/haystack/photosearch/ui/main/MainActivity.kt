package com.haystack.photosearch.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.lifecycle.lifecycleScope
import com.haystack.photosearch.R
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity(), SearchView.OnQueryTextListener {
    private val sf = supportFragmentManager

    private val addFragment: (FragmentManager, Fragment, Int) -> Unit =
        { fm,  fragment, containerId ->
            fm.beginTransaction()
                .add(containerId, fragment)
                .commit()
        }

    val initFragment: (FragmentManager, Fragment, Int) -> Unit =
        {fm,  fragment, containerId ->
            val currentFragment =
                fm.findFragmentById(containerId)

            if (currentFragment == null)
                addFragment(fm, fragment, containerId)
        }

    private val viewModel: PhotoGalleryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val searchView = findViewById<SearchView>(R.id.leftSearch)

        //searchView.setOnQueryTextListener(this)
        initPhotoGalleryFragment()
    }

    private fun initPhotoGalleryFragment() =
        initFragment(sf, PhotoGalleryFragment(), R.id.fragment_container)

    companion object {

        fun newIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        val adapter = ArrayObjectAdapter(CardPresenter())
        //val searchDescription = findViewById<TextView>(R.id.search_description)

        //searchDescription.text = "Search Results for $p0"
        lifecycleScope.launch {
            Log.d("SEARCH-VIEW-MODIFIED", "Searching ... $p0")
            p0?.let {
                viewModel.setQuery(it)
            }
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }
}
