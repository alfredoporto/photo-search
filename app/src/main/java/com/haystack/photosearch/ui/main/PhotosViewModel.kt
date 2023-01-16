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
    private val mutableData = MutableLiveData<List<Image>>()
    val selectedImages: LiveData<List<Image>> get() = mutableData

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

    private fun setImages(images: List<Image>) {
        mutableData.value = images
    }

    private fun searchPhoto(searchQuery: String): List<Image> {
        var result: List<Image> = emptyList()

        viewModelScope.launch {
            result = WebClient.service.fetchImages(searchQuery).data.photos.map { it.toDomain() }
        }
        return result
    }

    private suspend fun defaultPhotos(): List<Image> {
        return WebClient.service.fetchImages("trending").data.photos.map { it.toDomain() }
    }
}