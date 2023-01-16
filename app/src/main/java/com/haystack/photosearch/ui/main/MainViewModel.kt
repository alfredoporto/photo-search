package com.haystack.photosearch.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haystack.photosearch.data.server.WebClient
import com.haystack.photosearch.data.server.model.toDomain
import com.haystack.photosearch.domain.Image
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var readData : List<Image> = emptyList()

    init {
        viewModelScope.launch {
            readData = WebClient.service.fetchImages("trending").data.photos.map { it.toDomain() }
        }
    }

    fun searchPhoto(searchQuery: String): List<Image> {
        var result : List<Image> = emptyList()

        viewModelScope.launch {
            result = WebClient.service.fetchImages(searchQuery).data.photos.map { it.toDomain() }
        }
        return result
    }
}