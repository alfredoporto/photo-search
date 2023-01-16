package com.haystack.photosearch.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.haystack.photosearch.R
import com.haystack.photosearch.databinding.MyMainActivityBinding

class MyMainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: MyMainActivityBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val myAdapter: MyAdapter by lazy { MyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val person = Person("Ana", "Brown", 38)
//        mainViewModel.insertData(person)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = myAdapter

        myAdapter.setData(mainViewModel.readData)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        myAdapter.setData(mainViewModel.searchPhoto(searchQuery))
    }

}