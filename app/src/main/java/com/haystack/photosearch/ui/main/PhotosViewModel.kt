package com.haystack.photosearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haystack.photosearch.data.server.WebClient
import com.haystack.photosearch.data.server.model.toDomain
import com.haystack.photosearch.domain.Image
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {
    suspend fun fetchImages(searchTerm: String): List<Image> {
        if (searchTerm.isBlank()) {
            return defaultPhotos()
        }

        val searchResponse = WebClient.service.fetchImages(searchTerm)
        return searchResponse.data.photos.map { it.toDomain() }
    }

    private val mutableData = MutableLiveData<List<Image>>()
    val selectedImages: LiveData<List<Image>> get() = mutableData

    fun selectImages(images: List<Image>) {
        mutableData.value = images
    }

    init {
        viewModelScope.launch {
            selectImages(defaultPhotos())
        }
    }

    fun searchPhoto(searchQuery: String): List<Image> {
        var result : List<Image> = emptyList()

        viewModelScope.launch {
            result = WebClient.service.fetchImages(searchQuery).data.photos.map { it.toDomain() }
        }
        return result
    }

    private suspend fun defaultPhotos(): List<Image> {
        return WebClient.service.fetchImages("trending").data.photos.map { it.toDomain() }
    }
}