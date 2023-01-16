package com.haystack.photosearch.ui.main

import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.CursorObjectAdapter
import androidx.leanback.widget.ObjectAdapter
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.haystack.photosearch.BuildConfig

class SearchFragment : SearchSupportFragment(),
    SearchSupportFragment.SearchResultProvider, LoaderManager.LoaderCallbacks<Cursor> {

    private val TAG = "SearchFragment"
    private val DEBUG: Boolean = BuildConfig.DEBUG
    private val FINISH_ON_RECOGNIZER_CANCELED = true
    private val REQUEST_SPEECH = 0x00000010

    private val mHandler = Handler()
    private val mRowsAdapter: ArrayObjectAdapter? = null
    private val mQuery: String? = null
    private val mVideoCursorAdapter = CursorObjectAdapter(CardPresenter())

    private val mSearchLoaderId = 1
    private val mResultsFound = false



    override fun getResultsAdapter(): ObjectAdapter {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newQuery: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }


}