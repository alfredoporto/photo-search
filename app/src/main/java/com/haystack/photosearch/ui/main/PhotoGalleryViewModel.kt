package com.haystack.photosearch.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haystack.photosearch.data.repository.FlickrRepository
import com.haystack.photosearch.domain.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryViewModel"
class PhotoGalleryViewModel : ViewModel() {
    private val photoRepository = FlickrRepository()

    private val _galleryPhotos: MutableStateFlow<List<Photo>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<Photo>>
        get() = _galleryPhotos.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val photos = photoRepository.fetchPhotos()
                _galleryPhotos.value = photos
                //setImages(defaultPhotos())
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch gallery photos", ex)
            }
        }
    }

    // for listeners
    fun setQuery(query: String) {
        viewModelScope.launch { _galleryPhotos.value = fetchGalleryPhotos(query) }
    }

    private suspend fun fetchGalleryPhotos(query: String) = when (query.isNotEmpty()) {
        true -> photoRepository.searchPhotos(query)
        else -> photoRepository.fetchPhotos()
    }

    //gaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

    private val mutableData = MutableLiveData<List<Photo>>()
    val selectedImages: LiveData<List<Photo>> get() = mutableData

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
            result = FlickrRepository().searchPhotos(searchQuery)
        }
        return result
    }

    private suspend fun defaultPhotos(): List<Photo> {
        return FlickrRepository().searchPhotos("trending")
    }
}