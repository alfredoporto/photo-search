package com.haystack.photosearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haystack.photosearch.data.server.WebClient
import com.haystack.photosearch.data.server.model.toDomain
import com.haystack.photosearch.domain.Photo
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {
    private val mutableData = MutableLiveData<List<Photo>>()
    val selectedImages: LiveData<List<Photo>> get() = mutableData

    init {
        viewModelScope.launch {
            setImages(defaultPhotos())
        }
    }

    suspend fun fetchImages(searchTerm: String) {
        if (searchTerm.isBlank()) {
            setImages(defaultPhotos())
        } else {
            setImages(searchPhoto(searchTerm))
        }
    }

    private fun setImages(photos: List<Photo>) {
        mutableData.value = photos
    }

    private fun searchPhoto(searchQuery: String): List<Photo> {
        var result: List<Photo> = emptyList()

        viewModelScope.launch {
            result = WebClient.service.fetchImages(searchQuery).data.photos.map { it.toDomain() }
        }
        return result
    }

    private suspend fun defaultPhotos(): List<Photo> {
        return WebClient.service.fetchImages("trending").data.photos.map { it.toDomain() }
    }
}