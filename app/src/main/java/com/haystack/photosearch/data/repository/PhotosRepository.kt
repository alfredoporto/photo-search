package com.haystack.photosearch.data.repository

import com.haystack.photosearch.domain.Photo

interface PhotosRepository {
    suspend fun fetchPhotos(query: String) : List<Photo>
}