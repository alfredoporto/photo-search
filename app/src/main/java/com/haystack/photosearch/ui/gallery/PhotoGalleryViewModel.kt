package com.haystack.photosearch.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haystack.photosearch.data.repository.FlickrRepository
import com.haystack.photosearch.data.repository.PhotosRepository
import com.haystack.photosearch.domain.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class PhotoGalleryUiState {
    object Loading : PhotoGalleryUiState()
    data class Content(val photos: List<Photo>, val query: String?) : PhotoGalleryUiState()
    data class Empty(val query: String?) : PhotoGalleryUiState()
    data class Error(val message: String, val query: String?) : PhotoGalleryUiState()
}

class PhotoGalleryViewModel(
    private val photoRepository: PhotosRepository = FlickrRepository()
) : ViewModel() {
    private val _uiState: MutableStateFlow<PhotoGalleryUiState> =
        MutableStateFlow(PhotoGalleryUiState.Loading)
    val uiState: StateFlow<PhotoGalleryUiState>
        get() = _uiState.asStateFlow()

    init {
        loadPhotos(query = null)
    }

    fun setQuery(query: String) {
        loadPhotos(query.trim().ifEmpty { null })
    }

    private fun loadPhotos(query: String?) {
        viewModelScope.launch {
            _uiState.value = PhotoGalleryUiState.Loading
            _uiState.value = runCatching {
                fetchGalleryPhotos(query)
            }.fold(
                onSuccess = { photos ->
                    when (photos.isEmpty()) {
                        true -> PhotoGalleryUiState.Empty(query)
                        false -> PhotoGalleryUiState.Content(photos, query)
                    }
                },
                onFailure = {
                    PhotoGalleryUiState.Error(
                        "Unable to load photos. Check the Flickr API key and network connection.",
                        query
                    )
                }
            )
        }
    }

    private suspend fun fetchGalleryPhotos(query: String?) = when (query.isNullOrEmpty()) {
        true -> photoRepository.fetchPhotos()
        else -> photoRepository.searchPhotos(query)
    }
}
